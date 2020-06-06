package com.example.myapplication.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.ChartData.ChartData;
import com.example.myapplication.R;
import com.example.myapplication.Repository.DataBaseHelper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ChartFragment extends Fragment {
    private BarChart barChart;
    private ArrayList<BarEntry> barEntryArrayList;
    private ArrayList<String> labelsNames;
    private ArrayList<ChartData> chartDataArrayList = new ArrayList<>();
    private DataBaseHelper mDatabaseHelper;
    private int crossCounter, zeroCounter, noWinCounter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.chart_fragment, container, false);
        mDatabaseHelper = new DataBaseHelper(getActivity());
        barChart = rootView.findViewById(R.id.barChart);
        barEntryArrayList = new ArrayList<>();
        labelsNames = new ArrayList<>();
        fillChart();
        for (int i = 0; i < chartDataArrayList.size(); i++) {
            String values = chartDataArrayList.get(i).getValues();
            int sales = chartDataArrayList.get(i).getSales();
            barEntryArrayList.add(new BarEntry(i, sales));
            labelsNames.add(values);
        }
        BarDataSet barDataSet = new BarDataSet(barEntryArrayList, "Tic-Tac-Toe winRate");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barChart.getDescription().setEnabled(false);
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barData.setBarWidth(0.9f);
        barChart.setFitBars(true);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelsNames));
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labelsNames.size());
        barChart.animateY(2000);
        barChart.invalidate();

        return rootView;
    }
    private void fillChart() {
        ArrayList<String> listData = new ArrayList<>();
        Cursor data1 = mDatabaseHelper.getWinnerData();
        while(data1.moveToNext()){
            listData.add(data1.getString(1));
        }
        for (int i = 0; i < listData.size(); i++) {
            if (listData.get(i).equals("Cross win"))
                crossCounter += 1;
            if (listData.get(i).equals("Zero win"))
                zeroCounter += 1;
            if (listData.get(i).equals("No win"))
                noWinCounter += 1;
        }
        System.out.println(crossCounter);
        System.out.println(zeroCounter);
        System.out.println(noWinCounter);
        chartDataArrayList.add(new ChartData("Cross", crossCounter));
        chartDataArrayList.add(new ChartData("Zero", zeroCounter));
        chartDataArrayList.add(new ChartData("No win", noWinCounter));
    }
}


