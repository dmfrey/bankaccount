/**
 * 
 */
package io.pivotal.bankaccount.event.account;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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
	
	private Double amount;

	@JsonCreator
	public AccountCreatedEvent( @JsonProperty( "id" ) final UUID id, @JsonProperty( "jobId" ) final UUID jobId, @JsonProperty( "account" ) final Account account, @JsonProperty( "amount" ) final Double amount ) {
		
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
	 * @param amount the amount to set
	 */
	public void setAmount( Double amount ) {
		
		this.amount = amount;
	
	}

	/**
	 * @return the amount
	 */
	public Double getAmount() {
		
		return amount;
	}

	public static AccountCreatedEvent notCreated( final UUID jobId, final Account account, final Double amount ) {
		
		AccountCreatedEvent event = new AccountCreatedEvent( null, jobId, account, amount );
		event.amount = amount;
		event.created = false;
		
		return event;
	}

}
