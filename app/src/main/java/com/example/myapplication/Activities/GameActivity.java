package com.example.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class GameActivity extends AppCompatActivity {

    private ElState turn = ElState.X;
    private int checkForWinnerFragment = 0;
    ElState winnerCheckVar = ElState.E;

    String playerName = "";
    String roomName = "";
    String role = "";

    boolean checker = false;
    int checker1 = 0;

    FirebaseDatabase database;
    DatabaseReference boardRef;

    final Board newBoard = new Board();
    Gson gson = new Gson();

    private String getTurnText() {
        return turn == ElState.X ? "X" : "O";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        setEnabled(false);

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
                            checker = true;
                            System.out.println("1. guest x winner");
                            checkForWinner(winnerCheckVar.toString());
                            System.out.println(winnerCheckVar);
                         }
                        if (newBoard.checkForWinner() == ElState.O) {
                            winnerCheckVar = ElState.O;
                            checker = true;
                            checkForWinner(winnerCheckVar.toString());
                            System.out.println(winnerCheckVar);
                        }
                        if (newBoard.checkForWinner() == ElState.N) {
                            winnerCheckVar = ElState.N;
                            checker = true;
                            checkForWinner(winnerCheckVar.toString());
                            System.out.println(winnerCheckVar);
                        }
                        button.setText(getTurnText());
                        v.setClickable(false);
                        v.setEnabled(false);
                        sendBoardToServer();
                        setEnabled(false);
                        if (!checker)
                            Toast.makeText(GameActivity.this, "Wait for opponent turn", Toast.LENGTH_SHORT).show();
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
        boardRef = database.getReference("rooms/" + roomName + "/data");
        ClassForJsonObject classForJsonObject = new ClassForJsonObject(role, newBoard.getBoard(), winnerCheckVar);
        String json = gson.toJson(classForJsonObject);
        boardRef.setValue(json);
        setBoardEventListener();
    }

    private void sendBoardToServer()
    {
        ClassForJsonObject classForJsonObject = new ClassForJsonObject(role, newBoard.getBoard(), winnerCheckVar);
        String json = gson.toJson(classForJsonObject);
        if (winnerCheckVar == ElState.X) {
            System.out.println("2. send guest x winner");
            System.out.println(json);
        }
        boardRef.setValue(json);
    }

    public void checkForWinner(String winner)
    {
        Intent intent = new Intent(GameActivity.this, WinnerActivity.class);
        intent.putExtra("winner", winner);
        startActivity(intent);
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

    private void setBoardEventListener() {
        boardRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ClassForJsonObject obj = gson.fromJson(dataSnapshot.getValue(String.class), ClassForJsonObject.class);
                if (role.equals("host")) {
                    if (obj.role.equals("guest")) {
                        setEnabled(true);
                        turn = ElState.O;
                        newBoard.updateBoard(obj);
                        updateUI(newBoard.getBoard());
                        System.out.println("rendering fragment(guest) " + obj.winner);
                        if (obj.winner != ElState.E) {
                            checkForWinner(obj.winner.toString());
                        }
                    }
                } else {
                    if (obj.role.equals("host")) {
                        setEnabled(true);
                        newBoard.updateBoard(obj);
                        updateUI(newBoard.getBoard());
                        if (obj.winner != ElState.E) {
                            System.out.println("rendering fragment(host)");
                            checkForWinner(obj.winner.toString());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
