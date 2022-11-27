package pt.drumond.rumosdigitalbank.model;

import java.util.ArrayList;

/**
 * Entity class Account.
 */
public class Account {
    private String code;
    private double balance;
    private Customer mainHolder;
    private ArrayList<Customer> secondaryHolders = new ArrayList<>();
    private ArrayList<Movement> movements = new ArrayList<>();
    private ArrayList<DebitCard> debitCards = new ArrayList<>();
    private ArrayList<CreditCard> creditCards = new ArrayList<>();

    public Account(double balance, Customer mainHolder) {
        this.balance = balance;
        this.mainHolder = mainHolder;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Customer getMainHolder() {
        return mainHolder;
    }

    public void setMainHolder(Customer mainHolder) {
        this.mainHolder = mainHolder;
    }

    public ArrayList<Customer> getSecondaryHolders() {
        return secondaryHolders;
    }

    public void addSecondaryHolder(Customer secondaryHolder) {
        secondaryHolders.add(secondaryHolder);
    }

    public ArrayList<Movement> getTransactions() {
        return movements;
    }

    public void addTransaction(Movement movement) { // TODO move to TransactionListRepository and change the method name to save?
        movements.add(movement);
    }

    public ArrayList<DebitCard> getDebitCards() {
        return debitCards;
    }

    public void addtDebitCard(DebitCard debitCard) { // TODO move to DebitCardListRepository and change the method name to save?
        debitCards.add(debitCard);
    }

    public ArrayList<CreditCard> getCreditCards() {
        return creditCards;
    }

    public void addCreditCard(CreditCard creditCard) { // TODO move to CreditCardListRepository and change the method name to save?
        creditCards.add(creditCard);
    }

    @Override
    public String toString() {
        return "|CODE:" + code +
                " | BALANCE: " + balance +
                " | MAIN HOLDER: " + mainHolder +
                " |";
    }
}