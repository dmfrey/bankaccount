/**
 * 
 */
package io.pivotal.bankaccount.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import io.pivotal.bankaccount.event.account.AccountCreatedEvent;
import io.pivotal.bankaccount.event.account.AccountDetailsEvent;
import io.pivotal.bankaccount.event.account.CreateAccountEvent;
import io.pivotal.bankaccount.event.account.RequestAccountDetailsEvent;
import io.pivotal.bankaccount.persistence.service.AccountPersistenceService;

/**
 * @author dmfrey
 *
 */
@Component
public class AccountHandler {

	private static final Logger log = LoggerFactory.getLogger( AccountHandler.class );
	
	private final AccountPersistenceService service;
	
	@Autowired
	private AccountHandler( final AccountPersistenceService service ) {
		
		this.service = service;
		
	}
	
	@ServiceActivator( inputChannel = "responseChannel", outputChannel = "accountCreatedChannel" )
	public AccountCreatedEvent createAccount( Message<CreateAccountEvent> message ) {
		log.debug( "createAccount : enter" );
		
		CreateAccountEvent event = message.getPayload();
		
		log.debug( "createAccount : exit" );
		return service.requestCreateAccount( event );
	}
	
	@ServiceActivator( inputChannel = "accountCreatedChannel" )
	public AccountCreatedEvent accountCreated( Message<AccountCreatedEvent> message ) {
	
		log.info( "accountCreated : account created successfully!" );
		return message.getPayload();
	}

	@ServiceActivator( inputChannel = "accountDetailsChannel" )
	public AccountDetailsEvent accountDetails( Message<RequestAccountDetailsEvent> message ) {
		log.debug( "accountDetails : enter" );
		
		RequestAccountDetailsEvent event = message.getPayload();
		
		AccountDetailsEvent found = service.requestAccountDetails( event );
		
		log.debug( "accountDetails : exit" );
		return found;
	}

}
