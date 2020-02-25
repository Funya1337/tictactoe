package com.example.myapplication.components;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.myapplication.ElState;
import com.example.myapplication.R;

public class DialogFragment extends androidx.fragment.app.DialogFragment {
    public ElState firstPlayer = ElState.E;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_fragment, container, false);
        final Button playerButton1 = rootView.findViewById(R.id.button_player1);
        final Button playerButton2 = rootView.findViewById(R.id.button_player2);
        final TextView warningText = rootView.findViewById(R.id.warning_text);

        getDialog().setTitle("Simple Dialog");
        Button dismiss = rootView.findViewById(R.id.apply);
        playerButton1.setText("Choose");
        playerButton2.setText("Choose");
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playerButton1.getText() == "Choose" && playerButton2.getText() == "Choose")
                {
                    warningText.setText("Please fill the fields");
                }
                else
                {
                    dismiss();
                }
            }
        });
        playerButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playerButton1.getText() != "X" && playerButton2.getText() != "X")
                {
                    playerButton1.setText("X" + "");
                    firstPlayer = ElState.X;
                    playerButton2.setText("0" + "");
                }
                if (playerButton1.getText() == "X" && playerButton2.getText() == "0")
                {
                    playerButton1.setText("0" + "");
                    firstPlayer = ElState.O;
                    playerButton2.setText("X" + "");
                }
            }
        });
        playerButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playerButton1.getText() != "X" && playerButton2.getText() != "X")
                {
                    playerButton1.setText("0" + "");
                    firstPlayer = ElState.O;
                    playerButton2.setText("X" + "");
                }
                if (playerButton1.getText() == "0" && playerButton2.getText() == "X")
                {
                    playerButton1.setText("X" + "");
                    firstPlayer = ElState.X;
                    playerButton2.setText("0" + "");
                }
            }
        });
        return rootView;
    }
}
