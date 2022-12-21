package com.example.bank.service.account;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.bank.entity.Account;
import com.example.bank.error.AccountNotFoundException;
import com.example.bank.service.BaseService;

@Service
class AccountServiceImpl extends BaseService implements AccountService {

	@Override
	@Transactional
	public Account createNewAccount(Account newAccount) {
		newAccount.setDateOfCreation(LocalDate.now());
		newAccount.setId(null);
		this.entityManager.persist(newAccount);
		return newAccount;
	}

	public List<Account> getAllAccounts() {
		return this.entityManager.createQuery("From Account a ORDER BY a.firstName, a.lastName", Account.class)
				.setMaxResults(100).getResultList();
	}

	@Override
	public Account getAccount(Integer accountID) {
		return Optional.ofNullable(this.entityManager.find(Account.class, accountID))
				.orElseThrow(() -> new AccountNotFoundException("Account with id " + accountID + " not found!"));
	}

}
