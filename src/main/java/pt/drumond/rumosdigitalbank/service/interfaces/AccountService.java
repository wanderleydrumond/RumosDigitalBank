package pt.drumond.rumosdigitalbank.service.interfaces;

import pt.drumond.rumosdigitalbank.enums.ResponseType;
import pt.drumond.rumosdigitalbank.model.Account;
import pt.drumond.rumosdigitalbank.model.Card;
import pt.drumond.rumosdigitalbank.model.Customer;
import pt.drumond.rumosdigitalbank.enums.MovementType;
import pt.drumond.rumosdigitalbank.model.Movement;

import java.util.ArrayList;

public interface AccountService {
    Account create(Account account, Customer mainHolder);
    Account update(Account account);
    boolean validateInitialDeposit(double depositValue);
    Account findByCode(String code);
    boolean addSecondaryHolder(Account account, Customer secondaryHolder);
    void deposit(Account account, double value, MovementType deposit);
    boolean transfer(Account account, double value, String destinyAccountCode);
    ResponseType withdraw(double value, Account accountToBeDebited, MovementType movementType);
    boolean deleteSecondaryHolder(Account account, Customer secondaryHolder);
    Card addDebitCard(Account account, Customer cardHolder);
    Card addCreditCard(Account account, Customer cardHolder);

    ResponseType delete(Account loggedAccount);

    int getAmountOfSecondaryHolders(Account loggedAccount);
    int getAmountOfCreditCards(Account loggedAccount);
    int getAmountOfDebitCards(Account loggedAccount);
    ArrayList<Card> getDebitCards(Account loggedAccount);
    ArrayList<Card> getCreditCards(Account loggedAccount);
    Customer findCustomerByNif(String nif, Account loggedAccount);
    Boolean isMainHolder(Customer customerToBeDeleted, Account loggedAccount);
    Card getCardBySerialNumberOnCurrentAccount(Account loggedAccount, String cardSerialNumber);
    void loadDatabase(ArrayList<Customer> customers, ArrayList<Card> cards, ArrayList<Movement> movements);
}
