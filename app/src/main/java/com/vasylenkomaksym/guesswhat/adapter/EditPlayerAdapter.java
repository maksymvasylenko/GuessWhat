package com.vasylenkomaksym.guesswhat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.vasylenkomaksym.guesswhat.R;

import java.util.List;

public class EditPlayerAdapter extends BaseAdapter {

    private static final int PLAYERS_MIN = 2;
    private final List<String> players;
    private final LayoutInflater inflater;

    public EditPlayerAdapter(Context context, List<String> players) {
        this.players = players;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return players.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;
        if (view == null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.player_list_item, null);
            holder.deleteBtn = view.findViewById(R.id.btn_delete);
            holder.text = (EditText) view.findViewById(R.id.et_name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        setHolderEditTextValue(holder, players, i);
        updateDeleteButtonVisibility(holder, players, i);
        setFocusChangeListener(holder.text, players);

        return view;
    }

    private void setFocusChangeListener(EditText text, final List<String> players) {
        text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b){
                    final int i = view.getId();
                    final EditText caption = (EditText) view;
                    players.set(i, caption.getText().toString());
                }
            }
        });
    }

    private void setHolderEditTextValue(ViewHolder holder, List<String> players, int i) {
        holder.text.setText(players.get(i));
        holder.text.setId(i);
    }

    private void updateDeleteButtonVisibility(ViewHolder holder, List<String> players, int i) {
        if (players.size() > PLAYERS_MIN){
            setVisible(holder.deleteBtn);
            setDeleteButtonOnClickListener(holder.deleteBtn, i);
        } else {
            setInvisible(holder.deleteBtn);
        }
    }

    private void setInvisible(Button deleteBtn) {
        deleteBtn.setVisibility(View.INVISIBLE);
        notifyDataSetChanged();
    }

    private void setDeleteButtonOnClickListener(Button deleteBtn, final int i) {
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                players.remove(i);
                notifyDataSetChanged();
            }
        });
    }

    private void setVisible(View view) {
        view.setVisibility(View.VISIBLE);

        //is this necessary?
        notifyDataSetChanged();
    }
}
