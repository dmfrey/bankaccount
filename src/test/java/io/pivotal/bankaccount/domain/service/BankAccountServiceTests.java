/**
 * 
 */
package io.pivotal.bankaccount.domain.service;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import io.pivotal.bankaccount.domain.model.Account;
import io.pivotal.bankaccount.event.account.AccountCreatedEvent;
import io.pivotal.bankaccount.event.account.AccountDetailsEvent;
import io.pivotal.bankaccount.event.account.CreateAccountEvent;
import io.pivotal.bankaccount.event.account.FundsTransferedEvent;
import io.pivotal.bankaccount.event.account.RequestAccountDetailsEvent;
import io.pivotal.bankaccount.event.account.RequestTransferFundsEvent;

/**
 * @author dmfrey
 *
 */
@RunWith( SpringRunner.class )
@SpringBootTest( webEnvironment = WebEnvironment.NONE )
@DirtiesContext
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
		assertThat( event.getAmount(), not( nullValue() ) );
		assertThat( event.getAmount(), equalTo( 100.00 ) );
		
	}
	
	@Test
	public void testGetAccount() throws Exception {
		
		Account account = new Account();
		account.setAccountNumber( 1234567891L );
		
		CreateAccountEvent createAccountEvent = new CreateAccountEvent( UUID.randomUUID(), account, 100.00 );
		AccountCreatedEvent created = service.createAccount( createAccountEvent );

		AccountDetailsEvent event = service.getAccount( new RequestAccountDetailsEvent( created.getId() ) );
		assertThat( event, not( nullValue() ) );
		assertThat( event.getId(), not( nullValue() ) );
		assertThat( event.getAccount(), not( nullValue() ) );
		assertThat( event.getBalance(), not( nullValue() ) );
		assertThat( event.getBalance(), equalTo( 100.00 ) );
		assertThat( event.getHistory(), not( nullValue() ) );
		assertThat( event.getHistory(), hasSize( 1 ) );
		
		Account found = event.getAccount();
		assertThat( found.getId(), not(  nullValue()  ) );
		assertThat( found.getAccountNumber(), equalTo( account.getAccountNumber() ) );
		
	}
	
	@Test
	public void testTransfer() throws Exception {

		Account from = new Account();
		from.setAccountNumber( 1234567890L );
		service.createAccount( new CreateAccountEvent( UUID.randomUUID(), from, 100.00 ) );

		Account to = new Account();
		to.setAccountNumber( 1234567891L );
		service.createAccount( new CreateAccountEvent( UUID.randomUUID(), to, 100.00 ) );

		RequestTransferFundsEvent request = new RequestTransferFundsEvent( UUID.randomUUID(), from.getAccountNumber(), to.getAccountNumber(), 50.00 );
		FundsTransferedEvent fundsTransferedEvent = service.transfer( request );
		assertThat( fundsTransferedEvent, not( nullValue() ) );
		assertThat( fundsTransferedEvent.getJobId(), not( nullValue() ) );
		assertThat( fundsTransferedEvent.getFromAccountNumber(), not( nullValue() ) );
		assertThat( fundsTransferedEvent.getToAccountNumber(), not( nullValue() ) );
		assertThat( fundsTransferedEvent.getAmount(), not( nullValue() ) );
		assertThat( fundsTransferedEvent.getAmount(), equalTo( 50.00 ) );
		assertThat( fundsTransferedEvent.isUpdated(), equalTo( true ) );

		AccountDetailsEvent fromEvent = service.getAccount( new RequestAccountDetailsEvent( from.getId() ) );
		assertThat( fromEvent, not( nullValue() ) );
		assertThat( fromEvent.getBalance(), equalTo( 50.00 ) );
		
		AccountDetailsEvent toEvent = service.getAccount( new RequestAccountDetailsEvent( to.getId() ) );
		assertThat( toEvent, not( nullValue() ) );
		assertThat( toEvent.getBalance(), equalTo( 150.00 ) );
		
	}
	
}
