/**
 * 
 */
package io.pivotal.bankaccount.domain.model;

import java.util.UUID;

/**
 * @author dmfrey
 *
 */
public class Account {

	private UUID id;
	private Long accountNumber;

	public Account() { }

	/**
	 * @return the id
	 */
	public UUID getId() {
		
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId( UUID id ) {
		
		this.id = id;
	
	}

	/**
	 * @return the accountNumber
	 */
	public Long getAccountNumber() {
		
		return accountNumber;
	}

	/**
	 * @param accountNumber the accountNumber to set
	 */
	public void setAccountNumber( Long accountNumber ) {
		
		this.accountNumber = accountNumber;
	
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Account [id=" + id + ", accountNumber=" + accountNumber + "]";
	}

}
