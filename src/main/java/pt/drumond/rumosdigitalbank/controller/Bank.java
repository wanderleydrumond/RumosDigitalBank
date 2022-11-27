package pt.drumond.rumosdigitalbank.controller;

import pt.drumond.rumosdigitalbank.HelloApplication;
import pt.drumond.rumosdigitalbank.model.Account;
import pt.drumond.rumosdigitalbank.model.Customer;
import pt.drumond.rumosdigitalbank.service.AccountService;
import pt.drumond.rumosdigitalbank.service.AccountServiceImplementation;
import pt.drumond.rumosdigitalbank.service.CustomerService;
import pt.drumond.rumosdigitalbank.service.CustomerServiceImplementation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
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
    private CustomerService customerServiceImplementation;
    /**
     * Object used to give access to methods from service layer from account.
     */
    private AccountService accountServiceImplementation;
    /**
     * Object that will be made all current operations.
     */
    private Account loggedAccount;
    private Scanner scanner;

    public Bank() {
        customerGeneralList = new HashSet<>();
        accountGeneralList = new HashSet<>();

        customerServiceImplementation = new CustomerServiceImplementation();
        customerServiceImplementation.loadDatabase();

        accountServiceImplementation = new AccountServiceImplementation();
        accountServiceImplementation.loadDatabase();
        scanner = new Scanner(System.in);
    }

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
                    loggedAccount = getAccountNumber();
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
                case 1 -> { // ADD SECONDARY HOLDER
                    Customer secondaryHolder = null;

                    switch (menuAddSecondaryHolder()) {
                        case 1 -> secondaryHolder = createCustomer(false);
                        case 2 -> secondaryHolder = getCustomer();
                        default -> updateAccountMenu();
                    }

                    if (secondaryHolder != null) {
                        accountServiceImplementation.addSecondaryHolder(loggedAccount, secondaryHolder);
                    }
                }

                case 2 -> {
                    /*
                    * 1. pedir valor do depósito
                    * 2. enviar para o service (deposit(loggedAccount, value, MovementType.DEPOSIT))
                    * 3. dentro do deposit():
                    * 3.1. Pegar o saldo da conta e somar com o value e atualizar o saldo da conta
                    * 3.2. criar um movimento de depósito
                    * 3.3. colocar esse movimento dentro da loggedAccount
                    * 4. chamar o metodo accountListRespositoryImplementation.update*/
                    //TODO deposit
                    System.out.print("Insert the deposit value: ");
                    double depositValue = Double.parseDouble(scanner.nextLine());
                    //TODO use saved account
                }
                case 3 -> {
                    //TODO tranfer
                    /*
                    * 1. Pedir valor da transferencia
                    * 2. Pedir conta de destino
                    * 2.1. findByCode da conta de destino (só prossegue se a conta encontrada for diferente de null) (prende dentro de um do_while)
                    * 3. Método que verifica se a loggedAccount tem saldo maior ou igual ao valor da transferência (só prossegue se for true) (prende dentro de um do_while)
                    * 4. Metodo de fazer levantamento
                    * 4.1. Pegar o saldo da conta e subtrair com o value e atualizar o saldo da conta
                    * 5. criar um movimento de transfer_out para o loggedAccount
                    * 6. colocar esse movimento dentro da loggedAccount
                    * 7. chamar o metodo accountListRespositoryImplementation.update(loggedAccount)
                    * 8. chamar o método deposit(destinyAccount, value, MovementType.TRANSFER_IN)
                    * */
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

    private int menuAddSecondaryHolder() {
        System.out.print("""
                0. Cancel operation
                1. Add a new client
                2. Add an existent client
                                            
                Option:\040""");

        return Integer.parseInt(scanner.nextLine());
    }

    private Customer getCustomer() {
        Customer secondaryHolder;
        System.out.print("Enter client NIF number: ");
        boolean customerExists;
        do {
            customerExists = false;
            secondaryHolder = customerServiceImplementation.findByNif(scanner.nextLine());
            if (secondaryHolder != null) {
                customerExists = true;
            } else {
                System.out.println("There is no client for the given NIF number.");
            }
        } while (!customerExists);
        return secondaryHolder;
    }

    private Account getAccountNumber() {
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

    private int updateAccountMenu() {
        System.out.print("""
                ╭═════════════════════════════════════════$═══╮
                     RUMOS DIGITAL BANK MANAGEMENT ACCOUNT
                ╰═══€═════════════════════════════════════════╯
                Choose your option:
                 0. Back to previous menu (logout account)
                 1. Insert new secondary client
                 2. Deposit
                 3. Transfer
                 4. Pay loan
                 5. Update client
                 6. List all clients
                 7. Delete account
                 8. Add new debit card
                 9. Add new credit card
                10. Delete secondary holder
                11. View account details
                                
                Option:\040""");

        return Integer.parseInt(scanner.nextLine());
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