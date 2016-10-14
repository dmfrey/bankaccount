/**
 * 
 */
package io.pivotal.bankaccount.event.account;

import java.util.UUID;

import io.pivotal.bankaccount.event.RequestReadEvent;

/**
 * @author dmfrey
 *
 */
public class SearchAccountHistoryDetailsEvent extends RequestReadEvent {

	private final UUID jobId;
	
	public SearchAccountHistoryDetailsEvent( final UUID jobId ) {
		
		this.jobId = jobId;
		
	}

	/**
	 * @return the jobId
	 */
	public UUID getJobId() {
		
		return jobId;
	}
	
}
