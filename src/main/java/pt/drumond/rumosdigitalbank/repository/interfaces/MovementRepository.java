package pt.drumond.rumosdigitalbank.repository.interfaces;

import pt.drumond.rumosdigitalbank.model.Movement;

import java.util.List;

public interface MovementRepository {
    Movement create(Movement movement);
    List<Movement> findAll();
    List<Movement> findAll(int accountId);
    List<Movement> loadDatabase();
    void deleteAll(int accountToBeDeletedId);
    double sumAllTodayWithdrawMovements(int accountIdThatOwnsThisMovement);
}