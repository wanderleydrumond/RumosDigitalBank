package pt.drumond.rumosdigitalbank.service.interfaces;

import pt.drumond.rumosdigitalbank.model.Account;
import pt.drumond.rumosdigitalbank.model.Customer;
import pt.drumond.rumosdigitalbank.model.MovementType;

public interface AccountService {
    Account create(Account account, Customer mainHolder);
    Account update(Account account);
    boolean validateInitialDeposit(double depositValue);
    Account findByCode(String code);
    void addSecondaryHolder(Account account, Customer secondaryHolder);
    void deposit(Account account, double value, MovementType deposit);
    boolean transfer(Account account, double value, String destinyAccountCode);
    void payLoan(Account account, double value, String creditCardSerialNumber);
    void deleteSecondaryHolder(Account account, Customer secondaryHolder);
    boolean addDebitCard(Account account, Customer cardHolder);
    void addCreditCard(Account account, Customer cardHolder);

    void loadDatabase();

    int getAmountOfSecondaryHolders(Account loggedAccount);
    int getAmountOfCreditCards(Account loggedAccount);
    int getAmountOfDebitCards(Account loggedAccount);

    void delete(Account loggedAccount);
}