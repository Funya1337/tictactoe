package com.example.myapplication.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.Activities.MainActivity;
import com.example.myapplication.Model.ElState;
import com.example.myapplication.R;

public class WinnerDialogFragment extends androidx.fragment.app.DialogFragment {
    private WinnerDialogFragmentListener winnerDialogFragmentListener;
    private int mainChecker;
    private ElState data;

    public WinnerDialogFragment(int checkForWinnerFragment) {
        mainChecker = checkForWinnerFragment;
    }

    public interface WinnerDialogFragmentListener {
        void onWinnerDialogFragmentSent(ElState input);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.winner_dialog_fragment, container, false);
        TextView winnerText = rootView.findViewById(R.id.winner_text);
        String name1 = ((MainActivity) getActivity()).getName1();
        String name2 = ((MainActivity) getActivity()).getName2();
        if (data == ElState.X && mainChecker == 0)
        {
            winnerText.setText("CROSS WIN");
        }
        else if (data == ElState.O && mainChecker == 0)
        {
            winnerText.setText("ZERO WIN");
        }
        else if (data == ElState.N && mainChecker == 0)
        {
            winnerText.setText("NO WIN");
        }
        if (data == ElState.X && mainChecker == 1)
        {
            winnerText.setText(name1 + " WIN");
        }
        else if (data == ElState.O && mainChecker == 1)
        {
            winnerText.setText(name2 + " WIN");
        }
        else if (data == ElState.N && mainChecker == 1)
        {
            winnerText.setText("NO WIN");
        }
        return rootView;
    }
    public void updateData(ElState newData) {
        data = newData;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof WinnerDialogFragment.WinnerDialogFragmentListener) {
            winnerDialogFragmentListener = (WinnerDialogFragment.WinnerDialogFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentBListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        winnerDialogFragmentListener = null;
    }
}
