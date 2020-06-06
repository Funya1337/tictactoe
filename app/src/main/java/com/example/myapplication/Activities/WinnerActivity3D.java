package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;

public class WinnerActivity3D extends AppCompatActivity {
    private static final String TAG = "MainTagName";
    private TextView winnerText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner_activity3_d);
        winnerText = findViewById(R.id.winnerText);
        button = findViewById(R.id.goToMainActivityBtn);
        Intent intent = getIntent();
        int data = intent.getIntExtra("data", -1);
        Log.d(TAG, data + "");
        if (data == 1)
            winnerText.setText("Cross win");
        if (data == 0)
            winnerText.setText("Zero win");
        if (data == 2)
            winnerText.setText("No win");
        button.setOnClickListener(v -> startActivity(new Intent(WinnerActivity3D.this, MainActivity.class)));
    }
}