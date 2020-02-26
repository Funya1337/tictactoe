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

import com.example.myapplication.components.DialogFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements DialogFragment.FragmentAListener, PlayFragment.FragmentBListener {
    private DialogFragment dialogFragment;
    private PlayFragment playFragment;
    private ElState firstPlayer = ElState.E;

    Board board = new Board();

    private SensorManager sm;

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
            acelVal = (float) Math.sqrt((double) (x*x + y*y + z*z ));
            float delta = acelVal - acelLast;
            shake = shake * 0.9f + delta;
            if (shake > 12 && playFragment != null)
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
                            break;
                        case R.id.navigation_play:
                            FragmentManager fm =  getSupportFragmentManager();
                            DialogFragment dialogFragment = new DialogFragment();
                            dialogFragment.show(fm, "Sample Fragment");
                            selectedFragment = dialogFragment;
                            break;
                        case R.id.navigation_build:
                            selectedFragment = new BuildLevelFragment();
                            break;
                        case R.id.navigation_botPlay:
                            selectedFragment = new PlayWithBotFragment();
                            break;
                        case R.id.navigation_funPlay:
                            selectedFragment = new FunPlayFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                            selectedFragment).commit();
                return true;
                }
            };

    @Override
    public void onInputBSent(CharSequence input) {
        dialogFragment.updateEditText(input);
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
}