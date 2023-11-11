package com.example.PlanetResistance.map;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Money {

    private String text;
    private Paint paint;

    public Money(String text) {
        this.text = text;
        this.paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setTextSize(20);
    }

    public void onDraw(Canvas canvas) {
        canvas.drawText(this.text, 229, 760, paint);
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
