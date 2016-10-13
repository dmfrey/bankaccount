/**
 * 
 */
package io.pivotal.bankaccount.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.handler.annotation.SendTo;

import io.pivotal.bankaccount.event.account.AccountCreatedEvent;
import io.pivotal.bankaccount.event.account.CreateAccountEvent;
import io.pivotal.bankaccount.persistence.service.AccountPersistenceService;

/**
 * @author dmfrey
 *
 */
@EnableBinding( Processor.class )
public class AccountService {

	private final Logger log = LoggerFactory.getLogger( AccountService.class );
	
	private final AccountPersistenceService service;
	
	@Autowired
	public AccountService( final AccountPersistenceService service ) {
		
		this.service = service;
		
	}
	
	@StreamListener( Processor.INPUT )
	@SendTo( Processor.OUTPUT )
	public AccountCreatedEvent createAccount( CreateAccountEvent event ) {
		log.debug( "createAccount : enter" );
		
		AccountCreatedEvent created = service.requestCreateAccount( event );
		if( log.isTraceEnabled() ) {
			
			log.debug( "createAccount : created=" + created.toString() );
			
		}
		
		log.debug( "createAccount : exit" );
		return created;
	}
	
}
