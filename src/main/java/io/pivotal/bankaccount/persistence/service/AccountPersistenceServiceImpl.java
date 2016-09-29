/**
 * 
 */
package io.pivotal.bankaccount.persistence.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
public class AccountPersistenceServiceImpl implements AccountPersistenceService {

	private static final Logger log = LoggerFactory.getLogger( AccountPersistenceServiceImpl.class );
	
	private final AccountRepository repository;
	
	@Autowired
	public AccountPersistenceServiceImpl( final AccountRepository repository ) {
		
		this.repository = repository;
		
	}
	
	/* (non-Javadoc)
	 * @see io.pivotal.bankaccount.persistence.service.AccountPersistenceService#requestCreateAccount(io.pivotal.bankaccount.domain.event.account.CreateAccountEvent)
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see io.pivotal.bankaccount.persistence.service.AccountPersistenceService#requestAccountDetails(io.pivotal.bankaccount.event.account.RequestAccountDetailsEvent)
	 */
	@Override
	public AccountDetailsEvent requestAccountDetails( RequestAccountDetailsEvent event ) {
		log.debug( "requestAccountDetails : enter" );
	
		Assert.notNull( event );
		Assert.notNull( event.getId() );
		
		AccountEntity found = repository.findOne( event.getId() );
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
