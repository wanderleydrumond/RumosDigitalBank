package pt.drumond.rumosdigitalbank.service.interfaces;

import pt.drumond.rumosdigitalbank.model.Account;
import pt.drumond.rumosdigitalbank.model.Card;
import pt.drumond.rumosdigitalbank.model.Customer;

import java.util.ArrayList;
import java.util.List;

public interface CardService {
    Card create(Customer cardHolder, boolean isCreditCard);
    Card create(Customer cardHolder, boolean isCreditCard, Account loggedAccount);
    Card update(String pin, Card card);
    Card findBySerialNumber(String serialNumber);
    List<Card> findAllByAccount(Account account);
    void delete(Card cardOwnedByCustomerToBeDeleted);
    boolean payLoan(Card card, double value);
    boolean makeLoan(Card card, double value);
    int getAmountOfCards(int loggedAccountId);
    List<Card> loadDatabase(ArrayList<Customer> customers);
}