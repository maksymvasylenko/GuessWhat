package com.vasylenkomaksym.guesswhat.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.vasylenkomaksym.guesswhat.R;

/**
 * Created by Maks on 14.12.2017.
 */

public class StartUpFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start_up, container, false);

        final Fragment setPlayerFragment = new SetPlayersFragment();

        Button newGameButton = view.findViewById(R.id.btn_new_game);

        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, setPlayerFragment).addToBackStack(null).commit();
            }
        });

        return view;
    }
}
