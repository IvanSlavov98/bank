package com.example.bank.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.bank.service.account.AccountService;
import com.example.bank.service.transaction.TransactionService;

public class BaseController {

	@Autowired
	protected TransactionService transactionService;

	@Autowired
	protected AccountService accountService;

	@Autowired
	protected ModelMapper modelMapper;

}
