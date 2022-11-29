package pt.drumond.rumosdigitalbank.service.interfaces;

import pt.drumond.rumosdigitalbank.model.Customer;

import java.time.LocalDate;
import java.util.ArrayList;

public interface CustomerService {
    Customer createCustomer(Customer customer);

    Customer findByNif(String nif);
//    Customer updateCustomer(Customer customer);
    void delete(Customer customer);
    ArrayList<Customer> findAll();
    void loadDatabase();

    boolean validateAge(LocalDate birthDate);

    boolean validateNif(String nif);

    boolean validatePhone(String phone);

    boolean validateMobile(String mobile);

    boolean validateEmail(String email);
}