package com.example.PlanetResistance.game;

import com.example.PlanetResistance.enemy.Enemy;
import com.example.PlanetResistance.tower.Tower;
import java.util.ArrayList;
import java.util.List;

public class GameValues {
    public static int[][] getLayout() {
        return layout;
    }

    public static void setLayout(int[][] layout) {
        GameValues.layout = layout;
    }

    public enum Difficulty {
        EASY,
        NORMAL,
        HARD
    }
    private static Difficulty difficulty;
    private static String username;
    private static Integer money = 0;
    private static Integer health = 0;
    private static int towerIndex;
    private static List<Tower> towerList = new ArrayList<>();
    private static List<Enemy> enemyList = new ArrayList<>();
    private static boolean combatMode = false;
    private static boolean placementMode = false;
    private static int level = 0;
    private static final int FPS = 30;
    private static int[][] layout;
    private static final int BOSS_WAVE = 5;
    private static Integer enemiesKilled = 0;
    private static Integer moneySpent = 0;
    private static Integer towersUpgraded = 0;

    public static int getBossWave() {
        return BOSS_WAVE;
    }

    public static Difficulty getDifficulty() {
        return difficulty;
    }

    public static void setDifficulty(Difficulty difficulty) {
        GameValues.difficulty = difficulty;
    }

    public static int getTowerIndex() {
        return towerIndex;
    }

    public static void setTowerIndex(int towerIndex) {
        GameValues.towerIndex = towerIndex;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        GameValues.username = username;
    }

    public static Integer getMoney() {
        return money;
    }

    public static void setMoney(Integer money) {
        GameValues.money = money;
    }

    public static Integer getHealth() {
        return health;
    }

    public static void setHealth(Integer health) {
        GameValues.health = health;
    }

    public static List<Tower> getTowerList() {
        return towerList;
    }

    public static List<Enemy> getEnemyList() {
        return enemyList;
    }

    public static boolean getPlacementMode() {
        return GameValues.placementMode;
    }

    public static void setPlacementMode(boolean set) {
        GameValues.placementMode = set;
    }

    public static int getFPS() {
        return FPS;
    }

    public static boolean getCombatMode() {
        return combatMode;
    }

    public static void setCombatMode(boolean mode) {
        GameValues.combatMode = mode;
    }

    public static int getLevel() {
        return level;
    }

    public static void setLevel(int level) {
        GameValues.level = level;
    }

    public static void increaseLevel() {
        level++;
    }

    public static void enemyKilled() {
        enemiesKilled++;
    }

    public static Integer getEnemiesKilled() {
        return enemiesKilled;
    }

    public static void setEnemiesKilled(int n) {
        enemiesKilled = n;
    }

    public static void addMoneySpent(Integer n) {
        moneySpent += n;
    }

    public static void setMoneySpent(Integer n) {
        moneySpent = n;
    }

    public static void clearMoneySpent() {
        moneySpent = 0;
    }

    public static Integer getMoneySpent() {
        return moneySpent;
    }

    public static void towerUpgraded() {
        towersUpgraded++;
    }

    public static Integer getTowersUpgraded() {
        return towersUpgraded;
    }

    public static void setTowersUpgraded(int n) {
        towersUpgraded = n;
    }
}
