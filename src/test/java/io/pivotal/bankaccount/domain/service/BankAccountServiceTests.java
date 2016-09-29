/**
 * 
 */
package io.pivotal.bankaccount.domain.service;

import static org.hamcrest.CoreMatchers.equalTo;
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

import io.pivotal.bankaccount.domain.model.Account;
import io.pivotal.bankaccount.event.account.AccountCreatedEvent;
import io.pivotal.bankaccount.event.account.AccountDetailsEvent;
import io.pivotal.bankaccount.event.account.CreateAccountEvent;
import io.pivotal.bankaccount.event.account.RequestAccountDetailsEvent;

/**
 * @author dmfrey
 *
 */
@RunWith( SpringRunner.class )
@SpringBootTest( webEnvironment = WebEnvironment.NONE )
public class BankAccountServiceTests {

	@Autowired
	private BankAccountService service;
	
	@Test
	public void testCreateAccount() throws Exception {
		
		Account account = new Account();
		account.setAccountNumber( 1234567890L );
		
		CreateAccountEvent createAccountEvent = new CreateAccountEvent( UUID.randomUUID(), account, 100.00 );
		AccountCreatedEvent event = service.createAccount( createAccountEvent );
		assertThat( event, not( nullValue() ) );
		assertThat( event.getId(), not( nullValue() ) );
		assertThat( event.getJobId(), not( nullValue() ) );
		assertThat( event.getAccount(), not( nullValue() ) );
		
	}
	
	@Test
	public void testGetAccount() throws Exception {
		
		Account account = new Account();
		account.setAccountNumber( 1234567890L );
		
		CreateAccountEvent createAccountEvent = new CreateAccountEvent( UUID.randomUUID(), account, 100.00 );
		AccountCreatedEvent created = service.createAccount( createAccountEvent );

		AccountDetailsEvent event = service.getAccount( new RequestAccountDetailsEvent( created.getId() ) );
		assertThat( event, not( nullValue() ) );
		assertThat( event.getId(), not( nullValue() ) );
		assertThat( event.getAccount(), not( nullValue() ) );
		
		Account found = event.getAccount();
		assertThat( found.getId(), not(  nullValue()  ) );
		assertThat( found.getAccountNumber(), equalTo( account.getAccountNumber() ) );
		
	}
	
}
