package pt.drumond.rumosdigitalbank.repository.interfaces;

import pt.drumond.rumosdigitalbank.model.Account;
import pt.drumond.rumosdigitalbank.model.Card;

import java.util.ArrayList;

public interface CardRepository {
    Card create(Card card);
    Card update(Card card);
    Card findBySerialNumber(String serialNumber);
    ArrayList<Card> findAllByAccount(Account account);
}