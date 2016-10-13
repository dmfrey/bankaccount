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
public class AccountBalanceUpdatedEvent extends UpdatedEvent {

	private final UUID jobId;
	private final Long accountNumber;
	private final Double amount;
	
	/**
	 * @param jobId
	 * @param accountNumber
	 * @param amount
	 */
	public AccountBalanceUpdatedEvent( final UUID jobId, final Long accountNumber, final Double amount ) {
	
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		return "AccountBalanceUpdatedEvent [jobId=" + jobId + ", accountNumber=" + accountNumber + ", amount=" + amount
				+ "]";
	}

	public static AccountBalanceUpdatedEvent notUpdated( final UUID jobId, final Long accountNumber, final Double amount ) {
		
		AccountBalanceUpdatedEvent event = new AccountBalanceUpdatedEvent( jobId, accountNumber, amount );
		event.updated = false;
		
		return event;
	}
	
}
