package com.example.PlanetResistance.game;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.example.PlanetResistance.enemy.BeetleEnemy;
import com.example.PlanetResistance.enemy.Enemy;
import com.example.PlanetResistance.enemy.RoachEnemy;
import com.example.PlanetResistance.ui_activity.GameOverActivity;
import com.example.PlanetResistance.map.MapSurfaceView;
import com.example.PlanetResistance.ui_activity.GameWonActivity;

import java.util.ArrayList;
import java.util.Iterator;

public class GameThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private MapSurfaceView mapSurfaceView;
    private boolean running;
    private static Canvas canvas;
    private Activity activity;

    public GameThread(SurfaceHolder surfaceHolder, MapSurfaceView mapSurfaceView) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.mapSurfaceView = mapSurfaceView;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @SuppressLint("NewApi")
    @Override
    public void run() {
        int frameCount = 0;
        int combatCount = 0;
        int enemiesSpawn = 0;
        long targetTime = 1000 / GameValues.getFPS();
        long startTime = System.currentTimeMillis();

        while (running) {

            long taskTime = System.currentTimeMillis();
            canvas = null;

            if (GameValues.getCombatMode()) {
                if (GameValues.getLevel() == GameValues.getBossWave()) {
                    if (combatCount == 0) {
                        mapSurfaceView.getMap().addBoss();
                        enemiesSpawn = 3 + (3 * GameValues.getLevel());
                    }
                    if (combatCount % 20 == 0 && enemiesSpawn != 0) {
                        mapSurfaceView.getMap().addEnemy();
                        enemiesSpawn--;
                    }
                    if (mapSurfaceView.getMap().getEnemies().size() == 0) {
                        combatCount = 0;
                        GameValues.setCombatMode(false);
                        GameValues.increaseLevel();
                    }
                    combatCount++;
                } else {
                    if (combatCount == 0) {
                        enemiesSpawn = 3 + (2 * GameValues.getLevel());
                    }
                    if (combatCount % 20 == 0 && enemiesSpawn != 0) {
                        mapSurfaceView.getMap().addEnemy();
                        enemiesSpawn--;
                    }
                    combatCount++;

                    if (mapSurfaceView.getMap().getEnemies().size() == 0 && enemiesSpawn == 0) {
                        combatCount = 0;
                        GameValues.setCombatMode(false);

                        // earn money (implement a good formula later)
                        GameValues.setMoney(GameValues.getMoney() + GameValues.getLevel() * 200);
                    }
                }
            } else {
                mapSurfaceView.getMap().getEnemies().clear();
            }

            // check enemy status and apply any effects
            ArrayList<Enemy> enemies = mapSurfaceView.getMap().getEnemies();

            //important to do it this way to avoid remove exception when enemy dies
            for (Iterator<Enemy> enemyIterator = enemies.iterator(); enemyIterator.hasNext();) {
                Enemy enemy = enemyIterator.next();
                enemy.applyStatusEffect(combatCount);
                if (enemy.getHealth() <= 0) {
                    GameValues.enemyKilled();
                    if (enemy instanceof RoachEnemy) {
                        GameValues.setMoney(GameValues.getMoney() + 25);
                    } else if (enemy instanceof BeetleEnemy) {
                        GameValues.setMoney(GameValues.getMoney() + 75);
                    } else {
                        GameValues.setMoney(GameValues.getMoney() + 50);
                    }
                    enemyIterator.remove();
                }
            }

            mapSurfaceView.getMap().updateMap();
            mapSurfaceView.getMap().moveEnemies();
            mapSurfaceView.update();


            if (GameValues.getHealth() <= 0) {
                Intent intent = new Intent(activity, GameOverActivity.class);
                activity.startActivity(intent);
                running = false;
            }

            if (GameValues.getLevel() > GameValues.getBossWave()) {
                Intent intent = new Intent(activity, GameWonActivity.class);
                activity.startActivity(intent);
                running = false;
            }

            taskTime = System.currentTimeMillis() - taskTime;

            try {
                long waitTime = targetTime - taskTime;
                if (waitTime < 0) {
                    waitTime = 0;
                }
                this.sleep(waitTime);
            } catch (Exception e) {
                e.printStackTrace();
            }

            frameCount++;
            long secondsRan = (System.currentTimeMillis() - startTime) / 1000;
            if (secondsRan != 0) {
                System.out.println("FPS: " + (frameCount / secondsRan));
            }
        }
    }

    public void setRunning(boolean isRunning) {
        running = isRunning;
    }
}