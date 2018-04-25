package com.vasylenkomaksym.guesswhat.model;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Maks on 10.12.2017.
 */

public class DataProvider {

    public static final String FILE_NAME = "data.txt";

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

    public static DataProvider getNewInstance(){
        instance = new DataProvider();
        return instance;
    }

    public void restartGame(){
        this.words = new ArrayList<>();
        this.isNewRound = true;
        this.skippedWords = new ArrayList<>();
        this.availableWords = new ArrayList<>();
        points = new ArrayList<>();
        for(String player : this.players){
            points.add(0);
        }
        this.round = 0;
        this.turn = 0;
    }

    public void setWordsPerPlayer(int wordsPerPlayer) {
        this.wordsPerPlayer = wordsPerPlayer;
    }

    public int getWordsPerPlayer() {
        return wordsPerPlayer;
    }


    public String getWord(int wordId){
        return this.words.get(wordId);
    }

    public void addArrayOfWords(ArrayList<String> words) {
        this.words.addAll(words);
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


    public int getRound() {
        return this.round;
    }


    public int getTurn() {
        return turn;
    }

    public void nextTurn() {
        if(this.turn == (this.players.size() - 1)){
            this.turn = 0;
        }else{
            this.turn++;
        }
    }

    public void nextRound() {
        this.round++;
        //this.turn = 0;
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

    public void addPlayer(String name){
        players.add(name);
    }



    public void addPoints(int plyerId, int points){
        this.points.set(plyerId, (this.points.get(plyerId) + points));
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



    public void writeIntoFile(Context context){
        FileOutputStream fileOutputStream = null;
        File file = new File(FILE_NAME);

        try {
            fileOutputStream = context.openFileOutput(FILE_NAME, MODE_PRIVATE);
            fileOutputStream.write(toJson().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public void readFromFile(Context context) {

        String ret;

        try {
            InputStream inputStream = context.openFileInput(FILE_NAME);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString;
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();

                fromJson(ret);

            }
        } catch (IOException e) {
        }
    }


    private String toJson(){
        try {

            JSONObject jsonObj = new JSONObject();
            jsonObj.put("round", this.round);
            jsonObj.put("turn", this.turn);
            jsonObj.put("wordsPerPlayer", this.wordsPerPlayer);


            JSONArray jsonArrayWords = new JSONArray();
            for(String word: this.words){
                jsonArrayWords.put(word);
            }
            jsonObj.put("words", jsonArrayWords);

            JSONArray jsonArrayPlayers = new JSONArray();
            for(String player: this.players){
                jsonArrayPlayers.put(player);
            }
            jsonObj.put("players", jsonArrayPlayers);

            JSONArray jsonArraAvailableWords = new JSONArray();
            for(int availableWord: this.availableWords){
                jsonArraAvailableWords.put(availableWord);
            }
            jsonObj.put("availableWords", jsonArraAvailableWords);

            JSONArray jsonArrayPoints = new JSONArray();
            for(int point: this.points){
                jsonArrayPoints.put(point);
            }
            jsonObj.put("points", jsonArrayPoints);

            return jsonObj.toString();
        }
        catch(JSONException ex) {
            ex.printStackTrace();
        }
        return null;

    }

    private void fromJson(String data){

        try {
            JSONObject jsonObj = new JSONObject(data);
            this.round = jsonObj.getInt("round");
            this.turn = jsonObj.getInt("turn");
            this.wordsPerPlayer = jsonObj.getInt("wordsPerPlayer");

            JSONArray jsonArrayWords = jsonObj.getJSONArray("words");
            for (int i = 0; i < jsonArrayWords.length(); i++) {
                 this.words.add(jsonArrayWords.getString(i));
            }


            JSONArray jsonArrayPlayers = jsonObj.getJSONArray("players");
            for (int i = 0; i < jsonArrayPlayers.length(); i++) {
                this.players.add(jsonArrayPlayers.getString(i));
            }


            JSONArray jsonArraAvailableWords = jsonObj.getJSONArray("availableWords");
            for (int i = 0; i < jsonArraAvailableWords.length(); i++) {
                this.availableWords.add(jsonArraAvailableWords.getInt(i));
            }

            JSONArray jsonArrayPoints = jsonObj.getJSONArray("points");
            for (int i = 0; i < jsonArrayPoints.length(); i++) {
                this.points.add(jsonArrayPoints.getInt(i));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public boolean hasAvailableWords() {
        return availableWords.size() > 0;
    }
}
