package com.example.PlanetResistance.ui_activity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.PlanetResistance.R;
import com.example.PlanetResistance.game.GameValues;
import com.example.PlanetResistance.tower.Tower;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class UpgradeActivity extends AppCompatActivity {

    private static Tower tower;
    private TextView priceText;
    private int price;
    private final double GROWTH_CONSTANT = 1.5;
    private HashMap<Character, TextView> stats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade);

        Button xButton = findViewById(R.id.xButton3);
        xButton.setOnClickListener(view -> finish());

        priceText = findViewById(R.id.upgradePriceText);
        updatePrice();

        stats = new HashMap<>();
        stats.put('d', findViewById(R.id.towerDamageText));
        stats.put('f', findViewById(R.id.towerFiringRateText));
        stats.put('r', findViewById(R.id.towerRangeText));
        stats.put('s', findViewById(R.id.towerStatusEffectText));
        updateStats();

        // upgrade buttons
        Button damageUpgrade = findViewById(R.id.damageUpgradeButton);
        damageUpgrade.setOnClickListener(view -> upgrade('d'));

        Button firingRateUpgrade = findViewById(R.id.firingRateUpgradeButton);
        firingRateUpgrade.setOnClickListener(view -> upgrade('f'));

        Button rangeButton = findViewById(R.id.rangeUpgradeButton);
        rangeButton.setOnClickListener(view -> upgrade('r'));

        Button statusEffectButton = findViewById(R.id.statusEffectUpgradeButton);
        statusEffectButton.setOnClickListener(view -> upgrade('s'));
    }

    private void upgrade(char type) {
        // using char for efficiency
        boolean upgradeSuccessful = tower.upgrade(type, price);
        if (upgradeSuccessful) {
            tower.setNumUpgrades(tower.getNumUpgrades()+1);
            GameValues.setMoney(GameValues.getMoney()-price);
            updatePrice();
            updateStats();
        }
    }

    private void updatePrice() {
        price = (int) ((Math.pow(GROWTH_CONSTANT, tower.getNumUpgrades()+1) / GROWTH_CONSTANT)*100);
        priceText.setText("Price: " + price);
    }

    private void updateStats() {
        stats.get('d').setText(String.valueOf(tower.getDamage()));
        stats.get('f').setText(String.valueOf((int)(1/(double)tower.getFiringRate()*1000)));
        stats.get('r').setText(String.valueOf(tower.getRange()));

        int statusValue = tower.getStatusEffectStat();
        if (statusValue == -1) {
            statusValue = 0; // visual reasons
        }
        stats.get('s').setText(String.valueOf(statusValue));
    }

    public static void setTower(Tower t) {
        tower = t;
    }
}