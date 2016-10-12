/**
 * 
 */
package io.pivotal.bankaccount.event.account;

import java.util.UUID;

import io.pivotal.bankaccount.event.RequestReadEvent;

/**
 * @author dmfrey
 *
 */
public class RequestAccountDetailsEvent extends RequestReadEvent {

	private final UUID id;
	private final Long accountNumber;
	
	public RequestAccountDetailsEvent( final UUID id ) {
		
		this.id = id;
		this.accountNumber = null;
		
	}

	public RequestAccountDetailsEvent( final Long accountNumber ) {
		
		this.id = null;
		this.accountNumber = accountNumber;
		
	}

	/**
	 * @return the id
	 */
	public UUID getId() {
		
		return id;
	}

	/**
	 * @return the accountNumber
	 */
	public Long getAccountNumber() {
		
		return accountNumber;
	}
	
}
