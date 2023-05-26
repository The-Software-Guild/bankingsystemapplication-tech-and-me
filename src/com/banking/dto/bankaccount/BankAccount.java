package com.banking.dto.bankaccount;

import java.io.Serializable;
import java.time.LocalDate;

import com.banking.dto.customer.CustomerModel;
import com.banking.exceptions.InsufficientBalanceException;


public abstract class BankAccount implements Serializable{
	private long accntNum;
	private long bsbCode;
	private String bankName;
	private double accntBal;
	private LocalDate accntOpeningDate;
	private String bankAccountType;
	
	public BankAccount() {		
	}
	
	public BankAccount(long accntNum, long bsbCode, String accntName, double accntBal, LocalDate accntOpeningDate) {
		this.accntNum = accntNum;
		this.bsbCode = bsbCode;
		this.bankName = accntName;
		this.accntBal = accntBal;
		this.accntOpeningDate = accntOpeningDate;
	}

	public long getAccntNum() {
		return accntNum;
	}

	public void setAccntNum(long accntNum) {
		this.accntNum = accntNum;
	}

	public long getBsbCode() {
		return bsbCode;
	}

	public void setBsbCode(long bsbCode) {
		this.bsbCode = bsbCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String accntName) {
		this.bankName = accntName;
	}

	public double getAccntBal() {
		return accntBal;
	}

	public void setAccntBal(double accntBal) {
		this.accntBal = accntBal;
	}

	public LocalDate getAccntOpeningDate() {
		return accntOpeningDate;
	}

	public void setAccntOpeningDate(LocalDate accntOpeningDate) {
		this.accntOpeningDate = accntOpeningDate;
	}
	
	public String getBankAccountType() {
		return bankAccountType;
	}

	public void setBankAccountType(String bankAccountType) {
		this.bankAccountType = bankAccountType;
	}

	public abstract double withdraw(double amount);
	
	public abstract double deposit(double amount);
	
	public abstract double calculateInterest();
	


	@Override
	public String toString() {
		return "accntNum=" + accntNum + ", bsbCode=" + bsbCode + ", bankName=" + bankName + ", accntBal="
				+ accntBal + ", accntOpeningDate=" + accntOpeningDate;
	}
	
	
}
