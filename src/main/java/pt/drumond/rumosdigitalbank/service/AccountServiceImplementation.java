package pt.drumond.rumosdigitalbank.service;

import pt.drumond.rumosdigitalbank.model.Account;
import pt.drumond.rumosdigitalbank.model.Movement;
import pt.drumond.rumosdigitalbank.model.MovementType;
import pt.drumond.rumosdigitalbank.repository.AccountListRepositoryImplementation;
import pt.drumond.rumosdigitalbank.repository.CustomerListRepositoryImplementation;

/**
 * Contains all methods responsible for the businees rules related to accounts.
 */
public class AccountServiceImplementation implements AccountService {
    private CustomerServiceImplementation customerServiceImplementation;
    private AccountListRepositoryImplementation accountListRepositoryImplementation;
    private CustomerListRepositoryImplementation customerListRepositoryImplementation;

    private MovementService movementService;

    public AccountServiceImplementation() {
        customerServiceImplementation = new CustomerServiceImplementation();
        accountListRepositoryImplementation = new AccountListRepositoryImplementation();
        customerListRepositoryImplementation = new CustomerListRepositoryImplementation();
        movementService = new MovementService();
    }

    /**
     * Creates a new account.
     *
     * @return the new account created
     */
    @Override
    public Account create(Account account) {
        Movement movement = movementService.createTransaction(account.getBalance(), MovementType.DEPOSIT);
        account.addTransaction(movement);

        return accountListRepositoryImplementation.save(account);
    }

    /**
     * Allows to do several activities and transactions for the logged account.
     */
    @Override
    public Account update(Account account) {
        /*Account loggedAccount = bank.getLoggedAccount();
        HashSet<Customer> customerGeneralList = bank.getCustomerGeneralList();
        HashSet<Account> accountGeneralList = bank.getAccountGeneralList();

        if (loggedAccount == null) { // Conta já existente
            System.out.print("Insert account code: ");
            loggedAccount = accountListRepositoryImplementation.findAccountByCode(scanner.nextLine(), accountGeneralList);
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

            System.out.println(" 8. Add new debit card");
            System.out.println(" 9. Add new credit card");
            System.out.println("10. Delete secondary holder");
            System.out.print("\nOption:\040");

            switch (Integer.parseInt(scanner.nextLine())) {
                case -1 -> bank.startAppManagement();
//                case 1 -> customerService.createCustomer(scanner, bank, false);
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
        } while (flag);*/
        return null;
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
    public boolean validateInitialDeposit(double depositValue) {
        return depositValue >= 50.;
    }
}