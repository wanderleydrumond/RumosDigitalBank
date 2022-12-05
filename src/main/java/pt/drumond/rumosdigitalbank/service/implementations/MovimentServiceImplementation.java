package pt.drumond.rumosdigitalbank.service.implementations;

import pt.drumond.rumosdigitalbank.model.Account;
import pt.drumond.rumosdigitalbank.model.Movement;
import pt.drumond.rumosdigitalbank.enums.MovementType;
import pt.drumond.rumosdigitalbank.repository.implementations.MovimentListRepositoryImplementation;
import pt.drumond.rumosdigitalbank.repository.interfaces.MovementListRepository;
import pt.drumond.rumosdigitalbank.service.interfaces.MovementService;

import java.time.LocalDate;
import java.util.ArrayList;

public class MovimentServiceImplementation implements MovementService {
    private MovementListRepository movementListRepositoryImplementation = new MovimentListRepositoryImplementation();

    /**
     * Creates a new account movement.
     *
     * @param value        to be used on the created account movement
     * @param movementType the type of the created account movement
     * @return a new account movement
     */
    @Override
    public Movement create(double value, MovementType movementType) {
        Movement movement = new Movement();
        movement.setDate(LocalDate.now());
        movement.setValue(value);
        movement.setType(movementType);

        return movementListRepositoryImplementation.create(movement);
    }

    @Override
    public Movement deleteAll(Account account) {
        return null;
    }

    @Override
    public ArrayList<Movement> findAll() {
        return null;
    }

    @Override
    public ArrayList<Movement> loadDatabase() {
        return movementListRepositoryImplementation.loadDatabase();
    }
}