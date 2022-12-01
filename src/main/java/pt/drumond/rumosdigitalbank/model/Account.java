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
    private ArrayList<Card> cards = new ArrayList<>();

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

    public void setSecondaryHolders(ArrayList<Customer> secondaryHolders) {
        this.secondaryHolders = secondaryHolders;
    }

    public ArrayList<Movement> getMovements() {
        return movements;
    }

    public void setMovements(ArrayList<Movement> movements) {
        this.movements = movements;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    @Override
    public String toString() {
        return "| CODE:" + code +
                " | BALANCE: " + balance +
                " | MAIN HOLDER: " + mainHolder +
                " |";
    }
}