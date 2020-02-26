package com.example.myapplication;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.components.DialogFragment;

public class PlayFragment extends Fragment {
    ElState turn = ElState.X;
    private FragmentBListener listener;
    private ElState firstPlayer = ElState.E;

    public PlayFragment(ElState fp) {
        firstPlayer = fp;
        turn = firstPlayer;
    }

    public interface FragmentBListener {
        void onInputBSent(CharSequence input);
    }

    public void setFirstPlayer (CharSequence data)
    {
        if (data == "X")
        {
            firstPlayer = ElState.X;
        }
        else
        {
            firstPlayer = ElState.O;
        }
        turn = firstPlayer;
    }

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
                                turn = firstPlayer;
                                newBoard.clearBoard();
                                newBoard.print();
                                FragmentTransaction ft = getFragmentManager().beginTransaction();
                                ft.detach(PlayFragment.this).attach(PlayFragment.this).commit();
                            }
                        });
                        Button button_change = view.findViewById(R.id.button_change);
                        button_change.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                System.out.println("you click to the on click listener");
                                Fragment selectedFragment = null;
                                FragmentManager fm =  getFragmentManager();
                                DialogFragment dialogFragment = new DialogFragment();
                                dialogFragment.show(fm, "Sample Fragment");
                                selectedFragment = dialogFragment;
                            }
                        });
                    }
                });
            }
        }
        return view;
    }
    public void updateEditText(CharSequence newText) {
        setFirstPlayer(newText);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentBListener) {
            listener = (FragmentBListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentBListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
