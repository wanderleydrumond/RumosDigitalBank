package pt.drumond.rumosdigitalbank.repository.interfaces;

import pt.drumond.rumosdigitalbank.model.Account;
import pt.drumond.rumosdigitalbank.model.Movement;
import pt.drumond.rumosdigitalbank.model.MovementType;

import java.time.LocalDate;
import java.util.ArrayList;

public interface MovementListRepository {
    Movement create(Movement movement);
    Movement delete(Account account);
    ArrayList<Movement> findAllBetweenTwoDates(LocalDate begin, LocalDate end);
}