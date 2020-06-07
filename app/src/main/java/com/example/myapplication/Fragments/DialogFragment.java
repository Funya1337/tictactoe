package com.example.myapplication.Fragments;

import android.content.Context;
import android.graphics.Color;
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
    private LoadStatusFragmentListener loadStatusFragmentListener;
    private LoadPlay3DFragmentListener loadPlay3DFragmentListener;
    private Button playerButton1;
    private Button statusBtn;
    private Button play3DBtn;

    public interface FragmentAListener {
        void onInputASent(CharSequence input);
    }

    public interface LoadPlay3DFragmentListener {
        void loadPlay3DFragment();
    }

    public interface LoadStatusFragmentListener {
        void loadStatusFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_fragment, container, false);
        playerButton1 = rootView.findViewById(R.id.button_player1);
        statusBtn = rootView.findViewById(R.id.statusBtn);
        final Button playerButton2 = rootView.findViewById(R.id.button_player2);
        final TextView warningText = rootView.findViewById(R.id.warning_text);
        play3DBtn = rootView.findViewById(R.id.PlayBtn3D);
        Button dismiss = rootView.findViewById(R.id.apply);
        playerButton1.setText("Choose");
        playerButton2.setText("Choose");
        dismiss.setOnClickListener(v -> {
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
        });
        playerButton1.setOnClickListener(v -> {
            playerButton1.setBackgroundColor(Color.parseColor("#6200EE"));
            playerButton2.setBackgroundColor(Color.parseColor("#6200EE"));
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
        });
        playerButton2.setOnClickListener(v -> {
            playerButton1.setBackgroundColor(Color.parseColor("#6200EE"));
            playerButton2.setBackgroundColor(Color.parseColor("#6200EE"));
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
        });
        play3DBtn.setOnClickListener(v -> loadPlay3DFragmentListener.loadPlay3DFragment());
        statusBtn.setOnClickListener(v -> loadStatusFragmentListener.loadStatusFragment());
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
            loadStatusFragmentListener = (LoadStatusFragmentListener) context;
            loadPlay3DFragmentListener = (LoadPlay3DFragmentListener) context;
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
