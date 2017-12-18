package com.vasylenkomaksym.guesswhat;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Maks on 10.12.2017.
 */

public class DataProvider {

    private ArrayList<String> words = new ArrayList<>();

    private ArrayList<Integer> availableWords = new ArrayList<>();
    private ArrayList<Integer> skippedWords = new ArrayList<>();

    private ArrayList<String> players = new ArrayList<>();
    private ArrayList<Integer> points = new ArrayList<>();

    private int round = 0;
    private int turn = 0;
    private boolean isNewRound = true;
    private int wordsPerPlayer = 0;

    private static DataProvider instance = null;

    private DataProvider(){

    }

    public static DataProvider getInstance() {
        if(instance == null){
            instance = new DataProvider();
        }
        return instance;
    }

    public void setWordsPerPlayer(int wordsPerPlayer) {
        this.wordsPerPlayer = wordsPerPlayer;
    }

    public int getWordsPerPlayer() {
        return wordsPerPlayer;
    }

    public ArrayList<String> getWords() {
        return this.words;
    }

    public String getWord(int wordId){
        return this.words.get(wordId);
    }

    public void addArrayOfWords(ArrayList<String> words) {
        this.words.addAll(words);
    }

    public void addWord(String word){
        this.words.add(word);
    }



    public ArrayList<Integer> getAvailableWords(){
        return this.availableWords;
    }

    public void moveFromAvailableToSkipped(Integer wordId){
        this.availableWords.remove(wordId);//careful here. might be wrong. Check if it understands it should be object not id
        this.skippedWords.add(wordId);
    }

    public void removeFromAvailable(Integer wordId){
        this.availableWords.remove(wordId);
    }

    public int getSkippedSize(){
        return this.skippedWords.size();
    }

    public void moveAllSkippedIntoAvailable(){
        this.availableWords.addAll(skippedWords);
        this.skippedWords.clear();
    }

    public void refillAvailable(){
        this.availableWords.clear();
        this.skippedWords.clear();
        for (int i = 0; i < this.words.size(); i++) {
            this.availableWords.add(i);
        }
    }





    public void setRound(int round) {
        this.round = round;
    }

    public int getRound() {
        return this.round;
    }


    public int getTurn() {
        return turn;
    }

    public void nextTurn() {
        if(this.turn == (this.players.size() - 1)){
            this.turn = 0;
            this.round++;
            this.isNewRound = true;
        }else{
            this.turn++;
        }
    }

    public void nextRound() {
        this.round++;
        this.turn = 0;
        this.isNewRound = true;
    }

    public boolean isNewRound() {
        return isNewRound;
    }

    public void setIsNewRound(boolean newRound) {
        isNewRound = newRound;
    }

    public ArrayList<String> getPlayers() {
        return this.players;
    }

    public String getPlayer(int playerId) {
        return this.players.get(playerId);
    }

    public void setPlayers(ArrayList<String> players) {
        this.players = players;
        for (int i = 0; i < players.size(); i++) {
            this.points.add(0);
        }
    }

    public void addPlayer(String name){
        players.add(name);
    }



    public void addPoints(int plyerId, int points){
        this.points.set(plyerId, (this.points.get(plyerId) + points));
    }

    public ArrayList<Integer> getPoints(){
        return this.points;
    }

    public void addPoint(int point){
        this.points.add(point);
}

    public int getPoint(int pointId){
        return this.points.get(pointId);
    }

    public String getWinner(){

        int biggestId = 0;

        int biggest = this.points.get(biggestId);
        int current;

        for (int i = 1; i < this.points.size(); i++) {
            current = this.points.get(i);
            if(biggest < current){
                biggest = current;
                biggestId = i;
            }
        }

        return this.players.get(biggestId);
    }


}
