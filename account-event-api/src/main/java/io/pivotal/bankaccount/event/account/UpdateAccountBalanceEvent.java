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
public class UpdaateAccountBalanceEvent extends UpdateEvent {

	private final UUID jobId;
	private final Long accountNumber;
	private final Double amount;

	public UpdaateAccountBalanceEvent( final UUID jobId, final Long accountNumber, final Double amount ) {
		
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
