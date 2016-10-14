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

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import io.pivotal.bankaccount.domain.model.Account;
import io.pivotal.bankaccount.event.account.AccountCreatedEvent;
import io.pivotal.bankaccount.event.account.AccountDetailsEvent;
import io.pivotal.bankaccount.event.account.CreateAccountEvent;
import io.pivotal.bankaccount.event.account.RequestAccountDetailsEvent;
import io.pivotal.bankaccount.persistence.model.AccountEntity;
import io.pivotal.bankaccount.persistence.repository.AccountRepository;

/**
 * @author dmfrey
 *
 */
public class AccountPersistenceServiceTest {

	@Mock
	private AccountRepository repository;
	
	@Mock
	private RestTemplate restTemplate;
	
	private AccountPersistenceService service;
	
	@Before
	public void setup() throws Exception {
		
		MockitoAnnotations.initMocks( this );
	
		service = new AccountPersistenceService( repository, restTemplate );
		
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
	
	@Test
	public void testRequestAccountDetails() throws Exception {
	
		Account account = new Account();
		account.setId( UUID.randomUUID() );
		account.setAccountNumber( 1234567890L );
		
		AccountEntity entity = AccountEntity.fromAccount( account );
		given( repository.save( entity ) ).willReturn( entity );
		given( repository.findOne( any( UUID.class ) ) ).willReturn( entity );
		
		CreateAccountEvent createAccountEvent = new CreateAccountEvent( UUID.randomUUID(), account, 100.00 );
		service.requestCreateAccount( createAccountEvent );

		RequestAccountDetailsEvent requestAccountDetailsEvent = new RequestAccountDetailsEvent( account.getId() );
		AccountDetailsEvent found = service.requestAccountDetails( requestAccountDetailsEvent );
		assertThat( found, not( nullValue() ) );
		assertThat( found.getId(), not( nullValue() ) );
		assertThat( found.getAccount(), not( nullValue() ) );
		assertThat( found.isFound(), is( true ) );
		
		verify( repository ).save( entity );
		verify( repository ).findOne( account.getId() );
		
	}

	@Test
	public void testRequestAccountDetailsNotFound() throws Exception {
	
		given( repository.findOne( any( UUID.class ) ) ).willReturn( null );
		
		UUID id = UUID.randomUUID();
		
		RequestAccountDetailsEvent requestAccountDetailsEvent = new RequestAccountDetailsEvent( id );
		AccountDetailsEvent found = service.requestAccountDetails( requestAccountDetailsEvent );
		assertThat( found, not( nullValue() ) );
		assertThat( found.getId(), not( nullValue() ) );
		assertThat( found.getAccount(), is( nullValue() ) );
		assertThat( found.isFound(), is( false ) );
		
		verify( repository ).findOne( id );
		
	}

}
