/**
 * 
 */
package io.pivotal.bankaccount.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.pivotal.bankaccount.event.account.AccountBalanceDetailsEvent;
import io.pivotal.bankaccount.event.account.RequestAccountBalanceDetailsEvent;
import io.pivotal.bankaccount.persistence.service.AccountBalancePersistenceService;

/**
 * @author dmfrey
 *
 */
@RestController
@RequestMapping( "/" )
public class AccountBalanceController {

	private static final Logger log = LoggerFactory.getLogger( AccountBalanceController.class );
	
	private final AccountBalancePersistenceService service;
	
	@Autowired
	public AccountBalanceController( final AccountBalancePersistenceService service ) {
		
		this.service = service;
		
	}
	
	@RequestMapping( value = "/{accountNumber}" )
	@ResponseBody
	public AccountBalanceDetailsEvent getBalance( @PathVariable( required = true ) Long accountNumber ) {
		log.debug( "getBalance : enter" );
		
		AccountBalanceDetailsEvent found = service.getBalance( new RequestAccountBalanceDetailsEvent( accountNumber ) );
		if( log.isTraceEnabled() ) {
			
			log.trace( "getBalance : found=" + found.toString() );
			
		}
		
		log.debug( "getBalance : exit" );
		return found;
	}
	
}
