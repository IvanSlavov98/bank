package com.example.bank.service.account;

import java.util.List;

import com.example.bank.entity.Account;

public interface AccountService {

	public Account createNewAccount(Account account);

	public List<Account> getAllAccounts();

	public Account getAccount(Integer accountID);

}
