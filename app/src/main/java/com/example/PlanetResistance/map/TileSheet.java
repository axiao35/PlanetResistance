package com.example.PlanetResistance.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.PlanetResistance.R;

public class TileSheet {

    private Bitmap bitmap;
    private Bitmap bottomBar;

    //Turns the tilesheet png into a bitmap to be interacted with by the tiles.

    public TileSheet(Context context) {
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        bitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.tilemap1, bitmapOptions);

        BitmapFactory.Options bottomBarOptions = new BitmapFactory.Options();
        bottomBarOptions.inScaled = false;
        bottomBar = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.bottombar, bottomBarOptions);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
    public Bitmap getBottomBar() {
        return bottomBar;
    }
}
