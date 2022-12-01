package pt.drumond.rumosdigitalbank.repository.implementations;

import pt.drumond.rumosdigitalbank.model.Account;
import pt.drumond.rumosdigitalbank.model.Movement;
import pt.drumond.rumosdigitalbank.repository.interfaces.MovementListRepository;

import java.time.LocalDate;
import java.util.ArrayList;

public class MovimentListRepositoryImplementation implements MovementListRepository {

    private ArrayList<Movement> tableMovements = new ArrayList<>();

    @Override
    public Movement create(Movement movement) {
        tableMovements.add(movement);

        return movement;
    }

    @Override
    public Movement delete(Account account) {
        return null;
    }

    @Override
    public ArrayList<Movement> findAllBetweenTwoDates(LocalDate begin, LocalDate end) {
        return null;
    }
}