/**
 * 
 */
package io.pivotal.bankaccount.event.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.pivotal.bankaccount.event.ReadEvent;

/**
 * @author dmfrey
 *
 */
public class AccountBalanceDetailsEvent extends ReadEvent {

	private final Long accountNumber;
	private final Double balance;
	
	@JsonCreator
	public AccountBalanceDetailsEvent( @JsonProperty( "accountNumber" ) final Long accountNumber, @JsonProperty( "balance" ) final Double balance ) {
		
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
