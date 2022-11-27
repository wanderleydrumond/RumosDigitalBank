package pt.drumond.rumosdigitalbank.repository;

import pt.drumond.rumosdigitalbank.model.Account;
import pt.drumond.rumosdigitalbank.model.CreditCard;
import pt.drumond.rumosdigitalbank.model.Customer;
import pt.drumond.rumosdigitalbank.model.DebitCard;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class AccountListRepositoryImplementation implements AccountRepository{

    private ArrayList<Account> accountsBankList = new ArrayList<>();

    public AccountListRepositoryImplementation() {
    }

    /**
     * Adds the given <code>Account</code> instance to the accounts list
     *
     * @param account instance to be added
     */
    @Override
    public Account save(Account account) {
        account.setCode(String.valueOf(100 + accountsBankList.size()));
        accountsBankList.add(account);
        System.out.println("Lista de consta do banco"); //TODO to be deleted
        accountsBankList.forEach(System.out::println);

        return account;
    }

    @Override
    public ArrayList<Account> findAll() {
        return accountsBankList;
    }

    /**
     * Finds a specific account given a code.
     *
     * @param code account identifier
     * @return account that have that identifier
     */
    @Override
    public Account findByCode(String code) {

        return accountsBankList.stream().filter(accountElement -> accountElement.getCode().equals(code)).findFirst().orElse(null);
    }

    @Override
    public Account update(Account account) {
        accountsBankList.set(accountsBankList.indexOf(account), account);
        System.out.println("Lista do banco após update da conta"); //TODO to be deletd
        accountsBankList.forEach(System.out::println); //TODO to be deletd

        return account;
    }

    @Override
    public void delete(Account account) {

    }

    /**
     * Generates initial data to fill the account HashSet that's serves as database.
     */
    @Override
    public void loadDatabase() {
        Account account = new Account(60., new Customer("987456321", "Jane Doe", "123456", "321654987", "99885544", "someone@email.com", "Lawyer", LocalDate.of(1983, 2, 24)));
        account.setCode(String.valueOf(100 + accountsBankList.size()));
        accountsBankList.add(account);
    }
}