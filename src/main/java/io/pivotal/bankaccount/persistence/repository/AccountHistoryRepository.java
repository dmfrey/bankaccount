/**
 * 
 */
package io.pivotal.bankaccount.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import io.pivotal.bankaccount.persistence.model.AccountHistoryEntity;

/**
 * @author dmfrey
 *
 */
public interface AccountHistoryRepository extends CrudRepository<AccountHistoryEntity, UUID> {

	@Query( "from AccountHistory where accountNumber = :accountNumber order by dateCreated desc" )
	List<AccountHistoryEntity> findAllByAccountNumber( @Param( "accountNumber" ) Long accountNumber );
	
}
