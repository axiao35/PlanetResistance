package com.example.PlanetResistance.map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.PlanetResistance.game.GameThread;
import com.example.PlanetResistance.game.GameValues;
import com.example.PlanetResistance.ui_activity.GameOverActivity;
import com.example.PlanetResistance.ui_activity.UpgradeActivity;

public class MapSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private Map map;
    private Canvas canvas = null;
    private GameThread thread;
    private Activity activity;

    //Initializes the SurfaceView with a new map.

    //Needs to chain constructors to display properly.

    public MapSurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        getHolder().addCallback(this);
        initializeMPV(context);
    }

    public MapSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        initializeMPV(context);
    }

    public void addActivity(Activity activity) {
        this.activity = activity;
        thread.setActivity(this.activity);
    }

    public MapSurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
        initializeMPV(context);
    }

    //All non-SurfaceView related processes here!

    public void initializeMPV(Context context) {
        TileSheet tileSheet = new TileSheet(context);
        map = new Map(tileSheet);
        thread = new GameThread(getHolder(), this);
    }


    //Creates new canvas from the SurfaceView, draws the initial map image, updates
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        canvas = getHolder().lockCanvas();
        draw(canvas);
        setWillNotDraw(false);
        getHolder().unlockCanvasAndPost(canvas);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        map.onDraw(canvas, this.getWidth(), this.getHeight());
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean handled = false;

        float x = motionEvent.getX();
        float y = motionEvent.getY();

        int tileHeight = (this.getHeight() / 12);
        int tileWidth = (this.getWidth() / 7);

        int col = (int) (x / tileWidth);
        int row = (int) (y / tileHeight);

        if (GameValues.getPlacementMode()) {
            handled = map.placeTower(row, col);
            if (handled) {
                GameValues.setPlacementMode(false);
            }
        } else if (map.isTower(row, col) && motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            UpgradeActivity.setTower(map.getTowers()[row][col]);
            Intent intent = new Intent(activity, UpgradeActivity.class);
            activity.startActivity(intent);
        }
        return handled;
    }

    //Needed to run!

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    //Needed to run!

    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public void update() {
        this.invalidate();
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

}
