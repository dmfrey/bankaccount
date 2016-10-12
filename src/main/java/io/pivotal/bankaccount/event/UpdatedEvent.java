package io.pivotal.bankaccount.event;

public class UpdatedEvent implements Event {

	protected boolean updated;
	
	public boolean isUpdated() {
		
		return updated;
	}

}
