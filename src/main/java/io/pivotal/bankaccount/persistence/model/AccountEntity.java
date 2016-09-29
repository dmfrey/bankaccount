package io.pivotal.bankaccount.persistence.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import io.pivotal.bankaccount.domain.model.Account;

@Entity( name = "Account" )
public class AccountEntity {

	@Id
	@GenericGenerator( name = "uuid-gen", strategy = "uuid2" )
    @GeneratedValue( generator = "uuid-gen" )
	private UUID id;
	
	private Long accountNumber;
		
	public AccountEntity() { }

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
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountNumber == null) ? 0 : accountNumber.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof AccountEntity)) {
			return false;
		}
		AccountEntity other = (AccountEntity) obj;
		if (accountNumber == null) {
			if (other.accountNumber != null) {
				return false;
			}
		} else if (!accountNumber.equals(other.accountNumber)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AccountEntity [id=" + id + ", accountNumber=" + accountNumber + "]";
	}
	
	public Account toAccount() {
		
		Account account = new Account();
		account.setId( this.id );
		account.setAccountNumber( this.accountNumber );
		
		return account;
	}
	
	public static AccountEntity fromAccount( Account account ) {
		
		AccountEntity entity = new AccountEntity();
		entity.setId( account.getId() );
		entity.setAccountNumber( account.getAccountNumber() );
		
		return entity;
	}
	
}
