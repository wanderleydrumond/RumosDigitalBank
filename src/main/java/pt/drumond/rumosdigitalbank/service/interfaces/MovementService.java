package pt.drumond.rumosdigitalbank.service.interfaces;

import pt.drumond.rumosdigitalbank.model.Movement;
import pt.drumond.rumosdigitalbank.model.MovementType;

import java.time.LocalDate;

/**
 * Contains all methods responsible for the businees rules related to transactions.
 */
public class MovementService {
    /**
     * Creates a new account movement.
     *
     * @param value        to be used on the created account movement
     * @param movementType the type of the created account movement
     * @return a new account movement
     */
    public Movement create(double value, MovementType movementType) {
        Movement movement = new Movement();
        movement.setDate(LocalDate.now());
        movement.setValue(value);
        movement.setType(movementType);

        return movement;
    }
}