package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class PlayFragment extends Fragment {

    ElState turn = ElState.X;
    final Board newBoard = new Board();

    private void nextTurn() {
        turn = turn == ElState.X ? ElState.O : ElState.X;
    }

    private String getTurnText() {
        return turn == ElState.X ? "X" : "O";
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.play_fragment, container, false);
        for (int i = 0; i < newBoard.boardSize; i++) {
            for (int j=0; j< newBoard.boardSize; j++) {
                final int indexI = i;
                final int indexJ = j;
                String buttonId = "button_" + indexI + "_" + indexJ;
                final Button button = view.findViewById(getResources().getIdentifier(buttonId, "id", getActivity().getPackageName()));
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        newBoard.setElement(indexI, indexJ, turn);
                        newBoard.print();
                        TextView winnerText = view.findViewById(R.id.winner);
                        if (newBoard.checkForWinner() == ElState.X) {
                            winnerText.setText("CROSS WIN");
                        }
                        if (newBoard.checkForWinner() == ElState.O) {
                            winnerText.setText("ZERO WIN");
                        }
                        if (newBoard.checkForWinner() == ElState.N) {
                            winnerText.setText("NO WIN");
                        }
                        button.setText(getTurnText());
                        nextTurn();
                        v.setClickable(false);
                        v.setEnabled(false);
                        Button button = view.findViewById(R.id.button_reset);
                        button.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                newBoard.clearBoard();
                                newBoard.print();
                                FragmentTransaction ft = getFragmentManager().beginTransaction();
                                ft.detach(PlayFragment.this).attach(PlayFragment.this).commit();
                            }
                        });
                    }
                });
            }
        }
        return view;
    }
}
