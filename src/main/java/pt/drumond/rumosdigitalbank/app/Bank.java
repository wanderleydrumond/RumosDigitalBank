package pt.drumond.rumosdigitalbank.app;

import pt.drumond.rumosdigitalbank.HelloApplication;
import pt.drumond.rumosdigitalbank.model.Customer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Application controller class.
 */
public class Bank {
    private ArrayList<Customer> customers = new ArrayList<>();

    /**
     * Contains the application core.
     *
     * @param scanner field to be filled on menu
     */
    public void run(Scanner scanner) {
        boolean flag = false;
        do {
            System.out.print("""
                    ╭═════════════════════════════════$═══╮
                         RUMOS DIGITAL BANK MANAGEMENT
                    ╰═══€═════════════════════════════════╯
                    Choose your option:
                    0. Quit application
                    1. Insert new client
                    2. Search client by NIF
                    3. Update client by NIF
                    4. Delete client by NIF
                    5. Display all clients
                                    
                    Option:\040""");

            switch (Integer.parseInt(scanner.nextLine())) {
                case 1 -> createCustomer(scanner);
                case 2 -> findCustomerByNif(scanner);
                case 3 -> updateCustomer(scanner);
                case 4 -> deleteCustomer(scanner);
                case 5 -> findAllCustomers();
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
     * Creates a new customer.<br>
     * <em>Allows returning to main menu typing 0</em>
     * <ol>
     *     <li>Instiates a new customer object.</li>
     *     <li>Calls the methods to fill each field.</li>
     *     <li>Adds it to to database (Arraylist)</li>
     *     <li>Displays success message</li>
     *     <li>Displays the inserted customer inside the database formatted</li>
     * </ol>
     *
     * @param scanner field to be filled inside each iner method.
     */
    private void createCustomer(Scanner scanner) {
        Customer customer = new Customer();

        insertNif(scanner, customer, false);
        insertName(scanner, customer);
        insertPassword(scanner, customer);
        insertPhone(scanner, customer, false);
        insertMobile(scanner, customer, false);
        insertEmail(scanner, customer, false);
        insertProfession(scanner, customer);
        insertBirthDate(scanner, customer);

        customers.add(customer);

        System.out.println("Client successfully created");
        displayMargin(customer);
        customers.forEach(customerElement -> {
            if (customerElement.getNif().equals(customer.getNif())){
                System.out.println(customer);
            }
        });
        displayMargin(customer);
    }

    /**
     * Finds a customer with a given NIF number. <br>
     * <em>Allows returning to main menu typing 0</em>
     *
     * @param scanner field to be filled with the NIF number
     */
    private void findCustomerByNif(Scanner scanner) {
        System.out.print("Enter client NIF number (0 to cancel): ");
        String typedNif = scanner.nextLine();
        if (typedNif.equals("0")) {
            return;
        } else {
            Customer customer = findByNif(typedNif, scanner);
            if (customer != null) {
                displayMargin(customer);
                System.out.println(customer);
                displayMargin(customer);
            }
        }
    }

    /**
     * Updates a customer with a given NIF number.<br>
     * <em>Allows returning to main menu typing 0</em>
     *
     * @param scanner field to be filled with the NIF number
     */
    private void updateCustomer(Scanner scanner) {
        int option;
        System.out.print("Insert the client NIF to be updated (0 to cancel): ");
        Customer customer = findByNif(scanner.nextLine(), scanner);
        boolean flagUpdate = false;
        do {
            System.out.println(customer);
            System.out.print("""
                    What do you want to update?
                                            
                    0. Nothing, I changed my mind
                    1. Name
                    2. Password
                    3. Phone number
                    4. Mobile number
                    5. e-mail
                    6. Profession
                                            
                    Option:\040""");

            option = Integer.parseInt(scanner.nextLine());
            switch (option) {
                case 1 -> {
                    insertName(scanner, customer);
                    setAndShowCustomer(customer);
                }
                case 2 -> {
                    insertPassword(scanner, customer);
                    setAndShowCustomer(customer);
                }
                case 3 -> {
                    insertPhone(scanner, customer, false);
                    setAndShowCustomer(customer);
                }
                case 4 -> {
                    insertMobile(scanner, customer, false);
                    setAndShowCustomer(customer);
                }
                case 5 -> {
                    insertEmail(scanner, customer, false);
                    setAndShowCustomer(customer);
                }
                case 6 -> {
                    insertProfession(scanner, customer);
                    setAndShowCustomer(customer);
                }
            }
            if (option != 0) {
                System.out.print("Do you want update something else? (Y)es/(N)o: ");
                if (scanner.nextLine().equalsIgnoreCase("Y")) {
                    flagUpdate = true;
                }
            }
        } while (flagUpdate && option != 0);
    }

    /**
     * Deletes a customer with a given NIF number.<br>
     * <em>Allows returning to main menu typing 0</em>
     *
     * @param scanner  field to be filled with the NIF number
     */
    private void deleteCustomer(Scanner scanner) {
        System.out.print("Insert the client NIF to be deleted (0 to cancel): ");
        Customer customer = findByNif(scanner.nextLine(), scanner);
        displayMargin(customer);
        System.out.println(customer);
        displayMargin(customer);
        System.out.print("\nDo you confirm operation for this customer? it is irrevesible.\n(Y)es/(N)o: ");

        if (scanner.nextLine().equalsIgnoreCase("Y")) {
            if (customers.removeIf(customerElement -> customerElement.getNif().equals(customer.getNif()))) {
                System.out.println("Client successfully deleted");
            }
        } else {
            return;
        }
    }

    /**
     * Displays all customers.
     */
    private void findAllCustomers() {
        customers.forEach(System.out::println);
    }

    /**
     * <ol>
     *     <li>Sets a costumer attribute inside the customers list.</li>
     *     <li>Displays all customers for the list.</li>
     * </ol>
     *
     * @param customer object that contains all parameters that will be updated
     */
    private void setAndShowCustomer(Customer customer) {
        customers.set(customers.indexOf(customer), customer);
        customers.forEach(customerElement -> {
            if (customerElement.getNif().equals(customer.getNif())) {
                displayMargin(customerElement);
                System.out.println(customerElement);
                displayMargin(customerElement);
            }
        });
    }

    /**
     * Displays a sequence of hyphens in the <code>Object.toString()</code> length.
     * Implies calling in the begining and in the end.
     *
     * @param customerElement object that will be made <code>toString().length()</code>
     */
    private void displayMargin(Customer customerElement) {
        for (int index = 0; index < customerElement.toString().length(); index++) {
            System.out.print("-");
        }
        System.out.println();
    }

    /**
     * Fills the NIF field.
     *
     * @param scanner     field to be filled
     * @param customer    object that contains the attribute NIF to be inserted
     * @param isValidated flag that controns if the NIF is set or not
     */
    private void insertNif(Scanner scanner, Customer customer, boolean isValidated) {
        while (!isValidated) {
            System.out.print("Insert nif (0 to cancel): ");
            String nif = scanner.nextLine();
            if (nif.equals("0")) {
                run(scanner);
            } else {
                isValidated = customer.setNif(nif);
            }
        }
    }

    /**
     * Fills the Phone field.
     *
     * @param scanner     field to be filled
     * @param customer    object that contains the attribute phone to be inserted
     * @param isValidated flag that controns if the phone is set or not
     */
    private void insertPhone(Scanner scanner, Customer customer, boolean isValidated) {
        while (!isValidated) {
            System.out.print("Insert phone number (0 to cancel): ");
            String phone = scanner.nextLine();
            if (phone.equals("0")) {
                run(scanner);
            } else {
                isValidated = customer.setPhone(phone);
            }
        }
    }

    /**
     * Fills the password field.
     *
     * @param scanner  field to be filled
     * @param customer object that contains the attribute password to be inserted
     */
    private void insertPassword(Scanner scanner, Customer customer) {
        System.out.print("Insert password (0 to cancel): ");
        String password = scanner.nextLine();
        if (password.equals("0")) {
            run(scanner);
        } else {
            customer.setPassword(password);
        }
    }

    /**
     * Fills the name field.
     *
     * @param scanner  field to be filled
     * @param customer object that contains the attribute name to be inserted
     */
    private void insertName(Scanner scanner, Customer customer) {
        System.out.print("Insert name (0 to cancel): ");
        String name = scanner.nextLine();
        if (name.equals("0")) {
            run(scanner);
        } else {
            customer.setName(name);
        }
    }

    /**
     * Fills the profession field.
     *
     * @param scanner  field to be filled
     * @param customer object that contains the attribute profession to be inserted
     */
    private void insertProfession(Scanner scanner, Customer customer) {
        System.out.print("Insert profession (0 to cancel): ");
        String profession = scanner.nextLine();
        if (profession.equals("0")) {
            run(scanner);
        } else {
            customer.setProfession(profession);
        }
    }

    /**
     * Fills the email field.
     *
     * @param scanner     field to be filled
     * @param customer    object that contains the attribute email to be inserted
     * @param isValidated flag that controns if the email is set or not
     */
    private void insertEmail(Scanner scanner, Customer customer, boolean isValidated) {
        while (!isValidated) {
            System.out.print("Insert email (0 to cancel): ");
            String email = scanner.nextLine();
            if (email.equals("0")) {
                run(scanner);
            } else {
                isValidated = customer.setEmail(email);
            }
        }
    }

    /**
     * Fills the mobile field.
     *
     * @param scanner     field to be filled
     * @param customer    object that contains the attribute mobile to be inserted
     * @param isValidated flag that controns if the mobile is set or not
     */
    private void insertMobile(Scanner scanner, Customer customer, boolean isValidated) {
        while (!isValidated) {
            System.out.print("Insert mobile number (0 to cancel): ");
            String mobile = scanner.nextLine();
            if (mobile.equals("0")) {
                run(scanner);
            } else {
                isValidated = customer.setMobile(mobile);
            }
        }
    }

    /**
     * Fills the birthDate field.
     *
     * @param scanner  field to be filled
     * @param customer object that contains the attribute mobile to be inserted
     */
    private void insertBirthDate(Scanner scanner, Customer customer) {
        System.out.print("Insert date of birth (dd/MM/yyyy) (0 to cancel): ");
        String birthDate = scanner.nextLine();
        if (birthDate.equals("0")) {
            run(scanner);
        } else {
            customer.setBirthDate(LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
    }


    /**
     * Finds a customer given a NIF number.
     *
     * @param nif     customer key search
     * @param scanner field to be filled
     * @return the customer who owns that NIF number
     */
    public Customer findByNif(String nif, Scanner scanner) {
        boolean wasNifFound = false;
        String typedNif = nif;
        while (!wasNifFound) {
            for (Customer customerElement : customers) {
                if (customerElement.getNif().equalsIgnoreCase(typedNif)) {
                    wasNifFound = true;
                    return customerElement;
                }
            }
            if (!wasNifFound) {
                System.out.print("Does not exist a client with the given NIF number\nEnter a valid NIF or type 0 to quit: ");
                typedNif = scanner.nextLine();
                if (typedNif.equals("0")) {
                    break;
                }
            }
        }
        return null;
    }

    /**
     * Generates initial data to fill the Arraylist that's serves as database.
     */
    public void loadDatabase() {
        Customer customer1 = new Customer("987456321", "Jane Doe", "123456", "321654987", "99885544", "someone@email.com", "Lawyer", LocalDate.of(1983, 2, 24));
        Customer customer2 = new Customer("123456789", "John Doe", "654321", "321644481", "99221166", "anything@email.com", "Pilot", LocalDate.of(1973, 12, 12));
        Customer customer3 = new Customer("132456789", "Rosalvo Doe", "123654", "325554937", "99887766", "something@email.com", "Firefighter", LocalDate.of(1985, 8, 2));
        Customer customer4 = new Customer("369258147", "João das Couves", "526341", "222111333", "951951951", "cabbages@email.pt", "seller", LocalDate.of(1972, 1, 22));
        Customer customer5 = new Customer("144774144", "Aang", "540022", "250140320", "981258457", "air@avatar.com", "avatar", LocalDate.of(2005, 1, 22));
        Customer customer6 = new Customer("144774144", "Korra", "541166", "335478852", "999258741", "water@avatar.com", "avatar", LocalDate.of(2011, 1, 22));

        customers.addAll(Arrays.asList(customer1, customer2, customer3, customer4, customer5, customer6));
    }
}