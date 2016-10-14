/**
 * 
 */
package io.pivotal.bankaccount.persistence.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.pivotal.bankaccount.domain.model.AccountHistory;
import io.pivotal.bankaccount.event.account.AccountHistoryCreatedEvent;
import io.pivotal.bankaccount.event.account.AccountHistoryDetailsEvent;
import io.pivotal.bankaccount.event.account.CreateAccountHistoryEvent;
import io.pivotal.bankaccount.event.account.RequestAccountHistoryDetailsEvent;
import io.pivotal.bankaccount.event.account.SearchAccountHistoryDetailsEvent;
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
	
		service = new AccountHistoryPersistenceService( repository );
		
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

		given( repository.save( any( AccountHistoryEntity.class ) ) ).willReturn( null );
		
		CreateAccountHistoryEvent createAccountHistoryEvent = new CreateAccountHistoryEvent( jobId, accountHistory );
		AccountHistoryCreatedEvent created = service.requestCreateAccountHistory( createAccountHistoryEvent );
		assertThat( created, not( nullValue() ) );
		assertThat( created.getId(), is( nullValue() ) );
		assertThat( created.getJobId(), not( nullValue() ) );
		assertThat( created.getAccountHistory(), not( nullValue() ) );
		assertThat( created.isCreated(), is( false ) );
		
		verify( repository ).save( any( AccountHistoryEntity.class ) );
		
	}
	
	@Test
	public void testRequestAccountHistory() throws Exception {
	
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
		
		List<AccountHistoryEntity> accountHistories = new ArrayList<AccountHistoryEntity>();
		accountHistories.add( AccountHistoryEntity.fromAccountHistory( accountHistory ) );
		
		given( repository.findAllByAccountNumber( accountNumber ) ).willReturn( accountHistories );
		
		AccountHistoryDetailsEvent found = service.getAccountHistory( new RequestAccountHistoryDetailsEvent( accountNumber ) );
		assertThat( found, not( nullValue() ) );
		assertThat( found.getAccountHistories(), not( nullValue() ) );
		
		verify( repository ).findAllByAccountNumber( accountNumber );
		
	}

	@Test
	public void testSearchAccountHistoryByJobId() throws Exception {
	
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
		
		List<AccountHistoryEntity> accountHistories = new ArrayList<AccountHistoryEntity>();
		accountHistories.add( AccountHistoryEntity.fromAccountHistory( accountHistory ) );
		
		given( repository.findAllByJobId( jobId ) ).willReturn( accountHistories );
		
		AccountHistoryDetailsEvent found = service.searchAccountHistory( new SearchAccountHistoryDetailsEvent( jobId ) );
		assertThat( found, not( nullValue() ) );
		assertThat( found.getAccountHistories(), not( nullValue() ) );
		
		verify( repository ).findAllByJobId( jobId );
		
	}

}
