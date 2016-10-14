/**
 * 
 */
package io.pivotal.bankaccount.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.pivotal.bankaccount.domain.model.Account;
import io.pivotal.bankaccount.event.account.AccountBalanceUpdatedEvent;
import io.pivotal.bankaccount.event.account.AccountCreatedEvent;
import io.pivotal.bankaccount.event.account.UpdateAccountBalanceEvent;
import io.pivotal.bankaccount.persistence.service.AccountBalancePersistenceService;

/**
 * @author dmfrey
 *
 */
public class AccountBalanceServiceTests {

	private AccountBalanceService service;
	
	@Mock
	private AccountBalancePersistenceService persistence;
	
	@Before
	public void setup() throws Exception {
		
		MockitoAnnotations.initMocks( this );
		
		service = new AccountBalanceService( persistence );
	}
	
	@Test
	public void testUpdateBalance() throws Exception {
		
		UUID jobId = UUID.randomUUID();
		Long accountNumber = 1234567890L;
		Double amount = 100.00;
		
		AccountBalanceUpdatedEvent event = new AccountBalanceUpdatedEvent( jobId, accountNumber, amount );
		given( persistence.updateBalance( any( UpdateAccountBalanceEvent.class ) ) ).willReturn( event );
		
		Account account = new Account();
		account.setId( UUID.randomUUID() );
		account.setAccountNumber( accountNumber );
		
		AccountCreatedEvent request = new AccountCreatedEvent( jobId, account.getId(), account, amount );
		AccountBalanceUpdatedEvent updated = service.updateBalance( request );
		assertThat( updated, not( nullValue() ) );
		assertThat( updated.getJobId(), not( nullValue() ) );
		assertThat( updated.getJobId(), equalTo( jobId ) );
		assertThat( updated.getAccountNumber(), not( nullValue() ) );
		assertThat( updated.getAccountNumber(), equalTo( accountNumber ) );
		assertThat( updated.getAmount(), not( nullValue() ) );
		assertThat( updated.getAmount(), equalTo( amount ) );
		
		verify( persistence, times( 1 ) ).updateBalance( any( UpdateAccountBalanceEvent.class ) );
		
	}
	
}
