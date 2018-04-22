package com.vasylenkomaksym.guesswhat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.vasylenkomaksym.guesswhat.model.DataProvider;

/**
 * Created by Maks on 14.12.2017.
 */

public class SetPlayersFragment extends Fragment {

    private DataProvider dataProvider = null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set_players, container, false);

        dataProvider = DataProvider.getInstance();

        final Fragment setWordsFragment = new SetWordsFragment();

        Button next = view.findViewById(R.id.btn_next);
        Button addPlayer = view.findViewById(R.id.btn_add_player);
        final EditText numberOfWords = view.findViewById(R.id.et_number_of_words);

        dataProvider.addPlayer("Player 1");
        dataProvider.addPlayer("Player 2");


        dataProvider.addPoint(0);
        dataProvider.addPoint(0);


        final EditTextArrayAddapter playersAdapter = new EditTextArrayAddapter(getContext(), dataProvider.getPlayers(), EditTextArrayAddapter.KEY_PLAYER);
        final ListView listView = view.findViewById(R.id.lv_player);
        listView.setAdapter(playersAdapter);


        addPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataProvider.addPlayer("Player " + (dataProvider.getPlayers().size() + 1));
                dataProvider.addPoint(0);

                playersAdapter.notifyDataSetChanged();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i = 0; i < playersAdapter.getCount(); i++) {
                    //Log.e("adapter item " + i, String.valueOf(playersAdapter.getPlayer(i)));
                    //dataProvider.addPlayer(String.valueOf(playersAdapter.getPlayer(i)));
                }

                for (int i = 0; i < dataProvider.getPlayers().size(); i++) {
                    Log.e("adapter item " + i, dataProvider.getPlayer(i));
                }


                int wordsCount = Integer.valueOf(String.valueOf(numberOfWords.getText()));
                dataProvider.setWordsPerPlayer(wordsCount);


                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, setWordsFragment).addToBackStack(null).commit();
            }
        });

        return view;
    }
}
