package com.vasylenkomaksym.guesswhat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by Maks on 14.12.2017.
 */

public class EditTextArrayAddapter extends BaseAdapter{

    private LayoutInflater mInflater;
    public ArrayList<String> players = new ArrayList<>();

    public EditTextArrayAddapter(Context context, ArrayList<String> players) {
        this.players = players;
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
            convertView = mInflater.inflate(R.layout.player_list_item, null);
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