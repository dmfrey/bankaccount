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

import io.pivotal.bankaccount.event.account.AccountCreatedEvent;
import io.pivotal.bankaccount.event.account.AccountDetailsEvent;
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
	
	@Autowired
	public AccountPersistenceService( final AccountRepository repository ) {
		
		this.repository = repository;
		
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
			
			if( log.isTraceEnabled() ) {
				
				log.trace( "requestAccountDetails : found=" + found.toString() );
				
			}
			
			log.debug( "requestAccountDetails : exit" );
			return new AccountDetailsEvent( found.getId(), found.toAccount() );
		}
		
		log.debug( "requestAccountDetails : exit, account not found!" );
		return AccountDetailsEvent.notFound( event.getId() );
	}
	
}
