package com.example.PlanetResistance.ui_activity;

import androidx.appcompat.app.AppCompatActivity;
import com.example.PlanetResistance.R;
import com.example.PlanetResistance.game.GameValues;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class GameWonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setFinishOnTouchOutside(false);
        setContentView(R.layout.activity_game_won);

        Button restartButton = findViewById(R.id.restartButton2);
        restartButton.setOnClickListener(view -> restart());

        TextView enemiesKilledText2 = findViewById(R.id.enemiesKilledText2);
        enemiesKilledText2.setText("Enemies Killed: " + GameValues.getEnemiesKilled().toString());
        TextView moneySpentText2 = findViewById(R.id.moneySpentText2);
        moneySpentText2.setText("Money Spent: " + GameValues.getMoneySpent().toString());
        TextView towersUpgradedText2 = findViewById(R.id.towersUpgradedText2);
        towersUpgradedText2.setText("Upgrades Purchased: " + GameValues.getTowersUpgraded().toString());
    }

    public void restart() {
        Intent intent = new Intent(this, ConfigActivity.class);
        startActivity(intent);
    }
}