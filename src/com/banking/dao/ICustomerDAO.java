package com.banking.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.banking.dto.bankaccount.BankAccount;
import com.banking.dto.customer.CustomerModel;

public interface ICustomerDAO {
	void create(CustomerModel customer);
	CustomerModel retrieveCustomerById(int id);
	List<CustomerModel> retrieveCustomerByName(String name);
	Map<Integer, CustomerModel> retrieveAllCustomersFromDatabase();
	void updateAllCustomersToDatabase(Map<Integer, CustomerModel> customers);
	void update(CustomerModel stu);
	int retrieveLastCustomerIdFromDatabase();
	void delete(int id);
//	void updateLastCustomerIdToDatabase(int lastCustomerId);
	void addCustomerToDb(CustomerModel customer);
	void addBankAccountToDb(CustomerModel customer);
	void updateBankBalanceInDb(BankAccount bankAccount);
	

}
