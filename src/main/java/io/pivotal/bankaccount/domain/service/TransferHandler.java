/**
 * 
 */
package io.pivotal.bankaccount.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import io.pivotal.bankaccount.domain.model.Account;
import io.pivotal.bankaccount.event.account.AccountDetailsEvent;
import io.pivotal.bankaccount.event.account.FundsTransferedEvent;
import io.pivotal.bankaccount.event.account.RequestAccountDetailsEvent;
import io.pivotal.bankaccount.event.account.RequestTransferFundsEvent;

/**
 * @author dmfrey
 *
 */
@Component
public class TransferHandler {

	private final BankAccountService bankAccountService;
	
	@Autowired
	public TransferHandler( final BankAccountService bankAccountService ) {
		
		this.bankAccountService = bankAccountService;
	}
	
	@ServiceActivator( inputChannel = "requestTransferFundsChannel" )
	public FundsTransferedEvent transferFunds( Message<RequestTransferFundsEvent> message ) {
	
		RequestTransferFundsEvent request = message.getPayload();
		
		AccountDetailsEvent fromAccountEvent = bankAccountService.getAccount( new RequestAccountDetailsEvent( request.getFromAccountNumber() ) );
		if( fromAccountEvent.isFound() ) {
			
			if( fromAccountEvent.getBalance() < request.getAmount() ) {
				
				return FundsTransferedEvent.insuffientFunds( request.getJobId(), request.getFromAccountNumber(), request.getToAccountNumber(), request.getAmount() );
			} else {
				
				
			}

		}
		
		return FundsTransferedEvent.notTransfered( request.getJobId(), request.getFromAccountNumber(), request.getToAccountNumber(), request.getAmount() );
	}
	
}
