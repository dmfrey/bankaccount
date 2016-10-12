/**
 * 
 */
package io.pivotal.bankaccount.persistence;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author dmfrey
 *
 */
@Configuration
public class RedisConfig {

	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
		jedisConnectionFactory.setUsePool( true );
		
		return jedisConnectionFactory;
	}
	
	@Bean
	public RedisTemplate<Long, Double> redisTemplate( final JedisConnectionFactory jedisConnectionFactory ) {
		
		RedisTemplate<Long, Double> redisTemplate = new RedisTemplate<Long, Double>();
		redisTemplate.setConnectionFactory( jedisConnectionFactory );
		
		return redisTemplate;
	}
	
}
