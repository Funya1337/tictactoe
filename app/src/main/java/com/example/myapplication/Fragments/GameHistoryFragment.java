package com.example.myapplication.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Repository.DataBaseHelper;
import com.example.myapplication.R;

public class GameHistoryFragment extends Fragment {
    private ListView listView;
    private DataBaseHelper mDatabaseHelper;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.game_history_fragment, container, false);
        mDatabaseHelper = new DataBaseHelper(getActivity());
//        listView = rootView.findViewById(R.id.winnerList);
//        ArrayList<String> listData = new ArrayList<>();
//        Cursor data1 = mDatabaseHelper.getGameHistoryData();
//        while(data1.moveToNext()){
//            listData.add(data1.getString(1));
//        }
//        System.out.println(listData);
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, listData);
//        listView.setAdapter(arrayAdapter);
        return rootView;
    }
}
