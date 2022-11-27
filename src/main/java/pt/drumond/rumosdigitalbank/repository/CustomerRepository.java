package pt.drumond.rumosdigitalbank.repository;

import pt.drumond.rumosdigitalbank.model.Customer;

import java.util.ArrayList;

public interface CustomerRepository {
    Customer save(Customer customer);
    Customer findByNif(String nif);
    void delete(Customer customer);
    ArrayList<Customer> findAll();

    void loadDatabase();
}