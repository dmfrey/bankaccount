/**
 * 
 */
package io.pivotal.bankaccount.event.account;

import java.util.UUID;

import io.pivotal.bankaccount.domain.model.Account;
import io.pivotal.bankaccount.event.CreatedEvent;

/**
 * @author dmfrey
 *
 */
public class CreateAccountEvent extends CreatedEvent {

	private final UUID jobId;
	private final Account account;
	private final Double initialAmount;
	
	public CreateAccountEvent( final UUID jobId, final Account account, final Double initialAmount ) {
		
		this.jobId = jobId;
		this.account = account;
		this.initialAmount = initialAmount;
		
	}
	
	/**
	 * @return the jobId
	 */
	public UUID getJobId() {
		
		return jobId;
	}

	public Account getAccount() {
		
		return account;
	}

	/**
	 * @return the initialAmount
	 */
	public Double getInitialAmount() {
		
		return initialAmount;
	}
	
}
