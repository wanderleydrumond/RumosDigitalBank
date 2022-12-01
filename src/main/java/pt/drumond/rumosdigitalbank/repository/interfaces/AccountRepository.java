package pt.drumond.rumosdigitalbank.repository.interfaces;

import pt.drumond.rumosdigitalbank.enums.ResponseType;
import pt.drumond.rumosdigitalbank.model.Account;
import pt.drumond.rumosdigitalbank.model.Card;
import pt.drumond.rumosdigitalbank.model.Movement;
import pt.drumond.rumosdigitalbank.enums.MovementType;

import java.util.ArrayList;

public interface AccountRepository {
    Account create(Account account);
    Account findByCode(String code);
    Account update(Account account);
    void delete(Account account);
    ArrayList<Account> findAll();
    ArrayList<Card> findAllDebitCardsByAccount(Account account); // used only on ArrayLists?
    ArrayList<Card> findAllCreditCardsByAccount(Account account); // used only on ArrayLists?

    ArrayList<Movement> findAllSpecificMovements(MovementType movementType, Account accountToBeDebited);
    void loadDatabase();
}