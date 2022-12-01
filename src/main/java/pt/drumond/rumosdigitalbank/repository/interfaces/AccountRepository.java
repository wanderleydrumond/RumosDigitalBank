package pt.drumond.rumosdigitalbank.repository.interfaces;

import pt.drumond.rumosdigitalbank.model.Account;
import pt.drumond.rumosdigitalbank.model.Card;

import java.util.ArrayList;

public interface AccountRepository {
    Account create(Account account);
    Account findByCode(String code);
    Account update(Account account);
    void delete(Account account);
    ArrayList<Account> findAll();
    ArrayList<Card> findAllDebitCardsByAccount(Account account); // used only on ArrayLists?
    ArrayList<Card> findAllCreditCardsByAccount(Account account); // used only on ArrayLists?
    void loadDatabase();
}