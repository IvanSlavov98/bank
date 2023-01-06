package com.example.bank.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bank.dto.AccountDTO;
import com.example.bank.dto.MultipleAccountIdsDTO;
import com.example.bank.entity.Account;
import com.example.bank.service.account.AccountServiceNew;

@RestController
@RequestMapping("/v2/bank")
public class BankControllerNew {

	@Autowired
	AccountServiceNew accountServiceNew;
	
	@Autowired
	ThreadPoolTaskExecutor tste;

	@Autowired
	ModelMapper modelMapper;

	@GetMapping
	public List<AccountDTO> getAllAccounts() {
		return this.accountServiceNew.getAllAccounts().parallelStream()
				.map(account -> modelMapper.map(account, AccountDTO.class)).collect(Collectors.toList());
	}
	
	@PostMapping
	public AccountDTO createNewAccount(@RequestBody AccountDTO accountDTO) {
		Account newAccount = modelMapper.map(accountDTO, Account.class);
		return modelMapper.map(this.accountServiceNew.createNewAccount2(newAccount), AccountDTO.class);
	}
	
	@PutMapping
	public void updateAccount(@RequestBody AccountDTO accountDTO) {
		Account account = modelMapper.map(accountDTO, Account.class);
		this.accountServiceNew.updateAccountWithTransaction(account);
	}
	
	@DeleteMapping("/{id}")
	public void deleteAccount(@PathVariable int id) {
		this.accountServiceNew.deleteAccountById(id);
	}
	
	@GetMapping("/getmultipleaccounts")
	public List<Account> getMultipleAccounts(@RequestBody MultipleAccountIdsDTO idsDTO){
		List<Account> res = idsDTO.getIds().parallelStream().
				map(this::submitAccount).collect(Collectors.toList());
		
		return res;
	}
	
	private Account submitAccount(Integer id) {
		try {
			return tste.submit(() -> accountServiceNew.getAccountById(id)).get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
