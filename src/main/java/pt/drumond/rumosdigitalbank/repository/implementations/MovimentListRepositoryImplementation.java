package pt.drumond.rumosdigitalbank.repository.implementations;

import pt.drumond.rumosdigitalbank.enums.MovementType;
import pt.drumond.rumosdigitalbank.model.Movement;
import pt.drumond.rumosdigitalbank.repository.interfaces.MovementListRepository;

import java.time.LocalDate;
import java.util.ArrayList;

public class MovimentListRepositoryImplementation implements MovementListRepository {

    private ArrayList<Movement> tableMovements = new ArrayList<>();
    private static int id = 1;

    @Override
    public Movement create(Movement movement) {
        movement.setId(++id);
        tableMovements.add(movement);

        return movement;
    }

    @Override
    public ArrayList<Movement> findAll() {
        return tableMovements;
    }

    @Override
    public ArrayList<Movement> loadDatabase() {
        // Conta 102
        Movement movement0 = new Movement(MovementType.WITHDRAW, LocalDate.of(2022, 12, 1), 50.);
        create(movement0);
        Movement movement1 = new Movement(MovementType.TRANSFER_IN, LocalDate.of(2022, 12, 1), 30.);
        create(movement1);
        Movement movement2 = new Movement(MovementType.TRANSFER_OUT, LocalDate.of(2022, 12, 1), 80.);
        create(movement2);

        // Conta 101
        Movement movement3 = new Movement(MovementType.DEPOSIT, LocalDate.now(), 10.);
        create(movement3);
        Movement movement4 = new Movement(MovementType.WITHDRAW, LocalDate.now(), 200.);
        create(movement4);
        Movement movement5 = new Movement(MovementType.WITHDRAW, LocalDate.now(), 300.);
        create(movement5);

        // Conta 100
        Movement movement6 = new Movement(MovementType.WITHDRAW, LocalDate.now(), 100.);
        create(movement6);
        Movement movement7 = new Movement(MovementType.DEPOSIT, LocalDate.now(), 10.);
        create(movement7);
        Movement movement8 = new Movement(MovementType.TRANSFER_IN, LocalDate.now(), 15.);
        create(movement8);
        Movement movement9 = new Movement(MovementType.TRANSFER_OUT, LocalDate.now(), 50.);
        create(movement9);

        return tableMovements;
    }
}