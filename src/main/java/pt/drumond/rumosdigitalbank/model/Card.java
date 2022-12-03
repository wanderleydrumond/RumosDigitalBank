package pt.drumond.rumosdigitalbank.model;

public class Card {
    private static int id;
    private String serialNumber, pin;
    private boolean isVirgin;
    private Customer cardHolder;
    private double monthyPlafond, plafondBalance;

    public Card() {
    }

    public Card(boolean isVirgin, Customer cardHolder, double monthyPlafond, double plafondBalance) {
        this.isVirgin = isVirgin;
        this.cardHolder = cardHolder;
        this.monthyPlafond = monthyPlafond;
        this.plafondBalance = plafondBalance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        Card.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public boolean isVirgin() {
        return isVirgin;
    }

    public void setVirgin(boolean virgin) {
        isVirgin = virgin;
    }

    public Customer getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(Customer cardHolder) {
        this.cardHolder = cardHolder;
    }

    public double getMonthyPlafond() {
        return monthyPlafond;
    }

    public void setMonthyPlafond(double monthyPlafond) {
        this.monthyPlafond = monthyPlafond;
    }

    public double getPlafondBalance() {
        return plafondBalance;
    }

    public void setPlafondBalance(double plafondBalance) {
        this.plafondBalance = plafondBalance;
    }

    @Override
    public String toString() {
        return "| " + serialNumber +
                " | PIN: " + pin +
                " | IS VIRGIN? " + isVirgin +
                " | CARD HOLDER: " + cardHolder +
                " |";
    }
}