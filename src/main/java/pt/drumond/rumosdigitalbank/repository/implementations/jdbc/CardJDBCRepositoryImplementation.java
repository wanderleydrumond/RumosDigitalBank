package pt.drumond.rumosdigitalbank.repository.implementations.jdbc;

import pt.drumond.rumosdigitalbank.model.Account;
import pt.drumond.rumosdigitalbank.model.Card;
import pt.drumond.rumosdigitalbank.model.Customer;
import pt.drumond.rumosdigitalbank.repository.interfaces.CardRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardJDBCRepositoryImplementation extends JDBCRepository implements CardRepository {

    @Override
    public Card create(Card card, boolean isCreditCard) {
//        debitCard.setPin(String.valueOf((int)(Math.random() * 4)));
        try {
            openConnection();

            double valueToSearch = isCreditCard ? 100. : 0.;
            preparedStatement = connection.prepareStatement("SELECT EXISTS(SELECT * FROM cards WHERE accounts_id = " + card.getAccount().getId() + " AND customers_id = " + card.getCardHolder().getId() + " AND monthly_plafond = " + valueToSearch + ");"); // verfica se este usuário já tem um cartão de crédito
            resultSet = preparedStatement.executeQuery();
            preparedStatement.clearParameters();

            boolean cardExists = false;
            while (resultSet.next()) {
                cardExists = resultSet.getBoolean(1);
            }

            if (cardExists) {
                return null;
            }
            preparedStatement = connection.prepareStatement("INSERT INTO cards(pin, is_virgin, monthly_plafond, plafond_balance, customers_id, accounts_id) VALUES (?, ?, ?, ?, ?, ?);");

            preparedStatement.setString(1, card.getPin());
            preparedStatement.setBoolean(2, card.isVirgin());
            preparedStatement.setDouble(3, card.getMonthyPlafond());
            preparedStatement.setDouble(4, card.getPlafondBalance());
            preparedStatement.setInt(5, card.getCardHolder().getId());
            preparedStatement.setInt(6, card.getAccount().getId());

            preparedStatement.execute();
            preparedStatement.clearParameters();

            preparedStatement = connection.prepareStatement("SELECT MAX(id) FROM cards");
            resultSet = preparedStatement.executeQuery();

            int lastId = 0;
            while (resultSet.next()) {
                lastId = resultSet.getInt(1);
            }
            preparedStatement.clearParameters();

            int serialNumber = lastId + 800;

            preparedStatement = connection.prepareStatement("UPDATE cards SET serial_number = ? WHERE id = " + lastId + ";");
            preparedStatement.setInt(1, serialNumber);
            preparedStatement.executeUpdate();

            return findBySerialNumber(String.valueOf(serialNumber));
        } catch (SQLException sqlException) {
            System.err.println("Error on CardJDBCRepositoryImplementation.create() " + sqlException.getMessage());
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
        return null;
    }

    @Override
    public Card create(Card card) {
        return null;
    }

    @Override
    public Card update(Card card) {
        return null;
    }

    @Override
    public Card findBySerialNumber(String serialNumber) {
        Card cardToBeFound = null;
        try {
            openConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM cards WHERE serial_number = " + serialNumber + ";");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                cardToBeFound = new Card(
                        resultSet.getInt("id"),
                        resultSet.getString("serial_number"),
                        resultSet.getString("pin"),
                        resultSet.getBoolean("is_virgin"),
                        resultSet.getDouble("monthly_plafond"),
                        resultSet.getDouble("plafond_balance")
                );
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
        return cardToBeFound;
    }

    @Override
    public List<Card> findAllByAccount(Account account) {
        return null;
    }

    @Override
    public void delete(Card cardOwnedByCustomerToBeDeleted) {
    }

    @Override
    public int countDebitCards(int loggedAccountId) {
        int amountOfDebitCards = 0;
        try {
            openConnection();

            preparedStatement = connection.prepareStatement("SELECT COUNT(id) FROM cards WHERE accounts_id = " + loggedAccountId + ";");
            resultSet = preparedStatement.executeQuery();
            preparedStatement.clearParameters();

            while (resultSet.next()) {
                amountOfDebitCards = resultSet.getInt(1);
            }
        } catch (SQLException sqlException) {
            System.err.println("Error on AccountJDBCRepositoryImplementation.findAmountOfSecondaryHolders() " + sqlException.getMessage());
            return 0;
        } catch (ClassNotFoundException classNotFoundException) {
            System.err.println("Error opening database connection " + classNotFoundException.getMessage());
            return 0;
        } finally {
            try {
                closeConnection();
            } catch (SQLException sqlException) {
                System.err.println("Error closing database connection " + sqlException.getMessage());
            }
        }
        return amountOfDebitCards;
    }

    @Override
    public List<Card> loadDatabase(ArrayList<Customer> tableCustomers) {
        // used only on Lists
        return null;
    }
}