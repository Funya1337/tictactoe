package com.example.myapplication.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.myapplication.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class BuildLevelFragment extends Fragment {
    private BarChart barChart;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.build_level_fragment, container, false);
        barChart = rootView.findViewById(R.id.barChart);
        List<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(200, 100));
        barEntries.add(new BarEntry(201, 138));
        barEntries.add(new BarEntry(202, 173));
        barEntries.add(new BarEntry(203, 202));
        barEntries.add(new BarEntry(204, 223));
        barEntries.add(new BarEntry(205, 304));
        BarDataSet barDataSet = new BarDataSet(barEntries, "Growth");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.9f);
        barChart.setVisibility(View.VISIBLE);
        barChart.animateY(5000);
        barChart.setData(barData);
        barChart.setFitBars(true);
        Description description = new Description();
        description.setText("Growth Rate per year");
        return rootView;
    }
}


