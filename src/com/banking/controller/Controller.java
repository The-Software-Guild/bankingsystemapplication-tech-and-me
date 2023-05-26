package com.banking.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.banking.dto.customer.CustomerModel;
import com.banking.service.CustomerService;
import com.banking.utilities.InputUtilities;


public class Controller {
	public static void main(String[] args) {
		File customersDataFile = new File("C:\\C353\\CustomerRecords.txt");
		Map<Integer,CustomerModel> customers = new HashMap<>(); // for temporaryStorage
		CustomerService customerService = new CustomerService(customers);
		int customerId;
		
		// Display main menu
		boolean readyToExit = false;
		Scanner scanner = new Scanner(System.in);
		while(!readyToExit) {
			System.out.println("-------------------------");
			System.out.println("Java Banking Application");
			System.out.println("-------------------------");
			System.out.println("1 - Create New Customer Data"); // COMPELTED
			System.out.println("2 - Assign a Bank Account to a Customer"); // COMPLETED
			System.out.println("3 - Display balance or interest earned of a Customer"); //COMPLETED (but need to modify calInterst logic)
			System.out.println("4 - Sort Customers"); // COMPLETED
			System.out.println("5 - Persist Customer Data to flat file"); //COMPLETED
			System.out.println("6 - Show All Customers"); //COMPLETED
			System.out.println("7 - Search Customers by Name"); //COMPLETED
			System.out.println("8 - Exit"); //COMPLETED
			System.out.println("9 - Withdraw bank balance by customer ID"); //COMPLETED
			//            System.out.println("10 - Deposit to Customer Bank Account by customer ID");

			String option = scanner.nextLine();

			switch(option) {
			case "1": // Create New Customer Data and add it to the customerMap
				customerService.createCustomer();
				break;
			case "2":// Assign a Bank Account to a Customer
				customerId = InputUtilities.getInputAsInteger("CustomerId", "Enter customer Id you want to assigg bank account : ");
				customerService.assignBankAccountForCustomer(customerId);
				break;
			case "3": // Display balance or interest earned of a customer
				customerId = InputUtilities.getInputAsInteger("CustomerId", "Enter customer Id : "); 
				customerService.displayInterstEarnedForCustomer(customerId);
				break;
			case "4": // Sort customer data 
				System.out.println("1: Sort by name\n2: Sort by Id");
				String sortingType = scanner.nextLine();
				if(sortingType.equals("1")) {
					customerService.sortByName();
				}else {
					customerService.sortById();
				}
				break;
			case "5": // Persist Customer Data to flat file 
				customerService.persistDataToFile(customersDataFile);
				break;
			case "6": // Show all customers            
				customerService.displayCustomerBySortedIds();
				break;
			case "7": // Search Customers by Name
				System.out.println("Enter customer name you want to search : ");
				String searchName = scanner.nextLine();
				List<CustomerModel> matchingCustomers = customerService.searchCustomersByName(searchName);
				break;
			case "8": // Exit
				//Persist Customers Data to Database 
//				customerService.updateAllCustomersToDatabase(customers);
				// Exit the program
				System.out.println("Goodbye!");
				scanner.close();
				readyToExit = true;
				break;
			case "9": // Withdraw
				customerService.withdrawal();
				break;
			default:
				System.out.println("Option not valid - try again !");
			}
		}	
	}

}
