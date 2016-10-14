/**
 * 
 */
package io.pivotal.bankaccount.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.handler.annotation.SendTo;

import io.pivotal.bankaccount.event.account.AccountBalanceUpdatedEvent;
import io.pivotal.bankaccount.event.account.AccountCreatedEvent;
import io.pivotal.bankaccount.event.account.UpdateAccountBalanceEvent;
import io.pivotal.bankaccount.persistence.service.AccountBalancePersistenceService;

/**
 * @author dmfrey
 *
 */
@EnableBinding( Processor.class )
public class AccountBalanceService {

	private static final Logger log = LoggerFactory.getLogger( AccountBalanceService.class );
	
	private final AccountBalancePersistenceService service;
	
	@Autowired
	public AccountBalanceService( final AccountBalancePersistenceService service ) {
		
		this.service = service;
		
	}
	
	@StreamListener( Processor.INPUT )
	@SendTo( Processor.OUTPUT )
	public AccountBalanceUpdatedEvent updateBalance( AccountCreatedEvent event ) {
		log.debug( "updateBalance : enter" );
		
		AccountBalanceUpdatedEvent updated = service.updateBalance( new UpdateAccountBalanceEvent( event.getJobId(), event.getAccount().getAccountNumber(), event.getAmount() ) );
		if( log.isTraceEnabled() ) {
			
			log.trace( "updateBalance : updated=" + updated.toString() );
			
		}
		
		log.debug( "updateBalance : exit" );
		return updated;
	}
	
}
