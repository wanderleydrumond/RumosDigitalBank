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
        boolean flag = false;
        do {
            switch (mainMenu()) {
                case 1 -> { // CREATE ACCOUNT
                    LocalDate birthDate = getBirthDateAndValidateAge();
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

                    Customer mainHolder = customerServiceImplementation.createCustomer(new Customer(nif, name, password, phone, mobile, email, profession, birthDate));
                    Account account = firstDepositAndCreateAccount(mainHolder, loggedAccount);
                }
//                case 2 -> accountService.manageAccountByCode(scanner);
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
            System.out.print("Do you want to perform another operation? (Y)es/(N)o: ");
            if (scanner.nextLine().equalsIgnoreCase("Y")) {
                flag = true;
            } else {
                initialMenu();
            }
        } while (flag);
    }

    private Account firstDepositAndCreateAccount(Customer mainHolder, Account loggedAccount) {
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

//                            accountService.manageAccountByCode(scanner, bank); //TODO WE STOPPED HERE
            } else {
                System.out.print("Insuficient value \nDo you want to enter a new value?(Y)es / (N)o \nOption: ");
                if (scanner.nextLine().equalsIgnoreCase("n")) {
                    mainMenu(); // Go back to main menu
                }
            }
        } while (!wasAccountCreated);
        return loggedAccount;
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

    private LocalDate getBirthDateAndValidateAge() {
        LocalDate birthDate;
        boolean isOlderThan18;
        do {
            isOlderThan18 = true;
            System.out.print("Insert date of birth (dd/MM/yyyy): ");
            String answer = scanner.nextLine();
            birthDate = LocalDate.parse(answer, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            if (!customerServiceImplementation.validateAge(birthDate)) {
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
                0. Quit application
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