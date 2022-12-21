package com.example.bank.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = "account", indexes = { @Index(name = "idx_first_name_account", columnList = "firstName") })
public class Account extends BaseEntity {

	private String firstName;

	private String lastName;

	private String iban;

	private LocalDate dateOfCreation;

	private BigDecimal balance;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public LocalDate getDateOfCreation() {
		return dateOfCreation;
	}

	public void setDateOfCreation(LocalDate dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Account [firstName=" + firstName + ", lastName=" + lastName + ", iban=" + iban + ", dateOfCreation="
				+ dateOfCreation + ", balance=" + balance + "]";
	}

}
