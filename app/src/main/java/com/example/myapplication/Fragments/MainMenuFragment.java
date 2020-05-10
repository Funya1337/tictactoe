package com.example.myapplication.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

public class MainMenuFragment extends Fragment {
    private View first, second, third, fourth, fifth, sixth;
    private TextView tagLine;
    private ImageView logo;

    private Animation topAnimation, bottomAnimation, middleAnimation;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.main_menu_fragment, container, false);
        topAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.bottom_animation);
        middleAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.middle_animation);

        first = rootView.findViewById(R.id.first_line);
        second = rootView.findViewById(R.id.second_line);
        third = rootView.findViewById(R.id.third_line);
        fourth = rootView.findViewById(R.id.fourth_line);
        fifth = rootView.findViewById(R.id.fifth_line);
        sixth = rootView.findViewById(R.id.sixth_line);

        logo = rootView.findViewById(R.id.logo);
        tagLine = rootView.findViewById(R.id.tagLine);

        first.setAnimation(topAnimation);
        second.setAnimation(topAnimation);
        third.setAnimation(topAnimation);
        fourth.setAnimation(topAnimation);
        fifth.setAnimation(topAnimation);
        sixth.setAnimation(topAnimation);

        logo.setAnimation(middleAnimation);
        tagLine.setAnimation(bottomAnimation);
        return rootView;
    }
}
