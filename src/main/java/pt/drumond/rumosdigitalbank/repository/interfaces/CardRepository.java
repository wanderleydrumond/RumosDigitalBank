package pt.drumond.rumosdigitalbank.repository.interfaces;

import pt.drumond.rumosdigitalbank.model.Account;
import pt.drumond.rumosdigitalbank.model.Card;
import pt.drumond.rumosdigitalbank.model.Customer;

import java.util.ArrayList;
import java.util.List;

public interface CardRepository {
    Card create(Card card);
    Card create(Card card, boolean isCreditCard);
    Card update(Card card);
    Card findBySerialNumber(String serialNumber);
    List<Card> findAllByAccount(Account account);
    void delete(Card cardOwnedByCustomerToBeDeleted);
    int countDebitCards(int loggedAccountId);
    List<Card> loadDatabase(ArrayList<Customer> customers);
}