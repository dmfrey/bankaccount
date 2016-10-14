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
public class TransferFundsEvent extends UpdateEvent {

	private final UUID jobId;
	private final Long fromAccountNumber;
	private final Long toAccountNumber;
	private final Double amount;
	
	/**
	 * @param accountNumber
	 * @param amount
	 */
	@JsonCreator
	public TransferFundsEvent( @JsonProperty( "jobId" ) final UUID jobId, @JsonProperty( "fromAccountNumber" ) final Long fromAccountNumber, @JsonProperty( "toAccountNumber" ) final Long toAccountNumber, @JsonProperty( "amount" ) final Double amount ) {
		
		this.jobId = jobId;
		this.fromAccountNumber = fromAccountNumber;
		this.toAccountNumber = toAccountNumber;
		this.amount = amount;
	
	}
	
	/**
	 * @return the jobId
	 */
	public UUID getJobId() {
	
		return jobId;
	}

	/**
	 * @return the toAccountNumber
	 */
	public Long getToAccountNumber() {
		
		return toAccountNumber;
	}

	/**
	 * @return the fromAccountNumber
	 */
	public Long getFromAccountNumber() {
		
		return fromAccountNumber;
	}

	/**
	 * @return the amount
	 */
	public Double getAmount() {
	
		return amount;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RequestTransferFundsEvent [jobId=" + jobId + ", fromAccountNumber=" + fromAccountNumber
				+ ", toAccountNumber=" + toAccountNumber + ", amount=" + amount + "]";
	}
	
}
