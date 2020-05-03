package com.example.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.Fragments.DialogFragment;
import com.example.myapplication.Fragments.MultiPlayerFragment;
import com.example.myapplication.Fragments.PlayFragment;
import com.example.myapplication.Model.Board;
import com.example.myapplication.Model.ElState;
import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.EventListener;
import java.util.HashMap;

public class GameActivity extends AppCompatActivity {

    private ElState turn = ElState.X;
    private int checkForWinnerFragment = 0;
    ElState winnerCheckVar = ElState.E;
    Button button;

    String playerName = "";
    String roomName = "";
    String role = "";
    String message = "";

    FirebaseDatabase database;
    DatabaseReference messageRef;
    DatabaseReference boardRef;
    DatabaseReference dataRef;

    final Board newBoard = new Board();
    Gson gson = new Gson();


    private void nextTurn() {
        turn = turn == ElState.X ? ElState.O : ElState.X;
    }

    private String getTurnText() {
        return turn == ElState.X ? "X" : "O";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        button = findViewById(R.id.button3);
        button.setEnabled(false);
        setEnabled(false);
        database = FirebaseDatabase.getInstance();

        for (int i = 0; i < newBoard.boardSize; i++) {
            for (int j=0; j< newBoard.boardSize; j++) {
                final int indexI = i;
                final int indexJ = j;
                String buttonId = "button_" + indexI + "_" + indexJ;
                final Button button = findViewById(getResources().getIdentifier(buttonId, "id", GameActivity.this.getPackageName()));
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Animation animationUtils = AnimationUtils.loadAnimation(GameActivity.this, R.anim.fadein);
                        button.startAnimation(animationUtils);
                        newBoard.setElement(indexI, indexJ, turn);
                        newBoard.print();
                        if (newBoard.checkForWinner() == ElState.X) {
                            winnerCheckVar = ElState.X;
                            System.out.println(winnerCheckVar);
                        }
                        if (newBoard.checkForWinner() == ElState.O) {
                            winnerCheckVar = ElState.O;
                            System.out.println(winnerCheckVar);
                        }
                        if (newBoard.checkForWinner() == ElState.N) {
                            winnerCheckVar = ElState.N;
                            System.out.println(winnerCheckVar);
                        }
                        button.setText(getTurnText());
                        nextTurn();
                        sendBoard();
                        v.setClickable(false);
                        v.setEnabled(false);
                        setEnabled(false);
                        message = role + ":Poked!";
                        messageRef.setValue(message);
                    }
                });
            }
        }

        database = FirebaseDatabase.getInstance();

        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
        playerName = preferences.getString("playerName", "");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            roomName = extras.getString("roomName");
            if (roomName.equals(playerName)) {
                role = "host";
            } else {
                role = "guest";
            }
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // send message
                button.setEnabled(false);
                message = role + ":Poked!";
                messageRef.setValue(message);
            }
        });
        // listen for incoming messages
        messageRef = database.getReference("rooms/" + roomName + "/message");
        message = role + ":Poked!";
        messageRef.setValue(message);
        addRoomEventListener();
    }

    private void sendBoard()
    {
        String json = gson.toJson(newBoard.getBoard());
        boardRef = database.getReference("rooms/" + roomName + "/board");
        boardRef.setValue(json);
    }

    private void setEnabled(boolean checker)
    {
        for (int i = 0; i < newBoard.boardSize; i++)
        {
            for (int j = 0; j < newBoard.boardSize; j++)
            {
                String buttonId = "button_" + i + "_" + j;
                final Button button = findViewById(getResources().getIdentifier(buttonId, "id", GameActivity.this.getPackageName()));
                button.setEnabled(checker);
            }
        }
    }

    private void addRoomEventListener() {
        messageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // message received
                if (role.equals("host")) {
                    System.out.println(dataSnapshot);
                    if (dataSnapshot.getValue(String.class).contains("guest:")) {
                        setEnabled(true);
                        System.out.println("datasnapshot " + dataSnapshot);
                        button.setEnabled(true);
                        Toast.makeText(GameActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    System.out.println(dataSnapshot);
                    if (dataSnapshot.getValue(String.class).contains("host:")) {
                        setEnabled(true);
                        System.out.println("datasnapshot " + dataSnapshot);
                        button.setEnabled(true);
                        Toast.makeText(GameActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // error - retry
                messageRef.setValue(message);
            }
        });
    }
}
