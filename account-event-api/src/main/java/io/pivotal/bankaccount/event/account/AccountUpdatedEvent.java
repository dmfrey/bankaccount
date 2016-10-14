/**
 * 
 */
package io.pivotal.bankaccount.event.account;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.pivotal.bankaccount.event.UpdatedEvent;

/**
 * @author dmfrey
 *
 */
public class AccountUpdatedEvent extends UpdatedEvent {

	private final UUID jobId;
	private final Long accountNumber;
	private final Double amount;
	
	/**
	 * @param accountNumber
	 * @param amount
	 */
	@JsonCreator
	public AccountUpdatedEvent( @JsonProperty( "jobId" ) final UUID jobId, @JsonProperty( "accountNumber" ) final Long accountNumber, @JsonProperty( "amount" ) final Double amount ) {
		
		this.jobId = jobId;
		this.accountNumber = accountNumber;
		this.amount = amount;
		this.updated = true;
		
	}
	
	/**
	 * @return the jobId
	 */
	public UUID getJobId() {
		
		return jobId;
	}

	/**
	 * @return the accountNumber
	 */
	public Long getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}

	public static AccountUpdatedEvent fundsNotAdded( final UUID jobId, final Long accountNumber, final Double amount ) {
		
		AccountUpdatedEvent event = new AccountUpdatedEvent( jobId, accountNumber, amount );
		event.updated = false;
		
		return event;
	}
	
}
