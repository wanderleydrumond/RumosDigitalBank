package pt.drumond.rumosdigitalbank.repository.implementations;

import pt.drumond.rumosdigitalbank.enums.MovementType;
import pt.drumond.rumosdigitalbank.model.*;
import pt.drumond.rumosdigitalbank.repository.interfaces.AccountRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class AccountListRepositoryImplementation implements AccountRepository {

    private ArrayList<Account> tableAccounts = new ArrayList<>();

    public AccountListRepositoryImplementation() {
    }

    /**
     * Adds the given <code>Account</code> instance to the accounts list
     *
     * @param account instance to be added
     */
    @Override
    public Account create(Account account) {
        account.setCode(String.valueOf(100 + tableAccounts.size()));
        tableAccounts.add(account);
        System.out.println("Lista de contas do banco"); //TODO to be deleted
        tableAccounts.forEach(System.out::println);//TODO to be deleted

        return account;
    }

    @Override
    public ArrayList<Account> findAll() {
        return tableAccounts;
    }

    @Override
    public ArrayList<Card> findAllDebitCardsByAccount(Account account) {
        ArrayList<Card> debitCards = new ArrayList<>();
        account.getCards().forEach(cardElement -> {
            if (cardElement.getMonthyPlafond() == 0.) {
                debitCards.add(cardElement);
            }
        });
        return debitCards;
    }

    @Override
    public ArrayList<Card> findAllCreditCardsByAccount(Account account) {
        ArrayList<Card> creditCards = new ArrayList<>();
        account.getCards().forEach(cardElement -> {
            if (cardElement.getMonthyPlafond() > 0.) {
                creditCards.add(cardElement);
            }
        });
        return creditCards;
    }

    /**
     * Finds a specific account given a code.
     *
     * @param code account identifier
     * @return account that have that identifier
     */
    @Override
    public Account findByCode(String code) {

        return tableAccounts.stream().filter(accountElement -> accountElement.getCode().equals(code)).findFirst().orElse(null);
    }

    @Override
    public Account update(Account account) {
        tableAccounts.set(tableAccounts.indexOf(account), account);
        System.out.println("Lista do banco após update da conta"); //TODO to be deleted
        tableAccounts.forEach(System.out::println); //TODO to be deleted

        return account;
    }

    @Override
    public void delete(Account account) {
        //TODO implement method
    }


    @Override
    public ArrayList<Movement> findAllSpecificMovements(MovementType movementType, Account accountToBeDebited) {
        /*ArrayList<Movement> specificMovements = new ArrayList<>();
        accountToBeDebited.getMovements().forEach(movementElement -> {
            if (movementElement.getType().equals(movementType)) {
                specificMovements.add(movementElement);
            }
        });*/

        return accountToBeDebited.getMovements().stream().filter(movementElement -> movementElement.getType().equals(movementType)).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Generates initial data to fill the account HashSet that's serves as database.
     */
    @Override
    public void loadDatabase() {
        Account account1 = new Account(60., new Customer("987456321", "Jane Doe", "123456", "321654987", "99885544", "someone@email.com", "Lawyer", LocalDate.of(1983, 2, 24)));
        Account account2 = new Account(50., new Customer("123456789", "John Doe", "654321", "321644481", "99221166", "anything@email.com", "Pilot", LocalDate.of(1973, 12, 12)));
        Account account3 = new Account(75., new Customer("132456789", "Rosalvo Doe", "123654", "325554937", "99887766", "something@email.com", "Firefighter", LocalDate.of(1985, 8, 2)));
        Account account4 = new Account(40., new Customer("369258147", "João das Couves", "526341", "222111333", "951951951", "cabbages@email.pt", "seller", LocalDate.of(1972, 1, 22)));
        Account account5 = new Account(55., new Customer("148754243", "Aang", "540022", "250140320", "981258457", "air@avatar.com", "avatar", LocalDate.of(2005, 1, 22)));
        Account account6 = new Account(65., new Customer("144774144", "Korra", "541166", "335478852", "999258741", "water@avatar.com", "avatar", LocalDate.of(2011, 1, 22)));

        account1.setCode(String.valueOf(100 + tableAccounts.size()));
        tableAccounts.add(account1);
        account2.setCode(String.valueOf(100 + tableAccounts.size()));
        tableAccounts.add(account2);
        account3.setCode(String.valueOf(100 + tableAccounts.size()));
        tableAccounts.add(account3);
        account4.setCode(String.valueOf(100 + tableAccounts.size()));
        tableAccounts.add(account4);
        account5.setCode(String.valueOf(100 + tableAccounts.size()));
        tableAccounts.add(account5);
        account6.setCode(String.valueOf(100 + tableAccounts.size()));
        tableAccounts.add(account6);
    }
}