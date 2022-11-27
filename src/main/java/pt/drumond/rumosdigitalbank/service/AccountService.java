package pt.drumond.rumosdigitalbank.service;

import pt.drumond.rumosdigitalbank.model.Account;

public interface AccountService {
    Account create(Account account);
    Account update(Account account);
    boolean validateInitialDeposit(double depositValue);
}