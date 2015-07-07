package com.chipx.kqxs;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA
 * User: xc
 * Date: 10/01/14.
 * Time: 20:06
 */
@SuppressWarnings("ALL")
public class AdapterListViewDetail extends ArrayAdapter<miniItem> {
    private ArrayList<miniItem> arrayListAward;
    private Activity activity;
    private int layoutID;

    public AdapterListViewDetail(Activity context, int textViewResourceId, ArrayList<miniItem> arr) {
        super(context, textViewResourceId, arr);
        this.arrayListAward = arr;
        this.activity = context;
        this.layoutID = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        convertView = layoutInflater.inflate(layoutID, null);
//        //-----------------------------
        TextView textViewNameAward = (TextView) convertView.findViewById(R.id.textView_item_awardName);
        TextView textViewAward = (TextView) convertView.findViewById(R.id.textView_item_award);

        //textViewAward.setTextColor(Color.WHITE);
        //textViewNameAward.setTextColor(Color.WHITE);

        textViewNameAward.setText(arrayListAward.get(position).getGtName());
        textViewAward.setText(arrayListAward.get(position).getGt());
//        textViewAward.setText("------------");
//        //-----------------------------
        return convertView;
    }
}
