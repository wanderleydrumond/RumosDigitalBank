package pt.drumond.rumosdigitalbank.service.interfaces;

import pt.drumond.rumosdigitalbank.model.Customer;

import java.time.LocalDate;
import java.util.ArrayList;

public interface CustomerService {
    Customer save(Customer customer);

    Customer findByNif(String nif);
    Customer update(Customer customer);
    void delete(Customer customer);
    ArrayList<Customer> findAll();
    ArrayList<Customer> loadDatabase();

    boolean validateAge(LocalDate birthDate);

    boolean validateNif(String nif);

    boolean validatePhone(String phone);

    boolean validateMobile(String mobile);

    boolean validateEmail(String email);

}