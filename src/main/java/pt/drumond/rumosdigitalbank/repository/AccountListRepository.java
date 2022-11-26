package pt.drumond.rumosdigitalbank.repository;

import pt.drumond.rumosdigitalbank.model.Account;
import pt.drumond.rumosdigitalbank.model.CreditCard;
import pt.drumond.rumosdigitalbank.model.Customer;
import pt.drumond.rumosdigitalbank.model.DebitCard;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class AccountListRepository {
    private ArrayList<DebitCard> debitCards;
    private ArrayList<CreditCard> creditCards;

    public AccountListRepository() {

        debitCards = new ArrayList<>();
        creditCards = new ArrayList<>();
    }

    /**
     * Adds the given <code>Account</code> instance to the accounts list
     *
     * @param account instance to be added
     */
    public Account save(Account account, HashSet<Account> accountGeneralList) {
        accountGeneralList.add(account);

        return account;
    }

    public HashSet<Account> findAllAccounts(HashSet<Account> accountGeneralList) {
        return accountGeneralList;
    }

    public ArrayList<DebitCard> findAllDebitCards() {
        return debitCards;
    }

    public ArrayList<CreditCard> findAllCreditCards() {
        return creditCards;
    }

    /**
     * Finds a specific account given a code.
     *
     * @param code account identifier
     * @return account that have that identifier
     */
    public Account findAccountByCode(String code, HashSet<Account> accountGeneralList) {
        for (Account accountElement : accountGeneralList) {
            if (accountElement.getCode().equals(code)) {
                return accountElement;
            }
        }
        return null;
    }

    /**
     * Generates initial data to fill the account HashSet that's serves as database.
     */
    public void loadDatabase(HashSet<Account> accountsGeneralList) {
        Account account = new Account("101", 60., new Customer("987456321", "Jane Doe", "123456", "321654987", "99885544", "someone@email.com", "Lawyer", LocalDate.of(1983, 2, 24)), );
        Customer customer1 = new Customer("987456321", "Jane Doe", "123456", "321654987", "99885544", "someone@email.com", "Lawyer", LocalDate.of(1983, 2, 24));
        Customer customer2 = new Customer("123456789", "John Doe", "654321", "321644481", "99221166", "anything@email.com", "Pilot", LocalDate.of(1973, 12, 12));
        Customer customer3 = new Customer("132456789", "Rosalvo Doe", "123654", "325554937", "99887766", "something@email.com", "Firefighter", LocalDate.of(1985, 8, 2));
        Customer customer4 = new Customer("369258147", "João das Couves", "526341", "222111333", "951951951", "cabbages@email.pt", "seller", LocalDate.of(1972, 1, 22));
        Customer customer5 = new Customer("144774144", "Aang", "540022", "250140320", "981258457", "air@avatar.com", "avatar", LocalDate.of(2005, 1, 22));
        Customer customer6 = new Customer("144774144", "Korra", "541166", "335478852", "999258741", "water@avatar.com", "avatar", LocalDate.of(2011, 1, 22));

        accountsGeneralList.addAll(Arrays.asList(customer1, customer2, customer3, customer4, customer5, customer6));
    }
}