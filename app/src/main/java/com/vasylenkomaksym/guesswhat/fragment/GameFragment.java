package com.vasylenkomaksym.guesswhat.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daprlabs.aaron.swipedeck.SwipeDeck;
import com.vasylenkomaksym.guesswhat.R;
import com.vasylenkomaksym.guesswhat.adapter.SwipeDeckAdapter;
import com.vasylenkomaksym.guesswhat.model.DataProvider;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Maks on 16.12.2017.
 */

public class GameFragment extends Fragment {

    private TextView currentWordTextView, countDownTextView;
    private DataProvider dataProvider = null;
    private int currentWordId = -1;
    private int currentPlayerId = -1;
    private int eearnedPoints = 0;
    private CountDownTimer countDownTimer;

    /*
    Swipe deck
     */
    private SwipeDeck cardStack;
    private ArrayList<String> testData;
    private SwipeDeckAdapter adapter;
    /*
    Swipe deck
     */


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        final Fragment scoreFragment = new ScoreFragment();

        /*
        Swipe deck
         */
        cardStack = (SwipeDeck) view.findViewById(R.id.swipe_deck);

        testData = new ArrayList<>();
//        testData.add("word");

        adapter = new SwipeDeckAdapter(testData, this.getContext());
        if(cardStack != null){
            cardStack.setAdapter(adapter);
        }

        cardStack.setCallback(new SwipeDeck.SwipeDeckCallback() {
            @Override
            public void cardSwipedLeft(long stableId) {
                Log.i("MainActivity", "card was swiped left, position in adapter: " + stableId);
                // TODO: 4/23/18 implement this
//                addWord("wtf");
                dataProvider.moveFromAvailableToSkipped(currentWordId);
                nextWord();
            }

            @Override
            public void cardSwipedRight(long stableId) {
                Log.i("MainActivity", "card was swiped right, position in adapter: " + stableId);
                // TODO: 4/23/18 implement this
//                addWord("wtffff");

                dataProvider.removeFromAvailable(currentWordId);
                eearnedPoints++;

                if(!nextWord()){

                    dataProvider.addPoints(currentPlayerId, eearnedPoints);
                    dataProvider.nextRound();
                    countDownTimer.cancel();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, scoreFragment).addToBackStack(null).commit();

                }
            }
        });

        cardStack.setLeftImage(R.id.left_image);
        cardStack.setRightImage(R.id.right_image);
        /*
        End swipe deck
         */

//        currentWordTextView = view.findViewById(R.id.tv_word);
        countDownTextView = view.findViewById(R.id.tv_count_down);
        dataProvider = DataProvider.getInstance();
        logDataProvider();
        currentPlayerId = dataProvider.getTurn();

//        Button skipButton = view.findViewById(R.id.btn_skip);
//        Button guessedButton = view.findViewById(R.id.btn_guessed);

        nextWord();

//        skipButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dataProvider.moveFromAvailableToSkipped(currentWordId);
//
//                logDataProvider();
//                nextWord();
//                logDataProvider();
//            }
//        });

//        guessedButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dataProvider.removeFromAvailable(currentWordId);
//                eearnedPoints++;
//
//                Log.e("just ", "guessed");
//                logDataProvider();
//
//                if(!nextWord()){
//
//                    dataProvider.addPoints(currentPlayerId, eearnedPoints);
//                    dataProvider.nextRound();
//                    countDownTimer.cancel();
//                    getActivity().getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.fragment_container, scoreFragment).addToBackStack(null).commit();
//
//                }
//            }
//        });

        countDownTimer = new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                countDownTextView.setText(millisUntilFinished / 1000 + " sec");
            }

            public void onFinish() {
//                dataProvider.addPoints(currentPlayerId, eearnedPoints);
//                dataProvider.nextTurn();
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragment_container, scoreFragment).addToBackStack(null).commit();
            }
        }.start();

        return view;
    }

    private void addWord(String word){
        adapter.addItem(word);
        Log.d("hm", word + " added");
//        cardStack.unSwipeCard();
    }

    private boolean nextWord(){

        int size = dataProvider.getAvailableWords().size();

        if(size == 0){
           dataProvider.moveAllSkippedIntoAvailable();
           size = dataProvider.getAvailableWords().size();
        }

        if(size > 0){
            int i = ThreadLocalRandom.current().nextInt(dataProvider.getAvailableWords().size());
            currentWordId = dataProvider.getAvailableWords().get(i);
            String newWord = dataProvider.getWord(currentWordId);
            addWord(newWord);
            return true;
        }

        return false;
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
