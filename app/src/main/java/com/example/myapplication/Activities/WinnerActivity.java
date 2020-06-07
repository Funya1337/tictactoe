package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;

public class WinnerActivity extends AppCompatActivity {
    TextView winner_title;
    Button exitBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        setContentView(R.layout.activity_winner);
        winner_title = findViewById(R.id.winner_title);
        exitBtn = findViewById(R.id.exitBtn);
        Bundle extras = getIntent().getExtras();
        String str = extras.getString("winner");
        if (str.equals("X"))
            winner_title.setText("CROSS WIN");
        if (str.equals("O"))
            winner_title.setText("ZERO WIN");
        if (str.equals("N"))
            winner_title.setText("NO WIN");
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WinnerActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
