package pt.drumond.rumosdigitalbank.service.implementations.list;

import pt.drumond.rumosdigitalbank.enums.MovementType;
import pt.drumond.rumosdigitalbank.model.Account;
import pt.drumond.rumosdigitalbank.model.Movement;
import pt.drumond.rumosdigitalbank.repository.interfaces.MovementListRepository;
import pt.drumond.rumosdigitalbank.service.interfaces.MovementService;

import java.time.LocalDate;
import java.util.List;

public class MovementListServiceImplementation implements MovementService {
    private MovementListRepository movementListRepositoryImplementation;

    public MovementListServiceImplementation(MovementListRepository movementListRepositoryImplementation) {
        this.movementListRepositoryImplementation = movementListRepositoryImplementation;
    }

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
    public void create(double value, MovementType movementType, Account account) {

    }

    @Override
    public Movement deleteAll(Account account) {
        return null;
    }

    @Override
    public List<Movement> getAll() {
        return movementListRepositoryImplementation.findAll();
    }

    @Override
    public List<Movement> loadDatabase() {
        return movementListRepositoryImplementation.loadDatabase();
    }
}