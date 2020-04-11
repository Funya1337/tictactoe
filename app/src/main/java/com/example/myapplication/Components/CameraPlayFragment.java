package com.example.myapplication.Components;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.Model.Board;
import com.example.myapplication.Repository.DataBaseHelper;
import com.example.myapplication.Model.ElState;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CameraPlayFragment extends Fragment {
    private ElState turn = ElState.X;
    private ElState winnerCheckVar = ElState.E;
    private DataBaseHelper mDatabaseHelper;
    private String lastEl;
    private String preLastEl;
    private boolean checkerInFragment;

    final Board newBoard = new Board();

    private void nextTurnForBoard() {
        turn = turn == ElState.X ? ElState.O : ElState.X;
    }

    private void nextTurn()
    {
        checkerInFragment = !checkerInFragment;
    }

    public interface onCameraPlayFragmentListener {
        void loadWinnerDialogFragment(ElState winnerCheckVar);
    }

    CameraPlayFragment.onCameraPlayFragmentListener onCameraPlayFragmentListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            onCameraPlayFragmentListener = (CameraPlayFragment.onCameraPlayFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onSomeEventListener");
        }
    }

    private String getTurnImage() {
        mDatabaseHelper = new DataBaseHelper(getActivity().getApplicationContext());
        ArrayList<String> listData = new ArrayList<>();
        Cursor data1 = mDatabaseHelper.getData();
        while(data1.moveToNext()){
            listData.add(data1.getString(1));
        }
        lastEl = listData.get(listData.size() - 1);
        preLastEl = listData.get(listData.size() - 2);
        return checkerInFragment ? preLastEl : lastEl;
    }

    public CameraPlayFragment(boolean checker) {
        checkerInFragment = checker;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.camera_play_fragment, container, false);
        Button button = rootView.findViewById(R.id.button_reset);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                newBoard.clearBoard();
                newBoard.print();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(CameraPlayFragment.this).attach(CameraPlayFragment.this).commit();
            }
        });
        for (int i = 0; i < newBoard.boardSize; i++) {
            for (int j=0; j< newBoard.boardSize; j++) {
                final int indexI = i;
                final int indexJ = j;
                String buttonId = "button_" + indexI + "_" + indexJ;
                final ImageView imageView = rootView.findViewById(getResources().getIdentifier(buttonId, "id", getActivity().getPackageName()));
                imageView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Animation animationUtils = AnimationUtils.loadAnimation(getContext(), R.anim.shake_animation);
                        imageView.startAnimation(animationUtils);
                        newBoard.setElement(indexI, indexJ, turn);
                        newBoard.print();
                        if (newBoard.checkForWinner() == ElState.X) {
                            winnerCheckVar = ElState.X;
                            System.out.println(winnerCheckVar + " IS WINNER");
                            onCameraPlayFragmentListener.loadWinnerDialogFragment(winnerCheckVar);
                        }
                        if (newBoard.checkForWinner() == ElState.O) {
                            winnerCheckVar = ElState.O;
                            System.out.println(winnerCheckVar + " IS WINNER");
                            onCameraPlayFragmentListener.loadWinnerDialogFragment(winnerCheckVar);
                        }
                        if (newBoard.checkForWinner() == ElState.N) {
                            winnerCheckVar = ElState.N;
                            System.out.println(winnerCheckVar + " IS WINNER");
                            onCameraPlayFragmentListener.loadWinnerDialogFragment(winnerCheckVar);
                        }
                        Picasso.get()
                                .load(Uri.parse(getTurnImage()))
                                .resize(400, 400)
                                .into(imageView);
                        nextTurn();
                        nextTurnForBoard();
                        v.setClickable(false);
                        v.setEnabled(false);
                    }
                });
            }
        }
        return rootView;
    }
}
