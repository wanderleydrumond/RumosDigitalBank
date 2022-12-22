package pt.drumond.rumosdigitalbank.repository.interfaces;

import pt.drumond.rumosdigitalbank.model.Movement;

import java.util.ArrayList;

public interface MovementListRepository {
    Movement create(Movement movement);
    ArrayList<Movement> findAll();
    ArrayList<Movement> loadDatabase();
}