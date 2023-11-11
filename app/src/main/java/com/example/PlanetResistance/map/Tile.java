package com.example.PlanetResistance.map;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.PlanetResistance.enemy.BeetleEnemy;
import com.example.PlanetResistance.enemy.Enemy;
import com.example.PlanetResistance.enemy.GoldenBeetleEnemy;
import com.example.PlanetResistance.enemy.RoachEnemy;
import com.example.PlanetResistance.tower.AssassinTower;
import com.example.PlanetResistance.tower.GravityTower;
import com.example.PlanetResistance.tower.SniperTower;
import com.example.PlanetResistance.tower.Tower;

public class Tile {
    private String name;
    private Rect rect;
    private static final int TILE_WIDTH = 64;
    private static final int TILE_HEIGHT = 64;

    public Tile(Rect rect) {
        this.rect = rect;
    }

    public Tile(int tileIdx) {
        switch (tileIdx) {
        case (0):
            this.name = "Filler";
            this.rect = new Rect(0, 0, 64, 64);
            break;
        case (1):
            this.name = "Placement";
            this.rect = new Rect(64, 0, 128, 64);
            break;
        case (2):
            this.name = "Path_Down";
            this.rect = new Rect(0, 64, 64, 128);
            break;
        case (3):
            this.name = "Path_Right";
            this.rect = new Rect(64, 64, 128, 128);
            break;
        case (4):
            this.name = "Path_Left";
            this.rect = new Rect(128, 64, 192, 128);
            break;
        case (5):
            this.name = "Path_LeftDown";
            this.rect = new Rect(192, 64, 256, 128);
            break;
        case (6):
            this.name = "Path_RightDown";
            this.rect = new Rect(256, 64, 320, 128);
            break;
        case (7):
            this.name = "Path_DownRight";
            this.rect = new Rect(192, 128, 256, 192);
            break;
        case (8):
            this.name = "Path_DownLeft";
            this.rect = new Rect(256, 128, 320, 192);
            break;
        case (9):
            this.name = "Monument_TopLeft";
            this.rect = new Rect(192, 192, 256, 256);
            break;
        case (10):
            this.name = "Monument_TopRight";
            this.rect = new Rect(256, 192, 320, 256);
            break;
        case (11):
            this.name = "Monument_BottomLeft";
            this.rect = new Rect(192, 256, 256, 320);
            break;
        case (12):
            this.name = "Monument_BottomRight";
            this.rect = new Rect(256, 256, 320, 320);
            break;
        default:
            break;
        }
    }

    public Tile(Tower tower) {
        if (tower instanceof AssassinTower) {
            this.name = tower.getName();
            this.rect = new Rect(0, 128, 64, 192);
        } else if (tower instanceof SniperTower) {
            this.name = tower.getName();
            this.rect = new Rect(0, 192, 64, 256);
        } else if (tower instanceof GravityTower) {
            this.name = tower.getName();
            this.rect = new Rect(128, 128, 192, 192);
        } else {
            this.name = tower.getName();
            this.rect = new Rect(64, 128, 128, 192);
        }
    }

    public Tile(Enemy enemy) {
        if (enemy instanceof RoachEnemy) {
            this.name = enemy.getName();
            this.rect = new Rect(64, 192, 128, 256);
        } else if (enemy instanceof BeetleEnemy) {
            this.name = enemy.getName();
            this.rect = new Rect(128, 192, 192, 256);
        } else if (enemy instanceof GoldenBeetleEnemy) {
            this.name = enemy.getName();
            this.rect = new Rect(128, 256, 192, 320);
        } else {
            this.name = enemy.getName();
            this.rect = new Rect(0, 256, 64, 320);
        }
    }

    public void onDraw(Canvas canvas, TileSheet tileSheet, int x, int y) {
        canvas.drawBitmap(tileSheet.getBitmap(), this.rect, new Rect(x, y,
                x + TILE_WIDTH, y + TILE_HEIGHT), null);
    }

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
