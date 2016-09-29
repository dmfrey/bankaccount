/**
 * 
 */
package io.pivotal.bankaccount.persistence.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import io.pivotal.bankaccount.persistence.model.AccountHistoryEntity;

/**
 * @author dmfrey
 *
 */
public interface AccountHistoryRepository extends CrudRepository<AccountHistoryEntity, UUID> {

}
