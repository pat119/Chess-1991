package com.example.myapplication.leaderboard;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.myapplication.R;


import java.util.List;

public class LeaderboardAdapter extends ArrayAdapter<LeaderboardEntry> {
    public LeaderboardAdapter(Context context, List<LeaderboardEntry> entries) {
        super(context, 0, entries);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Typeface font = Typeface.createFromAsset(getContext().getAssets(), "jersey25.ttf");
        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.leaderboard_entry_item, parent, false);
        }

        LeaderboardEntry entry = getItem(position);

        TextView textViewName = itemView.findViewById(R.id.textViewName);
        TextView textViewScore = itemView.findViewById(R.id.textViewScore);
        TextView textViewPlace = itemView.findViewById(R.id.textViewPlace);

        // Set the name and score in the layout
        if (entry != null) {
            textViewName.setText(entry.getName());
            textViewName.setTypeface(font);
            textViewScore.setText(String.valueOf(entry.getScore()));
            textViewScore.setTypeface(font);
            textViewPlace.setText(String.valueOf(position + 1));
            textViewPlace.setTypeface(font);
        }

        return itemView;
    }
}

