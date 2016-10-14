/**
 * 
 */
package io.pivotal.bankaccount.event.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.pivotal.bankaccount.event.RequestReadEvent;

/**
 * @author dmfrey
 *
 */
public class RequestAccountHistoryDetailsEvent extends RequestReadEvent {

	private final Long accountNumber;
	
	@JsonCreator
	public RequestAccountHistoryDetailsEvent( @JsonProperty( "accountNumber" ) final Long accountNumber ) {
		
		this.accountNumber = accountNumber;
		
	}

	/**
	 * @return the accountNumber
	 */
	public Long getAccountNumber() {
		
		return accountNumber;
	}
	
}
