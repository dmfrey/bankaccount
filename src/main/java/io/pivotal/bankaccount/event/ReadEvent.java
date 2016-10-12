/**
 * 
 */
package io.pivotal.bankaccount.event;

/**
 * @author dmfrey
 *
 */
public class ReadEvent implements Event {

	protected boolean found;

	/**
	 * @return the found
	 */
	public boolean isFound() {
	
		return found;
	}
	
}
