package pt.drumond.rumosdigitalbank.model;

import java.io.Serializable;

public class CustomerAccount implements Serializable {
    private Customer customer;
    private Account account;

    public CustomerAccount() {
    }

    public CustomerAccount(Customer customer, Account account) {
        this.customer = customer;
        this.account = account;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}