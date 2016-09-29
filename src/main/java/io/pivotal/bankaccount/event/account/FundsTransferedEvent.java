/**
 * 
 */
package io.pivotal.bankaccount.event.account;

import java.util.UUID;

import io.pivotal.bankaccount.event.UpdatedEvent;

/**
 * @author dmfrey
 *
 */
public class FundsTransferedEvent extends UpdatedEvent {

	private final UUID jobId;
	private final Long fromAccountNumber;
	private final Long toAccountNumber;
	private final Double amount;

	public FundsTransferedEvent( final UUID jobId, final Long fromAccountNumber, final Long toAccountNumber, final Double amount ) {
		
		this.jobId = jobId;
		this.fromAccountNumber = fromAccountNumber;
		this.toAccountNumber = toAccountNumber;
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
	 * @return the fromAccountNumber
	 */
	public Long getFromAccountNumber() {
		return fromAccountNumber;
	}

	/**
	 * @return the toAccountNumber
	 */
	public Long getToAccountNumber() {
		return toAccountNumber;
	}

	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}

	public static FundsTransferedEvent notTransfered( final UUID jobId, final Long fromAccountNumber, final Long toAccountNumber, final Double amount ) {
		
		FundsTransferedEvent event = new FundsTransferedEvent( jobId, fromAccountNumber, toAccountNumber, amount );
		event.updated = false;
		
		return event;
	}

}
