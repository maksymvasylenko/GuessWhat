package com.vasylenkomaksym.guesswhat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import com.vasylenkomaksym.guesswhat.R;

import java.util.ArrayList;

/**
 *
 */
public class EditTextArrayAdapter extends BaseAdapter {

    public static final String KEY_WORD = "word";
    public static final String KEY_PLAYER = "player";

    private String type;
    private LayoutInflater mInflater;
    public ArrayList<String> data;

    public EditTextArrayAdapter(Context context, ArrayList<String> data, String type) {
        this.data = data;
        this.type = type;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        notifyDataSetChanged();
    }

    /*
    Return amount of data elements
     */
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    /*
    Get data from position
     */
    public String getData(int position) {
        return data.get(position);
    }

    /*
    Inflating editText view, filling with data
     */
    public View getView(final int position, View convertView, ViewGroup parent) {

        // Standard arrayAdapter functions
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();

            // If player, inflate player list item, define delete button
            if (type.equals(KEY_PLAYER)) {
                convertView = mInflater.inflate(R.layout.player_list_item, null);
                holder.deleteBtn = convertView.findViewById(R.id.btn_delete);

                // If word list, inflate word list item
            } else {
                convertView = mInflater.inflate(R.layout.word_list_item, null);
            }

            holder.text = (EditText) convertView.findViewById(R.id.et_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //Fill EditText with the value you have in data source
        holder.text.setText(data.get(position));
        holder.text.setId(position);

        if (type == KEY_PLAYER) {
            if (data.size() > 2) {

                holder.deleteBtn.setVisibility(View.VISIBLE);
                notifyDataSetChanged();

                holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        data.remove(position);
                        notifyDataSetChanged();
                    }
                });
            } else {
                holder.deleteBtn.setVisibility(View.GONE);
                notifyDataSetChanged();
            }
        }

        //we need to update adapter once we finish with editing
        holder.text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    final int position = v.getId();
                    final EditText Caption = (EditText) v;
                    data.set(position, Caption.getText().toString());
                }
            }
        });

        return convertView;
    }

}

