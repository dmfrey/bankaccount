package io.pivotal.bankaccount.domain.service;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import io.pivotal.bankaccount.event.account.AccountCreatedEvent;
import io.pivotal.bankaccount.event.account.AccountDetailsEvent;
import io.pivotal.bankaccount.event.account.CreateAccountEvent;
import io.pivotal.bankaccount.event.account.RequestAccountDetailsEvent;

@MessagingGateway( name = "bankAccountService", defaultRequestChannel = "requestChannel" )
public interface BankAccountService {

	@Gateway( requestChannel = "requestChannel" )
	public AccountCreatedEvent createAccount( CreateAccountEvent event );

	@Gateway( requestChannel = "accountDetailsChannel" )
	public AccountDetailsEvent getAccount( RequestAccountDetailsEvent event );
	
}
