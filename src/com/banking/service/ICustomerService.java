package com.banking.service;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.banking.dto.bankaccount.BankAccount;
import com.banking.dto.customer.CustomerModel;

public interface ICustomerService {
	 public boolean customerHasNoBankAccount(CustomerModel customer);
	 public void addCustomerToList(CustomerModel customer);
	 public void createCustomer();	       
	 public void getGenericBankAccountInfo(CustomerModel customer);
	 public void assignBankAccountForCustomer(int customerId);
	 public void assignBankAccountForCustomer(CustomerModel customer,long accntNum, long bsbCode, String accntName, double accntBal, LocalDate accntOpeningDate, String accntType);
	 public void withdrawal();
	 public List<CustomerModel> searchCustomersByName(String name);
	 public CustomerModel getCustomerById(int customerId);
	 public boolean allItemsAreNull();		
	 public void sortByName();
	 public void displayCustomerBySortedIds();
	 public void displayCustomerBySorteNames();
	 public void displayCustomers(List<CustomerModel> customerList);
	 public void displayInterstEarnedForCustomer(int customerId);
	 void displayCustomer(CustomerModel customer);
	 public void persistDataToFile(File filename);
//	 public static Customer[] readData(File filename);
	void sortById();
	int retrieveLastCustomerIdFromDatabase();
	Map<Integer, CustomerModel> retrieveAllCustomersFromDatabase();
	void updateAllCustomersToDatabase(Map<Integer, CustomerModel> customers);
//	void updateLastCustomerIdToDatabase(int lastCustomerId);
	
	
}
