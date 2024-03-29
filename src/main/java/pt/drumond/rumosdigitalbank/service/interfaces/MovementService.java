package pt.drumond.rumosdigitalbank.service.interfaces;

import pt.drumond.rumosdigitalbank.enums.MovementType;
import pt.drumond.rumosdigitalbank.model.Account;
import pt.drumond.rumosdigitalbank.model.Movement;

import java.util.List;

public interface MovementService {
    Movement create(double value, MovementType movementType, Account account);
    Movement create(double value, MovementType movementType);
    double getSumAllTodayWithdrawMovements(int accountIdThatOwnsThisMovement);
    Movement deleteAll(Account account);
    void deleteAll(int accountToBeDeletedId);
    List<Movement> getAll();
    List<Movement> getAll(int accountId);
    List<Movement> loadDatabase();
}