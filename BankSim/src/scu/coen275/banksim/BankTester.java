package scu.coen275.banksim;

import java.util.Scanner;

/**
 * @Title: BankTester.java
 * @Package scu.coen275.banksim
 * @Description:
 * @author Pengbin Li
 * @date 2019-1-12 9:41:13
 * @version V1.0
 */

public class BankTester {

	private static Scanner sc = new Scanner(System.in);

	private void start() {
		Bank bank = Bank.getInstace();
		while (true) {
			System.out.println("Input the number to choose opration,");
			System.out.println("1. Login");
			System.out.println("2. Save and Quit");
			String input = sc.nextLine();
			Integer choice = input.matches("[0-9]+") ? Integer.parseInt(input) : null;
			if (null != choice) {
				switch (choice) {
				case 1:
					bank.login();
					break;
				case 2:
					bank.write2Database();
					System.exit(0);
					break;
				default:
					break;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		new BankTester().start();
	}

}
