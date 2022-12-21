package com.example.bank.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.bank.entity.Account;

public class TransactionDTO {

	private BigDecimal amount;

	private LocalDate dateCreated;

	private Account sender;

	private Account receiver;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public LocalDate getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDate dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Account getSender() {
		return sender;
	}

	public void setSender(Account sender) {
		this.sender = sender;
	}

	public Account getReceiver() {
		return receiver;
	}

	public void setReceiver(Account receiver) {
		this.receiver = receiver;
	}

}
