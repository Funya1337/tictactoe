package com.example.myapplication.Activities;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Classes.DataBaseHelper;
import com.example.myapplication.Components.CameraDialogFragment;
import com.example.myapplication.R;

public class CameraActivityForFragments extends AppCompatActivity {
    private CameraDialogFragment cameraDialogFragment;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_activity_for_fragments);
        cameraDialogFragment = new CameraDialogFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.camera_frame_container, cameraDialogFragment)
                .commit();
    }
}
