package com.example.PlanetResistance.map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.PlanetResistance.enemy.BeetleEnemy;
import com.example.PlanetResistance.enemy.Enemy;
import com.example.PlanetResistance.enemy.GoldenBeetleEnemy;
import com.example.PlanetResistance.enemy.RoachEnemy;
import com.example.PlanetResistance.enemy.ScorpionEnemy;
import com.example.PlanetResistance.game.GameValues;
import com.example.PlanetResistance.tower.Tower;

import java.util.ArrayList;
import java.util.Random;

public class Map {

    //Controller class that draws map image from individual tile objects and the tile sheet

    private Bitmap mapBitmap;
    private Canvas mapCanvas;
    private TileSheet tileSheet;

    private Tile[][] backgroundTiles;

    private Tower[][] towers;

    private ArrayList<Enemy> enemies;

    private Health health;
    private Money money;

    protected static final int ROWS = 12;
    protected static final int COLUMNS = 7;
    protected static final int TILE_HEIGHT = 64;
    protected static final int TILE_WIDTH = 64;

    //Basic layout for the game, this system can be built upon for map variety.

    public Map(TileSheet tileSheet) {
        this.tileSheet = tileSheet;
        health = new Health("Health: " + GameValues.getHealth().toString());
        money = new Money("Money: " + GameValues.getMoney().toString());
        initializeLayout();
        this.towers = new Tower[ROWS][COLUMNS];
        this.enemies = new ArrayList<>();
    }

    //Initializes the bitmap, canvas, and tile map using the layout information to fetch tiles
    //and populate the 2D tile array. Then, the tile array is drawn to the canvas.

    private void initializeLayout() {
        //Hardcoded map for now just for consistency!

        int[][] layout = new int[][]{
                {0, 2, 0, 0, 0, 0, 0},
                {0, 7, 3, 3, 3, 6, 0},
                {0, 1, 1, 1, 1, 2, 0},
                {0, 1, 1, 1, 1, 2, 0},
                {0, 5, 4, 4, 4, 8, 0},
                {0, 2, 1, 1, 1, 1, 0},
                {0, 2, 1, 1, 1, 1, 0},
                {0, 7, 3, 3, 3, 6, 0},
                {0, 1, 1, 1, 1, 2, 0},
                {0, 9, 10, 1, 1, 2, 0},
                {0, 11, 12, 4, 4, 8, 0},
                {0, 0, 0, 0, 0, 0, 0},
        };

        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        mapBitmap = Bitmap.createBitmap(
                TILE_WIDTH * COLUMNS, TILE_HEIGHT * ROWS, config);
        mapCanvas = new Canvas(mapBitmap);
        backgroundTiles = new Tile[ROWS][COLUMNS];
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLUMNS; x++) {
                int tileIdx = layout[y][x];
                backgroundTiles[y][x] = new Tile(tileIdx);
                backgroundTiles[y][x].onDraw(mapCanvas, tileSheet, x * TILE_WIDTH, y * TILE_HEIGHT);
            }
        }
        health.onDraw(mapCanvas, tileSheet);
        money.onDraw(mapCanvas);
        GameValues.setLayout(layout);
    }

    public boolean placeTower(int row, int col) {
        if (!isTower(row, col) && GameValues.getLayout()[row][col] == 1) {
            Tower tower = GameValues.getTowerList().get(GameValues.getTowerList().size() - 1);
            towers[row][col] = tower;
            tower.setCol(col);
            tower.setRow(row);
            return true;
        } else {
            return false;
        }
    }

    public boolean isTower(int row, int col) {
        return !(towers[row][col] == null);
    }

    public Enemy detectEnemy(int row, int col, int range) {
        if (enemies.size() > 0) {
            if (towers[row][col].getTargetEnemy() != null) {
                return towers[row][col].getTargetEnemy();
            }
            for (int i = 0; i < enemies.size(); i++) {
                if (enemies.get(i).getX() >= ((col * TILE_WIDTH) - (range * TILE_WIDTH))
                    && enemies.get(i).getX() <= ((col * TILE_WIDTH) + (range * TILE_WIDTH))) {
                    if (enemies.get(i).getY() >= ((row * TILE_HEIGHT) - (range * TILE_HEIGHT))
                        && enemies.get(i).getY() <= ((row * TILE_HEIGHT) + (range * TILE_HEIGHT))) {
                        return enemies.get(i);
                    }
                }
            }
        }
        return null;
    }

    public void addEnemy() {
        Enemy enemy;
        Random rand = new Random();
        int rint = rand.nextInt(3);
        switch (rint) {
        case 0:
            enemy = new RoachEnemy();
            break;
        case 1:
            enemy = new BeetleEnemy();
            break;
        default:
            enemy = new ScorpionEnemy();
            break;
        }
        this.enemies.add(enemy);
    }

    public void addBoss() {
        Enemy enemy = new GoldenBeetleEnemy();
        this.enemies.add(enemy);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void moveEnemies() {
        if (this.enemies.size() >= 2) {
            this.enemies.sort((o1, o2) -> {
                String enemy1 = String.valueOf(o1.getLifeCounter());
                String enemy2 = String.valueOf(o2.getLifeCounter());
                return enemy1.compareTo(enemy2);
            });
        }
        for (Enemy enemy: enemies) {
            enemy.move(backgroundTiles[(enemy.getY() / 64)][(enemy.getX() / 64)]);
        }
    }

    public void updateMap() {
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLUMNS; x++) {
                backgroundTiles[y][x].onDraw(mapCanvas, tileSheet, x * TILE_WIDTH, y * TILE_HEIGHT);
            }
        }
        for (Enemy enemy: enemies) {
            enemy.getTile().onDraw(mapCanvas, tileSheet, enemy.getX(), enemy.getY());
        }
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLUMNS; x++) {
                if (towers[y][x] != null) {
                    towers[y][x].getTile().onDraw(mapCanvas,
                                                  tileSheet, x * TILE_WIDTH,
                                                  y * TILE_HEIGHT);
                    towers[y][x].setTargetEnemy(detectEnemy(y, x, towers[y][x].getRange()));
                    if (towers[y][x].getTargetEnemy() != null) {
                        boolean fired = towers[y][x].shoot(towers[y][x].getTargetEnemy());
                        if (fired) {
                            towers[y][x].getBullet().onDraw(mapCanvas, tileSheet,
                                                            towers[y][x].getBullet().getX(),
                                                            towers[y][x].getBullet().getY());
                        }
                    }
                }
            }
        }
        updateText();
        health.onDraw(mapCanvas, tileSheet);
        money.onDraw(mapCanvas);
    }

    public void updateText() {
        health.setText("Health: " + GameValues.getHealth().toString());
        money.setText("Money: " + GameValues.getMoney().toString());
    }

    public void onDraw(Canvas canvas, int x, int y) {
        canvas.drawBitmap(mapBitmap, new Rect(0, 0,
                        TILE_WIDTH * COLUMNS, TILE_HEIGHT * ROWS),
                new Rect(0, 0, x, y), null);
    }

    public Bitmap getMapBitmap() {
        return mapBitmap;
    }
    public void setMapBitmap(Bitmap mapBitmap) {
        this.mapBitmap = mapBitmap;
    }
    public Canvas getMapCanvas() {
        return mapCanvas;
    }
    public void setMapCanvas(Canvas mapCanvas) {
        this.mapCanvas = mapCanvas;
    }
    public Tile[][] getBackgroundTiles() {
        return backgroundTiles;
    }
    public void setBackgroundTiles(Tile[][] backgroundTiles) {
        this.backgroundTiles = backgroundTiles;
    }
    public TileSheet getTileSheet() {
        return tileSheet;
    }
    public void setTileSheet(TileSheet tileSheet) {
        this.tileSheet = tileSheet;
    }
    public void setHealth(Health health) {
        this.health = health;
    }
    public void setMoney(Money money) {
        this.money = money;
    }
    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public Tower[][] getTowers() {
        return towers;
    }






}
