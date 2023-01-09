package pt.drumond.rumosdigitalbank.repository.implementations.jdbc;

import pt.drumond.rumosdigitalbank.Main;
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
        int codeNewAccount = 0;
        try {
            openConnection();

            preparedStatement = connection.prepareStatement("SELECT COUNT(*) AS counter FROM accounts;" );
            resultSet = preparedStatement.executeQuery();
            int count = 0;
            while (resultSet.next()) {
                count = resultSet.getInt("counter");
            }
            codeNewAccount = count + 100;
            
            preparedStatement.clearParameters();
            preparedStatement = connection.prepareStatement("INSERT INTO accounts VALUES (null, ?, ?, ?)");
            preparedStatement.setInt(1, codeNewAccount);
            preparedStatement.setDouble(2, account.getBalance());
            preparedStatement.setInt(3, account.getMainHolder().getId());

            preparedStatement.execute();
        } catch (ClassNotFoundException classNotFoundException) {
            System.err.println("Error opening database connection" + classNotFoundException.getMessage());
        } catch (SQLException sqlException) {
            System.err.println("Error on AccountJDBCRepositoryImplemention.create() " + sqlException.getMessage());
        }
        return findByCode(String.valueOf(codeNewAccount));
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

            Main.getBank().getCustomerServiceImplementation();
        } catch (SQLException sqlException) {
            System.err.println("Error on CustomerJDBCRepositoryImplementation.create() " + sqlException.getMessage());
            return null;
        } catch (ClassNotFoundException classNotFoundException) {
            System.err.println("Error opening database connection " + classNotFoundException.getMessage());
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