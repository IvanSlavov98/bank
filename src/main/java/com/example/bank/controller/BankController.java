package com.example.bank.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bank.dto.AccountDTO;
import com.example.bank.dto.TransactionAmountDTO;
import com.example.bank.dto.TransactionDTO;
import com.example.bank.entity.Account;

@RestController
@RequestMapping("/v1/bank")
public class BankController extends BaseController {

	@PostMapping
	public ResponseEntity<AccountDTO> createNewAccount(@RequestBody AccountDTO accountDTO) {
		Account account = modelMapper.map(accountDTO, Account.class);
		return new ResponseEntity<AccountDTO>(
				modelMapper.map(accountService.createNewAccount(account), AccountDTO.class), HttpStatus.OK);
	}

	@PostMapping("/maketransaction")
	public ResponseEntity<TransactionDTO> makeTransaction(@RequestBody TransactionAmountDTO transactionAmountDTO) {
		return new ResponseEntity<TransactionDTO>(
				modelMapper.map(
						this.transactionService.makeTransaction(transactionAmountDTO.getSenderID(),
								transactionAmountDTO.getReceiverID(), transactionAmountDTO.getAmount()),
						TransactionDTO.class),
				HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<AccountDTO>> getAllAccounts() {
		return new ResponseEntity<List<AccountDTO>>(
				this.accountService.getAllAccounts().parallelStream()
						.map(account -> modelMapper.map(account, AccountDTO.class)).collect(Collectors.toList()),
				HttpStatus.OK);
	}

	@GetMapping("/getalltransactions/{id}")
	public ResponseEntity<List<TransactionDTO>> getTransactions(@PathVariable Integer id) {
		return new ResponseEntity<List<TransactionDTO>>(this.transactionService.getTransactions(id).parallelStream()
				.map(transaction -> modelMapper.map(transaction, TransactionDTO.class)).collect(Collectors.toList()),
				HttpStatus.OK);
	}
}
