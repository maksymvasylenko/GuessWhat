package com.vasylenkomaksym.guesswhat.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.vasylenkomaksym.guesswhat.R;

import java.util.ArrayList;

/**
 * Created by Maks on 14.12.2017.
 */

public class EditTextArrayAdapter extends BaseAdapter{

    public static final String KEY_WORD = "word";
    public static final String KEY_PLAYER = "player";

    private String type;
    private LayoutInflater mInflater;

    public ArrayList<String> players;

    public EditTextArrayAdapter(Context context, ArrayList<String> players, String type) {
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

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;


        int pos = position;

        Log.e("position", " start");

        Log.e("position", "" + pos);

        if (convertView == null) {

            holder = new ViewHolder();
            if(type == KEY_PLAYER){

                convertView = mInflater.inflate(R.layout.player_list_item, null);
                holder.deleteBtn = convertView.findViewById(R.id.btn_delete);

            }else{
                convertView = mInflater.inflate(R.layout.word_list_item, null);
            }

            holder.caption = (EditText) convertView
                    .findViewById(R.id.et_name);
            convertView.setTag(holder);
        } else {
            Log.e("position else", "" + pos);
            holder = (ViewHolder) convertView.getTag();
        }
        //Fill EditText with the value you have in data source
        holder.caption.setText(players.get(position));
        holder.caption.setId(position);

        if(type == KEY_PLAYER) {
            if(players.size() > 2){

                holder.deleteBtn.setVisibility(View.VISIBLE);
                notifyDataSetChanged();

                holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        players.remove(position);
                        notifyDataSetChanged();
                    }
                });
            }else{
                holder.deleteBtn.setVisibility(View.GONE);
                notifyDataSetChanged();
            }
        }


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
    Button deleteBtn;
}