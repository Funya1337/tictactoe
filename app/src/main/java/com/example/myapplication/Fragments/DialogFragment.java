package com.example.myapplication.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;

public class DialogFragment extends androidx.fragment.app.DialogFragment {
    public int checker = 10;
    private FragmentAListener listener;
    private Button playerButton1;
    private Button statusBtn;

    public interface FragmentAListener {
        void onInputASent(CharSequence input);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_fragment, container, false);
        playerButton1 = rootView.findViewById(R.id.button_player1);
        statusBtn = rootView.findViewById(R.id.statusBtn);
        final Button playerButton2 = rootView.findViewById(R.id.button_player2);
        final TextView warningText = rootView.findViewById(R.id.warning_text);
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
                    CharSequence input = playerButton1.getText();
                    listener.onInputASent(input);
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
                    checker = 1;
                    playerButton2.setText("0" + "");
                }
                if (playerButton1.getText() == "X" && playerButton2.getText() == "0")
                {
                    playerButton1.setText("0" + "");
                    checker = 0;
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
                    checker = 0;
                    playerButton2.setText("X" + "");
                }
                if (playerButton1.getText() == "0" && playerButton2.getText() == "X")
                {
                    playerButton1.setText("X" + "");
                    checker = 1;
                    playerButton2.setText("0" + "");
                }
            }
        });
        statusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return rootView;
    }
    public void updateEditText(CharSequence newText) {
        playerButton1.setText(newText);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentAListener) {
            listener = (FragmentAListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement fragmentAListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
