package com.vasylenkomaksym.guesswhat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Maks on 14.12.2017.
 */

public class SetWordsFragment extends Fragment {

    private DataProvider dataProvider = null;
    private static int counter = 0;
    private ArrayList<String> wordsOfPlayer = new ArrayList<>();
    private EditTextArrayAddapter wordsAdapter;

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

        wordsAdapter = new EditTextArrayAddapter(getContext(), wordsOfPlayer, EditTextArrayAddapter.KEY_WORD);
        final ListView listView = view.findViewById(R.id.lv_player);
        listView.setAdapter(wordsAdapter);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dataProvider.addArrayOfWords(wordsOfPlayer);



                for (int i = 0; i < wordsAdapter.getCount(); i++) {

                    Log.e("adapter item word:" + i, String.valueOf(wordsAdapter.getPlayer(i)));
                }


                for (int i = 0; i < dataProvider.getWordsPerPlayer(); i++) {
                    wordsOfPlayer.set(i, "");
                }
                wordsOfPlayer.set(1, "fuck yeah");

                wordsAdapter.swapItems(wordsOfPlayer);
                wordsAdapter.notifyDataSetChanged();

                for (int i = 0; i < wordsOfPlayer.size(); i++) {

                    Log.e("adapter item word3:" + i,"aaa " +  wordsOfPlayer.get(i));
                }

                for (int i = 0; i < wordsAdapter.getCount(); i++) {

                    Log.e("adapter item word2:" + i, String.valueOf(wordsAdapter.getPlayer(i)));
                }

                counter++;
                if(counter >= dataProvider.getPlayers().size()){
                    for (int i = 0; i < dataProvider.getWords().size(); i++) {

                        Log.e("adapter item word:" + i, String.valueOf(dataProvider.getWords().get(i)));
                    }

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, scoreFragment).addToBackStack(null).commit();
                }else{

                    playerName.setText(dataProvider.getPlayer(counter));
                }

                Log.e("adapter item check", String.valueOf(wordsAdapter.getPlayer(1)));
            }
        });

        return view;
    }

}
