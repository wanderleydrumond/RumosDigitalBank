package pt.drumond.rumosdigitalbank.controller;

import pt.drumond.rumosdigitalbank.Main;
import pt.drumond.rumosdigitalbank.enums.MovementType;
import pt.drumond.rumosdigitalbank.enums.ResponseType;
import pt.drumond.rumosdigitalbank.model.Account;
import pt.drumond.rumosdigitalbank.model.Card;
import pt.drumond.rumosdigitalbank.model.Customer;
import pt.drumond.rumosdigitalbank.model.Movement;
import pt.drumond.rumosdigitalbank.service.interfaces.AccountService;
import pt.drumond.rumosdigitalbank.service.interfaces.CardService;
import pt.drumond.rumosdigitalbank.service.interfaces.CustomerService;
import pt.drumond.rumosdigitalbank.service.interfaces.MovementService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import static pt.drumond.rumosdigitalbank.enums.OutputColours.*;

/**
 * Application controller class.
 */
public class Bank {
    /**
     * Object used to give access to methods from service layer from customer.
     */
    private CustomerService customerServiceImplementation;

    private CardService cardServiceImplementation;
    private MovementService movementServiceImplementation;
    /**
     * Object used to give access to methods from service layer from account.
     */
    private AccountService accountServiceImplementation;
    /**
     * Object that will be made all current operations.
     */
    private Account loggedAccount;
    private Scanner scanner;

    public Bank(CustomerService customerServiceImplementation, CardService cardServiceImplementation, MovementService movementServiceImplementation, AccountService accountServiceImplementation) {
        this.customerServiceImplementation = customerServiceImplementation;
        this.cardServiceImplementation = cardServiceImplementation;
        this.movementServiceImplementation = movementServiceImplementation;
        this.accountServiceImplementation = accountServiceImplementation;

        ArrayList<Customer> customers = this.customerServiceImplementation.loadDatabase();
        ArrayList<Card> cards = this.cardServiceImplementation.loadDatabase(customers);
        ArrayList<Movement> movements = this.movementServiceImplementation.loadDatabase();
        this.accountServiceImplementation.loadDatabase(customers, cards, movements);

        scanner = new Scanner(System.in);
    }

    public CustomerService getCustomerServiceImplementation() {
        return customerServiceImplementation;
    }

    public CardService getCardServiceImplementation() {
        return cardServiceImplementation;
    }

    public MovementService getMovementServiceImplementation() {
        return movementServiceImplementation;
    }

    public AccountService getAccountServiceImplementation() {
        return accountServiceImplementation;
    }

                                                          /*
- - - - - - - - - - - - - - - - - - - - - - - - - - - - MENUS - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
                                                         */

    /**
     * Displays the very first app menu.
     */
    public void initialMenu() {
        System.out.println(DARK_GRAY_TEXT_NORMAL.getValue() + "javafx.runtime.version: " + System.getProperty("javafx.runtime.version"));
        System.out.println("java.runtime.version: " + System.getProperty("java.runtime.version"));

        System.out.println(YELLOW_TEXT_BRIGHT.getValue() + "?????????????????????????????????????????????????????????????????????" + WHITE_TEXT_BRIGHT_BOLD.getValue() + "$" + YELLOW_TEXT_BRIGHT.getValue() + "????????????");
        System.out.println("\040\040\040\040\040" + BLUE_TEXT_BOLD.getValue() + "RUMOS DIGITAL BANK" + "\040\040\040\040\040");
        System.out.println(YELLOW_TEXT_BRIGHT.getValue() + "????????????" + WHITE_TEXT_BRIGHT_BOLD.getValue() + "???" + YELLOW_TEXT_BRIGHT.getValue() + "?????????????????????????????????????????????????????????????????????");
        System.out.println(RESET.getValue() + "Choose your option:");
        System.out.println(CYAN_TEXT_NORMAL.getValue() + "0. " + RESET.getValue() + "Quit");
        System.out.println(CYAN_TEXT_NORMAL.getValue() + "1. " + RESET.getValue() + "ATM");
        System.out.println(CYAN_TEXT_NORMAL.getValue() + "2. " + RESET.getValue() + "Management");
        System.out.print("\nOption:\040");

        new Main().startSelectedApp(Integer.parseInt(scanner.nextLine()));
    }

    /**
     * Displays the main menu.
     *
     * @return the choice made by user
     */
    private int mainMenu() {
        System.out.println(YELLOW_TEXT_BRIGHT.getValue() + "??????????????????????????????????????????????????????????????????????????????????????????????????????" + WHITE_TEXT_BRIGHT_BOLD.getValue() + "$" + YELLOW_TEXT_BRIGHT.getValue() + "????????????");
        System.out.println("\040\040\040\040\040" + BLUE_TEXT_BOLD.getValue() + "RUMOS DIGITAL BANK MANAGEMENT" + "\040\040\040\040\040");
        System.out.println(YELLOW_TEXT_BRIGHT.getValue() + "????????????" + WHITE_TEXT_BRIGHT_BOLD.getValue() + "???" + YELLOW_TEXT_BRIGHT.getValue() + "??????????????????????????????????????????????????????????????????????????????????????????????????????");
        System.out.println(RESET.getValue() + "Choose your option:");
        System.out.println(CYAN_TEXT_NORMAL.getValue() + "0. " + RESET.getValue() + "Back to previous menu");
        System.out.println(CYAN_TEXT_NORMAL.getValue() + "1. " + RESET.getValue() + "Create new account");
        System.out.println(CYAN_TEXT_NORMAL.getValue() + "2. " + RESET.getValue() + "Manage account by code");
        System.out.println(CYAN_TEXT_NORMAL.getValue() + "3. " + RESET.getValue() + "Search client by NIF");
        System.out.println(CYAN_TEXT_NORMAL.getValue() + "4. " + RESET.getValue() + "Update client by NIF");
        System.out.print("\nOption:\040");

        return Integer.parseInt(scanner.nextLine());
    }

    /**
     * <p>Displays the update account menu.</p>
     * <p>Certains options are visible only under conditions.</p>
     *
     * @return the choice made by user
     */
    private int updateAccountMenu() {
        System.out.println(YELLOW_TEXT_BRIGHT.getValue() + "??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????" + WHITE_TEXT_BRIGHT_BOLD.getValue() + "$" + YELLOW_TEXT_BRIGHT.getValue() + "????????????");
        System.out.println("\040\040\040\040\040" + BLUE_TEXT_BOLD.getValue() + "RUMOS DIGITAL BANK MANAGEMENT ACCOUNT" + "\040\040\040\040\040");
        System.out.println(YELLOW_TEXT_BRIGHT.getValue() + "????????????" + WHITE_TEXT_BRIGHT_BOLD.getValue() + "???" + YELLOW_TEXT_BRIGHT.getValue() + "??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
        System.out.println(RESET.getValue() + "Choose your option:");
        System.out.println(CYAN_TEXT_NORMAL.getValue() + "0. " + RESET.getValue() + "Back to previous menu (logout account)");
        System.out.println(CYAN_TEXT_NORMAL.getValue() + "1. " + RESET.getValue() + "View account details");
        System.out.println(CYAN_TEXT_NORMAL.getValue() + "2. " + RESET.getValue() + "Deposit");
        System.out.println(CYAN_TEXT_NORMAL.getValue() + "3. " + RESET.getValue() + "Transfer");
        System.out.println(CYAN_TEXT_NORMAL.getValue() + "4. " + RESET.getValue() + "Pay loan");
        System.out.println(CYAN_TEXT_NORMAL.getValue() + "5. " + RESET.getValue() + "List all clients");
        System.out.println(CYAN_TEXT_NORMAL.getValue() + "6. " + RESET.getValue() + "Delete account");
        System.out.println(CYAN_TEXT_NORMAL.getValue() + "7. " + RESET.getValue() + "List all movements");

        if (accountServiceImplementation.getAmountOfDebitCards(loggedAccount) < 5) {
            System.out.println(CYAN_TEXT_NORMAL.getValue() + "8. " + RESET.getValue() + "Add new debit card");
        } else {
            System.out.println(RED_TEXT_NORMAL.getValue() + "X. " + RESET.getValue() + "This account already reached the maximum amount of debit cards");
        }
        if (accountServiceImplementation.getAmountOfCreditCards(loggedAccount) < 2) {
            System.out.println(CYAN_TEXT_NORMAL.getValue() + "9. " + RESET.getValue() + "Add new credit card");
        } else {
            System.out.println(RED_TEXT_NORMAL.getValue() + "X. " + RESET.getValue() + "This account already reached the maximum amount of credit cards");
        }
        if (accountServiceImplementation.getAmountOfSecondaryHolders(loggedAccount) < 4) {
            System.out.println(CYAN_TEXT_NORMAL.getValue() + "10. " + RESET.getValue() + "Insert new secondary holder");
        } else {
            System.out.println(RED_TEXT_NORMAL.getValue() + "X. " + RESET.getValue() + "This account already reached the maximum amount of secondary holders");
        }
        if (accountServiceImplementation.getAmountOfSecondaryHolders(loggedAccount) > 0) {
            System.out.println(CYAN_TEXT_NORMAL.getValue() + "11. " + RESET.getValue() + "Delete secondary holder");
        } else {
            System.out.println(RED_TEXT_NORMAL.getValue() + "X. " + RESET.getValue() + "This account has no secondary holders to delete");
        }
        System.out.print("\nOption:\040");

        return Integer.parseInt(scanner.nextLine());
    }

    private int menuAddSecondaryHolder() {
        System.out.println(CYAN_TEXT_NORMAL.getValue() + "0. " + RESET.getValue() + "Cancel operation");
        System.out.println(CYAN_TEXT_NORMAL.getValue() + "1. " + RESET.getValue() + "Add a new client");
        System.out.println(CYAN_TEXT_NORMAL.getValue() + "2. " + RESET.getValue() + "Add an existent client");
        System.out.print("\nOption:\040");

        return Integer.parseInt(scanner.nextLine());
    }

    private int updateCustomerMenu() {
        System.out.println("What do you want to update?\n");
        System.out.println(CYAN_TEXT_NORMAL.getValue() + "0. " + RESET.getValue() + "Nothing, I changed my mind");
        System.out.println(CYAN_TEXT_NORMAL.getValue() + "1. " + RESET.getValue() + "Name");
        System.out.println(CYAN_TEXT_NORMAL.getValue() + "2. " + RESET.getValue() + "Password");
        System.out.println(CYAN_TEXT_NORMAL.getValue() + "3. " + RESET.getValue() + "Phone number");
        System.out.println(CYAN_TEXT_NORMAL.getValue() + "4. " + RESET.getValue() + "Mobile number");
        System.out.println(CYAN_TEXT_NORMAL.getValue() + "5. " + RESET.getValue() + "e-mail");
        System.out.println(CYAN_TEXT_NORMAL.getValue() + "6. " + RESET.getValue() + "Profession");
        System.out.print("\nOption:\040");

        return Integer.parseInt(scanner.nextLine());
    }
                                                          /*
- - - - - - - - - - - - - - - - - - - - - - - - - - - END MENUS - - - - - - - - - - - - - - - - - - - - - - - - - - - -
                                                         */

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
                case 3 -> findAndDisplayCustomer();
                case 4 -> updateCustomer();
                default -> initialMenu();
            }
            if (!quit) {
                System.out.print("Do you want to perform another operation? (" + GREEN_TEXT_BRIGHT.getValue() + "Y" + RESET.getValue() + ")es/(" + RED_TEXT_NORMAL.getValue() + "N" + RESET.getValue() + ")o managent: "); //TODO delete the 'management' word
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

    private Customer findAndDisplayCustomer() {
        System.out.print("Enter the NIF number of client to be found: ");
        Customer customer = customerServiceImplementation.findByNif(scanner.nextLine());
        if (customer == null) {
            System.out.println("Client not found");
        } else {
//        printCustomer(customer); // TODO Aqui d?? problema porque o cliente n??o est?? em uma conta
            displayMargin(customer);
            System.out.println(customer);
            displayMargin(customer);
        }

        return customer;
    }

    private boolean updateAccount(boolean quit) {
        boolean doAnotherOperation;
        do {
            doAnotherOperation = false;

            switch (updateAccountMenu()) {
                case 1 -> displayDetails();
                case 2 -> deposit();
                case 3 -> transfer();
                case 4 -> payLoan();
                case 5 -> displayAllHolders();
                case 6 -> deleteAccount();
                case 7 -> displayAllMovements();
                case 8 -> addDebitCard();
                case 9 -> addCreditCard();
                case 10 -> addSecondaryHolder();
                case 11 -> removeSecondaryHolder();
                default -> mainMenu();
            }
            System.out.print("Do you want to perform another operation? (" + GREEN_TEXT_BRIGHT.getValue() + "Y" + RESET.getValue() + ")es/(" + RED_TEXT_NORMAL.getValue() + "N" + RESET.getValue() + ")o account: "); //TODO delete the 'account' word
            if (scanner.nextLine().equalsIgnoreCase("Y")) {
                doAnotherOperation = true;
            } else {
                quit = true;
            }
        } while (doAnotherOperation);
        return quit;
    }

    /**
     * Provides an interface to treat all necessary data to pay loan in credit card in logged account.
     */
    private void payLoan() {
        // Pegando informa????es do cart??o
        String cardSerialNumber = getString("Enter card serial number: "); // recebe o n??mero de s??rie do cart??o
        Card card = accountServiceImplementation.getCardBySerialNumberOnCurrentAccount(loggedAccount, cardSerialNumber); // busca o cart??o, na conta logada, atrav??s do n??mero de s??rie

        if (card != null) {
            displayMargin(card);
            System.out.println(card);
            displayMargin(card);
        }

        if (validateCardSituation(card).equals(ResponseType.SUCCESS)) {
            double value = Double.parseDouble(getString("Enter value to pay: ")); // recebe o valor a ser pago no cart??o de cr??dito
            if (cardServiceImplementation.payLoan(card, value)) { // Tenta pagar o cart??o de cr??dito
                System.out.println("Operation concluded succesfully");
            } else {
                System.out.println("Value to pay exceeds value to pay");
            }
            displayMargin(card);
            System.out.println(card);
            displayMargin(card);
        }
    }

    private ResponseType validateCardSituation(Card card) {
        if (card == null) { // Se n??o encontrar nenhum cart??o com o n??mero de s??rie fornecido
            System.out.println("There are no cards with the given serial number in this account");
            return ResponseType.INEXISTENT;
        }
        if (card.getMonthyPlafond() == 0.) { // Se o cart??o encontrado for de d??bito
            System.out.println("This is a debit card. Operation available only for credit cards.");
            return ResponseType.WRONG_CARD_TYPE;
        }
        if (card.getPlafondBalance() == card.getMonthyPlafond()) { // Se o cart??o de cr??dito encontrado n??o tiver d??vidas
            System.out.println("This card has no debts.");
            return ResponseType.NO_DEBTS;
        }
        return ResponseType.SUCCESS; //Se o cart??o de cr??dito tiver d??vidas
    }

    private void addCreditCard() {
        Customer holderCardOwner = getCustomerByNif(true);
        Card creditCard = accountServiceImplementation.addCreditCard(loggedAccount, holderCardOwner);
        if (creditCard != null) {
            System.out.println(GREEN_TEXT_BRIGHT.getValue() + "Credit card successfully added to account" + RESET.getValue());

            displayMargin(creditCard);
            printCard(creditCard, true);
            displayMargin(creditCard);
        } else {
            System.out.println(RED_TEXT_BRIGHT.getValue() + "Client already has credit card." + RESET.getValue());
        }
    }

    /**
     * Displays all movements for the current account.
     */
    private void displayAllMovements() {
        displayMargin(loggedAccount.getMovements().stream().findFirst().get()); // imprime a quantidade de h??fens do primeiro elemento da lista de movimentos
        loggedAccount.getMovements().forEach(System.out::println); // imprime a lista
        displayMargin(loggedAccount.getMovements().stream().skip(loggedAccount.getMovements().size() - 1).findFirst().get()); // imprime a quantidade de h??fens do ??ltimo elemento da lista de movimentos
    }

    /**
     * Displays the following account data:
     * <ol>
     *     <li>code</li>
     *     <li>current balance</li>
     *     <li>main holder data</li>
     *     <li>secondary holders data list (if exists)</li>
     * </ol>
     */
    private void displayDetails() {
        System.out.println("ACCOUNT DETAILS: \n\nCODE: " + loggedAccount.getCode());
        System.out.printf("BALANCE: %.2f???%n", loggedAccount.getBalance());
        System.out.println("MAIN HOLDER:");
        displayMargin(loggedAccount.getMainHolder());
        System.out.println(loggedAccount.getMainHolder());
        displayMargin(loggedAccount.getMainHolder());

        if (loggedAccount.getSecondaryHolders().size() > 0) { // Se a houver secondary holders
            System.out.println("SECONDARY HOLDERS:");
            displayMargin(loggedAccount.getSecondaryHolders().stream().findFirst().get()); // imprime a quantidade de h??fens do primeiro elemento da lista de secondary holders
            loggedAccount.getSecondaryHolders().forEach(System.out::println);// imprime a lista
            displayMargin(loggedAccount.getSecondaryHolders().stream().skip(loggedAccount.getSecondaryHolders().size() - 1).findFirst().get()); // imprime a quantidade de h??fens do ??ltimo elemento da lista de secondaary holders
        }

        if (loggedAccount.getCards().size() > 0) {
            if (accountServiceImplementation.getAmountOfDebitCards(loggedAccount) > 0) {
                Card firstCardFound = loggedAccount.getCards().stream().findFirst().get(); // pega o primeiro elemento da lista de clientes da conta
                Card lastCardFound = loggedAccount.getCards().stream().skip(loggedAccount.getCards().size() - 1).findFirst().get(); // pega o ??ltimo elemento da lista de clientes da conta

                System.out.println("DEBIT CARDS:");

                displayMargin(firstCardFound);
                accountServiceImplementation.getDebitCards(loggedAccount).forEach(cardElement -> printCard(cardElement, false));
                displayMargin(lastCardFound);
            }
            if (accountServiceImplementation.getAmountOfCreditCards(loggedAccount) > 0) {
                System.out.println("CREDIT CARDS:");
                //TODO imprimir cart??es
                accountServiceImplementation.getCreditCards(loggedAccount).forEach(System.out::println); //TODO extrair para um m??todo
            }
        }
    }

    /**
     * Adds a new debit card into logged account and into database.
     */
    private void addDebitCard() {
        Customer holderCardOwner = getCustomerByNif(true);
        Card debitCard = accountServiceImplementation.addDebitCard(loggedAccount, holderCardOwner);
        if (debitCard != null) {
            System.out.println(GREEN_TEXT_BRIGHT.getValue() + "Debit card successfully added to account" + RESET.getValue());

            displayMargin(debitCard);
            printCard(debitCard, false);
            displayMargin(debitCard);
        } else {
            System.out.println(RED_TEXT_BRIGHT.getValue() + "Client already has debit card." + RESET.getValue());
        }
    }

    private void printCard(Card cardToBePrinted, boolean isCreditCard) {
        System.out.println("Number card: " + cardToBePrinted.getSerialNumber());
        System.out.println("Main holder: " + cardToBePrinted.getCardHolder().getName());
        if (isCreditCard) {
            System.out.println("Monthly plafond: " + cardToBePrinted.getMonthyPlafond());
            System.out.println("Plafond balance: " + cardToBePrinted.getPlafondBalance());
        }
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
        System.out.println(PURPLE_TEXT_BOLD.getValue() + "ATTENTION!" + RESET.getValue());
        System.out.println(PURPLE_TEXT_NORMAL.getValue() + "\nThis action is irreversible!" + RESET.getValue());
        //TODO print table account informations
        System.out.print("Do you want to proceed? (" + GREEN_TEXT_BRIGHT.getValue() + "Y" + RESET.getValue() + ")es/(" + RED_TEXT_NORMAL.getValue() + "N" + RESET.getValue() + ")o: ");

        if (scanner.nextLine().equalsIgnoreCase("Y")) {
            ResponseType answer = accountServiceImplementation.delete(loggedAccount);
            if (answer.equals(ResponseType.BALANCE_BIGGER_THAN_ZERO)) {
                System.out.println(RED_TEXT_BRIGHT.getValue() + "Impossible to remove account. Account balance is bigger than 0.00???" + RESET.getValue());
            }
            if (answer.equals(ResponseType.THERE_ARE_DEBTS)) {
                System.out.println(RED_TEXT_BRIGHT.getValue() + "Impossible to remove account. There are debts on credit card(s)" + RESET.getValue());
            }
            if (answer.equals(ResponseType.SUCCESS)) {
                System.out.println(GREEN_TEXT_BRIGHT.getValue() + "Account successfully deleted" + RESET.getValue());
            }
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
        String destinationAccount = getString("Insert the destination account code: ");
        ResponseType answer = accountServiceImplementation.transfer(loggedAccount, transferValue, destinationAccount);

        switch (answer) {
            case SUCCESS -> System.out.println(GREEN_TEXT_BRIGHT.getValue() + "Transfer successfully done" + RESET.getValue());
            case INSUFFICIENT_BALANCE -> {
                System.out.print(RED_TEXT_BRIGHT.getValue() + "Insuficient balance: " + RESET.getValue());
                System.out.printf("%.2f???%n", loggedAccount.getBalance());
            }
            case INEXISTENT -> System.out.println(RED_TEXT_BRIGHT.getValue() + "Account not found" + RESET.getValue());
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
            Customer lastCustomerFound = loggedAccount.getSecondaryHolders().stream().skip(count - 1).findFirst().get(); // pega o ??ltimo elemento da lista de clientes da conta

            displayMargin(firstCustomerFound);
            loggedAccount.getSecondaryHolders().forEach(System.out::println); // exibe os nomes dos titulares secund??rios
            displayMargin(lastCustomerFound);
        }
    }

    /**
     * Remove a secondary holder from the current account. If they don't exist on any other account (as main or
     * secondary holder), delete them from the bank database.
     */
    private void removeSecondaryHolder() {
        Customer customerToBeDeleted = getCustomerByNif(true);
        if (Boolean.FALSE.equals(accountServiceImplementation.isMainHolder(customerToBeDeleted, loggedAccount))) { // Se n??o for um titular principal
            /*displayMargin(customerToBeDeleted);
            System.out.println(customerToBeDeleted);
            displayMargin(customerToBeDeleted);*/
            printCustomer(customerToBeDeleted);
            System.out.print("Do you confirm this action? (" + GREEN_TEXT_BRIGHT.getValue() + "Y" + RESET.getValue() + ")es/(" + RED_TEXT_NORMAL.getValue() + "N" + RESET.getValue() + ")o: ");
            if (scanner.nextLine().equalsIgnoreCase("Y")) {
                if (accountServiceImplementation.deleteSecondaryHolder(loggedAccount, customerToBeDeleted)) {
                    System.out.println(GREEN_TEXT_BRIGHT.getValue() + "Client deleted successfully" + RESET.getValue());
                } else {
                    System.out.println(RED_TEXT_BRIGHT.getValue() + "Failed to remove: " + RESET.getValue() + "Client has debts on credit card");
                }
            }
        } else {
            System.out.println(RED_TEXT_BRIGHT.getValue() + "This client is a main holder and cannot be deleted" + RESET.getValue());
        }
        updateAccount(true);
    }

    /**
     * <p>Adds a new secondary holder into the logged account.</p>
     * <p>They can be a new customer or an existent one.</p>
     */
    private void addSecondaryHolder() {
        if (accountServiceImplementation.getAmountOfSecondaryHolders(loggedAccount) > 3) {
            return;
        }
        Customer secondaryHolder = null;

        switch (menuAddSecondaryHolder()) {
            case 1 -> secondaryHolder = createCustomer(false);
            case 2 -> secondaryHolder = getCustomerByNif(false);
            default -> updateAccountMenu();
        }

        if (secondaryHolder != null) {
            if (accountServiceImplementation.addSecondaryHolder(loggedAccount, secondaryHolder)) {
                System.out.println(GREEN_TEXT_BRIGHT.getValue() + "Client successfully added to account" + RESET.getValue());
            } else {
                System.out.println(RED_TEXT_BRIGHT.getValue() + "Client already existent in this account" + RESET.getValue());
            }
        }
    }

    /**
     * <p>Provides an interface that asks a customer NIF number and search for them in the database.</p>
     * <p>Keeps the entire logic inside a loop while a non-existent NIF number is provided.</p>
     *
     * @return the <code>Customer</code> that owns that NIF
     */
    private Customer getCustomerByNif(boolean verifyIfExistsInLoggedAccount) {
        Customer customer;
        boolean customerExists;
        do {
            System.out.print("Enter client NIF number: ");
            customerExists = false;
            String nif = scanner.nextLine();
            if (verifyIfExistsInLoggedAccount) {
                customer = accountServiceImplementation.findCustomerByNif(nif, loggedAccount);
            } else {
                customer = customerServiceImplementation.findByNif(nif);
            }
            if (customer != null) {
                customerExists = true;
            } else {
                if (verifyIfExistsInLoggedAccount) {
                    System.out.println(RED_TEXT_BRIGHT.getValue() + "There is no client for the given NIF number in this account." + RESET.getValue());
                } else {
                    System.out.println(RED_TEXT_BRIGHT + "There is no client for the given NIF number." + RESET.getValue());
                }
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
            loggedAccount = accountServiceImplementation.getByCode(scanner.nextLine());
            if (loggedAccount != null) {
                accountExists = true;
            } else {
                System.out.println(RED_TEXT_BRIGHT.getValue() + "There is no account for the given code number." + RESET.getValue());
            }
        } while (!accountExists);

        return loggedAccount;
    }

    /**
     * Gives the proper interface to create a new customer, inserting their information one by one.
     *
     * @param isMainHolder important to validate the holder's age
     * @return the new holder
     */
    private Customer createCustomer(boolean isMainHolder) {
        LocalDate birthDate = getBirthDateAndValidateAge(isMainHolder);
        String nif = getAndValidateNif();
        String phone = getAndValidatePhone();
        String mobile = getAndValidateMobile();
        String email = getAndValidateEmail();
        String name = getString("Insert name: ");
        String password = getString("Insert password: ");
        String profession = getString("Insert profession: ");

        return customerServiceImplementation.save(new Customer(nif, name, password, phone, mobile, email, profession, birthDate));
    }

    /**
     * Gives the proper interface to update the customer's information.
     */
    private void updateCustomer() {
        Customer customer = findAndDisplayCustomer(); // Procura algu??m na base de dados com o mesmo NIF
        if (customer == null) { // Se n??o encontrar
            System.out.println("Client not found");
        } else { // Se encontrar
            boolean exitUpdateCustomerMenu = false;
            do {
                switch (updateCustomerMenu()) { // Exibe um menu de op????es para as informa????es que podem ser atualizadas
                    case 1 -> customer.setName(getString("Insert new name: ")); // atualiza o nome do cliente no objeto
                    case 2 ->
                            customer.setPassword(getString("Insert new password: ")); // atualiza a senha do cliente no objeto
                    case 3 ->
                            customer.setPhone(getString("Insert new phone number: ")); // atualiza o n??mero de telefone do cliente no objeto
                    case 4 ->
                            customer.setMobile(getString("Insert new mobile number: ")); // atualiza o n??mero de telefone celular do cliente no objeto
                    case 5 ->
                            customer.setEmail(getString("Insert new e-mail: ")); // atualiza o e-mail do cliente no objeto
                    case 6 ->
                            customer.setProfession(getString("Insert new profession: ")); // atualiza a profiss??o do cliente no objeto
                    default -> {/*retorna ao menu principal*/}
                }
                customerServiceImplementation.update(customer); // atualiza as informa????es do cliente na base de dados
                System.out.println(YELLOW_TEXT_NORMAL.getValue() + "Client current informations:" + RESET.getValue());
                /*displayMargin(customer);
                System.out.println(customer);
                displayMargin(customer);*/

                printCustomer(customer);

                if (getString("Do you want to update another client information? (" + GREEN_TEXT_BRIGHT.getValue() + "Y" + RESET.getValue() + ")es/(" + RED_TEXT_NORMAL.getValue() + "N" + RESET.getValue() + ")o: ").equalsIgnoreCase("N")) {
                    exitUpdateCustomerMenu = true;
                }
            } while (!exitUpdateCustomerMenu);
        }
    }

    /**
     * Gets simple data to be inserted furthermore.
     *
     * @param sentenceToDisplay text that distiguish the kind of data wil be inserted
     * @return the data to be inserted
     */
    private String getString(String sentenceToDisplay) {
        System.out.print(sentenceToDisplay);

        return scanner.nextLine();
    }

    private void firstDepositAndCreateAccount(Customer mainHolder) {
        boolean wasAccountCreated = false;

        do {
            System.out.print("Insert initial deposit value: ");
            double firstDeposit = Double.parseDouble(scanner.nextLine());
            if (accountServiceImplementation.validateInitialDeposit(firstDeposit)) {
                loggedAccount = accountServiceImplementation.create(new Account(firstDeposit, mainHolder), mainHolder);

                wasAccountCreated = true;

                System.out.println(GREEN_TEXT_BRIGHT.getValue() + "Account Created successfully" + RESET.getValue());
                displayMargin(loggedAccount);
                System.out.println(loggedAccount);
                displayMargin(loggedAccount);

            } else {
                System.out.println(RED_TEXT_BRIGHT.getValue() + "Insuficient value " + RESET.getValue() + "\nDo you want to enter a new value?(" + GREEN_TEXT_BRIGHT.getValue() + "Y" + RESET.getValue() + ")es/(");
                System.out.print(RED_TEXT_NORMAL + "N" + RESET.getValue() + ")o \nOption: ");
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
                System.out.println(RED_TEXT_BRIGHT.getValue() + "Invalid email" + RESET.getValue());
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
                System.out.println(RED_TEXT_BRIGHT.getValue() + "Invalid mobile number" + RESET.getValue());
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
                System.out.println(RED_TEXT_BRIGHT.getValue() + "Invalid phone number" + RESET.getValue());
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
                System.out.println(RED_TEXT_BRIGHT.getValue() + "Invalid NIF number." + RESET.getValue());
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
                System.out.println(RED_TEXT_BRIGHT.getValue() + "Main holder must be 18 years old at least." + RESET.getValue());
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

                                                         /*
- - - - - - - - - - - - - - - - - - - - - - - - - DISPLAY METHODS - - - - - - - - - - - - - - - - - - - - - - - - - - -
                                                         */

    public void printCustomer(Customer customer) {
        ArrayList<String> names = new ArrayList<>();
        String biggestName = "", biggestEmail = "", biggestProfession = "", name = "NAME", email = "E-MAIL", profession = "PROFESSION";
        final String SPACE = "\040";

        if (loggedAccount.getSecondaryHolders() != null) {
            for (Customer customerElement : loggedAccount.getSecondaryHolders()) {
                names.add(customerElement.getName());
            }
            biggestName = Collections.max(names, Comparator.comparing(String::length));

            ArrayList<String> emails = new ArrayList<>();
            for (Customer customerElement : loggedAccount.getSecondaryHolders()) {
                emails.add(customerElement.getEmail());
            }
            biggestEmail = Collections.max(emails, Comparator.comparing(String::length));

            ArrayList<String> professions = new ArrayList<>();
            for (Customer customerElement : loggedAccount.getSecondaryHolders()) {
                professions.add(customerElement.getProfession());
            }
            biggestProfession = Collections.max(professions, Comparator.comparing(String::length));
        }

        printCustomerMargin(customer, biggestName, biggestEmail, biggestProfession, true); // Impress??o da linha superior do cabe??alho

        //Impress??o do conte??do do cabe??alho
        System.out.print("|" + SPACE + PURPLE_TEXT_NORMAL.getValue() + "NIF\040\040\040\040\040\040" + SPACE + RESET.getValue());
        System.out.print("|" + SPACE + PURPLE_TEXT_NORMAL.getValue() + name + SPACE + RESET.getValue());
        for (int index = 0; index < biggestName.length() - name.length(); index++) {
            System.out.print(SPACE);
        }
        System.out.print("|" + SPACE + PURPLE_TEXT_NORMAL.getValue() + "PHONE\040\040\040\040" + SPACE + RESET.getValue());
        System.out.print("|" + SPACE + PURPLE_TEXT_NORMAL.getValue() + "MOBILE\040\040" + SPACE + RESET.getValue());
        System.out.print("|" + SPACE + PURPLE_TEXT_NORMAL.getValue() + email + SPACE + RESET.getValue());
        for (int index = 0; index < biggestEmail.length() - email.length(); index++) {
            System.out.print(SPACE);
        }
        System.out.print("|" + SPACE + PURPLE_TEXT_NORMAL.getValue() + profession + SPACE + RESET.getValue());
        for (int index = 0; index < biggestProfession.length() - profession.length(); index++) {
            System.out.print(SPACE);
        }
        System.out.println("|");
        // Fim da impress??o do conte??do do cabe??alho

        printCustomerMargin(customer, biggestName, biggestEmail, biggestProfession, false); // Impress??o da linha inferior do cabe??alho

        //Impress??o dos dados
        System.out.print("|" + SPACE);
        System.out.print(customer.getNif() + SPACE);
        System.out.print("|" + SPACE);
        System.out.print(customer.getName() + SPACE);
        if (customer.getName().length() <= biggestName.length()) {
            for (int index = 0; index < biggestName.length() - customer.getName().length(); index++) {
                System.out.print(SPACE);
            }
            System.out.print("|" + SPACE);
        }
        System.out.print(customer.getPhone() + SPACE);
        System.out.print("|" + SPACE);
        System.out.print(customer.getMobile() + SPACE);
        System.out.print("|" + SPACE);
        System.out.print(customer.getEmail() + SPACE);
        if (customer.getEmail().length() <= biggestEmail.length()) {
            for (int index = 0; index < biggestEmail.length() - customer.getEmail().length(); index++) {
                System.out.print(SPACE);
            }
        }
        System.out.print("|" + SPACE);
        System.out.print(customer.getProfession() + SPACE);
        if (customer.getProfession().length() <= biggestProfession.length()) {
            for (int index = 0; index < biggestProfession.length() - customer.getProfession().length(); index++) {
                System.out.print(SPACE);
            }
        }
        System.out.println("|");

        printCustomerMargin(customer, biggestName, biggestEmail, biggestProfession, true); // Impress??o da linha inferior

    }

    private void printCustomerMargin(Customer customer, String biggestName, String biggestEmail, String biggestProfession, boolean isLineThin) {
        final String PLUS = "+", MINUS = "-", EQUAL = "=";

        System.out.print(PLUS);
        for (int index = 0; index < customer.getNif().length() + 2; index++) {
            if (isLineThin) {
                System.out.print(MINUS);
            } else {
                System.out.print(EQUAL);
            }
        }
        System.out.print(PLUS);
        for (int index = 0; index < biggestName.length() + 2; index++) {
            if (isLineThin) {
                System.out.print(MINUS);
            } else {
                System.out.print(EQUAL);
            }
        }
        System.out.print(PLUS);
        for (int index = 0; index < customer.getPhone().length() + 2; index++) {
            if (isLineThin) {
                System.out.print(MINUS);
            } else {
                System.out.print(EQUAL);
            }
        }
        System.out.print(PLUS);
        for (int index = 0; index < customer.getMobile().length() + 2; index++) {
            if (isLineThin) {
                System.out.print(MINUS);
            } else {
                System.out.print(EQUAL);
            }
        }
        System.out.print(PLUS);
        for (int index = 0; index < biggestEmail.length() + 2; index++) {
            if (isLineThin) {
                System.out.print(MINUS);
            } else {
                System.out.print(EQUAL);
            }
        }
        System.out.print(PLUS);
        for (int index = 0; index < biggestProfession.length() + 2; index++) {
            if (isLineThin) {
                System.out.print(MINUS);
            } else {
                System.out.print(EQUAL);
            }
        }
        System.out.println(PLUS);
    }
                                                          /*
- - - - - - - - - - - - - - - - - - - - - - - - - END DISPLAY METHODS - - - - - - - - - - - - - - - - - - - - - - - - -
                                                         */
}