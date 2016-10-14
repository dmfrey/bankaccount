/**
 * 
 */
package io.pivotal.bankaccount.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.pivotal.bankaccount.domain.model.AccountHistory;
import io.pivotal.bankaccount.event.account.AccountHistoryDetailsEvent;
import io.pivotal.bankaccount.event.account.RequestAccountHistoryDetailsEvent;
import io.pivotal.bankaccount.persistence.service.AccountHistoryPersistenceService;

/**
 * @author dmfrey
 *
 */
@RestController
@RequestMapping( "/" )
public class AccountHistoryController {

	private static final Logger log = LoggerFactory.getLogger( AccountHistoryController.class );
	
	private final AccountHistoryPersistenceService service;
	
	public AccountHistoryController( final AccountHistoryPersistenceService service ) {
		
		this.service = service;
		
	}
	
	@RequestMapping( value = "/{accountNumber}" )
	@ResponseBody
	public List<AccountHistory> getHistory( @PathVariable( required = true ) Long accountNumber ) {
		log.debug( "getHistory : enter" );
		
		AccountHistoryDetailsEvent found = service.getAccountHistory( new RequestAccountHistoryDetailsEvent( accountNumber ) );
		if( log.isTraceEnabled() ) {
			
			log.trace( "getHistory : found=" + found.toString() );
			
		}
		
		log.debug( "getHistory : exit" );
		return found.getAccountHistories();
	}

}
