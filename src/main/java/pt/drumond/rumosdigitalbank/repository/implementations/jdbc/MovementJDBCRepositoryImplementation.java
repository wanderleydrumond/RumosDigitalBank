package pt.drumond.rumosdigitalbank.repository.implementations.jdbc;

import pt.drumond.rumosdigitalbank.model.Movement;
import pt.drumond.rumosdigitalbank.repository.interfaces.MovementListRepository;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class MovementJDBCRepositoryImplementation extends JDBCRepository implements MovementListRepository {
    @Override
    public Movement create(Movement movement) {
        try {
            openConnection();

            preparedStatement = connection.prepareStatement("INSERT INTO movements VALUES (null, ?, ?, ?, ?)");
            preparedStatement.setString(1, movement.getType().toString());
            preparedStatement.setDate(2, Date.valueOf(LocalDate.now()));
            preparedStatement.setDouble(3, movement.getValue());
            preparedStatement.setInt(4, movement.getAccount().getId());

            preparedStatement.execute();
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

        return movement;
    }

    @Override
    public List<Movement> findAll() {
        return null;
    }

    @Override
    public List<Movement> loadDatabase() {
        return null;
    }
}