package com.example.myapplication.checkmate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

public class FragmentCheckmate extends Fragment {


    public FragmentCheckmate() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_checkmate, container, false);

        Button postLeaderBoard = view.findViewById(R.id.PostLeader);
        postLeaderBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Leaderboard coming soon!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }



}