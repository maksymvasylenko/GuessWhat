package com.vasylenkomaksym.guesswhat.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
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

public class GameFragment extends Fragment {

    private TextView countDownTextView;
    private DataProvider dataProvider = null;
    private int currentWordId = -1;
    private int currentPlayerId = -1;
    private int points = 0;
    private CountDownTimer countDownTimer;

    private SwipeDeck cardStack;
    private SwipeDeckAdapter adapter;
    private ScoreFragment scoreFragment = new ScoreFragment();

    static final int MIL_DELAY = 60000;
    static final int MIL_INTERVAL = 1000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        initDataProvider();
        initCardStack(view);
        initCountDown(view);
        delayNextWord();
        return view;
    }

    private void initDataProvider() {
        dataProvider = DataProvider.getInstance();
        currentPlayerId = dataProvider.getTurn();
    }

    private void initCardStack(View view) {
        initCardStackViews(view);
        initCardStackAdapter();
        initCardSwipeCallbacks();
    }

    private void initCardStackAdapter() {
        adapter = new SwipeDeckAdapter(new ArrayList<String>(), this.getContext());
        if(cardStack != null){
            cardStack.setAdapter(adapter);
        }
    }

    private void initCardStackViews(View view) {
        cardStack = view.findViewById(R.id.swipe_deck);
        cardStack.setLeftImage(R.id.left_image);
        cardStack.setRightImage(R.id.right_image);
    }

    private void initCountDown(View view) {
        countDownTextView = view.findViewById(R.id.tv_count_down);
        countDownTimer = new CountDownTimer(MIL_DELAY, MIL_INTERVAL) {
            public void onTick(long millisUntilFinished) {
                countDownTextView.setText(Integer.toString((int) (millisUntilFinished / 1000)));
            }
            public void onFinish() {
                dataProvider.addPoints(currentPlayerId, points);
                dataProvider.nextTurn();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, scoreFragment).addToBackStack(null).commit();
            }
        }.start();
    }

    private void initCardSwipeCallbacks() {
        cardStack.setCallback(new SwipeDeck.SwipeDeckCallback() {
            @Override
            public void cardSwipedLeft(long stableId) {
                dataProvider.moveFromAvailableToSkipped(currentWordId);
                delayNextWord();
            }

            @Override
            public void cardSwipedRight(long stableId) {
                dataProvider.removeFromAvailable(currentWordId);
                points++;
                if(!dataProvider.hasAvailableWords()){
                    finishRound();
                } else {
                    delayNextWord();
                }
            }
        });
    }

    void finishRound(){
        dataProvider.addPoints(currentPlayerId, points);
        dataProvider.nextRound();
        countDownTimer.cancel();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, scoreFragment).addToBackStack(null).commit();
    }

    private void addWord(String word){
        adapter.addItem(word);
    }

    private boolean nextWord(){
        if(!dataProvider.hasAvailableWords()){
            dataProvider.moveAllSkippedIntoAvailable();
        }

        if (dataProvider.hasAvailableWords()){
            int i = ThreadLocalRandom.current().nextInt(dataProvider.getAvailableWords().size());
            currentWordId = dataProvider.getAvailableWords().get(i);
            String newWord = dataProvider.getWord(currentWordId);
            addWord(newWord);
            return true;
        } else {
            return false;
        }
    }

    public void delayNextWord(){
        new CountDownTimer(500, 500) {
            public void onTick(long millisUntilFinished) {}
            public void onFinish() {
                nextWord();
            }

        }.start();
    }
}