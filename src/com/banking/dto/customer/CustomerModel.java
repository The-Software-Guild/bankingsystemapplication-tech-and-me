package com.banking.dto.customer;

import java.io.Serializable;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import com.banking.dto.bankaccount.BankAccount;
import com.banking.utilities.InputUtilities;

public class CustomerModel implements Serializable {
	private int custId;
	private String custName;
	private int custAge;
	private String custMobNum; // String cos i want the 0 at the start to not eliminated. 
	private String custPassportNum;
	private LocalDate dob;
	private BankAccount bankAccount = null;

	private static int lastCustId = 100;
	
	public CustomerModel() {
		super();
	}

	public CustomerModel(String custName, String custMobNum, String custPassportNum,LocalDate dob) {
		this.custId = lastCustId++;
		this.custName = custName;	
		this.custMobNum = custMobNum;
		this.custPassportNum = custPassportNum;
		this.dob = dob;
		this.setCustAge();
		
//		lastCustId++;
	}
	
	public CustomerModel(int custId, String custName, String custMobNum, String custPassportNum, LocalDate dob) {
		this.custId = custId;
		this.custName = custName;	
		this.custMobNum = custMobNum;
		this.custPassportNum = custPassportNum;
		this.dob = dob;
		this.setCustAge();
		// we don't increase lastCustId here cos we use this constructor when retrieve data from database which already have id assigned. 
		// lastCustId would be update with another method called retrievedLastCustId
	}
	
	public int getCustId() {
		return custId;
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public int getCustAge() {
		return custAge;
	}

	public void setCustAge() {
		LocalDate currentDate = LocalDate.now();
		Period agePeriod = Period.between(dob,currentDate);
		int age = agePeriod.getYears();
		this.custAge = age;
		
	}

	public String getCustMobNum() {
		return custMobNum;
	}

	public void setCustMobNum(String custMobNum) {
		this.custMobNum = custMobNum;
	}

	public String getCustPassportNum() {
		return custPassportNum;
	}

	public void setCustPassportNum(String custPassportNum) {
		this.custPassportNum = custPassportNum;
	}

	public static int getLastCustId() {
		return lastCustId;
	}

	public static void setLastCustId(int lastCustId) {
		CustomerModel.lastCustId = lastCustId;
	}
	
	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	  }

	public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setCustomerBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

	public void setCustAge(int custAge) {
		this.custAge = custAge;
	}

	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}

	@Override
	public String toString() {
		return "custId=" + custId + ", custName=" + custName + ", custAge=" + custAge + ", custMobNum="
				+ custMobNum + " , bankAccount [ " + bankAccount + " ]";
				
	}


	
}
