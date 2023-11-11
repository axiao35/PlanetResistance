package com.example.PlanetResistance.tower;

import com.example.PlanetResistance.enemy.Enemy;
import com.example.PlanetResistance.game.GameValues;
import com.example.PlanetResistance.map.Bullet;
import com.example.PlanetResistance.map.Tile;

public abstract class Tower {
    private String name;
    private int damage;
    private int firingRate;
    private int range;
    private int price;
    private Tile tile;
    private Bullet bullet;
    private int row;
    private int col;
    private int numUpgrades;
    private boolean firingMode = false;
    private Enemy targetEnemy = null;
    private int cooldown = 0;
    private final double STAT_CONSTANT = 0.1;

    private StatusEffect statusEffect = null;
    private int statusEffectDuration = 0;

    public Tower(String name, int damage, int firingRate, int range, int price) {
        this.name = name;
        this.damage = damage;
        this.firingRate = firingRate;
        this.range = range;
        this.price = price;
        this.tile = new Tile(this);
        this.bullet = new Bullet(this);
    }

    public boolean shoot(Enemy enemy) {
        if (enemy.getHealth() <= 0) {
            this.setTargetEnemy(null);
            firingMode = false;
            cooldown = (firingRate);
            bullet.setX(0);
            bullet.setY(0);
            return false;
        }
        if (bullet.getX() >= (enemy.getX() - 16) && (bullet.getX() + 32) <= (enemy.getX() + 80)
            && bullet.getY() >= (enemy.getY() - 16)
            && (bullet.getY() + 32) <= (enemy.getY() + 80)) {
            damageEnemy(enemy);
            cooldown = (firingRate);
            firingMode = false;
            enemy.addStatusEffect(getStatusEffect(), getStatusEffectDuration());
            // done so bullet does not linger and attack unwanted enemies
            bullet.setX(0);
            bullet.setY(0);
            return false;
        }
        if (cooldown == 0) {
            if (!firingMode) {
                bullet.setX((this.getCol() * 64) + 16);
                bullet.setY((this.getRow() * 64) + 16);
                firingMode = true;
            }
            if (firingMode) {
                int xDistance = (enemy.getX() - bullet.getX());
                int yDistance = (enemy.getY() - bullet.getY());
                double magnitude = Math.sqrt((Math.pow(xDistance, 2) + Math.pow(yDistance, 2)));

                if (magnitude <= 10) {
                    bullet.setX(enemy.getX() + 32);
                    bullet.setY(enemy.getY() + 32);
                }
                double xUnitVector = (xDistance / magnitude);
                double yUnitVector = (yDistance / magnitude);

                double xMoved = (25 * xUnitVector);
                double yMoved = (25 * yUnitVector);

                bullet.setX((int) (bullet.getX() + xMoved));
                bullet.setY((int) (bullet.getY() + yMoved));
            }
            return true;
        } else {
            cooldown--;
            return false;
        }



    }

    public void damageEnemy(Enemy enemy) {
        enemy.setHealth(enemy.getHealth() - this.damage);
    }

    public void purchase() {
        GameValues.addMoneySpent(price);
        GameValues.setMoney(GameValues.getMoney() - price);
    }

    public boolean upgrade(char type, int upgradePrice) {
        boolean upgradeSuccessful = false;
        switch(type) {
            case 'd': // damage
                if (GameValues.getMoney() >= upgradePrice) {
                    damage = increaseStat(damage, false);
                    upgradeSuccessful = true;
                    GameValues.addMoneySpent(upgradePrice);
                    GameValues.towerUpgraded();
                }
                break;
            case 'f': // firing rate
                if (GameValues.getMoney() >= upgradePrice && firingRate != 1) {
                    firingRate = increaseStat(firingRate, true);
                    upgradeSuccessful = true;
                    GameValues.addMoneySpent(upgradePrice);
                    GameValues.towerUpgraded();
                }
                break;
            case 'r': // range
                if (GameValues.getMoney() >= upgradePrice) {
                    range = increaseStat(range, false);
                    upgradeSuccessful = true;
                    GameValues.addMoneySpent(upgradePrice);
                    GameValues.towerUpgraded();
                }
                break;
            case 's': //status effect
                if (GameValues.getMoney() >= upgradePrice && getStatusEffectStat() != -1) {
                    setStatusEffectStat(increaseStat(getStatusEffectStat(), false));
                    upgradeSuccessful = true;
                    GameValues.addMoneySpent(upgradePrice);
                    GameValues.towerUpgraded();
                }
                break;
            default:
                break;
        }
        return upgradeSuccessful;
    }

    private int increaseStat(int stat, boolean inverse) {
        if (!inverse) {
            return (int)Math.ceil(stat*(1+STAT_CONSTANT));
        }
        return (int)Math.ceil(stat*(1-STAT_CONSTANT));
    }

    public StatusEffect getStatusEffect() {
        return statusEffect;
    }

    public void setStatusEffect(StatusEffect statusEffect) {
        this.statusEffect = statusEffect;
    }

    public int getStatusEffectDuration() {
        return statusEffectDuration;
    }

    public void setStatusEffectDuration(int statusEffectDuration) {
        this.statusEffectDuration = statusEffectDuration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getFiringRate() {
        return firingRate;
    }

    public void setFiringRate(int firingRate) {
        this.firingRate = firingRate;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getStatusEffectStat() {
        return -1;
    }

    public void setStatusEffectStat(int stat) {
        return;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public int getRow() {
        return this.row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return this.col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getNumUpgrades() {
        return this.numUpgrades;
    }

    public void setNumUpgrades(int numUpgrades) {
        this.numUpgrades = numUpgrades;
    }

    public Enemy getTargetEnemy() {
        return this.targetEnemy;
    }

    public void setTargetEnemy(Enemy enemy) {
        this.targetEnemy = enemy;
    }

    public Bullet getBullet() {
        return this.bullet;
    }
}
