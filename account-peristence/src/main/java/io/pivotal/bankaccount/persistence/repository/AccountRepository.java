package io.pivotal.bankaccount.persistence.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import io.pivotal.bankaccount.persistence.model.AccountEntity;

/**
 * @author dmfrey
 *
 */
public interface AccountRepository extends CrudRepository<AccountEntity, UUID> {

	AccountEntity findByAccountNumber( Long accountNumber );
	
}
