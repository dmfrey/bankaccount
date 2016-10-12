/**
 * 
 */
package io.pivotal.bankaccount.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.Transformer;
import org.springframework.stereotype.Component;

import io.pivotal.bankaccount.event.account.CreateAccountEvent;

/**
 * @author dmfrey
 *
 */
@Component
public class LoggingAdapter {

	private static final Logger log = LoggerFactory.getLogger( LoggingAdapter.class );
	
//	@Transformer( inputChannel = "requestChannel", outputChannel = "responseChannel" )
	public CreateAccountEvent logEvent( CreateAccountEvent event ) {
		
		log.info( "CreateAccountEvent[jobId=" + event.getJobId() + ", account=" + event.getAccount() + ", initialAmount=" + event.getInitialAmount() + "]" );
		
		return event;
	}
	
}
