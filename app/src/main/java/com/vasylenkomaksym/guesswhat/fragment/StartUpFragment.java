package com.vasylenkomaksym.guesswhat.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.vasylenkomaksym.guesswhat.R;
import com.vasylenkomaksym.guesswhat.model.DataProvider;

import java.io.File;

/**
 * Created by Maks on 14.12.2017.
 */

public class StartUpFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_start_up, container, false);



        Button newGameButton = view.findViewById(R.id.btn_new_game);
        final Button continueButton = view.findViewById(R.id.btn_continue);
        final File file = new File(getContext().getFilesDir(),DataProvider.FILE_NAME);

        DataProvider.getNewInstance();
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(file.exists()){
                    file.delete();
                }
                Fragment setPlayerFragment = new SetPlayersFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, setPlayerFragment).addToBackStack(null).commit();

            }
        });

        if(file.exists()){
            continueButton.setVisibility(View.VISIBLE);

            continueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DataProvider.getInstance().readFromFile(getContext());
                    Fragment scoreFragment = new ScoreFragment();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, scoreFragment).addToBackStack(null).commit();
                }
            });
        }



        return view;
    }
}
