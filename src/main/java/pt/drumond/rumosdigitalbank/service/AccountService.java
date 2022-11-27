package pt.drumond.rumosdigitalbank.service;

import pt.drumond.rumosdigitalbank.model.Account;
import pt.drumond.rumosdigitalbank.model.Customer;

public interface AccountService {
    Account create(Account account);
    Account update(Account account);
    boolean validateInitialDeposit(double depositValue);
    Account findByCode(String code);
    void addSecondaryHolder(Account account, Customer secondaryHolder);
    void deposit(Account account, double value);
    void transfer(Account account, double value, String destinyAccountCode);
    void payLoan(Account account, double value, String creditCardSerialNumber);
    void deleteSecondaryHolder(Account account, Customer secondaryHolder);
    void addDebitCard(Account account, Customer cardHolder);
    void addCreditCard(Account account, Customer cardHolder);

    void loadDatabase();
}