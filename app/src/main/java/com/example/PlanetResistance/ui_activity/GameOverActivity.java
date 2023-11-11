package com.example.PlanetResistance.ui_activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.PlanetResistance.R;
import com.example.PlanetResistance.game.GameValues;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Button restartButton = findViewById(R.id.restartButton);
        restartButton.setOnClickListener(view -> restart());

        TextView enemiesKilledText = findViewById(R.id.enemiesKilledText);
        enemiesKilledText.setText("Enemies Killed: " + GameValues.getEnemiesKilled().toString());
        TextView moneySpentText = findViewById(R.id.moneySpentText);
        moneySpentText.setText("Money Spent: " + GameValues.getMoneySpent().toString());
        TextView towersUpgradedText = findViewById(R.id.towersUpgradedText);
        towersUpgradedText.setText("Upgrades Purchased: " + GameValues.getTowersUpgraded().toString());
    }

    public void restart() {
        Intent intent = new Intent(this, ConfigActivity.class);
        startActivity(intent);
    }
}