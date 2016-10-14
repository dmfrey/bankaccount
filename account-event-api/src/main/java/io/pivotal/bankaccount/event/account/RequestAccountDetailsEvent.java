/**
 * 
 */
package io.pivotal.bankaccount.event.account;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.pivotal.bankaccount.event.RequestReadEvent;

/**
 * @author dmfrey
 *
 */
public class RequestAccountDetailsEvent extends RequestReadEvent {

	private final UUID id;
	private final Long accountNumber;

	@JsonCreator
	public RequestAccountDetailsEvent( @JsonProperty( "id" ) final UUID id ) {
		
		this.id = id;
		this.accountNumber = null;
		
	}

	@JsonCreator
	public RequestAccountDetailsEvent( @JsonProperty( "accountNumber" ) final Long accountNumber ) {
		
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
