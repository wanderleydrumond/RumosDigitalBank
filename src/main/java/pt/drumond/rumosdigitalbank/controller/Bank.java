package pt.drumond.rumosdigitalbank.controller;

import pt.drumond.rumosdigitalbank.HelloApplication;
import pt.drumond.rumosdigitalbank.model.Account;
import pt.drumond.rumosdigitalbank.model.Customer;
import pt.drumond.rumosdigitalbank.service.AccountService;
import pt.drumond.rumosdigitalbank.service.CustomerService;

import java.util.HashSet;
import java.util.Scanner;

/**
 * Application controller class.
 */
public class Bank {
    /**
     * Database that contains all app customers.
     */
    private HashSet<Customer> customerGeneralList;
    /**
     * Database that contains all app accounts.
     */
    private HashSet<Account> accountGeneralList;
    /**
     * Object used to give access to methods from service layer from customer.
     */
    private CustomerService customerService;
    /**
     * Object used to give access to methods from service layer from account.
     */
    private AccountService accountService;
    /**
     * Object that will be made all current operations.
     */
    private Account loggedAccount;

    public Bank() {
        customerGeneralList = new HashSet<>();
        accountGeneralList = new HashSet<>();

        customerService = new CustomerService();
        customerService.loadDatabase(customerGeneralList);

        accountService = new AccountService();
    }

    /**
     * Contains the application core.
     *
     * @param scanner field to be filled on menu
     * @param bank    management class object instance
     */
    public void run(Scanner scanner, Bank bank) {
        boolean flag = false;
        do {
            System.out.print("""
                    ╭═════════════════════════════════$═══╮
                         RUMOS DIGITAL BANK MANAGEMENT
                    ╰═══€═════════════════════════════════╯
                    Choose your option:
                    0. Quit application
                    1. Insert new account
                    2. Manage account by code
                    3. Search client by NIF
                    4. Update client by NIF
                                    
                    Option:\040""");

            switch (Integer.parseInt(scanner.nextLine())) {
                case 1 -> { // CREATE ACCOUNT
                    Customer mainHolder = customerService.createCustomer(scanner, bank, true);
                    boolean wasAccountCreated = false;
                    do {
                        System.out.print("Insert initial deposit value: ");
                        double depositValue = Double.parseDouble(scanner.nextLine());
                        if (accountService.verifyInitialDeposit(depositValue)) {
                            loggedAccount = accountService.createAccount(mainHolder, depositValue, accountGeneralList);

                            wasAccountCreated = true;

                            System.out.println("Account Created successfully");
                            displayMargin(loggedAccount);
                            System.out.println(loggedAccount);
                            displayMargin(loggedAccount);

                            accountService.manageAccountByCode(scanner, bank);
                        } else {
                            System.out.print("Insuficient value \nDo you want to enter a new value?(Y)es / (N)o \nOption: ");
                            if (scanner.nextLine().equalsIgnoreCase("n")) {
                                bank.run(scanner, bank);
                            }
                        }
                    } while (!wasAccountCreated);
                }
                case 2 -> accountService.manageAccountByCode(scanner, bank);
                case 3 -> {
                    Customer customer = customerService.getCustomerByNif(scanner, customerGeneralList);
                    displayMargin(customer);
                    System.out.println(customer);
                    displayMargin(customer);
                }
                case 4 -> {
                    Customer customer = customerService.updateCustomer(scanner, bank, customerGeneralList);
                    System.out.println("Client successfully updated");
                    displayMargin(customer);
                    System.out.println(customer);
                    displayMargin(customer);
                }
                default -> HelloApplication.main(null);
            }
            System.out.print("Do you want to perform another operation? (Y)es/(N)o: ");
            if (scanner.nextLine().equalsIgnoreCase("Y")) {
                flag = true;
            } else {
                HelloApplication.main(null);
            }
        } while (flag);
    }

    /**
     * Displays a sequence of hyphens in the <code>Object.toString()</code> length.
     * Implies calling in the begining and in the end.
     *
     * @param object object that will be made <code>toString().length()</code>
     */
    public void displayMargin(Object object) {
        for (int index = 0; index < object.toString().length(); index++) {
            System.out.print("-");
        }
        System.out.println();
    }

    public HashSet<Customer> getCustomerGeneralList() {
        return customerGeneralList;
    }

    public void setCustomerGeneralList(HashSet<Customer> customerGeneralList) {
        this.customerGeneralList = customerGeneralList;
    }

    public HashSet<Account> getAccountGeneralList() {
        return accountGeneralList;
    }

    public void setAccountGeneralList(HashSet<Account> accountGeneralList) {
        this.accountGeneralList = accountGeneralList;
    }

    public Account getLoggedAccount() {
        return loggedAccount;
    }

    public void setLoggedAccount(Account loggedAccount) {
        this.loggedAccount = loggedAccount;
    }
}