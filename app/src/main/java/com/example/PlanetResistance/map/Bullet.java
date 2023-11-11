package com.example.PlanetResistance.map;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.PlanetResistance.tower.AssassinTower;
import com.example.PlanetResistance.tower.GravityTower;
import com.example.PlanetResistance.tower.SniperTower;
import com.example.PlanetResistance.tower.Tower;

public class Bullet {
    private String name;
    private Rect rect;

    private int x;
    private int y;

    public Bullet(Tower tower) {
        this.x = 0;
        this.y = 0;
        if (tower instanceof AssassinTower) {
            this.name = tower.getName();
            this.rect = new Rect(96, 288, 128, 320);
        } else if (tower instanceof SniperTower) {
            this.name = tower.getName();
            this.rect = new Rect(64, 288, 96, 320);
        } else if (tower instanceof GravityTower) {
            this.name = tower.getName();
            this.rect = new Rect(64, 256, 96, 288);
        } else {
            this.name = tower.getName();
            this.rect = new Rect(96, 256, 128, 288);
        }
    }

    public void onDraw(Canvas canvas, TileSheet tileSheet, int x, int y) {
        canvas.drawBitmap(tileSheet.getBitmap(), this.rect, new Rect(x, y,
                x + 32, y + 32), null);
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return this.x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return this.y;
    }
}
