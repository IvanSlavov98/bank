package com.example.bank.error;

public class InsufficientFundsException extends IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InsufficientFundsException() {
		super();
	}

	public InsufficientFundsException(String message, Throwable cause) {
		super(message, cause);
	}

	public InsufficientFundsException(String s) {
		super(s);
	}

	public InsufficientFundsException(Throwable cause) {
		super(cause);
	}

}
