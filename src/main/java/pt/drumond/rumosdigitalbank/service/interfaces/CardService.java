package pt.drumond.rumosdigitalbank.service.interfaces;

import pt.drumond.rumosdigitalbank.model.Account;
import pt.drumond.rumosdigitalbank.model.Customer;
import pt.drumond.rumosdigitalbank.model.Card;

import java.util.ArrayList;

public interface CardService {
    Card create(Customer cardHolder, boolean isCreditCard);
    Card update(boolean isVirgin);
    Card update(String pin);
    Card findBySerialNumber(String serialNumber);
    ArrayList<Card> findAllByAccount(Account account);
}