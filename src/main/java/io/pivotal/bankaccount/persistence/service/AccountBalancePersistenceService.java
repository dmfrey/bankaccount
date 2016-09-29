/**
 * 
 */
package io.pivotal.bankaccount.persistence.service;

import io.pivotal.bankaccount.event.account.AccountBalanceUpdatedEvent;
import io.pivotal.bankaccount.event.account.RequestAccountBalanceUpdateEvent;

/**
 * @author dmfrey
 *
 */
public interface AccountBalancePersistenceService {

	AccountBalanceUpdatedEvent updateBalance( RequestAccountBalanceUpdateEvent event );
	
}
