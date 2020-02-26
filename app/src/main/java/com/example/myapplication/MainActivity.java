package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.myapplication.components.DialogFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements DialogFragment.FragmentAListener, PlayFragment.FragmentBListener {
    private DialogFragment dialogFragment;
    private PlayFragment playFragment;
    private ElState firstPlayer = ElState.E;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new MainMenuFragment()).commit();
    }
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
//                    playFragment = new PlayFragment();
                return true;
                }
            };

    @Override
    public void onInputBSent(CharSequence input) {
        dialogFragment.updateEditText(input);
    }

    @Override
    public void onInputASent(CharSequence input) {
//        playFragment.updateEditText(input);
        if (input == "X")
        {
            firstPlayer = ElState.X;
        }
        else
        {
            firstPlayer = ElState.O;
        }
        playFragment = new PlayFragment(firstPlayer);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                playFragment).commit();
    }
}