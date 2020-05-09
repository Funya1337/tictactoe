package com.example.myapplication.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.myapplication.Activities.MainActivity;
import com.example.myapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OnlineWinnerDialog extends DialogFragment {
    private String winner;
    private TextView textView;
    private Button againBtn, exitBtn;

    private FirebaseDatabase database;
    private DatabaseReference boardRef;

    public OnlineWinnerDialog(String winner) {
        this.winner = winner;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//        View view = inflater.inflate(R.layout.online_winner_dialog, null);
//        textView = view.findViewById(R.id.winner_title);
//        againBtn = view.findViewById(R.id.againBtn);
//        exitBtn = view.findViewById(R.id.exitBtn);
//        exitBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), MainActivity.class);
//                startActivity(intent);
//            }
//        });
//        if (winner.equals("X"))
//            textView.setText("Cross win");
//        if (winner.equals("O"))
//            textView.setText("Zero win");
//        if (winner.equals("N"))
//            textView.setText("No win");
//        builder.setView(view);
        return builder.create();
    }
}
