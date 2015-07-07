package com.chipx.kqxs;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA
 * User: xc
 * Date: 15/02/14.
 * Time: 01:21
 */
public class DetailShow extends Activity {
    ViewPager myPager;
    DetailAdapterPager detailAdapterPager;
    ArrayList<ItemDataXoSo> itemDataXoSoArrayList = new ArrayList<ItemDataXoSo>();
    ActionBar atb;
    private Context cxt;
    private Activity activity;
    Bundle bundle = new Bundle();
    Intent myIntent;
    int startPage = 0;
    Spinner spinnerDate;
    ArrayList<String> arrayListDate = new ArrayList<>();
    ArrayAdapter<String> adapterSpinner;
    int timesSelect = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showdetail);

        myIntent = getIntent();

        itemDataXoSoArrayList = (ArrayList<ItemDataXoSo>) myIntent.getExtras().get("data");
        startPage = myIntent.getExtras().getInt("start");

        myPager = (ViewPager) findViewById(R.id.home_pannels_pager);

        detailAdapterPager = new DetailAdapterPager();
        cxt = this;
        activity = this;

        myPager.setAdapter(detailAdapterPager);
        myPager.setCurrentItem(startPage);

        spinnerDate = (Spinner)findViewById(R.id.spinner);
        for (int i=itemDataXoSoArrayList.size()-1; i>=0 ;i--)
        {
            String titleStr = "\r\n\t" + itemDataXoSoArrayList.get(i).display;
            int indexSub =  titleStr.toUpperCase().toString().lastIndexOf("NG");
            String subTitle = "";
            if (indexSub>-1 && indexSub < titleStr.length() - 4) {
                subTitle= titleStr.substring(indexSub);
                arrayListDate.add(subTitle);
            }
        }
        adapterSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayListDate);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDate.setAdapter(adapterSpinner);
        spinnerDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (timesSelect != 0)
                {
                    myPager.setCurrentItem(arrayListDate.size()-position-1);
                }
                timesSelect ++;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        atb = getActionBar();
        atb.setDisplayHomeAsUpEnabled(true);
        atb.setTitle("Back to home");
        atb.setIcon(R.drawable.home);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        setResult(Activity.RESULT_CANCELED, myIntent);
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    ///
    public class DetailAdapterPager extends PagerAdapter {

        @Override
        public int getCount() {
            return itemDataXoSoArrayList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final LinearLayout layout = new LinearLayout(cxt);
            layout.setOrientation(LinearLayout.VERTICAL);
            TextView textItem = new TextView(cxt);
            TextView textItem2 = new TextView(cxt);
            ListView listView = new ListView(cxt);
            AdapterListViewDetail adapterListViewDetail = new AdapterListViewDetail(activity, R.layout.ly_iteam,
                    itemDataXoSoArrayList.get(position).getMiniItem());
            /////////
            layout.setBackgroundColor(Color.rgb(200, 255, 182));
            textItem.setTextColor(Color.WHITE);
            textItem.setBackgroundColor(Color.rgb(168, 189, 235));
            textItem.setTextSize(17);
            textItem.setGravity(Gravity.CENTER_HORIZONTAL);
            String titleStr = "\r\n\t" + itemDataXoSoArrayList.get(position).display;
            int indexSub =  titleStr.toUpperCase().toString().lastIndexOf("NG");
            String subTitle = "";
            if (indexSub>-1 && indexSub < titleStr.length() - 4) {
                subTitle= titleStr.substring(indexSub);
                titleStr = titleStr.substring(0,indexSub-1) + "\r\n" + subTitle;
            }
            textItem.setText(titleStr);

            textItem2.setTextColor(Color.WHITE);
            textItem2.setBackgroundColor(Color.rgb(168, 189, 235));
            textItem2.setText(" ");

            listView.setAdapter(adapterListViewDetail);
            listView.setBackgroundColor(Color.rgb(200, 255, 182));

            layout.addView(textItem);
            layout.addView(textItem2);
            layout.addView(listView);

            container.addView(layout, 0); // This is the line I added
            return layout;
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
            super.restoreState(state, loader);
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public void finishUpdate(ViewGroup container) {
            super.finishUpdate(container);
            Log.d("finish update ", "event is running" + container.getChildCount());
            if (container.getChildCount() == 2) {
                Log.d("finish update ", "event is running 1x");
                if (myPager.getCurrentItem() == 0) {
                    Log.d("finish update ", "event is running 2x");
                    setResult(Activity.RESULT_OK, myIntent);
                    finish();
                }
            }
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == ((View) o);
        }
    }
}
