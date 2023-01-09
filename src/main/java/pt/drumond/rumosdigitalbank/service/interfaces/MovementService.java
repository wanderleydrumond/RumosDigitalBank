package pt.drumond.rumosdigitalbank.service.interfaces;

import pt.drumond.rumosdigitalbank.enums.MovementType;
import pt.drumond.rumosdigitalbank.model.Account;
import pt.drumond.rumosdigitalbank.model.Movement;

import java.util.List;

public interface MovementService {
    Movement create(double value, MovementType movementType);
    void create(double value, MovementType movementType, Account account);
    Movement deleteAll(Account account);
    List<Movement> getAll();
    List<Movement> loadDatabase();
}