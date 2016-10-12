/**
 * 
 */
package io.pivotal.bankaccount.event.account;

import java.util.UUID;

import io.pivotal.bankaccount.event.UpdateEvent;

/**
 * @author dmfrey
 *
 */
public class RequestTransferFundsEvent extends UpdateEvent {

	private final UUID jobId;
	private final Long fromAccountNumber;
	private final Long toAccountNumber;
	private final Double amount;
	
	/**
	 * @param accountNumber
	 * @param amount
	 */
	public RequestTransferFundsEvent( final UUID jobId, final Long fromAccountNumber, final Long toAccountNumber, final Double amount ) {
		
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
