package com.example.PlanetResistance.tower;

import com.example.PlanetResistance.enemy.Enemy;

public interface StatusEffect {
    void affect(Enemy enemy);

    // defaults will always be a hashmap once more defaults are needed
    void remove(Enemy enemy, Object defaults);
}
