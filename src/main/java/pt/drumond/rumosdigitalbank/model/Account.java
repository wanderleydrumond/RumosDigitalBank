package pt.drumond.rumosdigitalbank.model;

import java.util.ArrayList;

/**
 * Entity class Account.
 */
public class Account {
    private String code;
    private double balance;
    private Customer mainHolder;
    private ArrayList<Customer> secondaryHolders;
    private ArrayList<Transaction> transactions;
    private ArrayList<DebitCard> debitCards;
    private ArrayList<CreditCard> creditCards;

    public Account() {
        secondaryHolders = new ArrayList<>();
        transactions = new ArrayList<>();
        debitCards = new ArrayList<>();
        creditCards = new ArrayList<>();
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

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction transaction) { // TODO move to TransactionListRepository and change the method name to save?
        transactions.add(transaction);
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