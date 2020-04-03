package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myapplication.Classes.ElState;
import com.example.myapplication.Components.BuildLevelFragment;
import com.example.myapplication.Components.CameraDialogFragment;
import com.example.myapplication.Components.CameraPlayFragment;
import com.example.myapplication.Components.DialogFragment;
import com.example.myapplication.Components.FunPlayFragment;
import com.example.myapplication.Components.MainMenuFragment;
import com.example.myapplication.Components.PlayFragment;
import com.example.myapplication.Components.PlayWithBotFragment;
import com.example.myapplication.Components.WinnerDialogFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements DialogFragment.FragmentAListener, PlayFragment.FragmentBListener, PlayFragment.FragmentPlayListener, WinnerDialogFragment.WinnerDialogFragmentListener, FunPlayFragment.onFunPlayFragmentListener, CameraDialogFragment.onCameraDialogFragmentListener {
    private DialogFragment dialogFragment;
    private PlayFragment playFragment;
    private CameraPlayFragment cameraPlayFragment;
    private CameraDialogFragment cameraDialogFragment;
    private ElState firstPlayer = ElState.E;
    private SensorManager sm;
    private boolean checker = false;

    private float acelVal;
    private float acelLast;
    private float shake;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
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
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.navigation_main:
                            selectedFragment = new MainMenuFragment();
                            transactionFunction(selectedFragment);
                            break;
                        case R.id.navigation_play:
                            FragmentManager fm = getSupportFragmentManager();
                            DialogFragment dialogFragment = new DialogFragment();
                            dialogFragment.show(fm, "Sample Fragment");
                            selectedFragment = dialogFragment;
                            transactionFunction(selectedFragment);
                            break;
                        case R.id.navigation_build:
                            selectedFragment = new BuildLevelFragment();
                            transactionFunction(selectedFragment);
                            break;
                        case R.id.navigation_botPlay:
                            selectedFragment = new PlayWithBotFragment();
                            transactionFunction(selectedFragment);
                            break;
                        case R.id.navigation_funPlay:
                            checker = true;
                            selectedFragment = new FunPlayFragment();
                            transactionFunction(selectedFragment);
                            break;
                    }
                return true;
                }
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
    public void onInputPlaySent(ElState input) {
        FragmentManager fm =  getSupportFragmentManager();
        com.example.myapplication.Components.WinnerDialogFragment winnerDialogFragment = new WinnerDialogFragment();
        winnerDialogFragment.show(fm, "Sample Fragment");
        winnerDialogFragment.updateData(input);
    }

    @Override
    public void onWinnerDialogFragmentSent(ElState input) {
        playFragment.sendData(input);
    }

    @Override
    public void loadDialogPlayFragment() {
        System.out.println("11111");
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
}