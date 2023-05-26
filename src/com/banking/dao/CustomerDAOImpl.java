package com.banking.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.banking.dto.bankaccount.BankAccount;
import com.banking.dto.bankaccount.FixedDepositAccount;
import com.banking.dto.bankaccount.SavingAccount;
import com.banking.dto.customer.CustomerModel;

public class CustomerDAOImpl implements ICustomerDAO {

	public Connection openConnection() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");//Type-4 driver is a registered with DriverManager
			System.out.println("MYSQL driver registred with DriverManager");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3307/customer2","root","root");
//			System.out.println(con);
		} catch (ClassNotFoundException e) {
			System.out.println("MYSQL suitable driver not found");
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	public void closeConnection(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addCustomerToDb(CustomerModel customer) {
		int id = customer.getCustId();
		String name = customer.getCustName();
		String phone = customer.getCustMobNum();
		String passport = customer.getCustPassportNum();
		LocalDate dob = customer.getDob();

		Connection con = openConnection();
		try {
			String customerSql = "INSERT INTO Customer (name, phone, Passport, dob) VALUES (?, ?, ?, ?)";
			PreparedStatement customerPstat = con.prepareStatement(customerSql, Statement.RETURN_GENERATED_KEYS);
			customerPstat.setString(1, name);
			customerPstat.setString(2, phone);
			customerPstat.setString(3, passport);
			customerPstat.setDate(4, java.sql.Date.valueOf(dob));
			int customerRowsInserted = customerPstat.executeUpdate();

			// Retrieve the generated ID value
			ResultSet generatedKeys = customerPstat.getGeneratedKeys();
			if (generatedKeys.next()) {
				int generatedId = generatedKeys.getInt(1);
				System.out.println("Customer Id assigned to is : " + generatedId);
			}

			System.out.println("Number of customer rows inserted: " + customerRowsInserted);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		closeConnection(con);
	}


	@Override
	public Map<Integer, CustomerModel> retrieveAllCustomersFromDatabase() {
		Map<Integer, CustomerModel> customers = new HashMap<>();
		Connection con = openConnection();

		try {
			// Retrieve customer and bank account data using a JOIN operation
			String sql = "SELECT * FROM Customer c LEFT JOIN BankAccount b ON c.id = b.custId";
			PreparedStatement statement = con.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();

			// Iterate over the result set and populate the customers map
			while (rs.next()) {
				int custId = rs.getInt("id");
				String custName = rs.getString("name");
				String custMobNum = rs.getString("phone");
				String custPassportNum = rs.getString("passport");
				LocalDate dob = rs.getDate("dob").toLocalDate();

				CustomerModel customer = new CustomerModel(custId, custName, custMobNum, custPassportNum, dob);
				customers.put(custId, customer);

				BankAccount bankAccount = null;
				long accntNum = rs.getLong("accntNum");
				if (accntNum != 0) {
					long bsbCode = rs.getLong("bsbCode");
					String bankName = rs.getString("bankName");
					double accntBal = rs.getDouble("accntBal");
					LocalDate accntOpeningDate = rs.getDate("accntOpeningDate").toLocalDate();
					String bankAccountType = rs.getString("bankAccountType");

					if (bankAccountType.equalsIgnoreCase("S")) {
						boolean isSalaryAccount = rs.getBoolean("isSalaryAccount");

						float minBalance = isSalaryAccount ? 0 : 100;
						bankAccount = new SavingAccount(accntNum, bsbCode, bankName, accntBal, accntOpeningDate, isSalaryAccount, minBalance);
						bankAccount.setBankAccountType("S");
					} else if (bankAccountType.equalsIgnoreCase("F")) {
						int tenure = rs.getInt("tenure");
						double depositAmount = rs.getDouble("depositAmount");
						bankAccount = new FixedDepositAccount(accntNum, bsbCode, bankName, accntBal, accntOpeningDate, depositAmount, tenure);
						bankAccount.setBankAccountType("F");
					}
				}

				customer.setBankAccount(bankAccount);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		closeConnection(con);
		return customers;
	}


	@Override
	public CustomerModel retrieveCustomerById(int id) {
		CustomerModel customer = null;
		Connection con = openConnection();

		try {
			String sql = "SELECT * FROM Customer c LEFT JOIN BankAccount b ON c.id = b.custId WHERE c.id = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, id);

			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				String name = rs.getString("name");
				String phone = rs.getString("phone");
				String passport = rs.getString("Passport");
				LocalDate dob = rs.getDate("dob").toLocalDate();

				customer = new CustomerModel(id, name, phone, passport, dob);

				BankAccount bankAccount = null;
				long accntNum = rs.getLong("accntNum");
				if (accntNum != 0) {
					long bsbCode = rs.getLong("bsbCode");
					String bankName = rs.getString("bankName");
					double accntBal = rs.getDouble("accntBal");
					LocalDate accntOpeningDate = rs.getDate("accntOpeningDate").toLocalDate();
					String bankAccountType = rs.getString("bankAccountType");

					if (bankAccountType.equalsIgnoreCase("S")) {
						boolean isSalaryAccount = rs.getBoolean("isSalaryAccount");

						float minBalance = isSalaryAccount ? 0 : 100;
						bankAccount = new SavingAccount(accntNum, bsbCode, bankName, accntBal, accntOpeningDate, isSalaryAccount, minBalance);
						bankAccount.setBankAccountType("S");
					} else{
						int tenure = rs.getInt("tenure");
						double depositAmount = rs.getDouble("depositAmount");
						bankAccount = new FixedDepositAccount(accntNum, bsbCode, bankName, accntBal, accntOpeningDate, depositAmount, tenure);
						bankAccount.setBankAccountType("F");
					}
					customer.setBankAccount(bankAccount);
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}



		closeConnection(con);

		return customer;
	}

	@Override
	public List<CustomerModel> retrieveCustomerByName(String name) {
		List<CustomerModel> customers = new ArrayList<>();
		Connection con = openConnection();

		try {
			String sql = "SELECT * FROM Customer c LEFT JOIN BankAccount b ON c.id = b.custId WHERE c.name = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, name);

			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String customerName = rs.getString("name");
				String phone = rs.getString("phone");
				String passport = rs.getString("Passport");
				LocalDate dob = rs.getDate("dob").toLocalDate();

				CustomerModel customer = new CustomerModel(id, customerName, phone, passport, dob);

				BankAccount bankAccount = null;
				long accntNum = rs.getLong("accntNum");
				if (accntNum != 0) {
					long bsbCode = rs.getLong("bsbCode");
					String bankName = rs.getString("bankName");
					double accntBal = rs.getDouble("accntBal");
					LocalDate accntOpeningDate = rs.getDate("accntOpeningDate").toLocalDate();
					String bankAccountType = rs.getString("bankAccountType");

					if (bankAccountType.equalsIgnoreCase("S")) {
						boolean isSalaryAccount = rs.getBoolean("isSalaryAccount");

						float minBalance = isSalaryAccount ? 0 : 100;
						bankAccount = new SavingAccount(accntNum, bsbCode, bankName, accntBal, accntOpeningDate, isSalaryAccount, minBalance);
						bankAccount.setBankAccountType("S");
					} else{
						int tenure = rs.getInt("tenure");
						double depositAmount = rs.getDouble("depositAmount");
						bankAccount = new FixedDepositAccount(accntNum, bsbCode, bankName, accntBal, accntOpeningDate, depositAmount, tenure);
						bankAccount.setBankAccountType("F");
					}
					customer.setBankAccount(bankAccount);
				}
				customers.add(customer);
			} 
		}catch (SQLException e) {
			e.printStackTrace();
		}

		closeConnection(con);

		return customers;

	}





	@Override
	public void addBankAccountToDb(CustomerModel customer){
		int cutId = customer.getCustId();
		BankAccount bankAccount = customer.getBankAccount();
		Connection con = openConnection();
		PreparedStatement bankAccountPstat = null;
		try {
			if (bankAccount != null) {
				String bankAccountSql = "INSERT INTO customer2.BankAccount (accntNum, bsbCode, bankName, accntBal, accntOpeningDate, bankAccountType, isSalaryAccount, depositAmount, tenure, custId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				bankAccountPstat = con.prepareStatement(bankAccountSql);

				bankAccountPstat.setLong(1, bankAccount.getAccntNum());
				bankAccountPstat.setLong(2, bankAccount.getBsbCode());
				bankAccountPstat.setString(3, bankAccount.getBankName());
				bankAccountPstat.setDouble(4, bankAccount.getAccntBal());
				bankAccountPstat.setDate(5, java.sql.Date.valueOf(bankAccount.getAccntOpeningDate()));
				bankAccountPstat.setString(6, bankAccount.getBankAccountType());

				if (bankAccount instanceof SavingAccount) {
					SavingAccount savingAccount = (SavingAccount)bankAccount;
					bankAccountPstat.setBoolean(7, savingAccount.isSalaryAccount());
					bankAccountPstat.setNull(8, Types.DOUBLE); // deposit for fixed deposit accnt only
					bankAccountPstat.setNull(9, Types.INTEGER);// tenure for fixed deposit accnt only
				} else {
					FixedDepositAccount fixedDepositAccount = (FixedDepositAccount)bankAccount;
					bankAccountPstat.setNull(7, Types.BOOLEAN);
					bankAccountPstat.setDouble(8, fixedDepositAccount.getDepositAmount());
					bankAccountPstat.setInt(9, fixedDepositAccount.getTenure());
				}

			}
			bankAccountPstat.setInt(10, customer.getCustId());
			bankAccountPstat.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		closeConnection(con);
	}

	@Override
	public void updateBankBalanceInDb(BankAccount bankAccount) {
	    Connection con = openConnection();
	    PreparedStatement statement = null;
	    
	    try {
	    	String sql = "UPDATE BankAccount SET accntBal = ? WHERE accntNum = ? AND bsbCode = ?";
	        statement = con.prepareStatement(sql);
	        statement.setDouble(1, bankAccount.getAccntBal());
	        statement.setLong(2, bankAccount.getAccntNum());
	        statement.setLong(3, bankAccount.getBsbCode());
	        
	        statement.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        closeConnection(con);
	    }
	}



















	@Override
	public void updateAllCustomersToDatabase(Map<Integer, CustomerModel> customers) {
		Connection con = openConnection();
		try {
			Statement truncateStatement = con.createStatement();
			truncateStatement.addBatch("TRUNCATE TABLE Customer");
			truncateStatement.addBatch("TRUNCATE TABLE BankAccount");
			truncateStatement.addBatch("TRUNCATE TABLE CustomerIdTracker");
			truncateStatement.executeBatch();

			String customerSql = "INSERT INTO Customer (id, name, phone, Passport, dob) VALUES (?, ?, ?, ?, ?)";
			String bankAccountSql = "INSERT INTO customer.BankAccount (accntNum, bsbCode, bankName, accntBal, accntOpeningDate, bankAccountType, isSalaryAccount, depositAmount, tenure, custId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement customerPstat = con.prepareStatement(customerSql);
			PreparedStatement bankAccountPstat = con.prepareStatement(bankAccountSql);

			for (CustomerModel customer : customers.values()) {
				customerPstat.setInt(1, customer.getCustId());
				customerPstat.setString(2, customer.getCustName());
				customerPstat.setString(3, customer.getCustMobNum());
				customerPstat.setString(4, customer.getCustPassportNum());
				customerPstat.setDate(5, java.sql.Date.valueOf(customer.getDob()));
				customerPstat.executeUpdate();

				BankAccount bankAccount = customer.getBankAccount();
				if (bankAccount != null) {
					bankAccountPstat.setLong(1, bankAccount.getAccntNum());
					bankAccountPstat.setLong(2, bankAccount.getBsbCode());
					bankAccountPstat.setString(3, bankAccount.getBankName());
					bankAccountPstat.setDouble(4, bankAccount.getAccntBal());
					bankAccountPstat.setDate(5, java.sql.Date.valueOf(bankAccount.getAccntOpeningDate()));
					bankAccountPstat.setString(6, bankAccount.getBankAccountType());

					if (bankAccount instanceof SavingAccount) {
						SavingAccount savingAccount = (SavingAccount)bankAccount;
						bankAccountPstat.setBoolean(7, savingAccount.isSalaryAccount());
						bankAccountPstat.setNull(8, Types.DOUBLE); // deposit for fixed deposit accnt only
						bankAccountPstat.setNull(9, Types.INTEGER);// tenure for fixed deposit accnt only
					} else if (bankAccount instanceof FixedDepositAccount) {
						FixedDepositAccount fixedDepositAccount = (FixedDepositAccount)bankAccount;
						bankAccountPstat.setNull(7, Types.BOOLEAN);
						bankAccountPstat.setDouble(8, fixedDepositAccount.getDepositAmount());
						bankAccountPstat.setInt(9, fixedDepositAccount.getTenure());
					}
					bankAccountPstat.setInt(10, customer.getCustId());
					bankAccountPstat.executeUpdate();
				}
			}
			//Updating the last customer id to database
			PreparedStatement insertLastCustIdPstat = con.prepareStatement("INSERT INTO customer.CustomerIdTracker (Last_Cust_Id) VALUES (?)");
			insertLastCustIdPstat.setInt(1, CustomerModel.getLastCustId());
			insertLastCustIdPstat.executeUpdate();

			System.out.println("All customer data and bank account records sent to the database.");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		closeConnection(con);	
	}









	@Override
	public void update(CustomerModel stu) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public int retrieveLastCustomerIdFromDatabase() {
		Connection con = openConnection();
		String sql = "SELECT last_cust_id FROM customeridtracker;";
		Statement stat;
		int retrieved_last_cust_id = 0;
		try {
			stat = con.createStatement();
			ResultSet rs = stat.executeQuery(sql);
			if(rs.next()) {
				retrieved_last_cust_id = rs.getInt("last_cust_id");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}

		closeConnection(con);
		return retrieved_last_cust_id;
	}

	@Override
	public void create(CustomerModel customer) {
		// TODO Auto-generated method stub

	}





}
