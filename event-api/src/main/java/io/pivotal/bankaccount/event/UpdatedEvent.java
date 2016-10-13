package io.pivotal.bankaccount.event;

/**
 * @author dmfrey
 *
 */
public class UpdatedEvent implements Event {

	protected boolean updated;
	
	public boolean isUpdated() {
		
		return updated;
	}

}
