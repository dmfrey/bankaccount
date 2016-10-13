/**
 * 
 */
package io.pivotal.bankaccount.persistence.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import io.pivotal.bankaccount.event.account.AccountBalanceDetailsEvent;
import io.pivotal.bankaccount.event.account.AccountBalanceUpdatedEvent;
import io.pivotal.bankaccount.event.account.RequestAccountBalanceDetailsEvent;
import io.pivotal.bankaccount.event.account.UpdaateAccountBalanceEvent;

/**
 * @author dmfrey
 *
 */
@Service
public class AccountBalancePersistenceServiceImpl implements AccountBalancePersistenceService {

	private static final Logger log = LoggerFactory.getLogger( AccountBalancePersistenceServiceImpl.class );
	
	private final RedisTemplate<Long, Double> redisTemplate;
	
	@Autowired
	public AccountBalancePersistenceServiceImpl( final RedisTemplate<Long, Double> redisTemplate ) {
		
		this.redisTemplate = redisTemplate;
		
	}
	
	/* (non-Javadoc)
	 * @see io.pivotal.bankaccount.persistence.service.AccountBalancePersistenceService#updateBalance(io.pivotal.bankaccount.event.account.RequestAccountBalanceUpdateEvent)
	 */
	@Override
	public AccountBalanceUpdatedEvent updateBalance( UpdaateAccountBalanceEvent event ) {
		log.debug( "updateBalance : enter" );

		Assert.notNull( event );
		Assert.notNull( event.getJobId() );
		Assert.notNull( event.getAccountNumber() );
		Assert.notNull( event.getAmount() );
		
		redisTemplate.opsForValue().set( event.getAccountNumber(), event.getAmount() );

		log.debug( "updateBalance : exit" );
		return new AccountBalanceUpdatedEvent( event.getJobId(), event.getAccountNumber(), event.getAmount() );
	}

	/* (non-Javadoc)
	 * @see io.pivotal.bankaccount.persistence.service.AccountBalancePersistenceService#getBalance(io.pivotal.bankaccount.event.account.RequestAccountBalanceDetailsEvent)
	 */
	@Override
	public AccountBalanceDetailsEvent getBalance( RequestAccountBalanceDetailsEvent event ) {
		log.debug( "getBalance : enter" );
		
		Assert.notNull( event );
		Assert.notNull( event.getAccountNumber() );

		Double balance = redisTemplate.opsForValue().get( event.getAccountNumber() );
		if( null != balance ) {
			
			if( log.isTraceEnabled() ) {
				
				log.trace( "getBalance : balance=" + balance );
				
			}
			
			log.debug( "getBalance : exit" );
			return new AccountBalanceDetailsEvent( event.getAccountNumber(), balance );
		}
		
		log.debug( "getBalance : enter, account balance not found!" );
		return AccountBalanceDetailsEvent.notFound( event.getAccountNumber() );
	}

}
