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
            preparedStatement = connection.prepareStatement("INSERT INTO accounts (balance, customers_id) VALUES (?, ?)"); // supostamente funcionaria para tudo
            preparedStatement.setDouble(1, account.getBalance());
            preparedStatement.setInt(2, account.getMainHolder().getId());

            preparedStatement.execute();
            preparedStatement.clearParameters();

            preparedStatement = connection.prepareStatement("SELECT MAX(id) FROM accounts");
            resultSet = preparedStatement.executeQuery();

            int lastId = 0;
            while (resultSet.next()) {
                lastId = resultSet.getInt(1);
            }
            preparedStatement.clearParameters();

            int code = lastId + 100;
//            preparedStatement = connection.prepareStatement("SELECT code FROM accounts WHERE id = " + lastId + ";"); // Isso era para usar o trigger
            preparedStatement = connection.prepareStatement("UPDATE accounts SET code = ? WHERE id = " + lastId + ";");
            preparedStatement.setInt(1, code);
            preparedStatement.executeUpdate();

            return findByCode(String.valueOf(code));
        } catch (ClassNotFoundException classNotFoundException) {
            System.err.println("Error opening database connection" + classNotFoundException.getMessage());
        } catch (SQLException sqlException) {
            System.err.println("Error on AccountJDBCRepositoryImplemention.create() " + sqlException.getMessage());
        }
        return null;
    }

    @Override
    public Account findByCode(String code) {
        Account accountToBeFound = null;
        int idCustomer;
        try {
            openConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM accounts WHERE code = " + code + ";");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                accountToBeFound = new Account(
                        resultSet.getInt("id"),
                        resultSet.getString("code"),
                        resultSet.getDouble("balance"));
                idCustomer = resultSet.getInt("customers_id");
            }
        } catch (SQLException sqlException) {
            System.err.println("Error on AccountJDBCRepositoryImplementation.findByCode() " + sqlException.getMessage());
            return null;
        } catch (ClassNotFoundException classNotFoundException) {
            System.err.println("Error opening database connection " + classNotFoundException.getMessage());
            return null;
        } finally {
            try {
                closeConnection();
            } catch (SQLException sqlException) {
                System.err.println("Error closing database connection " + sqlException.getMessage());
            }
        }
        return accountToBeFound;
    }

    @Override
    public Account update(Account account) {
        try {
            openConnection();

            preparedStatement = connection.prepareStatement("UPDATE accounts SET balance = ? WHERE code = " + account.getCode() + ";");
            preparedStatement.setDouble(1, account.getBalance());

            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            System.err.println("Error on CustomerJDBCRepositoryImplementation.create() " + sqlException.getMessage());
            return null;
        } catch (ClassNotFoundException classNotFoundException) {
            System.err.println("Error opening database connection" + classNotFoundException.getMessage());
        } finally {
            try {
                closeConnection();
            } catch (SQLException sqlException) {
                System.err.println("Error closing database connection " + sqlException.getMessage());
            }
        }
        return findByCode(account.getCode());
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