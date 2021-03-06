package com.example.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.myapplication.Fragments.CreateRoomFragment;
import com.example.myapplication.Fragments.MultiPlayerFragment;
import com.example.myapplication.Fragments.Play3DFragment;
import com.example.myapplication.Fragments.StatisticFragment;
import com.example.myapplication.Fragments.WinnerDialogFragment;
import com.example.myapplication.Model.ElState;
import com.example.myapplication.Fragments.ChartFragment;
import com.example.myapplication.Fragments.CameraDialogFragment;
import com.example.myapplication.Fragments.CameraPlayFragment;
import com.example.myapplication.Fragments.DialogFragment;
import com.example.myapplication.Fragments.FunPlayFragment;
import com.example.myapplication.Fragments.MainMenuFragment;
import com.example.myapplication.Fragments.PlayFragment;
import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.scichart.charting.visuals.SciChartSurface;
import com.scichart.extensions.builders.SciChartBuilder;
import com.scichart.extensions3d.builders.SciChart3DBuilder;

public class MainActivity extends AppCompatActivity implements DialogFragment.FragmentAListener,
        PlayFragment.FragmentBListener, PlayFragment.FragmentPlayListener,
        WinnerDialogFragment.WinnerDialogFragmentListener, FunPlayFragment.onFunPlayFragmentListener,
        CameraDialogFragment.onCameraDialogFragmentListener, CameraPlayFragment.onCameraPlayFragmentListener,
        FunPlayFragment.onFunPlayFragmentListenerA, MultiPlayerFragment.onMultiPlayerFragmentListener, DialogFragment.LoadStatusFragmentListener, DialogFragment.LoadPlay3DFragmentListener {
    private DialogFragment dialogFragment;
    private CreateRoomFragment createRoomFragment;
    private PlayFragment playFragment;
    private StatisticFragment statisticFragment;
    private CameraPlayFragment cameraPlayFragment;
    private CameraDialogFragment cameraDialogFragment;
    private Play3DFragment play3DFragment;
    private ElState firstPlayer = ElState.E;
    private SensorManager sm;
    private boolean checker = false;
    private String name1;
    private String name2;
    private Animation bottomAnimation;

    private float acelVal;
    private float acelLast;
    private float shake;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        try {
            SciChartSurface.setRuntimeLicenseKey("");
        } catch (Exception e) {
            e.printStackTrace();
        }
        SciChartBuilder.init(this);
        SciChart3DBuilder.init(this);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        bottomNav.setAnimation(bottomAnimation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new MainMenuFragment()).commit();
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sm.registerListener(sensorEventListener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;
    }

    private final SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            acelLast = acelVal;
            acelVal = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = acelVal - acelLast;
            shake = shake * 0.9f + delta;
            if (shake > 12 && playFragment != null && playFragment.isVisible())
            {
                playFragment = new PlayFragment(firstPlayer);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_container, playFragment, "PLAY_FRAGMENT")
                        .commit();
                Toast toast = Toast.makeText(getApplicationContext(), "You clean the board", Toast.LENGTH_LONG);
                toast.show();
            }
            if (shake > 12 && cameraPlayFragment != null && cameraPlayFragment.isVisible()) {
                cameraPlayFragment = new CameraPlayFragment(checker);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_container, cameraPlayFragment, "CAMERA_PLAY_FRAGMENT")
                        .commit();
                Toast toast = Toast.makeText(getApplicationContext(), "You clean the board", Toast.LENGTH_LONG);
                toast.show();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.navigation_play:
                        FragmentManager fm = getSupportFragmentManager();
                        DialogFragment dialogFragment = new DialogFragment();
                        dialogFragment.show(fm, "Sample Fragment");
                        selectedFragment = dialogFragment;
                        transactionFunction(selectedFragment);
                        break;
                    case R.id.navigation_chart:
                        selectedFragment = new ChartFragment();
                        transactionFunction(selectedFragment);
                        break;
                    case R.id.navigation_online:
                        selectedFragment = new MultiPlayerFragment();
                        transactionFunction(selectedFragment);
                        break;
                    case R.id.navigation_funPlay:
                        checker = true;
                        selectedFragment = new FunPlayFragment();
                        transactionFunction(selectedFragment);
                        break;
                }
            return true;
            };

    @Override
    public void onInputBSent(CharSequence input) {
        dialogFragment.updateEditText(input);
    }

    public void transactionFunction(Fragment selectedFragment)
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                selectedFragment).commit();
    }

    @Override
    public void onInputASent(CharSequence input) {
        if (input == "X")
        {
            firstPlayer = ElState.X;
        }
        else
        {
            firstPlayer = ElState.O;
        }
        playFragment = new PlayFragment(firstPlayer);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_container, playFragment, "PLAY_FRAGMENT")
                .commit();
    }

    @Override
    public void onInputPlaySent(ElState input, int checkForWinnerFragment) {
        FragmentManager fm =  getSupportFragmentManager();
        com.example.myapplication.Fragments.WinnerDialogFragment winnerDialogFragment = new WinnerDialogFragment(checkForWinnerFragment);
        winnerDialogFragment.show(fm, "Sample Fragment");
        winnerDialogFragment.updateData(input);
    }

    @Override
    public void onWinnerDialogFragmentSent(ElState input) {
        playFragment.sendData(input);
    }

    @Override
    public void loadDialogPlayFragment() {
        cameraDialogFragment = new CameraDialogFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_container, cameraDialogFragment)
                .commit();
    }

    @Override
    public void loadCameraPlayFragment(boolean checker) {
        cameraPlayFragment = new CameraPlayFragment(checker);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_container, cameraPlayFragment)
                .commit();
    }

    @Override
    public void loadWinnerDialogFragment(ElState winnerCheckVar, int checkForWinnerFragment) {
        FragmentManager fm =  getSupportFragmentManager();
        com.example.myapplication.Fragments.WinnerDialogFragment winnerDialogFragment = new WinnerDialogFragment(checkForWinnerFragment);
        winnerDialogFragment.show(fm, "Sample Fragment");
        winnerDialogFragment.updateData(winnerCheckVar);
    }

    public String getName1() {
        return name1;
    }

    public String getName2() {
        return name2;
    }

    public void assignFirstName(String name) {
        name1 = name;
    }

    public void assignSecondName(String name) {
        name2 = name;
    }

    @Override
    public void sendData(String firstName, String secondName) {
        assignFirstName(firstName);
        assignSecondName(secondName);
    }

    @Override
    public void loadCreateRoomFragment() {
        createRoomFragment = new CreateRoomFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_container, createRoomFragment)
                .commit();
    }

    @Override
    public void loadStatusFragment() {
        statisticFragment = new StatisticFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_container, statisticFragment)
                .commit();
    }

    @Override
    public void loadPlay3DFragment() {
        play3DFragment = new Play3DFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_container, play3DFragment)
                .commit();
    }
}