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

import io.pivotal.bankaccount.event.account.AccountCreatedEvent;
import io.pivotal.bankaccount.event.account.AccountUpdatedEvent;
import io.pivotal.bankaccount.event.account.FundsTransferedEvent;
import io.pivotal.bankaccount.event.account.RequestAccountBalanceUpdateEvent;
import io.pivotal.bankaccount.event.account.RequestTransferFundsEvent;
import io.pivotal.bankaccount.persistence.service.AccountBalancePersistenceService;

/**
 * @author dmfrey
 *
 */
@Component
public class AccountBalanceHandler {

	private static final Logger log = LoggerFactory.getLogger( AccountBalanceHandler.class );
	
	private final AccountBalancePersistenceService service;
	
	@Autowired
	public AccountBalanceHandler( final AccountBalancePersistenceService service ) {
		
		this.service = service;
		
	}
	
	@Transformer( inputChannel = "accountCreatedChannel", outputChannel = "accountBalanceUpdateChannel" )
	public RequestAccountBalanceUpdateEvent accountCreatedBalanceUpdateEventTransformer( Message<AccountCreatedEvent> message ) {
		log.debug( "accountBalanceUpdateEventTransformer : enter" );
		
		AccountCreatedEvent event = message.getPayload();

		log.debug( "accountBalanceUpdateEventTransformer : exit" );
		return new RequestAccountBalanceUpdateEvent( event.getJobId(), event.getAccount().getAccountNumber(), event.getAmount() );
	}
	
	@Transformer( inputChannel = "accountUpdatedChannel", outputChannel = "accountBalanceUpdateChannel" )
	public RequestAccountBalanceUpdateEvent accountUpdatedBalanceUpdateEventTransformer( Message<AccountUpdatedEvent> message ) {
		log.debug( "accountBalanceUpdateEventTransformer : enter" );
		
		AccountUpdatedEvent event = message.getPayload();

		log.debug( "accountBalanceUpdateEventTransformer : exit" );
		return new RequestAccountBalanceUpdateEvent( event.getJobId(), event.getAccountNumber(), event.getAmount() );
	}
	
	@ServiceActivator( inputChannel = "accountBalanceUpdateChannel" )
	public void adjustBalance( Message<RequestAccountBalanceUpdateEvent> message ) {
		log.debug( "adjustBalance : enter" );
		
		RequestAccountBalanceUpdateEvent event = message.getPayload();
		service.updateBalance( event );
		
		log.debug( "adjustBalance : exit" );
	}

	@ServiceActivator( inputChannel = "requestAccountBalanceUpdateChannel" )
	public FundsTransferedEvent  requestTransferFunds( Message<RequestTransferFundsEvent> message ) {
		log.debug( "requestTransferFunds : enter" );
		
		RequestTransferFundsEvent event = message.getPayload();
		
		log.debug( "requestTransferFunds : exit" );
		return FundsTransferedEvent.notTransfered( event.getJobId(), event.getFromAccountNumber(), event.getToAccountNumber(), event.getAmount() );
	}

}
