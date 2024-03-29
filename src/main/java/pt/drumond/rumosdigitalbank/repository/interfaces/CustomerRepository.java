package pt.drumond.rumosdigitalbank.repository.interfaces;

import pt.drumond.rumosdigitalbank.model.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerRepository {
    Customer create(Customer customer) throws SQLException;
    Customer findByNif(String nif);
    Customer findById(int id);
    Customer update(Customer customer);
    void delete(Customer customer);
    List<Customer> findAll();
    boolean verifyIfNifAlreadyExists(String nif);
    List<Customer> loadDatabase();
}