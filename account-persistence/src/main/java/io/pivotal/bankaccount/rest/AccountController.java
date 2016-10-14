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

import io.pivotal.bankaccount.event.account.AccountDetailsEvent;
import io.pivotal.bankaccount.event.account.RequestAccountDetailsEvent;
import io.pivotal.bankaccount.persistence.service.AccountPersistenceService;

/**
 * @author dmfrey
 *
 */
@RestController
@RequestMapping( "/" )
public class AccountController {

	private final Logger log = LoggerFactory.getLogger( AccountController.class );
	
	private final AccountPersistenceService service;
	
	@Autowired
	public AccountController( final AccountPersistenceService service ) {
		
		this.service = service;
		
	}

	@RequestMapping( value = "/{accountNumber}" )
	@ResponseBody
	public AccountDetailsEvent getAccount( @PathVariable( value = "accountNumber", required = true ) Long accountNumber ) {
		log.debug( "getAccount : enter" );
		
		AccountDetailsEvent found = service.requestAccountDetails( new RequestAccountDetailsEvent( accountNumber ) );
		if( log.isTraceEnabled() ) {
			
			log.trace( "getAccount : found=" + found.toString() );
			
		}
		
		log.debug( "getAccount : exit" );
		return found;
	}
	
}
