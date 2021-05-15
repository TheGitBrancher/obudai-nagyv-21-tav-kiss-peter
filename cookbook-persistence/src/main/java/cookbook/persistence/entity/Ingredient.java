package cookbook.persistence.entity;

import cookbook.domain.Unit;

import javax.persistence.*;

@Embeddable
public class Ingredient {

    private double amount;
    private String name;

    @Enumerated(EnumType.STRING)
    private Unit unit;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

}
