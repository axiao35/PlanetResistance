package com.example.PlanetResistance.ui_activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.PlanetResistance.game.GameActivity;
import com.example.PlanetResistance.game.GameValues;
import com.example.PlanetResistance.R;

public class ConfigActivity extends AppCompatActivity {
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GameValues.setEnemiesKilled(0);
        GameValues.setMoneySpent(0);
        GameValues.setTowersUpgraded(0);

        // turn combat off when back is clicked or restart happens

        setContentView(R.layout.activity_config);

        Button startButton = findViewById(R.id.startButton2);
        startButton.setOnClickListener(view -> {
            RadioGroup difficultyGroup = findViewById(R.id.difficultyGroup);
            int selectedId = difficultyGroup.getCheckedRadioButtonId();

            EditText usernameInput = findViewById(R.id.username);
            String username = usernameInput.getText().toString();

            if (selectedId != -1 && username.replaceAll(" ", "").length() != 0) {
                GameValues.setUsername(username);
                switch (selectedId) {
                case R.id.easyButton:
                    GameValues.setDifficulty(GameValues.Difficulty.EASY);
                    break;
                case R.id.normalButton:
                    GameValues.setDifficulty(GameValues.Difficulty.NORMAL);
                    break;
                case R.id.hardButton:
                    GameValues.setDifficulty(GameValues.Difficulty.HARD);
                    break;
                default:
                    break;
                }
                startGame();
            }
        });
    }

    private void startGame() {
        Intent intent = new Intent(this, GameActivity.class);
        GameValues.setCombatMode(false);
        GameValues.setLevel(0);
        startActivity(intent);
    }
}