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

/**
 * Created by Maks on 14.12.2017.
 */

public class SetWordsFragment extends Fragment {

    private DataProvider dataProvider = null;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set_words, container, false);

        dataProvider = DataProvider.getInstance();

        final Fragment scoreFragment = new ScoreFragment();

        Button next = view.findViewById(R.id.btn_next);

        final EditTextArrayAddapter wordsAdapter = new EditTextArrayAddapter(getContext(), dataProvider.getWords(), EditTextArrayAddapter.KEY_WORD);
        final ListView listView = view.findViewById(R.id.lv_player);
        listView.setAdapter(wordsAdapter);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i = 0; i < wordsAdapter.getCount(); i++) {

                    Log.e("adapter item word:" + i, String.valueOf(wordsAdapter.getPlayer(i)));
                }

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, scoreFragment).addToBackStack(null).commit();
            }
        });

        return view;
    }

}
