package pt.drumond.rumosdigitalbank.service.implementations.list;

import pt.drumond.rumosdigitalbank.enums.MovementType;
import pt.drumond.rumosdigitalbank.model.Account;
import pt.drumond.rumosdigitalbank.model.Movement;
import pt.drumond.rumosdigitalbank.repository.interfaces.MovementRepository;
import pt.drumond.rumosdigitalbank.service.interfaces.MovementService;

import java.time.LocalDate;
import java.util.List;

public class MovementListServiceImplementation implements MovementService {
    private MovementRepository movementRepositoryImplementation;

    public MovementListServiceImplementation(MovementRepository movementRepositoryImplementation) {
        this.movementRepositoryImplementation = movementRepositoryImplementation;
    }

    @Override
    public Movement create(double value, MovementType movementType, Account account) {
        // Used only on JDBC
        return null;
    }

    @Override
    public Movement create(double value, MovementType movementType) {
        Movement movement = new Movement();
        movement.setDate(LocalDate.now());
        movement.setValue(value);
        movement.setType(movementType);

        return movementRepositoryImplementation.create(movement);
    }

    @Override
    public double getSumAllTodayWithdrawMovements(int accountIdThatOwnsThisMovement) {
        return 0;
    }

    @Override
    public Movement deleteAll(Account account) {
        return null;
    }

    @Override
    public List<Movement> getAll() {
        return movementRepositoryImplementation.findAll();
    }

    @Override
    public List<Movement> loadDatabase() {
        return movementRepositoryImplementation.loadDatabase();
    }
}