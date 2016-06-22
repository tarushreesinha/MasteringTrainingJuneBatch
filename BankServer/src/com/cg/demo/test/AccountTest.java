package com.cg.demo.test;

import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cg.demo.beans.Account;
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
		
		service.showBalance(1);

	}

	@Test
	public void ifTheAccountNoIsValidSystemShouldReturnAccountDetils() throws InvalidAccountException{
		Account a = new Account(4);
		a.setBalance(4000);
		when(repo.findById(4)).thenReturn(a);
		
		assertEquals(a.getBalance()+"", service.showBalance(4).getBalance()+"");
	}

}



















