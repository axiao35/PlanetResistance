package com.example.PlanetResistance.ui_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.PlanetResistance.R;
import com.example.PlanetResistance.game.GameValues;
import com.example.PlanetResistance.tower.Tower;
import com.example.PlanetResistance.tower.AssassinTower;
import com.example.PlanetResistance.tower.GravityTower;
import com.example.PlanetResistance.tower.NormalTower;
import com.example.PlanetResistance.tower.SniperTower;

public class TowerInfoActivity extends AppCompatActivity {
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tower_info);
        Integer[] easyPrices = {50, 200, 100, 25};
        Integer[] normalPrices = {75, 300, 200, 50};
        Integer[] hardPrices = {100, 400, 300, 75};
        Integer priceHelper;

        TextView towerText = findViewById(R.id.towerText);
        towerText.setText(getChosenTower(0).getName());

        text(findViewById(R.id.damage), 0);
        text(findViewById(R.id.firingRate), 1);
        text(findViewById(R.id.range), 2);
        text(findViewById(R.id.multiplicity), 3);
        text(findViewById(R.id.poisonRate), 4);
        text(findViewById(R.id.slowRate), 5);

        TextView price = findViewById(R.id.price);
        if (GameValues.getDifficulty() == GameValues.Difficulty.EASY) {
            priceHelper = easyPrices[GameValues.getTowerIndex()];
        } else if (GameValues.getDifficulty() == GameValues.Difficulty.NORMAL) {
            priceHelper = normalPrices[GameValues.getTowerIndex()];
        } else {
            priceHelper = hardPrices[GameValues.getTowerIndex()];
        }
        price.setText(priceHelper.toString());

        Button xButton = findViewById(R.id.xButton2);
        xButton.setOnClickListener(view -> finish());

        Button purchaseButton = findViewById(R.id.purchaseButton);
        purchaseButton.setOnClickListener(view -> {
            if (GameValues.getMoney() < priceHelper) {
                TextView errorText = findViewById(R.id.errorText);
                errorText.setText("Not Enough Money");
                return;
            }
            Tower tower = getChosenTower(priceHelper);
            tower.purchase();
            GameValues.getTowerList().add(tower);
            GameValues.setPlacementMode(true);
            setResult(1);
            finish();
        });
    }

    private Tower getChosenTower(int priceHelper) {
        Tower tower;
        switch (GameValues.getTowerIndex()) {
        case 0:
            tower = new NormalTower(priceHelper);
            break;
        case 1:
            tower = new SniperTower(priceHelper);
            break;
        case 2:
            tower = new AssassinTower(priceHelper);
            break;
        default:
            tower = new GravityTower(priceHelper);
            break;
        }
        return tower;
    }

    private void text(TextView textView, int k) {
        Integer stat = 0;
        switch (k) {
        case 0:
            stat = getChosenTower(0).getDamage();
            break;
        case 1:
            stat = getChosenTower(0).getFiringRate();
            stat = (int)(1/(double)stat*1000);
            break;
        case 2:
            stat = getChosenTower(0).getRange();
            break;
        case 3:
            if (getChosenTower(0) instanceof SniperTower) {
                stat = new SniperTower(0).getMultiplicity();
            }
            break;
        case 4:
            if (getChosenTower(0) instanceof AssassinTower) {
                stat = new AssassinTower(0).getPoisonDmg();
            }
            break;
        case 5:
            if (getChosenTower(0) instanceof GravityTower) {
                stat = new GravityTower(0).getSlowRate();
            }
            break;
        default:
            break;
        }
        textView.setText(stat.toString());
    }
}