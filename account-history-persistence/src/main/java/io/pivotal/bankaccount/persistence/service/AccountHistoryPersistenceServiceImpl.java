/**
 * 
 */
package io.pivotal.bankaccount.persistence.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.pivotal.bankaccount.domain.model.AccountHistory;
import io.pivotal.bankaccount.event.account.AccountHistoryCreatedEvent;
import io.pivotal.bankaccount.event.account.AccountHistoryDetailsEvent;
import io.pivotal.bankaccount.event.account.CreateAccountHistoryEvent;
import io.pivotal.bankaccount.event.account.RequestAccountHistoryDetailsEvent;
import io.pivotal.bankaccount.persistence.model.AccountHistoryEntity;
import io.pivotal.bankaccount.persistence.repository.AccountHistoryRepository;

/**
 * @author dmfrey
 *
 */
@Service
public class AccountHistoryPersistenceServiceImpl implements AccountHistoryPersistenceService {

	private static final Logger log = LoggerFactory.getLogger( AccountHistoryPersistenceServiceImpl.class );
	
	private AccountHistoryRepository repository;
	
	@Autowired
	public AccountHistoryPersistenceServiceImpl( final AccountHistoryRepository repository ) {
		
		this.repository = repository;
		
	}
	
	/* (non-Javadoc)
	 * @see io.pivotal.bankaccount.persistence.service.AccountHistoryPersistenceService#requestCreateAccountHistory(io.pivotal.bankaccount.event.account.CreateAccountHistoryEvent)
	 */
	@Override
	public AccountHistoryCreatedEvent requestCreateAccountHistory( CreateAccountHistoryEvent event ) {
		log.debug( "requestCreateAccountHistory : enter" );
		
		AccountHistoryEntity history = AccountHistoryEntity.fromAccountHistory( event.getAccountHistory() );
		
		AccountHistoryEntity created = repository.save( history );
		if( null != created ) {
			
			if( log.isTraceEnabled() ) {
				
				log.debug( "requestCreateAccountHistory : created=" + created.toString() );
				
			}
			
			log.debug( "requestCreateAccountHistory : exit" );
			return new AccountHistoryCreatedEvent( created.getId(), event.getJobId(), created.toAccountHistory() );
		}

		log.debug( "requestCreateAccountHistory : exit, account history not created" );
		return AccountHistoryCreatedEvent.notCreated( event.getJobId(), event.getAccountHistory() );
	}

	/* (non-Javadoc)
	 * @see io.pivotal.bankaccount.persistence.service.AccountHistoryPersistenceService#getAccountHistory(io.pivotal.bankaccount.event.account.RequestAccountHistoryDetailsEvent)
	 */
	@Override
	public AccountHistoryDetailsEvent getAccountHistory( RequestAccountHistoryDetailsEvent event ) {
		log.debug( "getAccountHistory : enter" );
		
		List<AccountHistoryEntity> entities = repository.findAllByAccountNumber( event.getAccountNumber() );
		if( null != entities && !entities.isEmpty() ) {
			
			List<AccountHistory> accountHistories = new ArrayList<AccountHistory>();
			for( AccountHistoryEntity entity : entities ) {
			
				accountHistories.add( entity.toAccountHistory() );
				
			}
			
			log.debug( "getAccountHistory : exit" );
			return new AccountHistoryDetailsEvent( accountHistories );
		}
		
		log.debug( "getAccountHistory : exit, account history not found!" );
		return AccountHistoryDetailsEvent.notFound();
	}

}
