package com.vasylenkomaksym.guesswhat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import com.vasylenkomaksym.guesswhat.R;

import java.util.List;

public class EditWordsAdapter extends BaseAdapter{

    private final Context context;
    private final List<String> words;
    private final LayoutInflater inflater;

    public EditWordsAdapter(Context context, List<String> words) {
        this.context = context;
        this.words = words;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return words.size();
    }

    @Override
    public Object getItem(int i) {
        return words.get(i);
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
            view = inflater.inflate(R.layout.word_list_item, null);
            holder.text = view.findViewById(R.id.et_name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        setHolderEditTextValue(holder, words, i);
        setFocusChangeListener(holder.text, words);

        return view;
    }

    private void setHolderEditTextValue(ViewHolder holder, List<String> words, int i) {
        holder.text.setText(words.get(i));
        holder.text.setId(i);
    }

    private void setFocusChangeListener(EditText text, final List<String> words) {
        text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b){
                    final int i = view.getId();
                    final EditText text = (EditText) view;
                    words.set(i, text.getText().toString());
                }
            }
        });
    }
}
