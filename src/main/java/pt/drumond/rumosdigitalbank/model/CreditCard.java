package pt.drumond.rumosdigitalbank.model;

public class CreditCard extends DebitCard {
    private double monthyPlafond, plafondBalance;

    public CreditCard() {
        super();
    }

    public CreditCard(String serialNumber, String pin, boolean isVirgin, Customer cardHolder, double monthyPlafond, double plafondBalance) {
        super(serialNumber, pin, isVirgin, cardHolder, monthyPlafond, plafondBalance);
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
}
