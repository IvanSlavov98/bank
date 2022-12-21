package com.example.bank.service.transaction;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bank.entity.Account;
import com.example.bank.entity.Transaction;
import com.example.bank.error.InsufficientFundsException;
import com.example.bank.service.BaseService;
import com.example.bank.service.account.AccountService;

@Service
public class TransactionServiceImpl extends BaseService implements TransactionService {

	@Autowired
	AccountService accountService;

	@Override
	@Transactional
	public Transaction makeTransaction(Integer senderID, Integer receiverID, BigDecimal amount) {

		Account senderAccount = accountService.getAccount(senderID);

		Account receiverAccount = accountService.getAccount(receiverID);

		if (senderAccount.getBalance().compareTo(amount) == -1) {
			throw new InsufficientFundsException("Insufficient Funds!");
		}

		senderAccount.setBalance(senderAccount.getBalance().subtract(amount));
		receiverAccount.setBalance(receiverAccount.getBalance().add(amount));

		this.entityManager.persist(new Transaction(amount, receiverAccount, senderAccount));

		Transaction senderTransaction = new Transaction(amount.negate(), senderAccount, receiverAccount);

		this.entityManager.persist(senderTransaction);
		return senderTransaction;

	}

	@Override
	public List<Transaction> getTransactions(Integer accountID) {

		accountService.getAccount(accountID);

		return this.entityManager.createNamedQuery("getTransactionsOfUser", Transaction.class)
				.setParameter(1, accountID).setMaxResults(100).getResultList();
	}

}
