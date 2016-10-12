/**
 * 
 */
package io.pivotal.bankaccount.event.account;

import io.pivotal.bankaccount.event.RequestReadEvent;

/**
 * @author dmfrey
 *
 */
public class RequestAccountBalanceDetailsEvent extends RequestReadEvent {

	private final Long accountNumber;

	public RequestAccountBalanceDetailsEvent( final Long accountNumber ) {
		
		this.accountNumber = accountNumber;
		
	}

	/**
	 * @return the accountNumber
	 */
	public Long getAccountNumber() {
		
		return accountNumber;
	}

}
