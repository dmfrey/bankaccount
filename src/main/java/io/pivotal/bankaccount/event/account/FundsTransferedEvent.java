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
	
	private boolean insuffientFunds;

	public FundsTransferedEvent( final UUID jobId, final Long fromAccountNumber, final Long toAccountNumber, final Double amount ) {
		
		this.jobId = jobId;
		this.fromAccountNumber = fromAccountNumber;
		this.toAccountNumber = toAccountNumber;
		this.amount = amount;
		this.updated = true;
		this.insuffientFunds = false;
		
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

	/**
	 * @return the insuffientFunds
	 */
	public boolean isInsuffientFunds() {
		
		return insuffientFunds;
	}

	public static FundsTransferedEvent notTransfered( final UUID jobId, final Long fromAccountNumber, final Long toAccountNumber, final Double amount ) {
		
		FundsTransferedEvent event = new FundsTransferedEvent( jobId, fromAccountNumber, toAccountNumber, amount );
		event.updated = false;
		
		return event;
	}

	public static FundsTransferedEvent insuffientFunds( final UUID jobId, final Long fromAccountNumber, final Long toAccountNumber, final Double amount ) {
		
		FundsTransferedEvent event = new FundsTransferedEvent( jobId, fromAccountNumber, toAccountNumber, amount );
		event.updated = false;
		event.insuffientFunds = true;
		
		return event;
	}

}
