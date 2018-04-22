package com.vasylenkomaksym.guesswhat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vasylenkomaksym.guesswhat.DataProvider;
import com.vasylenkomaksym.guesswhat.R;

/**
 * Created by Maks on 16.12.2017.
 */

public class PlayerAdapter extends ArrayAdapter<String> {

    DataProvider dataProvider = null;

    public PlayerAdapter(Context context) {
        super(context, 0, DataProvider.getInstance().getPlayers());
        dataProvider = DataProvider.getInstance();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.player_list_item2, parent, false);
        }

        TextView tvName = (TextView) convertView.findViewById(R.id.tv_player);
        TextView tvHome = (TextView) convertView.findViewById(R.id.tv_score);

        Log.e("position", "" + position);

        tvHome.setText("" + dataProvider.getPoint(position));
        tvName.setText(dataProvider.getPlayer(position));


        return convertView;
    }

}
