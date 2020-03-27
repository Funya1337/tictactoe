package com.example.myapplication.Components;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.Classes.DataBaseHelper;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class CameraDialogFragment extends Fragment {

    private static final int PERMISSION_CODE = 1000;
    private ImageView image1, image2;
    private DataBaseHelper mDatabaseHelper;
    private Button playBtn;
    private String lastEl;
    private String preLastEl;
    private boolean checker = false;
    private boolean checkToLoadFragment = false;
    private Button changeValuesBtn;

    public interface onCameraDialogListener {
        public void loadPlayFragmentEvent(boolean checker);
    }

    onCameraDialogListener cameraDialogListener;

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        try {
            cameraDialogListener = (onCameraDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.camera_dialog_fragment, container, false);
        image1 = rootView.findViewById(R.id.image1);
        image2 = rootView.findViewById(R.id.image2);
        playBtn = rootView.findViewById(R.id.applyBtn);
        changeValuesBtn = rootView.findViewById(R.id.changeBtn);
        mDatabaseHelper = new DataBaseHelper(getActivity().getApplicationContext());
        ArrayList<String> listData = new ArrayList<>();
        Cursor data1 = mDatabaseHelper.getData();
        while(data1.moveToNext()){
            listData.add(data1.getString(1));
        }
        lastEl = listData.get(listData.size() - 1);
        preLastEl = listData.get(listData.size() - 2);
        setImage1();
        changeValuesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checker = !checker;
                if (checker)
                {
                    setImage2();
                }
                if (!checker)
                {
                    setImage1();
                }
            }
        });
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraDialogListener.loadPlayFragmentEvent(checker);
            }
        });
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
