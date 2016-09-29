/**
 * 
 */
package io.pivotal.bankaccount.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import io.pivotal.bankaccount.domain.model.AccountHistory;
import io.pivotal.bankaccount.event.account.AccountCreatedEvent;
import io.pivotal.bankaccount.event.account.AccountUpdatedEvent;
import io.pivotal.bankaccount.event.account.CreateAccountHistoryEvent;
import io.pivotal.bankaccount.persistence.service.AccountHistoryPersistenceService;

/**
 * @author dmfrey
 *
 */
@Component
public class AccountHistoryHandler {

	private static final Logger log = LoggerFactory.getLogger( AccountHistoryHandler.class );
	
	private final AccountHistoryPersistenceService service;
	
	@Autowired
	private AccountHistoryHandler( final AccountHistoryPersistenceService service ) {
		
		this.service = service;
		
	}
	
	@Transformer( inputChannel = "accountCreatedChannel", outputChannel = "accountHistoryChannel" )
	public CreateAccountHistoryEvent accountHistoryEventTransformer( Message<AccountCreatedEvent> message ) {
		log.debug( "accountHistoryEventTransformer : enter" );
		
		AccountCreatedEvent event = message.getPayload();

		AccountHistory accountHistory = new AccountHistory();
		accountHistory.setJobId( event.getJobId() );
		accountHistory.setAccountNumber( event.getAccount().getAccountNumber() );
		accountHistory.setAmount( event.getAmount() );
		accountHistory.setDateCreated( System.nanoTime() );
		
		log.debug( "accountHistoryEventTransformer : exit" );
		return new CreateAccountHistoryEvent( event.getJobId(), accountHistory );
	}
	
	@Transformer( inputChannel = "accountUpdatedChannel", outputChannel = "accountHistoryChannel" )
	public CreateAccountHistoryEvent accountUpdatedBalanceUpdateEventTransformer( Message<AccountUpdatedEvent> message ) {
		log.debug( "accountBalanceUpdateEventTransformer : enter" );
		
		AccountUpdatedEvent event = message.getPayload();

		AccountHistory accountHistory = new AccountHistory();
		accountHistory.setJobId( event.getJobId() );
		accountHistory.setAccountNumber( event.getAccountNumber() );
		accountHistory.setAmount( event.getAmount() );
		accountHistory.setDateCreated( System.nanoTime() );

		log.debug( "accountBalanceUpdateEventTransformer : exit" );
		return new CreateAccountHistoryEvent( event.getJobId(), accountHistory );
	}
	
	@ServiceActivator( inputChannel = "accountHistoryChannel" )
	public void createAccountHistory( Message<CreateAccountHistoryEvent> message ) {
		log.debug( "createAccountHistory : enter" );
		
		CreateAccountHistoryEvent event = message.getPayload();
		service.requestCreateAccountHistory( event );
		
		log.debug( "createAccountHistory : exit" );
	}
	
}
