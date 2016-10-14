/**
 * 
 */
package io.pivotal.bankaccount.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import io.pivotal.bankaccount.event.account.AccountHistoryCreatedEvent;
import io.pivotal.bankaccount.event.account.CreateAccountHistoryEvent;
import io.pivotal.bankaccount.persistence.service.AccountHistoryPersistenceService;

/**
 * @author dmfrey
 *
 */
@EnableBinding( Sink.class )
public class AccountHIstoryService {

	private static final Logger log = LoggerFactory.getLogger( AccountHIstoryService.class );
	
	private final AccountHistoryPersistenceService service;
	
	@Autowired
	public AccountHIstoryService( final AccountHistoryPersistenceService service ) {
		
		this.service = service;
		
	}
	
	@StreamListener( Sink.INPUT )
	public void addHistory( CreateAccountHistoryEvent event ) {
		log.debug( "addHistory : enter" );
		
		AccountHistoryCreatedEvent updated = service.requestCreateAccountHistory( event );
		if( log.isTraceEnabled() ) {
			
			log.trace( "addHistory : updated=" + updated.toString() );
			
		}
		
		log.debug( "addHistory : exit" );
	}
	
}
