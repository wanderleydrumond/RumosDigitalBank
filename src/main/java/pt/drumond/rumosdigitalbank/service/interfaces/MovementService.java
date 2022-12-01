package pt.drumond.rumosdigitalbank.service.interfaces;

import pt.drumond.rumosdigitalbank.model.Account;
import pt.drumond.rumosdigitalbank.model.Movement;
import pt.drumond.rumosdigitalbank.model.MovementType;

import java.util.ArrayList;

public interface MovementService {
    Movement create(double value, MovementType movementType);
    Movement deleteAll(Account account);
    ArrayList<Movement> findAll();
}