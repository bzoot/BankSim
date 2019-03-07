package scu.coen275.banksim;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @Title: Bank.java
 * @Package scu.coen275.banksim
 * @Description:
 * @author Pengbin Li
 * @date 2019-1-12 9:41:05
 * @version V1.0
 */

public class Bank {

	private List<BankAccount> list = new ArrayList<BankAccount>();

	private final String dataFile = "database.txt";

	private Scanner sc = new Scanner(System.in);

	private static Bank bank = new Bank();

	private Bank() {
		int count = 0;
		while (count < 10) {
			try {
				readData();
				return;
			} catch (IOException | UnsupportedOperationException e) {
				System.out.println("Connect to database failed, " + e.getMessage() + ", " + ++count + " times.");
			}
		}
		System.out.println("Initialize failed, system out.");
		System.exit(0);
	}

	public static Bank getInstace() {
		return bank;
	}

	public void login() {
		System.out.println("Please input your account number:");
		String input = sc.nextLine();
		Integer loginAccount = input.matches("[0-9]+") ? Integer.parseInt(input) : null;
		if (null == loginAccount) {
			System.out.println("No such account.");
			return;
		}
		BankAccount ba = null;
		for (BankAccount b : list) {
			if (loginAccount == b.getAccountNumber()) {
				ba = b;
			}
		}
		if (null == ba) {
			System.out.println("No such account.");
			return;
		}
		int count = 0;
		while (count < 3) {
			System.out.println("Please input your pin number:");
			input = sc.nextLine();
			Integer password = input.matches("[0-9]+") ? Integer.parseInt(input) : null;
			if (null != password && ba.verifyPIN(password)) {
				showMenu(ba);
				return;
			} else {
				System.out.println("Wrong password.");
				count++;
			}
		}
		System.out.println("Attempt to login too much time.");
		return;
	}

	private void showMenu(BankAccount ba) {
		if (ba.hasCheck()) {
			System.out.println("Hello " + ba.getAccountHolderName() + ", input the number to choose account,");
			System.out.println("1. Saving Account");
			System.out.println("2. Checking Account");
			String input = sc.nextLine();
			Integer choice = input.matches("[0-9]+") ? Integer.parseInt(input) : null;
			if (null != choice) {
				depositOrWithdraw(ba, choice);
			}
		} else {
			System.out.println("Hello " + ba.getAccountHolderName());
			depositOrWithdraw(ba, 1);
		}
	}

	private void depositOrWithdraw(BankAccount ba, int type) {
		if (type == 1) { // type being 1 means saving account
			double present = ba.getSavingBalance();
			System.out.println("Your saving account has a balance of $" + present);
			System.out.println("How would you like to proceed?");
			System.out.println("(Enter in positive number to deposit, negative number to withdraw)");
			String input = sc.nextLine();
			Double change = input.matches("(-)?[0-9]+(\\.[0-9]{1,2})?") ? Double.parseDouble(input) : null;
			while (null == change) {
				input = sc.nextLine();
				change = input.matches("(-)?[0-9]+(\\.[0-9]{1,2})?") ? Double.parseDouble(input) : null;
			}
			ba.setSavingBalance(Math.abs((present * 100 + change * 100) / 100));
		} else if (type == 2) { // type being 2 means checking account
			try {
				double present = ba.getCheckingBalance();
				System.out.println("Your checking account has a balance of $" + present);
				System.out.println("How would you like to proceed?");
				System.out.println("(Enter in positive number to deposit, negative number to withdraw)");
				String input = sc.nextLine();
				Double change = input.matches("(-)?[0-9]+(\\.[0-9]{1,2})?") ? Double.parseDouble(input) : null;
				while (null == change) {
					input = sc.nextLine();
					change = input.matches("(-)?[0-9]+(\\.[0-9]{1,2})?") ? Double.parseDouble(input) : null;
				}
				ba.setCheckingBalance(Math.abs((present * 100 + change * 100) / 100));
			} catch (UnsupportedOperationException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	private void readData() throws IOException, UnsupportedOperationException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(dataFile))));
		String data = br.readLine();
		while (null != data) {
			String[] s = data.split(",");
			if (s.length == 5) { // length being 5 means the account has
									// checking account
				BankAccount ba = new BankAccount(Integer.parseInt(s[0]), Integer.parseInt(s[1]), s[4], true);
				ba.setSavingBalance(Double.parseDouble(s[2]));
				ba.setCheckingBalance(Double.parseDouble(s[3]));
				list.add(ba);
			} else { // otherwise the account only has saving account
				BankAccount ba = new BankAccount(Integer.parseInt(s[0]), Integer.parseInt(s[1]), s[3], false);
				ba.setSavingBalance(Double.parseDouble(s[2]));
				list.add(ba);
			}
			data = br.readLine();
		}
		br.close();
	}

	public void write2Database() {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(dataFile))));
			for (BankAccount ba : list) {
				bw.write(ba.printCSV());
				bw.newLine();
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				bw.flush();
				bw.close();
			} catch (IOException | SecurityException e) {
				System.out.println(e.getMessage());
			}
		}
	}

}
