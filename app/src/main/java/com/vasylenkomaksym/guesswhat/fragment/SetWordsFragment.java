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

import com.vasylenkomaksym.guesswhat.R;
import com.vasylenkomaksym.guesswhat.adapter.EditWordsAdapter;
import com.vasylenkomaksym.guesswhat.model.DataProvider;

import java.util.ArrayList;

public class SetWordsFragment extends Fragment {

    private DataProvider dataProvider = null;
    private static int counter = 0;
    private ArrayList<String> wordsOfPlayer = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set_words, container, false);

        dataProvider = DataProvider.getInstance();

        final TextView playerName = view.findViewById(R.id.tv_player);
        playerName.setText(dataProvider.getPlayer(counter));

        final Fragment scoreFragment = new ScoreFragment();


        wordsOfPlayer.clear();
        for (int i = 0; i < dataProvider.getWordsPerPlayer(); i++) {
            wordsOfPlayer.add("");
        }

        Button next = view.findViewById(R.id.btn_next);

        EditWordsAdapter wordsAdapter = new EditWordsAdapter(getContext(), wordsOfPlayer);
        final ListView listView = view.findViewById(R.id.lv_player);
        listView.setAdapter(wordsAdapter);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dataProvider.addArrayOfWords(wordsOfPlayer);

                counter++;
                if(counter >= dataProvider.getPlayers().size()){

                    counter = 0;
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, scoreFragment).addToBackStack(null).commit();
                }else{
                    final Fragment setWordsFragment = new SetWordsFragment();

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, setWordsFragment).addToBackStack(null).commit();
                }

            }
        });

        return view;
    }

}
