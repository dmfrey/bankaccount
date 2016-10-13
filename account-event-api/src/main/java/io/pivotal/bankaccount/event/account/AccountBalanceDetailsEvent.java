/**
 * 
 */
package io.pivotal.bankaccount.event.account;

import io.pivotal.bankaccount.event.ReadEvent;

/**
 * @author dmfrey
 *
 */
public class AccountBalanceDetailsEvent extends ReadEvent {

	private final Long accountNumber;
	private final Double balance;
	
	public AccountBalanceDetailsEvent( final Long accountNumber, final Double balance ) {
		
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.found = true;
		
	}

	/**
	 * @return the accountNumber
	 */
	public Long getAccountNumber() {
		
		return accountNumber;
	}

	/**
	 * @return the balance
	 */
	public Double getBalance() {
		
		return balance;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		return "AccountBalanceDetailsEvent [accountNumber=" + accountNumber + ", balance=" + balance + "]";
	}

	public static AccountBalanceDetailsEvent notFound( final Long accountNumber ) {
		
		AccountBalanceDetailsEvent event = new AccountBalanceDetailsEvent( accountNumber, null );
		event.found = false;
		
		return event;
	}
	
}
