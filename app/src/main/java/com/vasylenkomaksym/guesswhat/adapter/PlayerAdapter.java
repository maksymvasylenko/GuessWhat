package com.vasylenkomaksym.guesswhat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vasylenkomaksym.guesswhat.R;
import com.vasylenkomaksym.guesswhat.model.DataProvider;

/**
 *
 */
public class PlayerAdapter extends ArrayAdapter<String> {

    /* Set up singleton model */
    DataProvider dataProvider = null;

    /* Set up adapter, passing singleton instance with players */
    public PlayerAdapter(Context context) {
        super(context, 0, DataProvider.getInstance().getPlayers());
        dataProvider = DataProvider.getInstance();
    }

    /* Inflating view for player list */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Standard list adapter functions
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.player_list_item2, parent, false);
        }

        // Defining textViews and filling with data
        TextView tvPlayerName = (TextView) convertView.findViewById(R.id.tv_player);
        TextView tvPlayerScore = (TextView) convertView.findViewById(R.id.tv_score);
        tvPlayerScore.setText(Integer.toString(dataProvider.getPoint(position)));
        tvPlayerName.setText(dataProvider.getPlayer(position));

        return convertView;
    }

}
