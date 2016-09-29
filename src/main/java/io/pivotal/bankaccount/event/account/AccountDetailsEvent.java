/**
 * 
 */
package io.pivotal.bankaccount.event.account;

import java.util.UUID;

import io.pivotal.bankaccount.domain.model.Account;
import io.pivotal.bankaccount.event.ReadEvent;

/**
 * @author dmfrey
 *
 */
public class AccountDetailsEvent extends ReadEvent {

	private final UUID id;
	private final Account account;
	
	public AccountDetailsEvent( final UUID id, final Account account ) {
		
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
	
	public static AccountDetailsEvent notFound( final UUID id ) {
		
		AccountDetailsEvent event = new AccountDetailsEvent( id, null );
		event.found = false;
		
		return event;
	}
	
}
