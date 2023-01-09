package pt.drumond.rumosdigitalbank.service.interfaces;

import pt.drumond.rumosdigitalbank.enums.MovementType;
import pt.drumond.rumosdigitalbank.enums.ResponseType;
import pt.drumond.rumosdigitalbank.model.Account;
import pt.drumond.rumosdigitalbank.model.Card;
import pt.drumond.rumosdigitalbank.model.Customer;
import pt.drumond.rumosdigitalbank.model.Movement;

import java.util.List;

public interface AccountService {
    Account create(Account account);
    Account update(Account account);
    boolean validateInitialDeposit(double depositValue);
    Account getByCode(String code);
    boolean addSecondaryHolder(Account account, Customer secondaryHolder);
    boolean deposit(Account account, double value, MovementType deposit);
    ResponseType transfer(Account account, double value, String destinyAccountCode);
    ResponseType withdraw(double value, Account accountToBeDebited, MovementType movementType);
    boolean deleteSecondaryHolder(Account account, Customer secondaryHolder);
    Card addDebitCard(Account account, Customer cardHolder);
    Card addCreditCard(Account account, Customer cardHolder);

    ResponseType delete(Account loggedAccount);

    int getAmountOfSecondaryHolders(Account loggedAccount);
    int getAmountOfCreditCards(Account loggedAccount);
    int getAmountOfDebitCards(Account loggedAccount);
    List<Card> getDebitCards(Account loggedAccount);
    List<Card> getCreditCards(Account loggedAccount);
    Customer findCustomerByNif(String nif, Account loggedAccount);
    Boolean isMainHolder(Customer customerToBeDeleted, Account loggedAccount);
    Card getCardBySerialNumberOnCurrentAccount(Account loggedAccount, String cardSerialNumber);
    Account getAccountByCardSerialNumber(String cardSerialNumber);
    void loadDatabase(List<Customer> customers, List<Card> cards, List<Movement> movements);
}
