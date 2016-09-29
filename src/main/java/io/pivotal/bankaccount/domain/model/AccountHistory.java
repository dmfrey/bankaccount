/**
 * 
 */
package io.pivotal.bankaccount.domain.model;

import java.util.UUID;

/**
 * @author dmfrey
 *
 */
public class AccountHistory {

	private UUID id;
	private UUID jobId;
	private Long accountNumber;
	private Double amount;
	private Long dateCreated;

	public AccountHistory() { }

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
	 * @return the jobId
	 */
	public UUID getJobId() {
	
		return jobId;
	}

	/**
	 * @param jobId the jobId to set
	 */
	public void setJobId( UUID jobId ) {
		
		this.jobId = jobId;
	
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

	/**
	 * @return the amount
	 */
	public Double getAmount() {
		
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount( Double amount ) {
		
		this.amount = amount;
	
	}

	/**
	 * @return the dateCreated
	 */
	public long getDateCreated() {
		
		return dateCreated;
	}

	/**
	 * @param dateCreated the dateCreated to set
	 */
	public void setDateCreated( long dateCreated ) {
		
		this.dateCreated = dateCreated;
	
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountNumber == null) ? 0 : accountNumber.hashCode());
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + (int) (dateCreated ^ (dateCreated >>> 32));
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((jobId == null) ? 0 : jobId.hashCode());
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
		if (!(obj instanceof AccountHistory)) {
			return false;
		}
		AccountHistory other = (AccountHistory) obj;
		if (accountNumber == null) {
			if (other.accountNumber != null) {
				return false;
			}
		} else if (!accountNumber.equals(other.accountNumber)) {
			return false;
		}
		if (amount == null) {
			if (other.amount != null) {
				return false;
			}
		} else if (!amount.equals(other.amount)) {
			return false;
		}
		if (dateCreated != other.dateCreated) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (jobId == null) {
			if (other.jobId != null) {
				return false;
			}
		} else if (!jobId.equals(other.jobId)) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AccountHistoryEntity [id=" + id + ", jobId=" + jobId + ", accountNumber=" + accountNumber + ", amount="
				+ amount + ", dateCreated=" + dateCreated + "]";
	}
	
}
