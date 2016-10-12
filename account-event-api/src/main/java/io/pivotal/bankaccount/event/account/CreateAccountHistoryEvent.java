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
public class CreateAccountHistoryEvent extends CreatedEvent {

	private final UUID jobId;
	private final AccountHistory accountHistory;
	
	public CreateAccountHistoryEvent( final UUID jobId, final AccountHistory accountHistory ) {
		
		this.jobId = jobId;
		this.accountHistory = accountHistory;
		this.created = true;
		
	}
	
	/**
	 * @return the jobId
	 */
	public UUID getJobId() {
		
		return jobId;
	}

	public AccountHistory getAccountHistory() {
		
		return accountHistory;
	}

}
