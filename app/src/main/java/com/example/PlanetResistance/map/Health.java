package com.example.PlanetResistance.map;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Health {

    private Rect rect;
    private String text;
    private Paint paint;

    public Health(String text) {
        this.rect = new Rect(0, 0, 448, 32);
        this.text = text;
        this.paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(20);
    }

    public void onDraw(Canvas canvas, TileSheet tileSheet) {
        canvas.drawBitmap(tileSheet.getBottomBar(), this.rect, new Rect(0, 736, 448, 768), null);
        canvas.drawText(this.text, 5, 760, paint);
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
