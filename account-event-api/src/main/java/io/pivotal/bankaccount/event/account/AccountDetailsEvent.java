/**
 * 
 */
package io.pivotal.bankaccount.event.account;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.pivotal.bankaccount.domain.model.Account;
import io.pivotal.bankaccount.domain.model.AccountHistory;
import io.pivotal.bankaccount.event.ReadEvent;

/**
 * @author dmfrey
 *
 */
public class AccountDetailsEvent extends ReadEvent {

	private final UUID id;
	private final Account account;
	
	private Double balance;
	private List<AccountHistory> history;
	
	@JsonCreator
	public AccountDetailsEvent( @JsonProperty( "id" ) final UUID id, @JsonProperty( "account" ) final Account account ) {
		
		this.id = id;
		this.account = account;
		this.found = true;
		
	}

	/**
	 * @return the id
	 */
	public UUID getId() {
		
		return id;
	}

	/**
	 * @return the account
	 */
	public Account getAccount() {
		
		return account;
	}
	
	/**
	 * @return the balance
	 */
	public Double getBalance() {
		
		return balance;
	}

	/**
	 * @param balance the balance to set
	 */
	public void setBalance( Double balance ) {
		
		this.balance = balance;
	
	}

	/**
	 * @return the history
	 */
	public List<AccountHistory> getHistory() {
		
		return history;
	}

	/**
	 * @param history the history to set
	 */
	public void setHistory( List<AccountHistory> history ) {
		
		this.history = history;
	
	}

	public static AccountDetailsEvent notFound( final UUID id ) {
		
		AccountDetailsEvent event = new AccountDetailsEvent( id, null );
		event.found = false;
		
		return event;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AccountDetailsEvent [id=" + id + ", account=" + account + ", balance=" + balance + ", history="
				+ history + "]";
	}

	
}
