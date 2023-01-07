package pt.drumond.rumosdigitalbank.repository.implementations.jdbc;

import pt.drumond.rumosdigitalbank.enums.MovementType;
import pt.drumond.rumosdigitalbank.model.Account;
import pt.drumond.rumosdigitalbank.model.Card;
import pt.drumond.rumosdigitalbank.model.Customer;
import pt.drumond.rumosdigitalbank.model.Movement;
import pt.drumond.rumosdigitalbank.repository.interfaces.AccountRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountJDBCRepositoryImplemention extends JDBCRepository implements AccountRepository {
    @Override
    public Account create(Account account) {
        try {
            openConnection();

            preparedStatement = connection.prepareStatement("INSERT INTO accounts VALUES (null, ?, ?, ?)");
            preparedStatement.setString(1, account.getCode());
            preparedStatement.setDouble(2, account.getBalance());
        } catch (ClassNotFoundException classNotFoundException) {
            System.err.println("Error opening database connection" + classNotFoundException.getMessage());
        } catch (SQLException sqlException) {
            System.err.println("Error on AccountJDBCRepositoryImplemention.create() " + sqlException.getMessage());
        }
        return account;
    }

    @Override
    public Account findByCode(String code) {
        return null;
    }

    @Override
    public Account update(Account account) {
        return null;
    }

    @Override
    public void delete(Account account) {

    }

    @Override
    public List<Account> findAll() {
        return null;
    }

    @Override
    public List<Card> findAllDebitCardsByAccount(Account account) {
        return null;
    }

    @Override
    public List<Card> findAllCreditCardsByAccount(Account account) {
        return null;
    }

    @Override
    public List<Movement> findAllSpecificMovements(MovementType movementType, Account accountToBeDebited) {
        return null;
    }

    @Override
    public Account findByCardSerialNumber(String cardSerialNumber) {
        return null;
    }

    @Override
    public void loadDatabase(ArrayList<Customer> customers, ArrayList<Card> cards, ArrayList<Movement> movements) {

    }
}