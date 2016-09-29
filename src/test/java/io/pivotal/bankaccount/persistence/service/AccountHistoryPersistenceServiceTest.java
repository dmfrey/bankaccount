/**
 * 
 */
package io.pivotal.bankaccount.persistence.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.pivotal.bankaccount.domain.model.AccountHistory;
import io.pivotal.bankaccount.event.account.AccountHistoryCreatedEvent;
import io.pivotal.bankaccount.event.account.CreateAccountHistoryEvent;
import io.pivotal.bankaccount.persistence.model.AccountHistoryEntity;
import io.pivotal.bankaccount.persistence.repository.AccountHistoryRepository;

/**
 * @author dmfrey
 *
 */
public class AccountHistoryPersistenceServiceTest {

	@Mock
	private AccountHistoryRepository repository;
	
	private AccountHistoryPersistenceService service;
	
	@Before
	public void setup() throws Exception {
		
		MockitoAnnotations.initMocks( this );
	
		service = new AccountHistoryPersistenceServiceImpl( repository );
		
	}

	@Test
	public void testRequestCreateAccountHistory() throws Exception {
	
		UUID jobId = UUID.randomUUID();
		Long accountNumber = 1234567890L;
		Double amount = 100.00;
		Long dateCreated = System.nanoTime();
		
		AccountHistory accountHistory = new AccountHistory();
		accountHistory.setId( UUID.randomUUID() );
		accountHistory.setJobId( jobId );
		accountHistory.setAccountNumber( accountNumber );
		accountHistory.setAmount( amount );
		accountHistory.setDateCreated( dateCreated );
		
		AccountHistoryEntity entity = AccountHistoryEntity.fromAccountHistory( accountHistory );
		given( repository.save( entity ) ).willReturn( entity );
		
		CreateAccountHistoryEvent createAccountHistoryEvent = new CreateAccountHistoryEvent( jobId, accountHistory );
		AccountHistoryCreatedEvent created = service.requestCreateAccountHistory( createAccountHistoryEvent );
		assertThat( created, not( nullValue() ) );
		assertThat( created.getId(), not( nullValue() ) );
		assertThat( created.getJobId(), not( nullValue() ) );
		assertThat( created.getAccountHistory(), not( nullValue() ) );
		assertThat( created.isCreated(), is( true ) );
		
		verify( repository ).save( entity );
		
	}
	
	@Test
	public void testRequestCreateAccountFailed() throws Exception {
	
		UUID jobId = UUID.randomUUID();
		Long accountNumber = 1234567890L;
		Double amount = 100.00;
		Long dateCreated = System.nanoTime();
		
		AccountHistory accountHistory = new AccountHistory();
		accountHistory.setId( UUID.randomUUID() );
		accountHistory.setJobId( jobId );
		accountHistory.setAccountNumber( accountNumber );
		accountHistory.setAmount( amount );
		accountHistory.setDateCreated( dateCreated );
		
		AccountHistoryEntity entity = AccountHistoryEntity.fromAccountHistory( accountHistory );
		given( repository.save( entity ) ).willReturn( null );
		
		CreateAccountHistoryEvent createAccountHistoryEvent = new CreateAccountHistoryEvent( jobId, accountHistory );
		AccountHistoryCreatedEvent created = service.requestCreateAccountHistory( createAccountHistoryEvent );
		assertThat( created, not( nullValue() ) );
		assertThat( created.getId(), is( nullValue() ) );
		assertThat( created.getJobId(), not( nullValue() ) );
		assertThat( created.getAccountHistory(), not( nullValue() ) );
		assertThat( created.isCreated(), is( false ) );
		
		verify( repository ).save( entity );
		
	}
	
}
