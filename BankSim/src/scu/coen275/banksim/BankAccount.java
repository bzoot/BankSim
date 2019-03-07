package scu.coen275.banksim;

/**
 * @Title: BankAccount.java
 * @Package scu.coen275.banksim
 * @Description:
 * @author Pengbin Li
 * @date 2019-1-12 9:40:59
 * @version V1.0
 */

public class BankAccount {

	private int accountNumber;

	private int pinNumber;

	private double savingBalance;

	private double checkingBalance;

	private String accountHolderName;

	private boolean check;

	public BankAccount(int accountNumber, int pinNumber, String accountHolderName, boolean check) {
		super();
		this.accountNumber = accountNumber;
		this.pinNumber = pinNumber;
		this.accountHolderName = accountHolderName;
		this.check = check;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public boolean verifyPIN(int pinNumber) {
		return this.pinNumber == pinNumber;
	}

	public double getSavingBalance() {
		return savingBalance;
	}

	public void setSavingBalance(double savingBalance) {
		this.savingBalance = savingBalance;
	}

	public double getCheckingBalance() throws UnsupportedOperationException {
		if (this.hasCheck()) {
			return checkingBalance;
		} else {
			throw new UnsupportedOperationException("This account does not have checkings account.");
		}
	}

	public void setCheckingBalance(double checkingBalance) throws UnsupportedOperationException {
		if (this.hasCheck()) {
			this.checkingBalance = checkingBalance;
		} else {
			throw new UnsupportedOperationException("This account does not have checkings account.");
		}
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public boolean hasCheck() {
		return check;
	}

	public String printCSV() {
		String res = accountNumber + "," + pinNumber + "," + savingBalance;
		if (this.hasCheck()) {
			res += "," + checkingBalance + "," + accountHolderName;
		} else {
			res += "," + accountHolderName;
		}

		return res;
	}

}
