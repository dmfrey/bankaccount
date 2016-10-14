/**
 * 
 */
package io.pivotal.bankaccount.event.account;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.pivotal.bankaccount.event.UpdateEvent;

/**
 * @author dmfrey
 *
 */
public class UpdateAccountBalanceEvent extends UpdateEvent {

	private final UUID jobId;
	private final Long accountNumber;
	private final Double amount;

	/**
	 * @param jobId
	 * @param accountNumber
	 * @param amount
	 */
	@JsonCreator
	public UpdateAccountBalanceEvent( @JsonProperty( "jobId" ) final UUID jobId, @JsonProperty( "accountNumber" ) final Long accountNumber, @JsonProperty( "amount" ) final Double amount ) {
		
		this.jobId = jobId;
		this.accountNumber = accountNumber;
		this.amount = amount;
		
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
	
}
