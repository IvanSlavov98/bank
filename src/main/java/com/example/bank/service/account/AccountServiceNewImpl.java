package com.example.bank.service.account;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bank.entity.Account;

@Service
public class AccountServiceNewImpl implements AccountServiceNew {

	@Autowired
	DataSource dataSource;
	
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new Error("Fatal: no driver...");
		}
	}

	public List<Account> getAllAccounts() {

		List<Account> accounts = new LinkedList<>();

		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = 
						connection.prepareStatement("SELECT id, first_name, last_name, iban, balance "
						+ "FROM Account ORDER BY first_name LIMIT 100");
				ResultSet resultSet = preparedStatement.executeQuery();) {

			while (resultSet.next()) {
				Account account = new Account();
				account.setId(resultSet.getInt("id"));
				account.setFirstName(resultSet.getString("first_name"));
				account.setLastName("last_name");
				account.setBalance(resultSet.getBigDecimal("balance"));
				account.setIban(resultSet.getString("iban"));

				accounts.add(account);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return accounts;
	}

	@Override
	public Account createNewAccount(Account newAccount) {
		String query = "INSERT INTO Account "
				+ "(id, first_name, last_name, iban, date_of_creation, balance) values (?, ?, ?, ?, ?, ?)";

		newAccount.setDateOfCreation(LocalDate.now());
		newAccount.setId(null);

		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(query);) {
			
			int param = 1;
			
			ps.setInt(param++, newAccount.getId());
			ps.setString(param++, newAccount.getFirstName());
			ps.setString(param++, newAccount.getLastName());
			ps.setString(param++, newAccount.getIban());
			ps.setDate(param++, Date.valueOf(newAccount.getDateOfCreation()));
			ps.setBigDecimal(param, newAccount.getBalance());

			ps.executeUpdate();

			return newAccount;
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void updateAccount(Account account) {
		String query = "UPDATE Account " 
				+ "SET first_name = IF(? IS NOT NULL, ?, first_name),"
				+ "last_name = IF(? IS NOT NULL, ?, last_name)," 
				+ "balance = IF(? IS NOT NULL, ?, balance) "
				+ "WHERE id=?";

		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(query);) {
			
			int param = 1;
			
			ps.setString(param++, account.getFirstName());
			ps.setString(param++, account.getFirstName());
			ps.setString(param++, account.getLastName());
			ps.setString(param++, account.getLastName());
			ps.setBigDecimal(param++, account.getBalance());
			ps.setBigDecimal(param++, account.getBalance());
			ps.setInt(param, account.getId());

			ps.executeUpdate();
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void deleteAccountById(int id) {

		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement("DELETE FROM Account where id=?");) {
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void updateAccount2(Account account) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(
						"SELECT id, first_name, last_name, balance FROM Account WHERE id=?", ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE);) {

			ps.setInt(1, account.getId());
			ps.executeQuery();

			try (ResultSet rs = ps.getResultSet();) {

				if(rs.next()) {
					if(account.getId().equals(rs.getInt("id"))) {
						rs.updateString("first_name", Optional.ofNullable(account.getFirstName())
								.orElseThrow(() -> new RuntimeException("First Name is null")));
						rs.updateString("last_name", Optional.ofNullable(account.getLastName())
								.orElseThrow(() -> new RuntimeException("Last Name is null")));
						rs.updateBigDecimal("balance", Optional.ofNullable(account.getBalance())
								.orElseThrow(() -> new RuntimeException("Balance is null")));
	
						rs.updateRow();
					}
				}

			} catch (Exception e) {
				throw new RuntimeException(e);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void deleteAccountById2(Integer id) {
		String query = "SELECT id FROM Account WHERE id=?";

		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
				) {
			
				try(ResultSet rs = ps.executeQuery()){
					if (rs.next()) {
						if(id.equals(rs.getInt("id"))) {
							rs.deleteRow();
						}
					}
				}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public Account createNewAccount2(Account newAccount) {
		
		String query = "SELECT id, first_name, last_name, iban, date_of_creation, balance FROM Account WHERE 1 <> 1";
		
		try(Connection connection = dataSource.getConnection();
			Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				ResultSet rs = stmt.executeQuery(query)) {
			
			newAccount.setDateOfCreation(LocalDate.now());
			
			rs.moveToInsertRow();
			rs.updateString("first_name", newAccount.getFirstName());
			rs.updateString("last_name", newAccount.getLastName());
			rs.updateString("iban", newAccount.getIban());
			rs.updateDate("date_of_creation", Date.valueOf(newAccount.getDateOfCreation()));
			rs.updateBigDecimal("balance", newAccount.getBalance());
			
			rs.insertRow();
			rs.moveToCurrentRow();
			
			return newAccount;
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void updateAccountWithTransaction(Account account){
		
		String query = "UPDATE Account " 
				+ "SET first_name = IF(? IS NOT NULL, ?, first_name),"
				+ "last_name = IF(? IS NOT NULL, ?, last_name)," 
				+ "balance = IF(? IS NOT NULL, ?, balance) "
				+ "WHERE id=?";
		
		try(Connection con = dataSource.getConnection()){
			try(PreparedStatement ps = con.prepareStatement(query);){
				con.setAutoCommit(false);
				
				int param = 1;
				
				ps.setString(param++, account.getFirstName());
				ps.setString(param++, account.getFirstName());
				ps.setString(param++, account.getLastName());
				ps.setString(param++, account.getLastName());
				ps.setBigDecimal(param++, account.getBalance());
				ps.setBigDecimal(param++, account.getBalance());
				ps.setInt(param, account.getId());
				
				ps.executeUpdate();
				
				con.commit();
				
			} catch (Exception e) {
				con.rollback();
				throw new RuntimeException(e);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Account getAccountById(int Id) {
		String query = "SELECT id, first_name, last_name, iban, date_of_creation, balance FROM Account WHERE id=?";
		System.out.println(Thread.currentThread().getName());
		Account account = new Account();
		
		try(Connection c = dataSource.getConnection();
				PreparedStatement ps = c.prepareStatement(query)){
			
			ps.setInt(1, Id);
			
			try(ResultSet rs = ps.executeQuery()){
				
				if(rs.next()) {
					account.setId(rs.getInt("id"));
					account.setFirstName(rs.getString("first_name"));
					account.setLastName(rs.getString("last_name"));
					account.setBalance(rs.getBigDecimal("balance"));
					account.setIban(rs.getString("iban"));
				}
				
				return account;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
			
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void selectStoredProcedure() {
		
		try(Connection con = dataSource.getConnection();
				CallableStatement cstmt = con.prepareCall("call bank.new_procedure();");
				){ 
			ResultSet rs = cstmt.executeQuery();
			do {
				rs = cstmt.getResultSet();
				while(rs.next()) {
					System.out.println(rs.getString(1));
				}
				
			}while(cstmt.getMoreResults());
			
			rs.close();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static int pobeda() {
		if(1>0) return 1;
		throw new RuntimeException();
	}

}
