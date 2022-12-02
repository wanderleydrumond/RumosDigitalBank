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