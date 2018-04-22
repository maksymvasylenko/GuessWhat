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

import com.vasylenkomaksym.guesswhat.adapter.PlayerAddapter;
import com.vasylenkomaksym.guesswhat.R;
import com.vasylenkomaksym.guesswhat.model.DataProvider;

/**
 * Created by Maks on 14.12.2017.
 */

public class ScoreFragment extends Fragment {

    private DataProvider dataProvider = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_score, container, false);

        dataProvider = DataProvider.getInstance();

        if(dataProvider.getRound() >= 3){
            Fragment winnerFragment = new WinnerFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, winnerFragment).addToBackStack(null).commit();
        }


        final Fragment gameFragment = new GameFragment();

        Button next = view.findViewById(R.id.btn_go);

        final PlayerAddapter scoreAdapter = new PlayerAddapter(getContext());
        final ListView listView = view.findViewById(R.id.lv_player);
        listView.setAdapter(scoreAdapter);

        TextView roundTextView = view.findViewById(R.id.tv_round);
        TextView turnTextView = view.findViewById(R.id.tv_players_turn);

        roundTextView.setText("Round " + (dataProvider.getRound() + 1));
        turnTextView.setText("" + dataProvider.getPlayer(dataProvider.getTurn()));

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(dataProvider.isNewRound()){
                    dataProvider.refillAvailable();
                    dataProvider.setIsNewRound(false);
                }


                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, gameFragment).addToBackStack(null).commit();
            }
        });

        return view;
    }



}
