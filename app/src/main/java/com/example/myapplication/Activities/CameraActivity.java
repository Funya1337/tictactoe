package com.example.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Classes.DataBaseHelper;
import com.example.myapplication.Components.CameraDialogFragment;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class CameraActivity extends AppCompatActivity {
    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;

    private CameraDialogFragment cameraDialogFragment;
    private Button btnAdd, btnViewData;
    private EditText editText;
    Button mCaptureBtn, mPlayBtn;
    Button mGetDataBtn;
    ImageView mImageView;
    TextView mShowDataBaseData;
    EditText mEditText;
    DataBaseHelper mDatabaseHelper;

    Uri image_uri;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
//        btnAdd = findViewById(R.id.button_saveData);
//        btnViewData = findViewById(R.id.button_getData);
//        editText = findViewById(R.id.editTextData);
        mImageView = findViewById(R.id.image_view);
        mCaptureBtn = findViewById(R.id.button_capture);
        mPlayBtn = findViewById(R.id.button_play);
//        mGetDataBtn = findViewById(R.id.button_getData);
//        mEditText = findViewById(R.id.editTextData);
//        mShowDataBaseData = findViewById(R.id.text1);
        mDatabaseHelper = new DataBaseHelper(this);
        mCaptureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
                    {
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission, PERMISSION_CODE);
                    }
                    else
                    {
                        openCamera();
                    }
                }
                else
                {
                    openCamera();
                }
            }
        });
        mPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CameraActivity.this, CameraActivityForFragments.class);
                startActivity(intent);
            }
        });
//        btnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String newEntry = editText.getText().toString();
//                if (editText.length() != 0) {
//                    AddData(newEntry);
//                    editText.setText("");
//                } else {
//                    toastMessage("You must put something in the text field!");
//                }
//
//            }
//        });
//
//        btnViewData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                monitorData();
//                Intent intent = new Intent(CameraActivity.this, ListDataActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case PERMISSION_CODE:
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    openCamera();
                }
                else
                {
                    Toast.makeText(this, "PERMISSION DENIED", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            AddData(image_uri.toString());
            mImageView.setImageURI(Uri.parse(monitorData()));
        }
    }
    public void AddData(String newEntry) {
        boolean insertData = mDatabaseHelper.addData(newEntry);

        if (insertData) {
            toastMessage("Data Successfully Inserted!");
        } else {
            toastMessage("Something went wrong");
        }
    }
    public String monitorData() {
        ArrayList<String> listData = new ArrayList<>();
        Cursor data = mDatabaseHelper.getData();
        while(data.moveToNext()){
            listData.add("\n" + data.getString(1));
        }
        return listData.get(0);
    }
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
