package com.example.myapplication.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Activities.GameActivity;
import com.example.myapplication.Activities.MainActivity;
import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OnlineGameFragment extends Fragment {
    private String roomName;
    private Button button;
    private String playerName = "";
    private String role = "";
    private String message = "";
    public OnlineGameFragment(String roomName) {
        this.roomName = roomName;
    }

    private FirebaseDatabase database;
    private DatabaseReference messageRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.online_game_fragment, container, false);
        button = rootView.findViewById(R.id.button3);
        button.setEnabled(false);

        database = FirebaseDatabase.getInstance();

        SharedPreferences preferences = getActivity().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        playerName = preferences.getString("playerName", "");

        if (roomName.equals(playerName))
            role = "host";
        else
            role = "guest";

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // send message
                button.setEnabled(false);
                message = role + ":Poked!";
                messageRef.setValue(message);
            }
        });
        // listen for incoming messages
        messageRef = database.getReference("rooms/" + roomName + "/message");
        message = role + ":Poked!";
        messageRef.setValue(message);
        addRoomEventListener();
        return rootView;
    }
    private void addRoomEventListener() {
        messageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // message received
                if (role.equals("host")) {
                    if (dataSnapshot.getValue(String.class).contains("guest:")) {
                        button.setEnabled(true);
                        Toast.makeText(getActivity(), "" + dataSnapshot.getValue(String.class).replace("guest:", ""), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (dataSnapshot.getValue(String.class).contains("host:")) {
                        button.setEnabled(true);
                        Toast.makeText(getActivity(), "" + dataSnapshot.getValue(String.class).replace("host:", ""), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // error - retry
                messageRef.setValue(message);
            }
        });
    }
}
