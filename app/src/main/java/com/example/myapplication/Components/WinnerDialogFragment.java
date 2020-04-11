package com.example.myapplication.Components;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.Model.ElState;
import com.example.myapplication.R;

public class WinnerDialogFragment extends androidx.fragment.app.DialogFragment {
    private WinnerDialogFragmentListener winnerDialogFragmentListener;
    private ElState data;

    public interface WinnerDialogFragmentListener {
        void onWinnerDialogFragmentSent(ElState input);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.winner_dialog_fragment, container, false);
        TextView winnerText = rootView.findViewById(R.id.winner_text);
        if (data == ElState.X)
        {
            winnerText.setText("CROSS WIN");
        }
        else if (data == ElState.O)
        {
            winnerText.setText("ZERO WIN");
        }
        else if (data == ElState.N)
        {
            winnerText.setText("NO WIN");
        }
        System.out.println(data);
//        Button dismiss = rootView.findViewById(R.id.apply);
//        dismiss.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
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
