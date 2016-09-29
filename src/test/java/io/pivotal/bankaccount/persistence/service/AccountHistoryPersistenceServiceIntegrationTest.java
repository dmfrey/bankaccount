/**
 * 
 */
package io.pivotal.bankaccount.persistence.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import io.pivotal.bankaccount.domain.model.AccountHistory;
import io.pivotal.bankaccount.event.account.AccountHistoryCreatedEvent;
import io.pivotal.bankaccount.event.account.CreateAccountHistoryEvent;

/**
 * @author dmfrey
 *
 */
@RunWith( SpringRunner.class )
@SpringBootTest( webEnvironment = WebEnvironment.NONE )
public class AccountHistoryPersistenceServiceIntegrationTest {

	@Autowired
	private AccountHistoryPersistenceService service;
	
	@Test
	public void testRequestCreateAccountHistory() throws Exception {
	
		UUID jobId = UUID.randomUUID();
		Long accountNumber = 1234567890L;
		Double amount = 100.00;
		Long dateCreated = System.nanoTime();
		
		AccountHistory accountHistory = new AccountHistory();
		accountHistory.setJobId( jobId );
		accountHistory.setAccountNumber( accountNumber );
		accountHistory.setAmount( amount );
		accountHistory.setDateCreated( dateCreated );
		
		CreateAccountHistoryEvent createAccountHistoryEvent = new CreateAccountHistoryEvent( jobId, accountHistory );
		AccountHistoryCreatedEvent created = service.requestCreateAccountHistory( createAccountHistoryEvent );
		assertThat( created, not( nullValue() ) );
		assertThat( created.getId(), not( nullValue() ) );
		assertThat( created.getJobId(), not( nullValue() ) );
		assertThat( created.getAccountHistory(), not( nullValue() ) );
		assertThat( created.isCreated(), is( true ) );
		
	}
	
}
