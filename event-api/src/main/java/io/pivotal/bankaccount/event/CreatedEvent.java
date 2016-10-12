/**
 * 
 */
package io.pivotal.bankaccount.event;

/**
 * @author dmfrey
 *
 */
public class CreatedEvent implements Event {

	protected boolean created;
	
	public boolean isCreated() {
		
		return created;
	}
	
}
