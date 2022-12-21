package com.example.bank.service.account;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.bank.entity.Account;

@Service
public class AccountServiceNew {

	@Value("${spring.datasource.password}")
	private String schemaPassword;
	
	@Value("${spring.datasource.username}")
	private String schemaUser;

	@Value("${spring.datasource.url}")
	private String schemaUrl;

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new Error("Fatal: nema drijvaer ...");
		}
	}
	
    // returns all accounts
	public List<Account> getAllAccounts() {

		String query = "SELECT id, first_name, last_name, iban, balance FROM Account ORDER BY first_name limit 100";

		List<Account> accounts = new LinkedList<>();

		try (Connection connection = //
				DriverManager.getConnection(schemaUrl, schemaUser, schemaPassword); //
				PreparedStatement preparedStatement = connection.prepareStatement(query); //
				ResultSet resultSet = preparedStatement.executeQuery();) //
		{

			while (resultSet.next()) {
				Account account = new Account();
				account.setId(resultSet.getInt("id"));
				account.setFirstName(resultSet.getString("first_name"));
				account.setLastName("last_name");
				account.setBalance(resultSet.getBigDecimal("balance"));
				account.setIban(resultSet.getString("iban"));

				//if (Objects.nonNull(resultSet.getDate("date_of_creation"))) {
				//	account.setDateOfCreation(resultSet.getDate("date_of_creation").toLocalDate());
				//}

				accounts.add(account);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return accounts;
	}

}
