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

import io.pivotal.bankaccount.event.account.AccountBalanceUpdatedEvent;
import io.pivotal.bankaccount.event.account.RequestAccountBalanceUpdateEvent;

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
	public AccountBalanceUpdatedEvent updateBalance( RequestAccountBalanceUpdateEvent event ) {
		log.debug( "updateBalance : enter" );

		Assert.notNull( event );
		Assert.notNull( event.getJobId() );
		Assert.notNull( event.getAccountNumber() );
		Assert.notNull( event.getAmount() );
		
		redisTemplate.opsForValue().set( event.getAccountNumber(), event.getAmount() );

		log.debug( "updateBalance : exit" );
		return new AccountBalanceUpdatedEvent( event.getJobId(), event.getAccountNumber(), event.getAmount() );
	}

}
