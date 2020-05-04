package com.example.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.Model.Board;
import com.example.myapplication.Model.ClassForJsonObject;
import com.example.myapplication.Model.ElState;
import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

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
    boolean checker;

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
                        v.setClickable(false);
                        v.setEnabled(false);
                        sendBoard();
                        setEnabled(false);
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
                System.out.println("THIS IS HOST DEVICE");
            } else {
                role = "guest";
                System.out.println("THIS IS GUEST DEVICE");
            }
        }
        boardRef = database.getReference("rooms/" + roomName + "/data");
        ClassForJsonObject classForJsonObject = new ClassForJsonObject(role, newBoard.getBoard());
        String json = gson.toJson(classForJsonObject);
        boardRef.setValue(json);
        getBoardEventListener();
    }

    private void sendBoard()
    {
        ClassForJsonObject classForJsonObject = new ClassForJsonObject(role, newBoard.getBoard());
        String json = gson.toJson(classForJsonObject);
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

    public void updateUI(ElState[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != ElState.E) {
                    String buttonId = "button_" + i + "_" + j;
                    final Button button = findViewById(getResources().getIdentifier(buttonId, "id", GameActivity.this.getPackageName()));
                    button.setText(board[i][j].toString());
                    button.setEnabled(false);
                }
            }
        }
    }

    private void getBoardEventListener() {
        boardRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ClassForJsonObject obj = gson.fromJson(dataSnapshot.getValue(String.class), ClassForJsonObject.class);
                if (role.equals("host")) {
                    if (obj.role.equals("guest")) {
                        setEnabled(true);
                        nextTurn();
                        newBoard.updateBoard(obj);
                        updateUI(newBoard.getBoard());
                    }
                } else {
                    if (obj.role.equals("host")) {
                        setEnabled(true);
                        newBoard.updateBoard(obj);
                        updateUI(newBoard.getBoard());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
