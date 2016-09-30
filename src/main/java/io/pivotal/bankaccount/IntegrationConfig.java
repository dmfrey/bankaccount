package io.pivotal.bankaccount;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.messaging.MessageChannel;

@Configuration
@EnableIntegration
@IntegrationComponentScan( basePackages = { "io.pivotal.bankaccount.domain.service" } )
public class IntegrationConfig {

	@Bean
	public MessageChannel requestChannel() {
		
		return new DirectChannel();
	}
	
	@Bean
	public MessageChannel responseChannel() {
		
		return new DirectChannel();
	}

	@Bean
	public MessageChannel accountCreatedChannel() {
		
		return new PublishSubscribeChannel();
	}

	@Bean
	public MessageChannel accountUpdatedChannel() {
		
		return new PublishSubscribeChannel();
	}

	@Bean
	public MessageChannel accountDetailsChannel() {
		
		return new DirectChannel();
	}

	@Bean
	public MessageChannel requestAccountBalanceDetailsChannel() {
		
		return new DirectChannel();
	}

	@Bean
	public MessageChannel requestAccountBalanceUpdateChannel() {
		
		return new DirectChannel();
	}

	@Bean
	public MessageChannel accountBalanceUpdateChannel() {
		
		return new DirectChannel();
	}
	
	@Bean
	public MessageChannel accountHistoryChannel() {
		
		return new DirectChannel();
	}

}
