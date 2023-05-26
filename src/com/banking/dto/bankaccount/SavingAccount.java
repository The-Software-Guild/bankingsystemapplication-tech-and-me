package com.banking.dto.bankaccount;

import java.time.LocalDate;

import com.banking.exceptions.InsufficientBalanceException;

public class SavingAccount extends BankAccount {
	private boolean isSalaryAccount;
	private float minBalance = 0;
	private double interestEarned;
	public static final double INTEREST_RATE = 0.04;
	
	public SavingAccount(long accntNum, long bsbCode, String accntName, double accntBal, LocalDate accntOpeningDate, boolean isSalaryAccount) {
	    super(accntNum, bsbCode, accntName, accntBal, accntOpeningDate);
	    this.isSalaryAccount = isSalaryAccount;
	    this.interestEarned = 0.0;
	}
	
	public SavingAccount(long accntNum, long bsbCode, String accntName, double accntBal, LocalDate accntOpeningDate, boolean isSalaryAccount,float minBalance) {
	    super(accntNum, bsbCode, accntName, accntBal, accntOpeningDate);
	    this.isSalaryAccount = isSalaryAccount;
	    this.minBalance = minBalance;
	    this.interestEarned = 0.0;
	}
	
	public boolean isSalaryAccount() {
        return isSalaryAccount;
    }

    public void setSalaryAccount(boolean isSalaryAccount) {
        this.isSalaryAccount = isSalaryAccount;
    }
    
    public float getMinBalance() {
		return minBalance;
	}

	public void setMinBalance(float minBalance) {
		this.minBalance = minBalance;
	}

	public double getInterestEarned() {
		return interestEarned;
	}
    
	public void setInterestEarned(double interestEarned) {
		this.interestEarned = interestEarned;
	}
	
	public SavingAccount(BankAccount b) {
		super(b.getAccntNum(), b.getBsbCode(), b.getBankName(), b.getAccntBal(), b.getAccntOpeningDate());
	}
	

	
	
	
	



	@Override
	public double withdraw(double amount){
		double updatedBankBal = this.getAccntBal();
		double maxAllowedAmount = this.getAccntBal() - this.getMinBalance();
	    try {
	        if (amount > maxAllowedAmount) {
	            throw new InsufficientBalanceException("Insufficient balance in the account to withdraw.\nMaximum amount allowed is :" + " $ " + maxAllowedAmount);
	        }
	        //Update account balance
	        updatedBankBal -= amount;
	        this.setAccntBal(updatedBankBal);
	        System.out.println("Withdrawal transaction is completed successfully.");
	    } catch (InsufficientBalanceException e) {
	        System.out.println(e.getMessage());
	    } catch (Exception e) {
	        System.out.println("Error occurred while withdrawing from the account. Please try again later.");
	    }finally {
	    	System.out.println("Your updated bank balance after this transaction is : $"+ this.getAccntBal());
	    }
	    
	    return updatedBankBal;
	}


	@Override
	public double calculateInterest() {
		return this.getAccntBal() * INTEREST_RATE;		
	}

	@Override
	public double deposit(double amount) {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
