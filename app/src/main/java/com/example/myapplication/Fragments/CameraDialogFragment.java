package com.example.myapplication.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Activities.MainActivity;
import com.example.myapplication.Repository.DataBaseHelper;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CameraDialogFragment extends Fragment {

    private static final int PERMISSION_CODE = 1000;
    private ImageView image1, image2;
    private DataBaseHelper mDatabaseHelper;
    private TextView mPlayerTitle1, mPlayerTitle2;
    private Button playBtn;
    private String lastEl;
    private String preLastEl;
    private boolean checker = false;
    private Button changeValuesBtn;

    public interface onCameraDialogFragmentListener {
        void loadCameraPlayFragment(boolean checker);
    }

    CameraDialogFragment.onCameraDialogFragmentListener onFunPlayFragmentListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            onFunPlayFragmentListener = (CameraDialogFragment.onCameraDialogFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onSomeEventListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.camera_dialog_fragment, container, false);
        String name1 = ((MainActivity) getActivity()).getName1();
        String name2 = ((MainActivity) getActivity()).getName2();
        image1 = rootView.findViewById(R.id.image1);
        image2 = rootView.findViewById(R.id.image2);
        mPlayerTitle1 = rootView.findViewById(R.id.player1_title);
        mPlayerTitle2 = rootView.findViewById(R.id.player2_title);
        playBtn = rootView.findViewById(R.id.applyBtn);
        changeValuesBtn = rootView.findViewById(R.id.changeBtn);
        mPlayerTitle1.setText(name1);
        mPlayerTitle2.setText(name2);
        mDatabaseHelper = new DataBaseHelper(getActivity().getApplicationContext());
        ArrayList<String> listData = new ArrayList<>();
        Cursor data1 = mDatabaseHelper.getData();
        while(data1.moveToNext()){
            listData.add(data1.getString(1));
        }
        lastEl = listData.get(listData.size() - 1);
        preLastEl = listData.get(listData.size() - 2);
        setImage1();
        changeValuesBtn.setOnClickListener(v -> {
            checker = !checker;
            if (checker)
            {
                setImage2();
            }
            if (!checker)
            {
                setImage1();
            }
        });
        playBtn.setOnClickListener(v -> onFunPlayFragmentListener.loadCameraPlayFragment(checker));
        return rootView;
    }
    public void setImage1()
    {
        Picasso.get()
                .load(lastEl)
                .resize(100, 100)
                .into(image1);
        Picasso.get()
                .load(preLastEl)
                .resize(100, 100)
                .into(image2);
    }
    public void setImage2()
    {
        Picasso.get()
                .load(lastEl)
                .resize(100, 100)
                .into(image2);
        Picasso.get()
                .load(preLastEl)
                .resize(100, 100)
                .into(image1);
    }
}
