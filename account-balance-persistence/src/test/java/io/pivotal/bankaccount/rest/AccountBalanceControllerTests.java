/**
 * 
 */
package io.pivotal.bankaccount.rest;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import io.pivotal.bankaccount.event.account.AccountBalanceDetailsEvent;
import io.pivotal.bankaccount.event.account.RequestAccountBalanceDetailsEvent;
import io.pivotal.bankaccount.persistence.service.AccountBalancePersistenceService;

/**
 * @author dmfrey
 *
 */
@RunWith( SpringRunner.class )
@WebMvcTest( AccountBalanceController.class )
public class AccountBalanceControllerTests {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AccountBalancePersistenceService service;
	
	@Test
	public void testGetBalance() throws Exception {
	
		Long accountNumber = 1234567890L;
		Double balance = 100.00;
		
		given( service.getBalance( any( RequestAccountBalanceDetailsEvent.class ) ) ).willReturn( new AccountBalanceDetailsEvent( accountNumber, balance ) );
		
		mockMvc.perform( get( "/" + String.valueOf( accountNumber ) )
				.accept( MediaType.APPLICATION_JSON ) )
				.andExpect( status().isOk() )
				.andDo( print() );
		
		verify( service, times( 1 ) ).getBalance( any( RequestAccountBalanceDetailsEvent.class ) );
		
	}
	
}
