package com.example.bank.service.transaction;

import java.math.BigDecimal;
import java.util.List;

import com.example.bank.entity.Transaction;

public interface TransactionService {

	public Transaction makeTransaction(Integer senderID, Integer receiverID, BigDecimal amount);

	public List<Transaction> getTransactions(Integer id);

}
