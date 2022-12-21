package com.example.bank.dto;

import java.math.BigDecimal;

public class TransactionAmountDTO {

	private Integer senderID;

	private Integer receiverID;

	private BigDecimal amount;

	public Integer getSenderID() {
		return senderID;
	}

	public void setSenderID(Integer senderID) {
		this.senderID = senderID;
	}

	public Integer getReceiverID() {
		return receiverID;
	}

	public void setReceiverID(Integer receiverID) {
		this.receiverID = receiverID;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}
