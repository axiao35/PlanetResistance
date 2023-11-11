package com.example.PlanetResistance.enemy;

import com.example.PlanetResistance.game.GameValues;
import com.example.PlanetResistance.map.Tile;
import com.example.PlanetResistance.tower.StatusEffect;

import java.util.ArrayList;

public abstract class Enemy {
    private String name;
    private int health;
    private int damage;
    private double defaultSpeed;
    private double speed;
    private int x;
    private int y;
    private Tile tile;
    private int lifeCounter;
    private ArrayList<StatusEffect> statusEffects;
    private ArrayList<Integer> statusEffectDurations;
    private int cooldown = 0;



    public Enemy(String name, int health, int damage, double speed) {
        this.name = name;
        this.health = health;
        this.damage = damage;
        this.speed = speed;
        this.defaultSpeed = this.speed;
        this.x = 64;
        this.y = 0;
        this.tile = new Tile(this);
        this.lifeCounter = 0;
        statusEffects = new ArrayList<StatusEffect>();
        statusEffectDurations = new ArrayList<Integer>();
    }

    public void addStatusEffect(StatusEffect statusEffect, int statusEffectDuration) {
        if (statusEffect == null || statusEffectDuration == 0) {
            return;
        }
        statusEffects.add(statusEffect);
        statusEffectDurations.add(statusEffectDuration);
    }

    public void applyStatusEffect(int tick) {
        // this might run into the same error as when checking status for enemies in gameThread
        for (int i = statusEffectDurations.size() - 1; i >= 0; i--) {
            StatusEffect statusEffect = statusEffects.get(i);
            int statusEffectDuration = statusEffectDurations.get(i);
            if (statusEffectDuration > 0) {
                if (tick % (GameValues.getFPS()) == 0) {
                    statusEffect.affect(this);
                    statusEffectDuration--;
                    statusEffectDurations.set(i, statusEffectDuration);
                }
            } else {
                // if other defaults are created need to pass this second value as the list of those
                statusEffect.remove(this, this.defaultSpeed);
                statusEffects.remove(i);
                statusEffectDurations.remove(i);
            }
        }

    }

    public void move(Tile tile) {
        lifeCounter += (defaultSpeed);
        if (tile.getName().equals("Path_Right")
                || tile.getName().equals("Path_DownRight")) {
            x += (speed);
        }
        if (tile.getName().equals("Path_Left")
                || tile.getName().equals("Path_DownLeft")) {
            x -= (speed);
        }
        if (tile.getName().equals("Path_Down")
                || tile.getName().equals("Path_RightDown")) {
            y += (speed);
        }
        if (tile.getName().equals("Path_LeftDown")) {
            if (this.x % 64 != 0) {
                x -= (speed);
            } else {
                y += (speed);
            }
        }
        if (tile.getName().equals("Monument_BottomRight")) {
            if (cooldown  == 0) {
                attackMonument();
                cooldown = (int) (50 / speed);
            } else {
                cooldown--;
            }
        }

    }

    public void attackMonument() {
        GameValues.setHealth(GameValues.getHealth() - this.getDamage());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Tile getTile() {
        return this.tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public int getLifeCounter() {
        return this.lifeCounter;
    }

    public void setLifeCounter(int lifeCounter) {
        this.lifeCounter = lifeCounter;
    }
}
