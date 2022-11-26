package pt.drumond.rumosdigitalbank.service;

import pt.drumond.rumosdigitalbank.HelloApplication;
import pt.drumond.rumosdigitalbank.controller.Bank;
import pt.drumond.rumosdigitalbank.model.Account;
import pt.drumond.rumosdigitalbank.model.Customer;
import pt.drumond.rumosdigitalbank.model.Transaction;
import pt.drumond.rumosdigitalbank.model.TransactionType;
import pt.drumond.rumosdigitalbank.repository.AccountListRepository;
import pt.drumond.rumosdigitalbank.repository.CustomerListRepository;

import java.util.HashSet;
import java.util.Scanner;

/**
 * Contains all methods responsible for the businees rules related to accounts.
 */
public class AccountService {
    private CustomerService customerService;
    private AccountListRepository accountListRepository;
    private CustomerListRepository customerListRepository;

    private TransactionService transactionService;

    public AccountService() {
        customerService = new CustomerService();
        accountListRepository = new AccountListRepository();
        customerListRepository = new CustomerListRepository();
        transactionService = new TransactionService();
    }

    /**
     * Creates a new account.
     *
     * @param mainHolder         the customer that owns the account
     * @param depositValue       the initial value nacessary to create the account
     * @param accountGeneralList collection that holds all bank accounts
     * @return the new account created
     */
    public Account createAccount(Customer mainHolder, double depositValue, HashSet<Account> accountGeneralList) {
        Account account = new Account();

        account.setCode(String.valueOf(10 + accountGeneralList.size()));
        account.setMainHolder(mainHolder);

        Transaction transaction = transactionService.createTransaction(depositValue, TransactionType.DEPOSIT);
        account.setBalance(depositValue);
        account.addTransaction(transaction);

        return accountListRepository.save(account, accountGeneralList);
    }

    /**
     * Allows to do several activities and transactions for the logged account.
     *
     * @param scanner field to be filled
     * @param bank    instance of the controller class that holds the services methods and databases lists
     */
    public void manageAccountByCode(Scanner scanner, Bank bank) {
        Account loggedAccount = bank.getLoggedAccount();
        HashSet<Customer> customerGeneralList = bank.getCustomerGeneralList();
        HashSet<Account> accountGeneralList = bank.getAccountGeneralList();

        if (loggedAccount == null) { // Conta já existente
            System.out.print("Insert account code: ");
            loggedAccount = accountListRepository.findAccountByCode(scanner.nextLine(), accountGeneralList);
        }

        boolean flag = false;
        do {
            System.out.println("""
                    ╭═════════════════════════════════$═══╮
                         RUMOS DIGITAL BANK MANAGEMENT
                    ╰═══€═════════════════════════════════╯
                    Choose your option:
                    -1. Back to previous menu (logout account)
                     0. Quit application
                     1. Insert new secondary client
                     2. Deposit
                     3. Transfer
                     4. Pay loan
                     5. Update client
                     6. List all clients
                     7. Delete account""");

            if (accountListRepository.findAllDebitCards().size() <= 5) {
                System.out.println(" 8. Add new debit card");
            }
            if (accountListRepository.findAllCreditCards().size() <= 2) {
                System.out.println(" 9. Add new credit card");
            }
            if (customerListRepository.findAll(customerGeneralList).size() > 1) {
                System.out.println("10. Delete secondary holder");
            }
            System.out.print("\nOption:\040");

            switch (Integer.parseInt(scanner.nextLine())) {
                case -1 -> bank.run(scanner, bank);
                case 1 -> customerService.createCustomer(scanner, bank, false);
                case 2 -> {
                    //TODO deposit
                    System.out.print("Insert the deposit value: ");
                    double depositValue = Double.parseDouble(scanner.nextLine());
                    //TODO use saved account
                }
                case 3 -> {
                    //TODO tranfer
                }
                case 4 -> {
                    //TODO pay loan
                }
                case 5 -> {
                    //TODO update client
                }
                case 6 -> {
                    //TODO list all clients
                }
                case 7 -> {
                    //TODO delete account
                }
                case 8 -> {
                    //TODO add new debit card
                }
                case 9 -> {
                    //TODO add new credit card
                }
                case 10 -> {
                    //TODO delete secondary holder
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
     * Verifies if the first deposit into account has a mininum value of 50.
     *
     * @param depositValue value to be deposited
     * @return <ul>
     * <li>true if the <span style="color:#ffb86c; font-style: italic">depositValue</span> is 50 or superior</li>
     * <li>false if the <span style="color:#ffb86c; font-style: italic">depositValue</span> is minor than 50</li>
     * </ul>
     */
    public boolean verifyInitialDeposit(double depositValue) {
        return depositValue >= 50.;
    }
}