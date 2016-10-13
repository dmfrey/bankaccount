package io.pivotal.bankaccount;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.aggregator.AbstractAggregatingMessageGroupProcessor;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.core.Pollers;
import org.springframework.integration.http.config.EnableIntegrationGraphController;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.integration.store.MessageGroup;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;

import io.pivotal.bankaccount.domain.model.AccountHistory;
import io.pivotal.bankaccount.event.account.AccountBalanceDetailsEvent;
import io.pivotal.bankaccount.event.account.AccountBalanceUpdatedEvent;
import io.pivotal.bankaccount.event.account.AccountCreatedEvent;
import io.pivotal.bankaccount.event.account.AccountDetailsEvent;
import io.pivotal.bankaccount.event.account.AccountHistoryDetailsEvent;
import io.pivotal.bankaccount.event.account.CreateAccountEvent;
import io.pivotal.bankaccount.event.account.CreateAccountHistoryEvent;
import io.pivotal.bankaccount.event.account.RequestAccountBalanceDetailsEvent;
import io.pivotal.bankaccount.event.account.UpdaateAccountBalanceEvent;
import io.pivotal.bankaccount.event.account.RequestAccountDetailsEvent;
import io.pivotal.bankaccount.event.account.RequestAccountHistoryDetailsEvent;
import io.pivotal.bankaccount.event.account.TransferFundsEvent;
import io.pivotal.bankaccount.persistence.service.AccountBalancePersistenceService;
import io.pivotal.bankaccount.persistence.service.AccountHistoryPersistenceService;
import io.pivotal.bankaccount.persistence.service.AccountPersistenceService;

@Configuration
@EnableIntegrationGraphController
@IntegrationComponentScan( basePackages = { "io.pivotal.bankaccount.domain.service" } )
public class IntegrationConfig {

	private static final Logger log = LoggerFactory.getLogger( IntegrationConfig.class );
	
	@Autowired
	AccountPersistenceService accountService;
	
	@Autowired
	private AccountHistoryPersistenceService accountHistoryService;
	
	@Autowired
	private AccountBalancePersistenceService accountBalanceService;
	
	@Bean( name = PollerMetadata.DEFAULT_POLLER )
	public PollerMetadata poller() {
	
		return Pollers.fixedDelay( 1000 ).get();
	}
	
	@Bean
	public IntegrationFlow createAccount() {
		
		return IntegrationFlows.from( "createAccountChannel" )
				.channel( c -> c.executor( Executors.newCachedThreadPool() ) )
				.<CreateAccountEvent>handle( (p, h) -> accountService.requestCreateAccount( p ) )
				.<AccountCreatedEvent, Boolean> route( AccountCreatedEvent::isCreated, mapping -> mapping
					.subFlowMapping( "true", sf -> sf
						.channel( c -> c.queue( 10 ) )
						.publishSubscribeChannel( c -> c
							.applySequence( true )
							.subscribe( s -> s
								.<AccountCreatedEvent>handle( (p, h) -> p )
								.channel( "accountCreatedAggregateFlow.input" )
							)
							.subscribe( s -> s
								.<AccountCreatedEvent, UpdaateAccountBalanceEvent>transform( event -> 
									new UpdaateAccountBalanceEvent( event.getJobId(), event.getAccount().getAccountNumber(), event.getAmount() )
								)
								.<UpdaateAccountBalanceEvent>handle( (p, h) -> accountBalanceService.updateBalance( p ) ) 
								.channel( "accountCreatedAggregateFlow.input" )
							) 
							.subscribe( s -> s
								.<AccountCreatedEvent, CreateAccountHistoryEvent>transform( event -> {
													
									AccountHistory accountHistory = new AccountHistory();
									accountHistory.setJobId( event.getJobId() );
									accountHistory.setAccountNumber( event.getAccount().getAccountNumber() );
									accountHistory.setAmount( event.getAmount() );
									accountHistory.setDateCreated( System.nanoTime() );
														
									return new CreateAccountHistoryEvent( event.getJobId(), accountHistory );
								})
								.<CreateAccountHistoryEvent>handle( (p, h) -> accountHistoryService.requestCreateAccountHistory( p ) ) 
								.channel( "accountCreatedAggregateFlow.input" )
							) 
						) 
					) 
					.subFlowMapping( "false", sf -> sf
						.handle( event -> log.error( "Account not created! " + event.toString() ) )
					)
				)
				.get();
	}
	
	@Bean
	public IntegrationFlow accountCreatedAggregateFlow() {
		
		return flow -> flow
						.aggregate( a -> a.outputProcessor( new AbstractAggregatingMessageGroupProcessor() {

							@Override
							protected Object aggregatePayloads( MessageGroup group, Map<String, Object> defaultHeaders ) {
							
								Collection<Message<?>> messages = group.getMessages();
								Assert.notEmpty( messages, this.getClass().getSimpleName() + " cannot process empty message groups" );
								
								AccountCreatedEvent accountCreatedEvent = null;
								for( Message<?> message : messages ) {
									
									if( message.getPayload() instanceof AccountCreatedEvent ) {
										accountCreatedEvent = (AccountCreatedEvent) message.getPayload();
									}
									
									if( message.getPayload() instanceof AccountBalanceUpdatedEvent ) {
										if( null != accountCreatedEvent ) {
											AccountBalanceUpdatedEvent event = (AccountBalanceUpdatedEvent) message.getPayload();
											if( event.isUpdated() ) {
												accountCreatedEvent.setAmount( event.getAmount() );
											}
										}
									}

								}

								return accountCreatedEvent;
							}
							
						}))
						.channel( c -> c.queue( "accountCreatedAggregateResult" ) );
	}

	@Bean
	public IntegrationFlow accountBalance() {
		
		return IntegrationFlows.from( "accountDetailsChannel" )
				.channel( c -> c.executor( Executors.newCachedThreadPool() ) )
				.<RequestAccountDetailsEvent>handle( (p, h) -> accountService.requestAccountDetails( p ) )
				.<AccountDetailsEvent, Boolean> route( AccountDetailsEvent::isFound, mapping -> mapping
					.subFlowMapping( "true", sf -> sf
						.channel( c -> c.queue( 10 ) )
						.publishSubscribeChannel( c -> c
							.applySequence( true )
							.subscribe( s -> s
								.<AccountDetailsEvent>handle( (p1, h1 ) -> p1 )
								.channel( "accountDetailsAggregateFlow.input" )
							)
							.subscribe( s -> s
								.<AccountDetailsEvent, RequestAccountBalanceDetailsEvent>transform( event1 -> new RequestAccountBalanceDetailsEvent( event1.getAccount().getAccountNumber() )	)
								.<RequestAccountBalanceDetailsEvent>handle( (p1, h1 ) -> accountBalanceService.getBalance( p1 ) )
								.channel( "accountDetailsAggregateFlow.input" )
							)
							.subscribe( s -> s
								.<AccountDetailsEvent, RequestAccountHistoryDetailsEvent>transform( event1 -> new RequestAccountHistoryDetailsEvent( event1.getAccount().getAccountNumber() )	)
								.<RequestAccountHistoryDetailsEvent>handle( (p1, h1 ) -> accountHistoryService.getAccountHistory( p1 ) )
								.channel( "accountDetailsAggregateFlow.input" )
							)
						)
					)
					.subFlowMapping( "false", sf -> sf
						.handle( event -> log.error( "Account details not found! " + event.toString() ) )
					)		
				)
				.get();
	}
	
	@Bean
	public IntegrationFlow accountDetailsAggregateFlow() {
		
		return flow -> flow
						.aggregate( a -> a.outputProcessor( new AbstractAggregatingMessageGroupProcessor() {

							@Override
							protected Object aggregatePayloads( MessageGroup group, Map<String, Object> defaultHeaders ) {
							
								Collection<Message<?>> messages = group.getMessages();
								Assert.notEmpty( messages, this.getClass().getSimpleName() + " cannot process empty message groups" );
								
								AccountDetailsEvent accountDetailsEvent = null;
								for( Message<?> message : messages ) {
									
									if( message.getPayload() instanceof AccountDetailsEvent ) {
										AccountDetailsEvent event = (AccountDetailsEvent) message.getPayload();
										if( event.isFound() ) {
											accountDetailsEvent = event;
										}
									}
									
									if( message.getPayload() instanceof AccountBalanceDetailsEvent ) {
										if( null != accountDetailsEvent ) {
											AccountBalanceDetailsEvent event = (AccountBalanceDetailsEvent) message.getPayload();
											if( event.isFound() ) {
												accountDetailsEvent.setBalance( event.getBalance() );
											}
										}
									}

									if( message.getPayload() instanceof AccountHistoryDetailsEvent ) {
										if( null != accountDetailsEvent ) {
											AccountHistoryDetailsEvent event = (AccountHistoryDetailsEvent) message.getPayload();
											if( event.isFound() ) {
												accountDetailsEvent.setHistory( event.getAccountHistories() );
											}
										}
									}
								}

								return accountDetailsEvent;
							}
							
						}))
						.channel( c -> c.queue( "accountDetailsAggregateResult" ) );
	}
	
	@Bean
	public IntegrationFlow requestTransfer() {
		
		return IntegrationFlows.from( "requestTransferFundsChannel" )
				.channel( c -> c.executor( Executors.newCachedThreadPool() ) )
				.publishSubscribeChannel( c -> c
					.applySequence( true )
					.subscribe( s -> s
						.<TransferFundsEvent>handle( (p1, h1 ) -> p1 )
						.channel( "transferFundsAggregateFlow.input" )
					)
					.subscribe( s -> s
						.<TransferFundsEvent, RequestAccountDetailsEvent>transform( event -> new RequestAccountDetailsEvent( event.getFromAccountNumber() )	)
						.<RequestAccountDetailsEvent>handle( (p1, h1 ) -> accountService.requestAccountDetails( p1 ) )
						.channel( "transferFundsAggregateFlow.input" )
					)
					.subscribe( s -> s
						.<TransferFundsEvent, RequestAccountDetailsEvent>transform( event -> new RequestAccountDetailsEvent( event.getToAccountNumber() )	)
						.<RequestAccountDetailsEvent>handle( (p2, h2 ) -> accountService.requestAccountDetails( p2 ) )
						.channel( "transferFundsAggregateFlow.input" )
					)
				)
				.get();
	}

	@Bean
	public IntegrationFlow transferFundsAggregateFlow() {
		
		return flow -> flow
						.aggregate()
//						a -> a.outputProcessor( new AbstractAggregatingMessageGroupProcessor() {
//
//							@Override
//							protected Object aggregatePayloads( MessageGroup group, Map<String, Object> defaultHeaders ) {
//							
//								Collection<Message<?>> messages = group.getMessages();
//								Assert.notEmpty( messages, this.getClass().getSimpleName() + " cannot process empty message groups" );
//								
//								AccountDetailsEvent accountDetailsEvent = null;
//								for( Message<?> message : messages ) {
//									
//									if( message.getPayload() instanceof AccountDetailsEvent ) {
//										AccountDetailsEvent event = (AccountDetailsEvent) message.getPayload();
//										if( event.isFound() ) {
//											accountDetailsEvent = event;
//										}
//									}
//									
//									if( message.getPayload() instanceof AccountBalanceDetailsEvent ) {
//										if( null != accountDetailsEvent ) {
//											AccountBalanceDetailsEvent event = (AccountBalanceDetailsEvent) message.getPayload();
//											if( event.isFound() ) {
//												accountDetailsEvent.setBalance( event.getBalance() );
//											}
//										}
//									}
//
//									if( message.getPayload() instanceof AccountHistoryDetailsEvent ) {
//										if( null != accountDetailsEvent ) {
//											AccountHistoryDetailsEvent event = (AccountHistoryDetailsEvent) message.getPayload();
//											if( event.isFound() ) {
//												accountDetailsEvent.setHistory( event.getAccountHistories() );
//											}
//										}
//									}
//								}
//
//								return accountDetailsEvent;
//							}
//							
//						}))
						.channel( c -> c.queue( "fundsTransferedAggregateResult" ) );
	}

}
