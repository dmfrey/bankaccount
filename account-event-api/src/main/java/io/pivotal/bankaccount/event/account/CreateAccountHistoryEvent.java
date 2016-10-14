/**
 * 
 */
package io.pivotal.bankaccount.event.account;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.pivotal.bankaccount.domain.model.AccountHistory;
import io.pivotal.bankaccount.event.CreatedEvent;

/**
 * @author dmfrey
 *
 */
public class CreateAccountHistoryEvent extends CreatedEvent {

	private final UUID jobId;
	private final AccountHistory accountHistory;
	
	@JsonCreator
	public CreateAccountHistoryEvent( @JsonProperty( "jobId" ) final UUID jobId, @JsonProperty( "accountHistory" ) final AccountHistory accountHistory ) {
		
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

	/**
	 * @return
	 */
	public AccountHistory getAccountHistory() {
		
		return accountHistory;
	}

}
