package com.example.myapplication.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.Repository.DataBaseHelper;

import java.util.ArrayList;

public class StatisticFragment extends Fragment {
    private DataBaseHelper mDatabaseHelper;
    ListView listView;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.statistic_fragment, container, false);
        listView = rootView.findViewById(R.id.winners_list);
        mDatabaseHelper = new DataBaseHelper(getActivity());
        ArrayList<String> listData = new ArrayList<>();
        Cursor data1 = mDatabaseHelper.getWinnerData();
        while(data1.moveToNext()){
            listData.add(data1.getString(1));
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(arrayAdapter);
        return rootView;
    }
}
