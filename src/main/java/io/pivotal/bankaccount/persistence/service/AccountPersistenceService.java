/**
 * 
 */
package io.pivotal.bankaccount.persistence.service;

import io.pivotal.bankaccount.event.account.AccountCreatedEvent;
import io.pivotal.bankaccount.event.account.AccountDetailsEvent;
import io.pivotal.bankaccount.event.account.CreateAccountEvent;
import io.pivotal.bankaccount.event.account.RequestAccountDetailsEvent;

/**
 * @author dmfrey
 *
 */
public interface AccountPersistenceService {

	AccountCreatedEvent requestCreateAccount( CreateAccountEvent event );

	AccountDetailsEvent requestAccountDetails( RequestAccountDetailsEvent event );
	
}
