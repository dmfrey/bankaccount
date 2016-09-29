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
public class AccountCreatedEvent extends CreatedEvent {

	private final UUID id;
	private final UUID jobId;
	private final Account account;
	private final Double amount;

	public AccountCreatedEvent( final UUID id, final UUID jobId, final Account account, final Double amount ) {
		
		this.id = id;
		this.jobId = jobId;
		this.account = account;
		this.amount = amount;
		this.created = true;
		
	}
	
	/**
	 * @return the id
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * @return the jobId
	 */
	public UUID getJobId() {
		
		return jobId;
	}

	/**
	 * @return
	 */
	public Account getAccount() {
		
		return account;
	}
	
	/**
	 * @return the amount
	 */
	public Double getAmount() {
		
		return amount;
	}

	public static AccountCreatedEvent notCreated( final UUID jobId, final Account account, final Double amount ) {
		
		AccountCreatedEvent event = new AccountCreatedEvent( null, jobId, account, amount );
		event.created = false;
		
		return event;
	}

}
