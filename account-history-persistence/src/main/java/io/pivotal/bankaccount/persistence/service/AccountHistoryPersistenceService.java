/**
 * 
 */
package io.pivotal.bankaccount.persistence.service;

import io.pivotal.bankaccount.event.account.AccountHistoryCreatedEvent;
import io.pivotal.bankaccount.event.account.AccountHistoryDetailsEvent;
import io.pivotal.bankaccount.event.account.CreateAccountHistoryEvent;
import io.pivotal.bankaccount.event.account.RequestAccountHistoryDetailsEvent;

/**
 * @author dmfrey
 *
 */
public interface AccountHistoryPersistenceService {

	AccountHistoryCreatedEvent requestCreateAccountHistory( CreateAccountHistoryEvent event );

	AccountHistoryDetailsEvent getAccountHistory( RequestAccountHistoryDetailsEvent event );
	
}
