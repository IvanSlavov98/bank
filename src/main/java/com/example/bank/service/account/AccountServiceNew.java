package com.example.bank.service.account;

import java.util.List;

import com.example.bank.entity.Account;

public interface AccountServiceNew {

	public List<Account> getAllAccounts();
	
	public Account createNewAccount(Account newAccount);
	
	public void updateAccount(Account account);
	
	public void updateAccount2(Account account);
	
	public void deleteAccountById(int id);
	
	public void deleteAccountById2(Integer id);
	
	public Account createNewAccount2(Account newAccount);

	void updateAccountWithTransaction(Account account);
	
	//public List<Account> getMultipleAccounts(List<Integer> ids);
	
	public Account getAccountById(int Id);
	
}
