package com.example.PlanetResistance.tower;

import com.example.PlanetResistance.enemy.Enemy;

public class AssassinTower extends Tower {
    private int poisonDmg;
    private int poisonDuration;

    public AssassinTower(int price) {
        super("AssassinTower", 4, 20, 3, price);

        poisonDuration = 5;
        poisonDmg = 3;

        setStatusEffectDuration(poisonDuration);
        setStatusEffect(new StatusEffect() {
            @Override
            public void affect(Enemy enemy) {
                enemy.setHealth(enemy.getHealth() - poisonDmg);
            }

            @Override
            public void remove(Enemy enemy, Object defaults) {
            };
        });
    }

    public int getPoisonDmg() {
        return poisonDmg;
    }

    public void setPoisonDmg(int poisonDmg) {
        this.poisonDmg = poisonDmg;
    }

    public int getPoisonDuration() {
        return poisonDuration;
    }

    public void setPoisonDuration(int poisonDuration) {
        this.poisonDuration = poisonDuration;
    }

    @Override
    public int getStatusEffectStat() {
        return poisonDmg;
    }

    @Override
    public void setStatusEffectStat(int stat) {
        poisonDmg = stat;
    }
}
