package pt.drumond.rumosdigitalbank.service.implementations;

import pt.drumond.rumosdigitalbank.controller.Bank;
import pt.drumond.rumosdigitalbank.model.Customer;
import pt.drumond.rumosdigitalbank.repository.implementations.CustomerListRepositoryImplementation;
import pt.drumond.rumosdigitalbank.repository.interfaces.CustomerRepository;
import pt.drumond.rumosdigitalbank.service.interfaces.CustomerService;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Contains all methods responsible for the businees rules related to customers.
 */
public class CustomerServiceImplementation implements CustomerService {
    /**
     * Contains all methods from the persistence layer.
     */
    private CustomerRepository customerListRepositoryImplementation;

    public CustomerServiceImplementation() {
        customerListRepositoryImplementation = new CustomerListRepositoryImplementation();
    }


    @Override
    public Customer save(Customer customer) {

        return customerListRepositoryImplementation.create(customer);
    }

    @Override
    public Customer update(Customer customer) {

        return customerListRepositoryImplementation.update(customer);
    }

    /**
     * Gets a specific customer that owns the given NIF number.
     *
     * @return the <code>Customer</code> object
     */
    public Customer findByNif(String nif) {

        return customerListRepositoryImplementation.findByNif(nif);
    }

    /**
     * Updates a customer with a given NIF number.<br>
     * <em>Allows returning to main menu typing 0</em>
     *
     * @param scanner field to be filled with the NIF number
     * @return the updated <code>Customer</code>
     */
    public Customer updateCustomer(Scanner scanner) {
        int option;
        System.out.print("Insert the client NIF to be updated (0 to cancel): ");
        Customer customer = customerListRepositoryImplementation.findByNif(scanner.nextLine());
        boolean flagUpdate = false;
        do {
//            bank.displayMargin(customer);
            System.out.println(customer);
//            bank.displayMargin(customer);

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
//                    insertName(scanner, customer, bank);
//                    setAndShowCustomer(customer, bank, customerGeneralList);
                }
                case 2 -> {
//                    insertPassword(scanner, customer, bank);
//                    setAndShowCustomer(customer, bank, customerGeneralList);
                }
                case 3 -> {
//                    insertPhone(scanner, customer, false, bank);
//                    setAndShowCustomer(customer, bank, customerGeneralList);
                }
                case 4 -> {
//                    insertMobile(scanner, customer, false, bank);
//                    setAndShowCustomer(customer, bank, customerGeneralList);
                }
                case 5 -> {
//                    insertEmail(scanner, customer, false, bank);
//                    setAndShowCustomer(customer, bank, customerGeneralList);
                }
                case 6 -> {
//                    insertProfession(scanner, customer, bank);
//                    setAndShowCustomer(customer, bank, customerGeneralList);
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
     */
    public void delete(Customer customer) {


        customerListRepositoryImplementation.delete(customer);
    }

    /**
     * Displays all customers.
     */
    public ArrayList<Customer> findAll() {
        return customerListRepositoryImplementation.findAll();
    }

    public boolean validateNif(String nif) {

        return Boolean.FALSE.equals(customerListRepositoryImplementation.verifyIfNifAlreadyExists(nif)) && nif.matches("^[1-9][0-9]{8}$");
    }

    public boolean validatePhone(String phone) {
        return phone.matches("^[2-3][0-9]{8}$");
    }

    public boolean validateMobile(String mobile) {
        return mobile.matches("^(9)[0-9]{8}$");
    }

    public boolean validateEmail(String email) {
        return email.matches("^(.+)@(.+)$");
    }

    public boolean validateAge(LocalDate birthDate) {
        LocalDate today = LocalDate.now();
        int age = Period.between(birthDate, today).getYears();
        if (age >= 18) {
            return true;
        }
        return false;
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
        /*customerListRepositoryImplementation.findAll(customerGeneralList).forEach(customerElement -> {
            if (customerElement.getNif().equals(customer.getNif())) {
                bank.displayMargin(customerElement);
                System.out.println(customerElement);
                bank.displayMargin(customerElement);
            }
        });*/
    }

    /**
     * Generates initial data to fill the Arraylist that's serves as database.
     */
    @Override
    public void loadDatabase() {
        customerListRepositoryImplementation.loadDatabase();
    }
}