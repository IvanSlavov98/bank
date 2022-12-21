package com.example.bank.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(indexes = { @Index(name = "idx_sender_account", columnList = "sender") })
@NamedQuery(name = "getTransactionsOfUser", query = "FROM Transaction t WHERE t.sender.id = ?1 ORDER BY t.dateCreated")
public class Transaction extends BaseEntity {

	private BigDecimal amount;

	private LocalDate dateCreated;

	@ManyToOne
	@JoinColumn(name = "sender", referencedColumnName = "id")
	private Account sender;

	@ManyToOne
	@JoinColumn(name = "receiver", referencedColumnName = "id")
	private Account receiver;

	public Transaction() {

	}

	public Transaction(BigDecimal amount, Account sender, Account receiver) {
		this.amount = amount;
		this.sender = sender;
		this.receiver = receiver;
		this.dateCreated = LocalDate.now();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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

	public LocalDate getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDate dateCreated) {
		this.dateCreated = dateCreated;
	}

}
