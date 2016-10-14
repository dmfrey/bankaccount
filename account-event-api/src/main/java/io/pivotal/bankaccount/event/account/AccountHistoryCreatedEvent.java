/**
 * 
 */
package io.pivotal.bankaccount.event.account;

import java.util.UUID;

import io.pivotal.bankaccount.domain.model.AccountHistory;
import io.pivotal.bankaccount.event.CreatedEvent;

/**
 * @author dmfrey
 *
 */
public class AccountHistoryCreatedEvent extends CreatedEvent {

	private final UUID id;
	private final UUID jobId;
	private final AccountHistory accountHistory;

	public AccountHistoryCreatedEvent( final UUID id, final UUID jobId, final AccountHistory accountHistory ) {
		
		this.id = id;
		this.jobId = jobId;
		this.accountHistory = accountHistory;
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
	public AccountHistory getAccountHistory() {
		
		return accountHistory;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
	
		return "AccountHistoryCreatedEvent [id=" + id + ", jobId=" + jobId + ", accountHistory=" + accountHistory + "]";
	}

	public static AccountHistoryCreatedEvent notCreated( final UUID jobId, final AccountHistory accountHistory ) {
		
		AccountHistoryCreatedEvent event = new AccountHistoryCreatedEvent( null, jobId, accountHistory );
		event.created = false;
		
		return event;
	}

}
