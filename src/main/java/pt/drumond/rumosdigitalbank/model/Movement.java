package pt.drumond.rumosdigitalbank.model;

import pt.drumond.rumosdigitalbank.enums.MovementType;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Movement {
    private static int id = 1;
    private MovementType type;
    private LocalDate date;
    private double value;

    public Movement() {
    }

    public Movement(MovementType type, LocalDate date, double value) {
        this.type = type;
        this.date = date;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        Movement.id = id;
    }

    public MovementType getType() {
        return type;
    }

    public void setType(MovementType type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "| TYPE: " + type +
                " | DATE: " + date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                " | VALUE: " + new DecimalFormat("0.00").format(value) +
                "€ |";
    }
}