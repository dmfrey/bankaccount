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

import io.pivotal.bankaccount.domain.model.Account;
import io.pivotal.bankaccount.event.account.AccountCreatedEvent;
import io.pivotal.bankaccount.event.account.CreateAccountEvent;
import io.pivotal.bankaccount.persistence.model.AccountEntity;
import io.pivotal.bankaccount.persistence.repository.AccountRepository;

/**
 * @author dmfrey
 *
 */
public class AccountPersistenceServiceTest {

	@Mock
	private AccountRepository repository;
	
	private AccountPersistenceService service;
	
	@Before
	public void setup() throws Exception {
		
		MockitoAnnotations.initMocks( this );
	
		service = new AccountPersistenceServiceImpl( repository );
		
	}

	@Test
	public void testRequestCreateAccount() throws Exception {
	
		Account account = new Account();
		account.setId( UUID.randomUUID() );
		account.setAccountNumber( 1234567890L );
		
		AccountEntity entity = AccountEntity.fromAccount( account );
		given( repository.save( entity ) ).willReturn( entity );
		
		CreateAccountEvent createAccountEvent = new CreateAccountEvent( UUID.randomUUID(), account, 100.00 );
		AccountCreatedEvent created = service.requestCreateAccount( createAccountEvent );
		assertThat( created, not( nullValue() ) );
		assertThat( created.getId(), not( nullValue() ) );
		assertThat( created.getJobId(), not( nullValue() ) );
		assertThat( created.getAccount(), not( nullValue() ) );
		assertThat( created.isCreated(), is( true ) );
		
		verify( repository ).save( entity );
		
	}
	
	@Test
	public void testRequestCreateAccountFailed() throws Exception {
	
		Account account = new Account();
		account.setId( UUID.randomUUID() );
		account.setAccountNumber( 1234567890L );
		
		AccountEntity entity = AccountEntity.fromAccount( account );
		given( repository.save( entity ) ).willReturn( null );
		
		CreateAccountEvent createAccountEvent = new CreateAccountEvent( UUID.randomUUID(), account, 100.00 );
		AccountCreatedEvent created = service.requestCreateAccount( createAccountEvent );
		assertThat( created, not( nullValue() ) );
		assertThat( created.getId(), is( nullValue() ) );
		assertThat( created.getJobId(), not( nullValue() ) );
		assertThat( created.getAccount(), not( nullValue() ) );
		assertThat( created.isCreated(), is( false ) );
		
		verify( repository ).save( entity );
		
	}
	
}
