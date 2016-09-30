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

import io.pivotal.bankaccount.event.account.AccountBalanceDetailsEvent;
import io.pivotal.bankaccount.event.account.AccountCreatedEvent;
import io.pivotal.bankaccount.event.account.AccountDetailsEvent;
import io.pivotal.bankaccount.event.account.AccountHistoryDetailsEvent;
import io.pivotal.bankaccount.event.account.CreateAccountEvent;
import io.pivotal.bankaccount.event.account.RequestAccountBalanceDetailsEvent;
import io.pivotal.bankaccount.event.account.RequestAccountDetailsEvent;
import io.pivotal.bankaccount.event.account.RequestAccountHistoryDetailsEvent;
import io.pivotal.bankaccount.persistence.service.AccountBalancePersistenceService;
import io.pivotal.bankaccount.persistence.service.AccountHistoryPersistenceService;
import io.pivotal.bankaccount.persistence.service.AccountPersistenceService;

/**
 * @author dmfrey
 *
 */
@Component
public class AccountHandler {

	private static final Logger log = LoggerFactory.getLogger( AccountHandler.class );
	
	private final AccountPersistenceService accountService;
	private final AccountBalancePersistenceService accountBalanceService;
	private final AccountHistoryPersistenceService accountHistoryService;
	
	@Autowired
	private AccountHandler( final AccountPersistenceService accountService, final AccountBalancePersistenceService accountBalanceService, final AccountHistoryPersistenceService accountHistoryService ) {
		
		this.accountService = accountService;
		this.accountBalanceService = accountBalanceService;
		this.accountHistoryService = accountHistoryService;
		
	}
	
	@ServiceActivator( inputChannel = "responseChannel", outputChannel = "accountCreatedChannel" )
	public AccountCreatedEvent createAccount( Message<CreateAccountEvent> message ) {
		log.debug( "createAccount : enter" );
		
		CreateAccountEvent event = message.getPayload();
		
		log.debug( "createAccount : exit" );
		return accountService.requestCreateAccount( event );
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
		
		AccountDetailsEvent accountDetails = accountService.requestAccountDetails( event );
		if( accountDetails.isFound() ) {
		
			AccountBalanceDetailsEvent accountBalance = accountBalanceService.getBalance( new RequestAccountBalanceDetailsEvent( accountDetails.getAccount().getAccountNumber() ) );
			if( accountBalance.isFound() ) {
				
				accountDetails.setBalance( accountBalance.getBalance() );
				
			}
			
			AccountHistoryDetailsEvent accountHistory = accountHistoryService.getAccountHistory( new RequestAccountHistoryDetailsEvent( accountDetails.getAccount().getAccountNumber() ) );
			if( accountHistory.isFound() ) {
				
				accountDetails.setHistory( accountHistory.getAccountHistories() );
				
			}
			
		}
		
		log.debug( "accountDetails : exit" );
		return accountDetails;
	}

}
