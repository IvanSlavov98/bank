package com.example.bank.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bank.dto.AccountDTO;
import com.example.bank.service.account.AccountServiceNew;

@RestController
@RequestMapping("/v2/bank")
public class BankControllerNew {

	@Autowired
	AccountServiceNew accountServiceNew;

	@Autowired
	ModelMapper modelMapper;

	@GetMapping
	public List<AccountDTO> getAllAccounts() {
		return this.accountServiceNew.getAllAccounts().parallelStream()
				.map(account -> modelMapper.map(account, AccountDTO.class)).collect(Collectors.toList());
	}
}
