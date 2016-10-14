/**
 * 
 */
package io.pivotal.bankaccount.event.account;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.pivotal.bankaccount.event.RequestReadEvent;

/**
 * @author dmfrey
 *
 */
public class SearchAccountHistoryDetailsEvent extends RequestReadEvent {

	private final UUID jobId;
	
	@JsonCreator
	public SearchAccountHistoryDetailsEvent( @JsonProperty( "jobId" ) final UUID jobId ) {
		
		this.jobId = jobId;
		
	}

	/**
	 * @return the jobId
	 */
	public UUID getJobId() {
		
		return jobId;
	}
	
}
