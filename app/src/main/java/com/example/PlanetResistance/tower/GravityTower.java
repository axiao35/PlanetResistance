package com.example.PlanetResistance.tower;

import com.example.PlanetResistance.enemy.Enemy;

public class GravityTower extends Tower {
    private int slowRate;
    private int slowDuration;

    public GravityTower(int price) {
        super("GravityTower", 2, 15, 2, price);
        slowRate = 50;
        slowDuration = 20;

        setStatusEffectDuration(slowDuration);
        setStatusEffect(new StatusEffect() {
            @Override
            public void affect(Enemy enemy) {
                enemy.setSpeed((int) (enemy.getSpeed() * slowRate / 100));
            }

            @Override
            public void remove(Enemy enemy, Object defaults) {
                // wont work when other defaults are needed other than speed
                enemy.setSpeed((double) defaults);
            }
        });
    }

    public int getSlowDuration() {
        return slowDuration;
    }

    public void setSlowDuration(int slowDuration) {
        this.slowDuration = slowDuration;
    }

    public int getSlowRate() {
        return slowRate;
    }

    public void setSlowRate(int slowRate) {
        this.slowRate = slowRate;
    }

    @Override
    public int getStatusEffectStat() {
        return slowDuration;
    }

    @Override
    public void setStatusEffectStat(int stat) {
        slowDuration = stat;
    }
}
