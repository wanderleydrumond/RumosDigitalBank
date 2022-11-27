package pt.drumond.rumosdigitalbank.service;

import pt.drumond.rumosdigitalbank.model.Movement;
import pt.drumond.rumosdigitalbank.model.MovementType;

import java.time.LocalDate;

/**
 * Contains all methods responsible for the businees rules related to transactions.
 */
public class MovementService {
    /**
     * Creates a new transaction.
     *
     * @param value           to be used on the created transaction
     * @param movementType the type of the created transaction
     * @return a new transaction
     */
    public Movement createTransaction(double value, MovementType movementType) {
        Movement movement = new Movement();
        movement.setDate(LocalDate.now());
        movement.setValue(value);
        movement.setType(movementType);

        return movement;
    }
}