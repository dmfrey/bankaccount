/**
 * 
 */
package io.pivotal.bankaccount.persistence.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import io.pivotal.bankaccount.event.account.AccountBalanceDetailsEvent;
import io.pivotal.bankaccount.event.account.AccountBalanceUpdatedEvent;
import io.pivotal.bankaccount.event.account.RequestAccountBalanceDetailsEvent;
import io.pivotal.bankaccount.event.account.UpdateAccountBalanceEvent;

/**
 * @author dmfrey
 *
 */
@Service
@Transactional( readOnly = true )
public class AccountBalancePersistenceService {

	private static final Logger log = LoggerFactory.getLogger( AccountBalancePersistenceService.class );
	
	private final RedisTemplate<Long, Double> redisTemplate;
	
	@Autowired
	public AccountBalancePersistenceService( final RedisTemplate<Long, Double> redisTemplate ) {
		
		this.redisTemplate = redisTemplate;
		
	}
	
	/**
	 * @param event
	 * @return
	 */
	@Transactional( readOnly = false )
	public AccountBalanceUpdatedEvent updateBalance( UpdateAccountBalanceEvent event ) {
		log.debug( "updateBalance : enter" );

		Assert.notNull( event );
		Assert.notNull( event.getJobId() );
		Assert.notNull( event.getAccountNumber() );
		Assert.notNull( event.getAmount() );
		
		redisTemplate.opsForValue().set( event.getAccountNumber(), event.getAmount() );

		log.debug( "updateBalance : exit" );
		return new AccountBalanceUpdatedEvent( event.getJobId(), event.getAccountNumber(), event.getAmount() );
	}

	/**
	 * @param event
	 * @return
	 */
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
