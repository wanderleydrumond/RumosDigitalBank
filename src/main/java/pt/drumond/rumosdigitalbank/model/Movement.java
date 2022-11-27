package pt.drumond.rumosdigitalbank.model;

import java.time.LocalDate;

public class Movement {
    private MovementType type;
    private LocalDate date;
    private double value;

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
}