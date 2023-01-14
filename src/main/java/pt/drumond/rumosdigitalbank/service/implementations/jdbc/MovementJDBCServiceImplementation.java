package pt.drumond.rumosdigitalbank.service.implementations.jdbc;

import pt.drumond.rumosdigitalbank.enums.MovementType;
import pt.drumond.rumosdigitalbank.model.Account;
import pt.drumond.rumosdigitalbank.model.Movement;
import pt.drumond.rumosdigitalbank.repository.interfaces.MovementRepository;
import pt.drumond.rumosdigitalbank.service.interfaces.MovementService;

import java.util.List;

public class MovementJDBCServiceImplementation implements MovementService {
    private MovementRepository movementJDBCRepositoryImplementation;

    public MovementJDBCServiceImplementation(MovementRepository movementJDBCRepositoryImplementation) {
        this.movementJDBCRepositoryImplementation = movementJDBCRepositoryImplementation;
    }

    @Override
    public double getSumAllTodayWithdrawMovements(int accountIdThatOwnsThisMovement) {
        return movementJDBCRepositoryImplementation.sumAllTodayWithdrawMovements(accountIdThatOwnsThisMovement);
    }

    @Override
    public Movement deleteAll(Account account) {
        return null;
    }

    @Override
    public List<Movement> getAll() {
        return movementJDBCRepositoryImplementation.findAll();
    }

    @Override
    public List<Movement> loadDatabase() {
        return movementJDBCRepositoryImplementation.loadDatabase();
    }
    @Override
    public Movement create(double value, MovementType movementType, Account account) {
        Movement movement = new Movement();
        movement.setValue(value);
        movement.setType(movementType);
        movement.setAccount(account);
        return movementJDBCRepositoryImplementation.create(movement);
    }

    @Override
    public Movement create(double value, MovementType movementType) {
        // Used only on Lists
        return null;
    }
}
