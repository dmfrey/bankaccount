/**
 * 
 */
package io.pivotal.bankaccount.persistence.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import io.pivotal.bankaccount.event.account.AccountBalanceDetailsEvent;
import io.pivotal.bankaccount.event.account.AccountCreatedEvent;
import io.pivotal.bankaccount.event.account.AccountDetailsEvent;
import io.pivotal.bankaccount.event.account.AccountHistoryDetailsEvent;
import io.pivotal.bankaccount.event.account.CreateAccountEvent;
import io.pivotal.bankaccount.event.account.RequestAccountDetailsEvent;
import io.pivotal.bankaccount.persistence.model.AccountEntity;
import io.pivotal.bankaccount.persistence.repository.AccountRepository;

/**
 * @author dmfrey
 *
 */
@Service
@Transactional( readOnly = true )
public class AccountPersistenceService {

	private static final Logger log = LoggerFactory.getLogger( AccountPersistenceService.class );
	
	private final AccountRepository repository;
	private final RestTemplate restTemplate;
	
	@Autowired
	public AccountPersistenceService( final AccountRepository repository, final RestTemplate restTemplate ) {
		
		this.repository = repository;
		this.restTemplate = restTemplate;
		
	}
	
	/**
	 * @param event
	 * @return
	 */
	@Transactional( readOnly = false )
	public AccountCreatedEvent requestCreateAccount( CreateAccountEvent event ) {
		log.debug( "requestCreateAccount : enter" );
		
		Assert.notNull( event );
		Assert.notNull( event.getJobId() );
		Assert.notNull( event.getAccount() );
		
		AccountEntity entity = AccountEntity.fromAccount( event.getAccount() );
		
		AccountEntity created = repository.save( entity );
		if( null != created ) {
			
			if( log.isTraceEnabled() ) {
			
				log.trace( "requestCreateAccount : created=" + created.toString() );
			
			}
			
			log.debug( "requestCreateAccount : exit" );
			return new AccountCreatedEvent( created.getId(), event.getJobId(), created.toAccount(), event.getInitialAmount() );
		}
		
		log.debug( "requestCreateAccount : exit, account not created" );
		return AccountCreatedEvent.notCreated( event.getJobId(), event.getAccount(), event.getInitialAmount() );
	}

	/**
	 * @param event
	 * @return
	 */
	public AccountDetailsEvent requestAccountDetails( RequestAccountDetailsEvent event ) {
		log.debug( "requestAccountDetails : enter" );
	
		Assert.notNull( event );

		AccountEntity found = null;
		if( null != event.getId() ) {
			log.debug( "requestAccountDetails : looking up account by id" );
			
			found = repository.findOne( event.getId() );
		
		} else if( null != event.getAccountNumber() ) {
			log.debug( "requestAccountDetails : looking up account by accountNumber" );
			
			found = repository.findByAccountNumber( event.getAccountNumber() );
			
		}
		
		if( null != found ) {
			
			AccountDetailsEvent details = new AccountDetailsEvent( found.getId(), found.toAccount() );
			
			AccountBalanceDetailsEvent balance = restTemplate.getForObject( "http://localhost:8083/" + event.getAccountNumber(), AccountBalanceDetailsEvent.class );
			if( null != balance && balance.isFound() ) {
				
				details.setBalance( balance.getBalance() );
				
			}

			AccountHistoryDetailsEvent history = restTemplate.getForObject( "http://localhost:8082/" + event.getAccountNumber(), AccountHistoryDetailsEvent.class );
			if( null != history && history.isFound() ) {
				
				details.setHistory( history.getAccountHistories() );
				
			}

			if( log.isTraceEnabled() ) {
				
				log.trace( "requestAccountDetails : found=" + found.toString() );
				
			}
			
			log.debug( "requestAccountDetails : exit" );
			return details; 
		}
		
		log.debug( "requestAccountDetails : exit, account not found!" );
		return AccountDetailsEvent.notFound( event.getId() );
	}
	
}
