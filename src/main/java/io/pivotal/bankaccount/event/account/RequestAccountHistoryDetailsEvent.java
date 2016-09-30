/**
 * 
 */
package io.pivotal.bankaccount.event.account;

import io.pivotal.bankaccount.event.RequestReadEvent;

/**
 * @author dmfrey
 *
 */
public class RequestAccountHistoryDetailsEvent extends RequestReadEvent {

	private final Long accountNumber;
	
	public RequestAccountHistoryDetailsEvent( final Long accountNumber ) {
		
		this.accountNumber = accountNumber;
		
	}

	/**
	 * @return the accountNumber
	 */
	public Long getAccountNumber() {
		
		return accountNumber;
	}
	
}
