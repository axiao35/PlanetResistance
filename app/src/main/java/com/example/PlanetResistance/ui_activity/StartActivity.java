package com.example.PlanetResistance.ui_activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.PlanetResistance.R;

public class StartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(view -> startConfig());

        Button quitButton = findViewById(R.id.quitButton);
        quitButton.setOnClickListener(view -> System.exit(0));
    }

    private void startConfig() {
        Intent intent = new Intent(this, ConfigActivity.class);
        startActivity(intent);
    }
}