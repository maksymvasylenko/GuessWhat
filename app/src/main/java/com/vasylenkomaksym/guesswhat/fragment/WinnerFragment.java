package com.vasylenkomaksym.guesswhat.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.vasylenkomaksym.guesswhat.DataProvider;
import com.vasylenkomaksym.guesswhat.R;
import com.vasylenkomaksym.guesswhat.SetPlayersFragment;
import com.vasylenkomaksym.guesswhat.adapter.PlayerAdapter;

/**
 * Created by Maks on 17.12.2017.
 */

public class WinnerFragment extends Fragment{

    private DataProvider dataProvider = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_winner, container, false);


        dataProvider = DataProvider.getInstance();

        String winner = dataProvider.getWinner();
        TextView winnerTextView = view.findViewById(R.id.tv_winner);
        winnerTextView.setText(winner + " Won");

        final Fragment setPlayersFragment = new SetPlayersFragment();

        Button restart = view.findViewById(R.id.btn_restart);

        final PlayerAdapter scoreAdapter = new PlayerAdapter(getContext());
        final ListView listView = view.findViewById(R.id.lv_player);
        listView.setAdapter(scoreAdapter);


        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, setPlayersFragment).addToBackStack(null).commit();
            }
        });

        return view;
    }
}
