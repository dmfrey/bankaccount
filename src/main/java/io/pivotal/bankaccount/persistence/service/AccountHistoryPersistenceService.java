/**
 * 
 */
package io.pivotal.bankaccount.persistence.service;

import io.pivotal.bankaccount.event.account.AccountHistoryCreatedEvent;
import io.pivotal.bankaccount.event.account.CreateAccountHistoryEvent;

/**
 * @author dmfrey
 *
 */
public interface AccountHistoryPersistenceService {

	AccountHistoryCreatedEvent requestCreateAccountHistory( CreateAccountHistoryEvent event );

}
