package pt.drumond.rumosdigitalbank.service.interfaces;

import pt.drumond.rumosdigitalbank.model.Account;
import pt.drumond.rumosdigitalbank.model.Customer;
import pt.drumond.rumosdigitalbank.model.Card;

import java.util.ArrayList;

public interface CardService {
    Card create(Customer cardHolder, boolean isCreditCard);
    Card update(String pin, Card card);
    Card findBySerialNumber(String serialNumber);
    ArrayList<Card> findAllByAccount(Account account);
    void delete(Card cardOwnedByCustomerToBeDeleted);
    boolean payLoan(Card card, double value);
    ArrayList<Card> loadDatabase(ArrayList<Customer> customers);
}