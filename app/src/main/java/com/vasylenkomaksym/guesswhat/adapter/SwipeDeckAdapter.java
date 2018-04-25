package com.vasylenkomaksym.guesswhat.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vasylenkomaksym.guesswhat.R;

import java.util.List;

public class SwipeDeckAdapter extends BaseAdapter {

    private List<String> data;
    private Context context;
    private LayoutInflater inflater;

    public SwipeDeckAdapter(List<String> data, Context context) {
        this.data = data;
        this.context = context;
    }

    public void addItem(String item){
        data.add(item);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (v == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // normally use a viewholder
            v = inflater.inflate(R.layout.test_card2, parent, false);
        }
        //((TextView) v.findViewById(R.id.textView2)).setText(data.get(position));
//        ImageView imageView = (ImageView) v.findViewById(R.id.offer_image);
//        Picasso.with(context).load(R.drawable.food).fit().centerCrop().into(imageView);
        TextView textView = v.findViewById(R.id.sample_text);
        String item = (String)getItem(position);
        textView.setText(item);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Layer type: ", Integer.toString(v.getLayerType()));
                Log.i("Hardware Accel type:", Integer.toString(View.LAYER_TYPE_HARDWARE));
                    /*Intent i = new Intent(v.getContext(), BlankActivity.class);
                    v.getContext().startActivity(i);*/
            }
        });
        return v;
    }
}