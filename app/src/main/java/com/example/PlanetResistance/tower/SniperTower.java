package com.example.PlanetResistance.tower;

public class SniperTower extends Tower {
    private int multiplicity;

    public SniperTower(int price) {
        super("SniperTower", 10, 45, 10, price);
        multiplicity = 2;
    }

    public int getMultiplicity() {
        return multiplicity;
    }

    public void setMultiplicity(int multiplicity) {
        this.multiplicity = multiplicity;
    }
}
