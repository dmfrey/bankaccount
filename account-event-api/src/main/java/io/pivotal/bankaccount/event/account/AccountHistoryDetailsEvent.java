/**
 * 
 */
package io.pivotal.bankaccount.event.account;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.pivotal.bankaccount.domain.model.AccountHistory;
import io.pivotal.bankaccount.event.ReadEvent;

/**
 * @author dmfrey
 *
 */
public class AccountHistoryDetailsEvent extends ReadEvent {

	private final List<AccountHistory> accountHistories;
	
	@JsonCreator
	public AccountHistoryDetailsEvent( @JsonProperty( "accountHistories" ) final List<AccountHistory> accountHistories ) {
		
		this.accountHistories = accountHistories;
		this.found = true;
		
	}

	/**
	 * @return the accountHistories
	 */
	public List<AccountHistory> getAccountHistories() {
		
		return accountHistories;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		return "AccountHistoryDetailsEvent [accountHistories=" + accountHistories + "]";
	}

	public static AccountHistoryDetailsEvent notFound() {
		
		AccountHistoryDetailsEvent event = new AccountHistoryDetailsEvent( null );
		event.found = false;
		
		return event;
	}
	
}
