package com.banking.dto.bankaccount;

import java.time.LocalDate;

public class FixedDepositAccount extends BankAccount {
	private int tenure;
	private double interestEarned;
	private double depositAmount;
	public static final float MIN_DEPOSIT_AMOUNT = 1000;
	public static final double INTEREST_RATE = 0.08;
	
	public FixedDepositAccount(BankAccount b) {
		super(b.getAccntNum(), b.getBsbCode(), b.getBankName(), b.getAccntBal(), b.getAccntOpeningDate());
	}
	
	public FixedDepositAccount(long accntNum, long bsbCode, String accntName, double accntBal, LocalDate accntOpeningDate,double depositAmount,int tenure) {
	    super(accntNum, bsbCode, accntName, accntBal, accntOpeningDate);
	    this.tenure = tenure;
	    this.depositAmount = depositAmount;
	    this.interestEarned = 0.0;
	}	
	
	public int getTenure() {
		return tenure;
	}
	public void setTenure(int tenure) {
		this.tenure = tenure;
	}

	public double getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(double depositAmount) {
		this.depositAmount = depositAmount;
	}

	public double getInterestEarned() {
		return interestEarned;
	}
	public void setInterestEarned(double interestEarned) {
		this.interestEarned = interestEarned;
	}

	@Override
	public double calculateInterest() {
		return this.getTenure()*this.getDepositAmount()*INTEREST_RATE;	
	}

	@Override
	public double withdraw(double amount) {
		System.out.println("No withdrawal transaction allowed for Fixed Deposit Account.\nYour updated bank balance after this transaction is $ " + this.getAccntBal());
		return this.getAccntBal();
	}

	@Override
	public double deposit(double amount) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
