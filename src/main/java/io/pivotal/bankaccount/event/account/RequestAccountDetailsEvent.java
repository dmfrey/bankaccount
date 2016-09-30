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
public class RequestAccountDetailsEvent extends RequestReadEvent {

	private final UUID id;
	
	public RequestAccountDetailsEvent( final UUID id ) {
		
		this.id = id;
		
	}

	/**
	 * @return the id
	 */
	public UUID getId() {
		
		return id;
	}
	
}
