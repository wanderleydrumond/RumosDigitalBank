package pt.drumond.rumosdigitalbank.repository.interfaces;

import pt.drumond.rumosdigitalbank.model.Account;

import java.util.ArrayList;

public interface AccountRepository {
    Account save(Account account);
    Account findByCode(String code);
    Account update(Account account);
    void delete(Account account);
    ArrayList<Account> findAll();
    void loadDatabase();
}