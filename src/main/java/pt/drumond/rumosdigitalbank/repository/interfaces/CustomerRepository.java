package pt.drumond.rumosdigitalbank.repository.interfaces;

import pt.drumond.rumosdigitalbank.model.Customer;

import java.util.List;

public interface CustomerRepository {
    Customer create(Customer customer);
    Customer findByNif(String nif);
    Customer update(Customer customer);
    void delete(Customer customer);
    List<Customer> findAll();
    boolean verifyIfNifAlreadyExists(String nif);
    List<Customer> loadDatabase();
}