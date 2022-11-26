package pt.drumond.rumosdigitalbank.service;

import pt.drumond.rumosdigitalbank.controller.Bank;
import pt.drumond.rumosdigitalbank.model.Customer;
import pt.drumond.rumosdigitalbank.repository.CustomerListRepository;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Contains all methods responsible for the businees rules related to customers.
 */
public class CustomerService {
    /**
     * Contains all methods from the persistence layer.
     */
    private CustomerListRepository customerListRepository;

    public CustomerService() {
        customerListRepository = new CustomerListRepository();
    }

    /**
     * Creates a new customer instance given the requirements to be done.
     *
     * @param scanner      field to be filled with the customer's data
     * @param bank         management class object instance
     * @param isMainHolder determines if the <code>Customer</code> is the account main holder
     * @return the new <code>Customer</code>
     */
    public Customer createCustomer(Scanner scanner, Bank bank, boolean isMainHolder) {
        Customer customer = new Customer();

        insertBirthDate(scanner, customer, bank, isMainHolder);
        insertNif(scanner, customer, false, bank);
        insertName(scanner, customer, bank);
        insertPassword(scanner, customer, bank);
        insertPhone(scanner, customer, false, bank);
        insertMobile(scanner, customer, false, bank);
        insertEmail(scanner, customer, false, bank);
        insertProfession(scanner, customer, bank);

        return customerListRepository.save(customer, bank.getCustomerGeneralList());
    }

    /**
     * Gets a specific customer that owns the given NIF number.
     *
     * @param scanner             field to be filled with the customer's data
     * @param customerGeneralList collection with all bank customers
     * @return the <code>Customer</code> object
     */
    public Customer getCustomerByNif(Scanner scanner, HashSet<Customer> customerGeneralList) {
        System.out.print("Enter client NIF number (0 to cancel): ");
        String typedNif = scanner.nextLine();
        if (typedNif.equals("0")) {
            return null;
        }
        return customerListRepository.findByNif(typedNif, scanner, customerGeneralList);
    }

    /**
     * Updates a customer with a given NIF number.<br>
     * <em>Allows returning to main menu typing 0</em>
     *
     * @param scanner             field to be filled with the NIF number
     * @param bank                management class object instance
     * @param customerGeneralList collection with all bank customers
     * @return the updated <code>Customer</code>
     */
    public Customer updateCustomer(Scanner scanner, Bank bank, HashSet<Customer> customerGeneralList) {
        int option;
        System.out.print("Insert the client NIF to be updated (0 to cancel): ");
        Customer customer = customerListRepository.findByNif(scanner.nextLine(), scanner, customerGeneralList);
        boolean flagUpdate = false;
        do {
            bank.displayMargin(customer);
            System.out.println(customer);
            bank.displayMargin(customer);

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
                    insertName(scanner, customer, bank);
                    setAndShowCustomer(customer, bank, customerGeneralList);
                }
                case 2 -> {
                    insertPassword(scanner, customer, bank);
                    setAndShowCustomer(customer, bank, customerGeneralList);
                }
                case 3 -> {
                    insertPhone(scanner, customer, false, bank);
                    setAndShowCustomer(customer, bank, customerGeneralList);
                }
                case 4 -> {
                    insertMobile(scanner, customer, false, bank);
                    setAndShowCustomer(customer, bank, customerGeneralList);
                }
                case 5 -> {
                    insertEmail(scanner, customer, false, bank);
                    setAndShowCustomer(customer, bank, customerGeneralList);
                }
                case 6 -> {
                    insertProfession(scanner, customer, bank);
                    setAndShowCustomer(customer, bank, customerGeneralList);
                }
            }
            if (option != 0) {
                System.out.print("Do you want update something else? (Y)es/(N)o: ");
                if (scanner.nextLine().equalsIgnoreCase("Y")) {
                    flagUpdate = true;
                }
            }
        } while (flagUpdate && option != 0);
        return customer;
    }

    /**
     * Deletes a customer with a given NIF number.<br>
     * <em>Allows returning to main menu typing 0</em>
     *
     * @param scanner             field to be filled with the NIF number
     * @param bank                management class object instance
     * @param customerGeneralList collection with all bank customers
     * @return <ul>
     * <li>true if deleted successfully</li>
     * <li>false if negative confirmation. Operation canceled</li>
     * </ul>
     */
    public boolean deleteCustomerByNif(Scanner scanner, Bank bank, HashSet<Customer> customerGeneralList) {
        System.out.print("Insert the client NIF to be deleted (0 to cancel): ");
        Customer customer = customerListRepository.findByNif(scanner.nextLine(), scanner, customerGeneralList);
        if (customer != null) {
            bank.displayMargin(customer);
            System.out.println(customer);
            bank.displayMargin(customer);
            System.out.print("\nDo you confirm operation for this customer? it is irrevesible.\n(Y)es/(N)o: ");
            if (scanner.nextLine().equalsIgnoreCase("Y")) {
                customerListRepository.delete(customer, customerGeneralList);

                return true;
            }
        }

        return false;
    }

    /**
     * Displays all customers.
     *
     * @param customerGeneralList collection with all bank customers
     */
    public void getAllCustomers(HashSet<Customer> customerGeneralList) {
        customerListRepository.findAll(customerGeneralList).forEach(System.out::println);
    }

    /**
     * Fills the NIF field.
     *
     * @param scanner     field to be filled
     * @param customer    object that contains the attribute NIF to be inserted
     * @param isValidated flag that controls if the NIF is set or not
     * @param bank        management class object instance
     */
    private void insertNif(Scanner scanner, Customer customer, boolean isValidated, Bank bank) {
        while (!isValidated) {
            System.out.print("Insert nif (0 to cancel): ");
            String nif = scanner.nextLine();
            if (nif.equals("0")) {
                bank.run(scanner, bank);
            } else {
                isValidated = customer.setNif(nif);
            }
        }
    }

    /**
     * Fills the name field.
     *
     * @param scanner  field to be filled
     * @param customer object that contains the attribute name to be inserted
     * @param bank     management class object instance
     */
    private void insertName(Scanner scanner, Customer customer, Bank bank) {
        System.out.print("Insert name (0 to cancel): ");
        String name = scanner.nextLine();
        if (name.equals("0")) {
            bank.run(scanner, bank);
        } else {
            customer.setName(name);
        }
    }

    /**
     * Fills the password field.
     *
     * @param scanner  field to be filled
     * @param customer object that contains the attribute password to be inserted
     * @param bank     management class object instance
     */
    private void insertPassword(Scanner scanner, Customer customer, Bank bank) {
        System.out.print("Insert password (0 to cancel): ");
        String password = scanner.nextLine();
        if (password.equals("0")) {
            bank.run(scanner, bank);
        } else {
            customer.setPassword(password);
        }
    }

    /**
     * Fills the Phone field.
     *
     * @param scanner     field to be filled
     * @param customer    object that contains the attribute phone to be inserted
     * @param isValidated flag that controls if the phone is set or not
     * @param bank        management class object instance
     */
    private void insertPhone(Scanner scanner, Customer customer, boolean isValidated, Bank bank) {
        while (!isValidated) {
            System.out.print("Insert phone number (0 to cancel): ");
            String phone = scanner.nextLine();
            if (phone.equals("0")) {
                bank.run(scanner, bank);
            } else {
                isValidated = customer.setPhone(phone);
            }
        }
    }

    /**
     * Fills the mobile field.
     *
     * @param scanner     field to be filled
     * @param customer    object that contains the attribute mobile to be inserted
     * @param isValidated flag that controls if the mobile is set or not
     * @param bank        management class object instance
     */
    private void insertMobile(Scanner scanner, Customer customer, boolean isValidated, Bank bank) {
        while (!isValidated) {
            System.out.print("Insert mobile number (0 to cancel): ");
            String mobile = scanner.nextLine();
            if (mobile.equals("0")) {
                bank.run(scanner, bank);
            } else {
                isValidated = customer.setMobile(mobile);
            }
        }
    }

    /**
     * Fills the email field.
     *
     * @param scanner     field to be filled
     * @param customer    object that contains the attribute email to be inserted
     * @param isValidated flag that controls if the email is set or not
     * @param bank        management class object instance
     */
    private void insertEmail(Scanner scanner, Customer customer, boolean isValidated, Bank bank) {
        while (!isValidated) {
            System.out.print("Insert email (0 to cancel): ");
            String email = scanner.nextLine();
            if (email.equals("0")) {
                bank.run(scanner, bank);
            } else {
                isValidated = customer.setEmail(email);
            }
        }
    }

    /**
     * Fills the profession field.
     *
     * @param scanner  field to be filled
     * @param customer object that contains the attribute profession to be inserted
     * @param bank     management class object instance
     */
    private void insertProfession(Scanner scanner, Customer customer, Bank bank) {
        System.out.print("Insert profession (0 to cancel): ");
        String profession = scanner.nextLine();
        if (profession.equals("0")) {
            bank.run(scanner, bank);
        } else {
            customer.setProfession(profession);
        }
    }

    /**
     * Fills the birthDate field.
     *
     * @param scanner      field to be filled
     * @param customer     object that contains the attribute mobile to be inserted
     * @param bank         management class object instance
     * @param isMainHolder determines if the <code>Customer</code> is the account main holder
     */
    private void insertBirthDate(Scanner scanner, Customer customer, Bank bank, boolean isMainHolder) {
        System.out.print("Insert date of birth (dd/MM/yyyy) (0 to cancel): ");
        String answer = scanner.nextLine();
        boolean doSetBirthDate = true;
        if (answer.equals("0")) {
            bank.run(scanner, bank);
        } else {
            LocalDate birthDate = LocalDate.parse(answer, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            if (isMainHolder) {
                LocalDate today = LocalDate.now();
                int age = Period.between(birthDate, today).getYears();
                if (age < 18) {
                    System.out.println("Main holder must be 18 years old at least. Current age: " + age);
                    doSetBirthDate = false;
                    bank.run(scanner, bank);
                }
            }
            if (doSetBirthDate) {
                customer.setBirthDate(birthDate);
            }
        }
    }

    /**
     * <ol>
     *     <li>Sets a costumer attribute inside the customers list.</li>
     *     <li>Displays all customers for the list.</li>
     * </ol>
     *
     * @param customer            object that contains all parameters that will be updated
     * @param bank                management class object instance
     * @param customerGeneralList collection with all bank customers
     */
    private void setAndShowCustomer(Customer customer, Bank bank, HashSet<Customer> customerGeneralList) {
//        customerListRepository.findAll(customerGeneralList).set(customerListRepository.findAll(customerGeneralList).indexOf(customer), customer);
        customerListRepository.findAll(customerGeneralList).forEach(customerElement -> {
            if (customerElement.getNif().equals(customer.getNif())) {
                bank.displayMargin(customerElement);
                System.out.println(customerElement);
                bank.displayMargin(customerElement);
            }
        });
    }

    /**
     * Generates initial data to fill the Arraylist that's serves as database.
     *
     * @param customerGeneralList collection with all bank customers
     */
    public void loadDatabase(HashSet<Customer> customerGeneralList) {
        customerListRepository.loadDatabase(customerGeneralList);
    }
}