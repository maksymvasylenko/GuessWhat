package com.vasylenkomaksym.guesswhat;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Maks on 16.12.2017.
 */

public class GameFragment extends Fragment{

    private TextView currentWordTextView, countDownTextView;
    private DataProvider dataProvider = null;
    private int currentWordId = -1;
    private int currentPlayerId = -1;
    private int eearnedPoints = 0;
    private CountDownTimer countDownTimer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        final Fragment scoreFragment = new ScoreFragment();

        currentWordTextView = view.findViewById(R.id.tv_word);
        countDownTextView = view.findViewById(R.id.tv_count_down);
        dataProvider = DataProvider.getInstance();
        logDataProvider();
        currentPlayerId = dataProvider.getTurn();

        Button skipButton = view.findViewById(R.id.btn_skip);
        Button guessedButton = view.findViewById(R.id.btn_guessed);


        nextWord();


        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataProvider.moveFromAvailableToSkipped(currentWordId);

                Log.e("just ", "skipped");
                logDataProvider();
                nextWord();
                Log.e("just ", "skipped 2");
                logDataProvider();
            }
        });

        guessedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataProvider.removeFromAvailable(currentWordId);
                eearnedPoints++;

                Log.e("just ", "guessed");
                logDataProvider();

                if(!nextWord()){

                    dataProvider.addPoints(currentPlayerId, eearnedPoints);
                    dataProvider.nextRound();
                    countDownTimer.cancel();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, scoreFragment).addToBackStack(null).commit();

                }
            }
        });


        countDownTimer = new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                countDownTextView.setText(millisUntilFinished / 1000 + " sec");
            }

            public void onFinish() {
                dataProvider.addPoints(currentPlayerId, eearnedPoints);
                dataProvider.nextTurn();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, scoreFragment).addToBackStack(null).commit();
            }
        }.start();


        return view;
    }

    private boolean nextWord(){

        int size = dataProvider.getAvailableWords().size();

        if(size == 0){
           dataProvider.moveAllSkippedIntoAvailable();
           size = dataProvider.getAvailableWords().size();
        }

        if(size != 0){
            int i = ThreadLocalRandom.current().nextInt(dataProvider.getAvailableWords().size());
            currentWordId = dataProvider.getAvailableWords().get(i);
            String newWord = dataProvider.getWord(currentWordId);

            currentWordTextView.setText(newWord);
            return true;
        }else{
            currentWordTextView.setText("DONE!!");
            return false;
        }
    }

    private void logDataProvider(){
        Log.e("Provider Contais", " start");
        Log.e("Provider ", " Players");
        for (int i = 0; i < dataProvider.getPlayers().size(); i++) {
            Log.e("Provider: Player " + i, " " + dataProvider.getPlayers().get(i));
        }

        Log.e("Provider: Round", "" + dataProvider.getRound());
        Log.e("Provider: Turn", "" + dataProvider.getTurn());
        Log.e("Provider: Available", "" + dataProvider.getAvailableWords().size());
        Log.e("Provider: Skiped", "" + dataProvider.getSkippedSize());
        Log.e("Provider: Total Words", "" + dataProvider.getWords().size());

        Log.e("Provider ", " Score");
        for (int i = 0; i < dataProvider.getPoints().size(); i++) {
            Log.e("Provider: Player" + i, " " + dataProvider.getPoint(i));
        }
    }



}
