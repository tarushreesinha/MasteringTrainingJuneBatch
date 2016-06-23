package com.cg.demo.test;

import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cg.demo.beans.Account;
import com.cg.demo.exceptions.InsufficientFundsException;
import com.cg.demo.exceptions.InsufficientInitialBalanceException;
import com.cg.demo.exceptions.InvalidAccountException;
import com.cg.demo.repo.AccountRepo;
import com.cg.demo.service.AccountService;
import com.cg.demo.service.AccountServiceImpl;

import static org.junit.Assert.*;

public class AccountTest {

	// Test cases for createAccount()
	
	//1. Account should be opened with min Rs. 500
	//2. If valid information is passed then system should create the account successfully.
	
	private AccountService service;
	@Mock private AccountRepo repo;
	
	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
		service = new AccountServiceImpl(repo);
	}
	
	@Test(expected=com.cg.demo.exceptions.InsufficientInitialBalanceException.class)
	public void accountShouldBeOpenedWithAtLeast500() throws InsufficientInitialBalanceException{
		service.createAccount(100);
	}
	
	@Test
	public void ifTheValidInfoIsPassedAccountShouldBeCreatedSuccessfully() throws InsufficientInitialBalanceException{
		Account a = new Account(1);
		a.setBalance(1000);
		when(repo.save(a)).thenReturn(true);
		assertEquals(a.getBalance()+"", service.createAccount(1000).getBalance()+"");
	}

	//1. if the account no is invalid system should throw exception while getting the balance
	//2. System should return Account details if the account no is valid
	
	@Test(expected=com.cg.demo.exceptions.InvalidAccountException.class)
	public void ifTheAccountNoIsInvalidSystemShouldThrowException() throws InvalidAccountException{
		when(repo.findById(1)).thenReturn(null);
		service.showBalance(1);

	}

	@Test
	public void ifTheAccountNoIsValidSystemShouldReturnAccountDetils() throws InvalidAccountException{
		Account a = new Account(4);
		a.setBalance(4000);
		when(repo.findById(4)).thenReturn(a);
		
		assertEquals(a.getBalance()+"", service.showBalance(4).getBalance()+"");
	}

	// 1. When amount passed is negative while withdrawing
	// 2. When account number is invalid while withdrawing
	// 3. When amount is greater than the available balance while withdrawing
	// 4. When account number and amount is valid while withdrawing.

	
	@Test(expected=java.lang.IllegalArgumentException.class)
	public void whenAmountPassedIsNegativeWhileWithdrawing() throws InvalidAccountException, InsufficientFundsException{
		service.withdraw(1, -765);
	}
	@Test(expected=com.cg.demo.exceptions.InvalidAccountException.class)
	public void whenAccountNumberIsInvalidWhileWithdrawing() throws InvalidAccountException, InsufficientFundsException{
		when(repo.findById(1)).thenReturn(null);
		service.withdraw(1, 1000);
	}

	@Test(expected=com.cg.demo.exceptions.InsufficientFundsException.class)
	public void whenAmountIsGreaterThanBalanceWhileWithdrawing() throws InvalidAccountException, InsufficientFundsException{
		Account a = new Account(1);
		a.setBalance(1000);
		when(repo.findById(1)).thenReturn(a);
		service.withdraw(1, 2000);
	}
	
	@Test
	public void whenAccountNumberAndAmountIsValidWhileWithdrawing() throws InvalidAccountException, InsufficientFundsException{
		Account a =new Account(1);
		a.setBalance(1000);
		
		when(repo.findById(1)).thenReturn(a);
		
		assertEquals(500.0+"", service.withdraw(1, 500).getBalance()+"");
	}
	
	//1. When amount passed is negative while depositing.
	//2. When account number passed is invalid while depositing.
	//3. When amount and account number are valid while depositing
	
	@Test(expected=java.lang.IllegalArgumentException.class)
	public void whenAmountPassedIsNegativeWhileDepositing() throws InvalidAccountException{
		service.deposit(1, -655);
	}

	@Test(expected=com.cg.demo.exceptions.InvalidAccountException.class)
	public void whenAccountNumberIsInvalidWhileDepositing() throws InvalidAccountException, InsufficientFundsException{
		when(repo.findById(1)).thenReturn(null);
		service.deposit(1, 1000);
	}

	@Test
	public void whenAccountNumberAndAmountIsValidWhileDepositing() throws InvalidAccountException{
		Account a =new Account(1);
		a.setBalance(1000);
		
		when(repo.findById(1)).thenReturn(a);
		
		assertEquals(1500.0+"", service.deposit(1, 500).getBalance()+"");
	}
	
}



















