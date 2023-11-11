package com.example.PlanetResistance.game;

import static com.example.PlanetResistance.game.GameValues.increaseLevel;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.PlanetResistance.R;
import com.example.PlanetResistance.map.MapSurfaceView;
import com.example.PlanetResistance.ui_activity.ShopActivity;
import com.example.PlanetResistance.ui_activity.TowerInfoActivity;

public class GameActivity extends AppCompatActivity {

    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        switch (GameValues.getDifficulty()) {
        case EASY:
            GameValues.setMoney(1000);
            GameValues.setHealth(1000);
            break;
        case NORMAL:
            GameValues.setMoney(500);
            GameValues.setHealth(500);
            break;
        case HARD:
            GameValues.setMoney(250);
            GameValues.setHealth(250);
            break;
        default:
            break;
        }

        ConstraintLayout gameLayout = findViewById(R.id.gameLayout);
        gameLayout.setOnTouchListener((view, motionEvent) -> false);

        MapSurfaceView mapSurfaceView = findViewById(R.id.mapSurfaceView);
        mapSurfaceView.addActivity(this);

        Button shopButton = findViewById(R.id.shopButton);
        shopButton.setOnClickListener(view -> showShop());

        Button startButton = findViewById(R.id.startButton3);
        startButton.setOnClickListener(view -> startCombat());
    }

    private void showShop() {
        Intent intent = new Intent(this, ShopActivity.class);
        startActivityForResult(intent, 0);
    }

    private void startCombat() {
        // if combat has already started don't do anything
        if (GameValues.getCombatMode()) {
            return;
        }
        GameValues.setCombatMode(true);
        increaseLevel();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != -1) {
            if (requestCode == 0) {
                Intent intent = new Intent(this, TowerInfoActivity.class);
                startActivityForResult(intent, 1);
            }
        }
    }
}