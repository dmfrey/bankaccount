package io.pivotal.bankaccount.domain.service;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import io.pivotal.bankaccount.event.account.AccountCreatedEvent;
import io.pivotal.bankaccount.event.account.AccountDetailsEvent;
import io.pivotal.bankaccount.event.account.CreateAccountEvent;
import io.pivotal.bankaccount.event.account.FundsTransferedEvent;
import io.pivotal.bankaccount.event.account.RequestAccountDetailsEvent;
import io.pivotal.bankaccount.event.account.TransferFundsEvent;

@MessagingGateway( name = "bankAccountService", defaultRequestChannel = "requestChannel" )
public interface BankAccountService {

	@Gateway( requestChannel = "createAccountChannel", replyChannel = "accountCreatedAggregateResult" )
	public AccountCreatedEvent createAccount( CreateAccountEvent event );

	@Gateway( requestChannel = "accountDetailsChannel", replyChannel = "accountDetailsAggregateResult" )
	public AccountDetailsEvent getAccount( RequestAccountDetailsEvent event );
	
	@Gateway( requestChannel = "requestTransferFundsChannel", replyChannel = "fundsTransferedAggregateResult" )
	public FundsTransferedEvent transfer( TransferFundsEvent event );
	
}
