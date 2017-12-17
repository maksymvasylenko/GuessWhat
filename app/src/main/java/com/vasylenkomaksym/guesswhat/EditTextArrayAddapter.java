package com.vasylenkomaksym.guesswhat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by Maks on 14.12.2017.
 */

public class EditTextArrayAddapter extends BaseAdapter{

    public static final String KEY_WORD = "word";
    public static final String KEY_PLAYER = "player";

    private String type;
    private LayoutInflater mInflater;

    public ArrayList<String> players = new ArrayList<>();

    public EditTextArrayAddapter(Context context, ArrayList<String> players, String type) {
        this.players = players;
        this.type = type;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        notifyDataSetChanged();
    }

    public int getCount() {
        return players.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public String getPlayer(int position){
        return players.get(position);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            if(type == KEY_PLAYER){

                convertView = mInflater.inflate(R.layout.player_list_item, null);
                Button deleteButton = convertView.findViewById(R.id.btn_delete);

                if(players.size() > 2){
                    deleteButton.setVisibility(View.VISIBLE);
                    notifyDataSetChanged();
                    deleteButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });
                }


            }else{
                convertView = mInflater.inflate(R.layout.word_list_item, null);
            }

            holder.caption = (EditText) convertView
                    .findViewById(R.id.et_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //Fill EditText with the value you have in data source
        holder.caption.setText(players.get(position));
        holder.caption.setId(position);

        //we need to update adapter once we finish with editing
        holder.caption.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    final int position = v.getId();
                    final EditText Caption = (EditText) v;
                    players.set(position, Caption.getText().toString());
                }
            }
        });

        return convertView;
    }
}

class ViewHolder {
    EditText caption;
}