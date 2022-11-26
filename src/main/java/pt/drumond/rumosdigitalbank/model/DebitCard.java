package pt.drumond.rumosdigitalbank.model;

public class DebitCard {
    private String serialNumber, pin;
    private double valueWithdrawToday;
    private boolean isVirgin;
    private Customer client;
    private Account account;
}