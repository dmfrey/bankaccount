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
import org.springframework.test.context.junit4.SpringRunner;

import io.pivotal.bankaccount.domain.model.Account;
import io.pivotal.bankaccount.event.account.AccountCreatedEvent;
import io.pivotal.bankaccount.event.account.CreateAccountEvent;

/**
 * @author dmfrey
 *
 */
@RunWith( SpringRunner.class )
@SpringBootTest
public class AccountPersistenceServiceIntegrationTest {

	@Autowired
	private AccountPersistenceService service;
	
	@Test
	public void testRequestCreateAccount() throws Exception {
	
		Account account = new Account();
		account.setAccountNumber( 1234567890L );
		
		CreateAccountEvent createAccountEvent = new CreateAccountEvent( UUID.randomUUID(), account, 100.00 );
		AccountCreatedEvent created = service.requestCreateAccount( createAccountEvent );
		assertThat( created, not( nullValue() ) );
		assertThat( created.getId(), not( nullValue() ) );
		assertThat( created.getJobId(), not( nullValue() ) );
		assertThat( created.getAccount(), not( nullValue() ) );
		assertThat( created.isCreated(), is( true ) );
		
	}
	
}
