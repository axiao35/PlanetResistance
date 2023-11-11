package com.example.PlanetResistance.ui_activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.PlanetResistance.game.GameValues;
import com.example.PlanetResistance.R;

import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        ConstraintLayout shopLayout = findViewById(R.id.shopLayout);
        ArrayList<View> buttons = shopLayout.getTouchables();
        for (int k = 0; k < buttons.size(); k++) {
            int l = k;
            if (!buttons.get(k).equals(findViewById(R.id.xButton))) {
                buttons.get(k).setOnClickListener(view -> {
                    GameValues.setTowerIndex(l);
                    finish();
                });
            } else {
                buttons.get(k).setOnClickListener(view -> {
                    setResult(-1);
                    finish();
                });
            }
        }
    }
}