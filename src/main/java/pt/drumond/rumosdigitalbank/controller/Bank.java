package pt.drumond.rumosdigitalbank.controller;

import pt.drumond.rumosdigitalbank.HelloApplication;
import pt.drumond.rumosdigitalbank.model.Account;
import pt.drumond.rumosdigitalbank.model.Customer;
import pt.drumond.rumosdigitalbank.model.DebitCard;
import pt.drumond.rumosdigitalbank.model.MovementType;
import pt.drumond.rumosdigitalbank.service.interfaces.AccountService;
import pt.drumond.rumosdigitalbank.service.implementations.AccountServiceImplementation;
import pt.drumond.rumosdigitalbank.service.interfaces.CustomerService;
import pt.drumond.rumosdigitalbank.service.implementations.CustomerServiceImplementation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;
import java.util.Scanner;

/**
 * Application controller class.
 */
public class Bank {
    /**
     * Database that contains all app customers.
     */
    private HashSet<Customer> customerGeneralList; //TODO  to be deleted
    /**
     * Database that contains all app accounts.
     */
    private HashSet<Account> accountGeneralList;//TODO  to be deleted
    /**
     * Object used to give access to methods from service layer from customer.
     */
    private CustomerService customerServiceImplementation = new CustomerServiceImplementation();
    /**
     * Object used to give access to methods from service layer from account.
     */
    private AccountService accountServiceImplementation = new AccountServiceImplementation();
    /**
     * Object that will be made all current operations.
     */
    private Account loggedAccount;
    private Scanner scanner;

    public Bank() {
        customerGeneralList = new HashSet<>();
        accountGeneralList = new HashSet<>();

        customerServiceImplementation.loadDatabase();
        accountServiceImplementation.loadDatabase();

        scanner = new Scanner(System.in);
    }

                                                          /*
- - - - - - - - - - - - - - - - - - - - - - - - - - - - MENUS - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
                                                         */

    /**
     * Displays the very first app menu.
     */
    public void initialMenu() {
        System.out.print("""
                ╭══════════════════════$═══╮
                     RUMOS DIGITAL BANK
                ╰═══€══════════════════════╯
                Choose your option:
                0. Quit
                1. ATM
                2. Management
                                
                Option:\040""");

        new HelloApplication().startSelectedApp(Integer.parseInt(scanner.nextLine()));
    }

    /**
     * Displays the main menu.
     *
     * @return the choice made by user
     */
    private int mainMenu() {
        System.out.print("""
                ╭═════════════════════════════════$═══╮
                     RUMOS DIGITAL BANK MANAGEMENT
                ╰═══€═════════════════════════════════╯
                Choose your option:
                0. Back to previous menu
                1. Create new account
                2. Manage account by code
                3. Search client by NIF
                4. Update client by NIF
                                
                Option:\040""");

        return Integer.parseInt(scanner.nextLine());
    }

    /**
     * <p>Displays the update account menu.</p>
     * <p>Certains options are visible only under conditions.</p>
     *
     * @return the choice made by user
     */
    private int updateAccountMenu() {
        System.out.println("""
                ╭═════════════════════════════════════════$═══╮
                     RUMOS DIGITAL BANK MANAGEMENT ACCOUNT
                ╰═══€═════════════════════════════════════════╯
                Choose your option:
                 0. Back to previous menu (logout account)
                 1. View account details
                 2. Deposit
                 3. Transfer
                 4. Pay loan
                 5. List all clients
                 6. Delete account
                 7. List all movements""");

        if (accountServiceImplementation.getAmountOfDebitCards(loggedAccount) < 5) {
            System.out.println(" 8. Add new debit card");
        }
        if (accountServiceImplementation.getAmountOfCreditCards(loggedAccount) < 2) {
            System.out.println(" 9. Add new credit card");
        }
        if (accountServiceImplementation.getAmountOfSecondaryHolders(loggedAccount) < 4) {
            System.out.println("10. Insert new secondary holder");
        }
        if (accountServiceImplementation.getAmountOfSecondaryHolders(loggedAccount) > 0) {
            System.out.println("11. Delete secondary holder");
        }
        System.out.print("\nOption:\040");

        return Integer.parseInt(scanner.nextLine());
    }

    private int menuAddSecondaryHolder() {
        System.out.print("""
                0. Cancel operation
                1. Add a new client
                2. Add an existent client
                                            
                Option:\040""");

        return Integer.parseInt(scanner.nextLine());
    }

    /**
     * Contains the application core.
     */
    public void startAppManagement() {
        boolean proceed = false, quit;
        do {
            quit = false;
            switch (mainMenu()) {
                case 1 -> { // CREATE ACCOUNT
                    Customer mainHolder = createCustomer(true);
                    firstDepositAndCreateAccount(mainHolder);
                    quit = updateAccount(quit);
                }
                case 2 -> { // UPDATE ACCOUNT
                    loggedAccount = getAccountByCode();
                    quit = updateAccount(quit);
                }
                case 3 -> {
                    /*Customer customer = customerServiceImplementation.findByNif(scanner, customerGeneralList);
                    displayMargin(customer);
                    System.out.println(customer);
                    displayMargin(customer);*/
                }
                case 4 -> {
                    /*Customer customer = customerServiceImplementation.updateCustomer(scanner);
                    System.out.println("Client successfully updated");
                    displayMargin(customer);
                    System.out.println(customer);
                    displayMargin(customer);*/
                }
                default -> initialMenu();
            }
            if (!quit) {
                System.out.print("Do you want to perform another operation? (Y)es/(N)o managent: ");
                if (scanner.nextLine().equalsIgnoreCase("Y")) {
                    proceed = true;
                } else {
                    initialMenu();
                }
            } else {
                proceed = true;
            }
        } while (proceed);
    } //FIM startAppManagement()

    private boolean updateAccount(boolean quit) {
        boolean doAnotherOperation;
        do {
            doAnotherOperation = false;

            switch (updateAccountMenu()) {
                case 1 -> {
                    //TODO View account details
                    System.out.println("ACCOUNT DETAILS: \n\nCODE: " + loggedAccount.getCode());
                    System.out.printf("BALANCE: %.2f€%n", loggedAccount.getBalance());
                    System.out.println("MAIN HOLDER:");
                    displayMargin(loggedAccount.getMainHolder());
                    System.out.println(loggedAccount.getMainHolder());
                    displayMargin(loggedAccount.getMainHolder());
                    loggedAccount.getSecondaryHolders().forEach(System.out::println);
                }
                case 2 -> deposit();
                case 3 -> transfer();
                case 4 -> {
                    //TODO pay loan
                }
                case 5 -> displayAllHolders();
                case 6 -> deleteAccount();
                case 7 -> {
                    //TODO list all movements between two dates
                }
                case 8 -> {
                    //TODO add new debit card
                    Customer holderCardOwner = getCustomerByNif();
                    if (accountServiceImplementation.addDebitCard(loggedAccount, holderCardOwner)) {
                        System.out.println("Debit card successfully added to account");
                        Optional<DebitCard> debitCard = loggedAccount.getDebitCards().stream().filter(debitCardElement -> debitCardElement.getCardHolder().getNif().equals(holderCardOwner.getNif())).findFirst(); // pego o cartão que acabou de ser criado para o cliente em questão

                        displayMargin(debitCard);
//                        https://www.callicoder.com/java-8-optional-tutorial/
                        debitCard.ifPresent(System.out::println);
                        displayMargin(debitCard);
                    }
                }
                case 9 -> {
                    //TODO add new credit card
                }
                case 10 -> addSecondaryHolder();
                case 11 -> removeSecondaryHolder();
                default -> mainMenu();
            }
            System.out.print("Do you want to perform another operation? (Y)es/(N)o account: ");
            if (scanner.nextLine().equalsIgnoreCase("Y")) {
                doAnotherOperation = true;
            } else {
                quit = true;
            }
        } while (doAnotherOperation);
        return quit;
    }

    /**
     * <ol>
     *     <li>Displays a warning and confoirmation message</li>
     *     <li>Deletes the logged account from the bank database</li>
     *     <li>Displays a success message</li>
     *     <li>Redirects to main menu</li>
     * </ol>
     */
    private void deleteAccount() {
        System.out.print("ATTENTION!\nThis action is irreversible!\nDon do want to proceed? (Y)es/(N)o: ");
        if (scanner.nextLine().equalsIgnoreCase("Y")) {
            accountServiceImplementation.delete(loggedAccount);
            System.out.println("Account successfully deleted");
        }
        mainMenu();
    }

    /**
     * <ol>
     *     <li>Asks the deposit value</li>
     *     <li>Perfom the deposit</li>
     * </ol>
     */
    private void deposit() {
        System.out.print("Insert the deposit value: ");
        double depositValue = Double.parseDouble(scanner.nextLine());
        accountServiceImplementation.deposit(loggedAccount, depositValue, MovementType.DEPOSIT);
    }

    /**
     * <ol>
     *     <li>Asks the value to be transfered</li>
     *     <li>Aks the destination account code</li>
     *     <li>Performs the tranfer</li>
     *     <li>Displays the feedback message</li>
     *     <li>Redirect to current menu</li>
     * </ol>
     */
    private void transfer() {
        System.out.print("Insert the transfer value: ");
        double transferValue = Double.parseDouble(scanner.nextLine());
        System.out.print("Insert the destination account code: ");
        String destinationAccount = scanner.nextLine();
        if (accountServiceImplementation.transfer(loggedAccount, transferValue, destinationAccount)) {
            System.out.println("Transfer successfully done");
        } else {
            System.out.printf("Insuficient balance: %.2f€%n", loggedAccount.getBalance());
        }
        updateAccount(true);
    }

    /**
     * Displays all account holders.
     */
    private void displayAllHolders() {
        System.out.println("Main holder:"); // exibe o nome do titular principal
        displayMargin(loggedAccount.getMainHolder());
        System.out.println(loggedAccount.getMainHolder());
        displayMargin(loggedAccount.getMainHolder());
        if (loggedAccount.getSecondaryHolders().size() > 0) { // Se houverem titulares secundarios
            System.out.println("Secondary holders: ");
            long count = loggedAccount.getSecondaryHolders().size();
            Customer firstCustomerFound = loggedAccount.getSecondaryHolders().stream().findFirst().get(); // pega o primeiro elemento da lista de clientes da conta
            Customer lastCustomerFound = loggedAccount.getSecondaryHolders().stream().skip(count - 1).findFirst().get(); // pega o último elemento da lista de clientes da conta

            displayMargin(firstCustomerFound);
            loggedAccount.getSecondaryHolders().forEach(System.out::println); // exibe os nomes dos titulares secundários
            displayMargin(lastCustomerFound);
        }
    }

    /**
     * Remove a secondary holder from the current account. If they don't exist on any other account (as main or
     * secondary holder), delete them from the bank database.
     */
    private void removeSecondaryHolder() {
        Customer customerToBeDeleted = getCustomerByNif();
        displayMargin(customerToBeDeleted);
        System.out.println(customerToBeDeleted);
        displayMargin(customerToBeDeleted);
        System.out.print("Do you confirm this action? (Y)es/(N)o: ");
        if (scanner.nextLine().equalsIgnoreCase("Y")) {
            accountServiceImplementation.deleteSecondaryHolder(loggedAccount, customerToBeDeleted);
            System.out.println("Client deleted successfully");
        }
        updateAccount(true);
    }

    /**
     * <p>Adds a new secondary holder into the logged account.</p>
     * <p>They can be a new customer or an existent one.</p>
     */
    private void addSecondaryHolder() {
        Customer secondaryHolder = null;

        switch (menuAddSecondaryHolder()) {
            case 1 -> secondaryHolder = createCustomer(false);
            case 2 -> secondaryHolder = getCustomerByNif();
            default -> updateAccountMenu();
        }

        if (secondaryHolder != null) {
            accountServiceImplementation.addSecondaryHolder(loggedAccount, secondaryHolder);
        }
    }

    /**
     * <p>Provides an interface that asks a customer NIF number and search for them in the database.</p>
     * <p>Keeps the entire logic inside a loop while a non-existent NIF number is provided.</p>
     *
     * @return the <code>Customer</code> that owns that NIF
     */
    private Customer getCustomerByNif() {
        Customer customer;
        boolean customerExists;
        do {
            System.out.print("Enter client NIF number: ");
            customerExists = false;
            customer = customerServiceImplementation.findByNif(scanner.nextLine());
            if (customer != null) {
                customerExists = true;
            } else {
                System.out.println("There is no client for the given NIF number.");
            }
        } while (!customerExists);
        return customer;
    }

    /**
     * <p>Provides an interface that asks an account code number and search for it in the database.</p>
     * <p>Keeps the entire logic inside a loop while a non-existent code number is provided.</p>
     *
     * @return the <code>Account</code> that owns that code
     */
    private Account getAccountByCode() {
        boolean accountExists;
        do {
            accountExists = false;
            System.out.print("Insert account code: ");
            loggedAccount = accountServiceImplementation.findByCode(scanner.nextLine());
            if (loggedAccount != null) {
                accountExists = true;
            } else {
                System.out.println("There is no account for the given code number.");
            }
        } while (!accountExists);

        return loggedAccount;
    }

    private Customer createCustomer(boolean isMainHolder) {
        LocalDate birthDate = getBirthDateAndValidateAge(isMainHolder);
        String nif = getAndValidateNif();
        String phone = getAndValidatePhone();
        String mobile = getAndValidateMobile();
        String email = getAndValidateEmail();

        System.out.print("Insert name: ");
        String name = scanner.nextLine();

        System.out.print("Insert password: ");
        String password = scanner.nextLine();

        System.out.print("Insert profession: ");
        String profession = scanner.nextLine();

        return customerServiceImplementation.createCustomer(new Customer(nif, name, password, phone, mobile, email, profession, birthDate));
    }

    private void firstDepositAndCreateAccount(Customer mainHolder) {
        boolean wasAccountCreated = false;

        do {
            System.out.print("Insert initial deposit value: ");
            double firstDeposit = Double.parseDouble(scanner.nextLine());
            if (accountServiceImplementation.validateInitialDeposit(firstDeposit)) {
                loggedAccount = accountServiceImplementation.create(new Account(firstDeposit, mainHolder));

                wasAccountCreated = true;

                System.out.println("Account Created successfully");
                displayMargin(loggedAccount);
                System.out.println(loggedAccount);
                displayMargin(loggedAccount);

            } else {
                System.out.print("Insuficient value \nDo you want to enter a new value?(Y)es / (N)o \nOption: ");
                if (scanner.nextLine().equalsIgnoreCase("n")) {
                    mainMenu(); // Go back to main menu
                }
            }
        } while (!wasAccountCreated);
    }

    private String getAndValidateEmail() {
        boolean isEmailOK;
        String email;
        do {
            isEmailOK = true;
            System.out.print("Insert e-mail: ");
            email = scanner.nextLine();

            if (!customerServiceImplementation.validateEmail(email)) {
                System.out.println("Invalid email");
                isEmailOK = false;
            }
        } while (!isEmailOK);
        return email;
    }

    private String getAndValidateMobile() {
        boolean isMobileOK;
        String mobile;
        do {
            isMobileOK = true;
            System.out.print("Insert mobile number: ");
            mobile = scanner.nextLine();

            if (!customerServiceImplementation.validateMobile(mobile)) {
                System.out.println("Invalid mobile number");
                isMobileOK = false;
            }
        } while (!isMobileOK);
        return mobile;
    }

    private String getAndValidatePhone() {
        boolean isPhoneOK;
        String phone;
        do {
            isPhoneOK = true;
            System.out.print("Insert phone number: ");
            phone = scanner.nextLine();

            if (!customerServiceImplementation.validatePhone(phone)) {
                System.out.println("Invalid phone number");
                isPhoneOK = false;
            }
        } while (!isPhoneOK);
        return phone;
    }

    private boolean cancelAndGoBack(String answer) { //TODO to be deleted?
        if (answer.equals("0")) {
            mainMenu(); // Go back to main menu
            return true;
        }
        return false;
    }

    private String getAndValidateNif() {
        boolean isNifOK;
        String nif;
        do {
            isNifOK = true;
            System.out.print("Insert nif: ");
            nif = scanner.nextLine();
            if (!customerServiceImplementation.validateNif(nif)) {
                System.out.println("Invalid NIF number.");
                isNifOK = false;
            }
        } while (!isNifOK);
        return nif;
    }

    private LocalDate getBirthDateAndValidateAge(boolean isMainHolder) {
        LocalDate birthDate;
        boolean isOlderThan18;
        do {
            isOlderThan18 = true;
            System.out.print("Insert date of birth (dd/MM/yyyy): ");
            birthDate = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            if (isMainHolder && !customerServiceImplementation.validateAge(birthDate)) {
                System.out.println("Main holder must be 18 years old at least.");
                isOlderThan18 = false;
            }
        } while (!isOlderThan18);
        return birthDate;
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