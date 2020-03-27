package com.example.myapplication.Components;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.Classes.Board;
import com.example.myapplication.Classes.DataBaseHelper;
import com.example.myapplication.Classes.ElState;
import com.example.myapplication.R;

import java.util.ArrayList;

public class CameraPlayFragment extends Fragment {
    ElState turn = ElState.X;
    private ElState firstPlayer = ElState.E;
    ElState winnerCheckVar = ElState.E;
    private DataBaseHelper mDatabaseHelper;
    private String lastEl;
    private String preLastEl;
    private boolean checkerInFragment;

    final Board newBoard = new Board();

    private void nextTurn() {
        turn = turn == ElState.X ? ElState.O : ElState.X;
    }

    private String getTurnText() {
        return turn == ElState.X ? "X" : "O";
    }

    public CameraPlayFragment(boolean checker) {
        checkerInFragment = checker;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.camera_play_fragment, container, false);
        getData();
        for (int i = 0; i < newBoard.boardSize; i++) {
            for (int j=0; j< newBoard.boardSize; j++) {
                final int indexI = i;
                final int indexJ = j;
                String buttonId = "button_" + indexI + "_" + indexJ;
                final Button button = rootView.findViewById(getResources().getIdentifier(buttonId, "id", getActivity().getPackageName()));
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        newBoard.setElement(indexI, indexJ, turn);
                        newBoard.print();
                        if (newBoard.checkForWinner() == ElState.X) {
                            winnerCheckVar = ElState.X;
                            System.out.println(winnerCheckVar + " IS WINNER");
                        }
                        if (newBoard.checkForWinner() == ElState.O) {
                            winnerCheckVar = ElState.O;
                            System.out.println(winnerCheckVar + " IS WINNER");
                        }
                        if (newBoard.checkForWinner() == ElState.N) {
                            winnerCheckVar = ElState.N;
                            System.out.println(winnerCheckVar + " IS WINNER");
                        }
                        button.setText(getTurnText());
                        nextTurn();
                        v.setClickable(false);
                        v.setEnabled(false);
                    }
                });
            }
        }
        return rootView;
    }
    public void getData()
    {
        mDatabaseHelper = new DataBaseHelper(getActivity().getApplicationContext());
        ArrayList<String> listData = new ArrayList<>();
        Cursor data1 = mDatabaseHelper.getData();
        while(data1.moveToNext()){
            listData.add(data1.getString(1));
        }
        lastEl = listData.get(listData.size() - 1);
        preLastEl = listData.get(listData.size() - 2);
        System.out.println(lastEl);
        System.out.println(preLastEl);
    }
}
