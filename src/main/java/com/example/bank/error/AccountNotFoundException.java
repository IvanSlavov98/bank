package com.example.bank.error;

public class AccountNotFoundException extends IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountNotFoundException() {
		super();
	}

	public AccountNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccountNotFoundException(String s) {
		super(s);
	}

	public AccountNotFoundException(Throwable cause) {
		super(cause);
	}

}
