package pt.drumond.rumosdigitalbank.repository.interfaces;

import pt.drumond.rumosdigitalbank.model.Customer;

import java.util.ArrayList;

public interface CustomerRepository {
    Customer create(Customer customer);
    Customer findByNif(String nif);
    void delete(Customer customer);
    ArrayList<Customer> findAll();
    boolean verifyIfNifAlreadyExists(String nif);

    void loadDatabase();
}