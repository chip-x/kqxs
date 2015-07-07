package com.chipx.kqxs;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("ALL")
public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    ExpandableListView expandableListView;
    List<String> header = new ArrayList<String>();
    HashMap<String, List<String>> hashMapCity = new HashMap<String, List<String>>();
    HashMap<String, List<ItemDataXoSo>> hashMapData = new HashMap<String, List<ItemDataXoSo>>();
    int[] pageNumberx;

    ProgressDialog progressD;

    ItemDataXoSo itemDataXoSoNow = null;
    ControlCity myControlCity = new ControlCity();

    String idNumber = "";
    int pageNumber = 0;
    AdapterExpandableListView adapterExpandableListView;

    TextView titleTextView;//
    ListView detailListView;//

    Boolean dataLoad = false;
    Boolean newOld = false;

    int CurentItem = 0;

    //isSwipeLeft preview the after times show
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);


        header.add(0, "Xổ số Miền Bắc");
        header.add(1, "Xổ số Miền Trung");
        header.add(2, "Xổ số Miền Nam");

        adapterExpandableListView = new AdapterExpandableListView(this, header, hashMapCity);
        expandableListView.setAdapter(adapterExpandableListView);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                setIdAndPage(groupPosition, childPosition);
                newOld = true;
                updateShowDetail();
                return false;
            }
        });

        ////
        if (hashMapCity.size() <= 0) {
            if (!isOnline()) {
                Toast.makeText(getApplicationContext(), "sorry!\nThis app can't running\nBecause the network is not working!", Toast.LENGTH_LONG).show();
                finish();
            } else {
                showDialog(0);
                DownloadCity dlct = new DownloadCity();
                dlct.execute();
            }
        } else {
            updateShowDetail();

        }
    }

    ////////////////////////result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 441991:
                if (resultCode == Activity.RESULT_OK) {
//                    reallyToUpdate();
                    newOld = true;
                    updateShowDetail();
                } else {
                    //do nothings
                    //this is end show detail
                }
                break;
            default:
                break;
        }
    }

    //////function
    //check network working or not
    private Boolean isOnline() {
        boolean status = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                {
                    status = true;
                }
            } else {
                netInfo = cm.getNetworkInfo(1);
                if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                    status = true;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return status;
    }

    ///dialog show when download
    public Dialog onCreateDialog(int id, Bundle args) {
        switch (id) {
            case 0:
                progressD = new ProgressDialog(this);
                progressD.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressD.setMessage("Đang tải các tỉnh...");
                return progressD;
            case 1:
                progressD = new ProgressDialog(this);
                progressD.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressD.setMessage("Đang tải kết quả...");
                return progressD;
        }

        return onCreateDialog(id, args);
    }

    ///get idNumber and page
    private void setIdAndPage(int group, int chil) {
        try {
            switch (group) {
                case 0://bac
                    idNumber = myControlCity.bac.getLinks()[chil];
                    pageNumber = chil;
                    Log.d("set mien bac: ", myControlCity.bac.getDisplays()[chil]);
                    break;
                case 1://trung
                    idNumber = myControlCity.trung.getLinks()[chil];
                    pageNumber = myControlCity.bac.getLinks().length + chil;
                    Log.d("set mien trung: ", myControlCity.trung.getDisplays()[chil]);
                    break;
                case 2://nam
                    idNumber = myControlCity.nam.getLinks()[chil];
                    pageNumber = myControlCity.bac.getLinks().length;
                    pageNumber += myControlCity.trung.getLinks().length + chil;
                    Log.d("set mien nam: ", myControlCity.nam.getDisplays()[chil]);
                    break;
                default:
                    return;
            }
        } catch (Exception e) {
            Log.d("missing when find idnumber: ", e.getMessage());
            return;
        }
        //idNumber begin 1
    }

    ///really to update detail
    private void reallyToUpdate() {
        pageNumberx[pageNumber]++;
        showDialog(1);
        DownloadData dldt = new DownloadData();
        dldt.execute();
        newOld = false;
        dismissDialog(1);
    }

    ///update show detail
    private void updateShowDetail() {
        ArrayList<ItemDataXoSo> itemDataXoSoList = new ArrayList<ItemDataXoSo>();
        try {
            itemDataXoSoList.addAll(hashMapData.get(idNumber));
        } catch (Exception e) {
            if (!isOnline()) {
                Toast.makeText(getApplicationContext(),
                        "sorry!\nThis app can't download\nBecause the network is not working!", Toast.LENGTH_LONG).show();
            } else {
                showDialog(1);
                DownloadData downloadData = new DownloadData();
                downloadData.execute();
            }
            return;
        }
        if (itemDataXoSoList == null || itemDataXoSoList.size() <= 0) {
            if (!isOnline()) {
                Toast.makeText(getApplicationContext(),
                        "sorry!\nThis app can't download\nBecause the network is not working!", Toast.LENGTH_LONG).show();
            } else {
                showDialog(1);
                DownloadData downloadData = new DownloadData();
                downloadData.execute();
            }
        } else {
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("data",itemDataXoSoList);
//            bundle.putInt("start",CurentItem);

            Log.d("tag = startIntent", " before start");
            Intent myin = new Intent(getApplicationContext(), DetailShow.class);
            Log.d("tag = startIntent", " before start2");
            myin.putExtra("start", CurentItem);
            myin.putExtra("data", itemDataXoSoList);
            Log.d("tag = startIntent", " before start3");
            startActivityForResult(myin, 441991);
            Log.d("tag = startIntent", " before start4");
            reallyToUpdate();

        }
    }

    //download city data
    private ControlCity downloadUrlCity() throws IOException {
        //String strUrl="http://lotto.vilalab.com/getChannelJSON.php";
        String strUrl = "http://lotto.vilalab.com/training/getCityJson.php";
        InputStream iStream = null;
        ControlCity controlCity = new ControlCity();
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();
            // Connecting to url
            urlConnection.connect();
            // Reading data from url
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            Gson gson = new Gson();
            Type listType = new TypeToken<ControlCity>() {
            }.getType();
            controlCity = gson.fromJson(br, listType);
        } catch (Exception e) {
            Log.d("check ----------------------", "error: " + e.getMessage());
            return null;
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return controlCity;
    }


    //download award data
    private List<ItemDataXoSo> downloadUrlAward(String id, int page) throws IOException {
        String strUrl = "http://lotto.vilalab.com/training/getAwardJson.php?citylink=" + id + "&page=" + page;
        InputStream iStream = null;
        List<ItemDataXoSo> _data = new ArrayList<ItemDataXoSo>();
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();
            // Connecting to url
            urlConnection.connect();
            // Reading data from url
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            Gson gson = new Gson();
            Type listType = new TypeToken<List<ItemDataXoSo>>() {
            }.getType();
            _data = gson.fromJson(br, listType);
        } catch (Exception e) {
            Log.d("check ----------------------", "error: " + e.getMessage());
            return null;
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return _data;

    }

    /////////////////_______________________________________________________________________
    //download data moi nhat
    private class DownloadData extends AsyncTask<Void, Void, List<ItemDataXoSo>> {
        // Downloading data in non-ui thread
        @Override
        protected List<ItemDataXoSo> doInBackground(Void... url) {
            if (!isOnline()) {
                return null;
            }
            try {
                return downloadUrlAward(idNumber, pageNumberx[pageNumber]);
            } catch (Exception e) {
                Log.d("missing when down award: ", e.getMessage());
            }
            return null;
        }

        // doInBackground()
        @Override
        protected void onPostExecute(List<ItemDataXoSo> result) {
            super.onPostExecute(result);
            List<ItemDataXoSo> resuftData = hashMapData.get(idNumber);
            if (result == null) {
                pageNumberx[pageNumber]--;
                dismissDialog(1);
                Toast.makeText(getApplicationContext(),
                        "Sorry! \nYou can't load any things!", Toast.LENGTH_LONG).show();
                updateShowDetail();
            } else {
                try {

                    if (resuftData == null || resuftData.size() <= 0) {
                        resuftData = new ArrayList<ItemDataXoSo>();
                    }
                    for (int i = 0; i < result.size(); i++) {
                        resuftData.add(0, result.get(i));
                    }
                    hashMapData.put(idNumber, resuftData);
                } catch (Exception e) {
                    Log.d("wrong!", " miss after download");
                }
                dismissDialog(1);
                if (newOld) {
                    CurentItem = result.size() - 1;
                    updateShowDetail();
                } else {
                    CurentItem = result.size();
                }
            }
        }
    }

    /////////////////_______________________________________________________________________
    //download cac tinh cua trang web tai dia chi co san
    private class DownloadCity extends AsyncTask<Void, Void, ControlCity> {
        // Downloading data in non-ui thread
        @Override
        protected ControlCity doInBackground(Void... url) {
            try {
//                BufferedReader br = downloadUrlAward("");
                return downloadUrlCity();
            } catch (Exception e) {
                Log.d("test Gson---", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(ControlCity result) {
            super.onPostExecute(result);
            myControlCity = result;
            int countCity = 0;
            ///////////////////////////////////////////////////////
            //get mien bac
            List<String> mb = new ArrayList<String>();
            for (int i = 0; i < myControlCity.bac.getLinks().length; i++) {
                mb.add(myControlCity.bac.getDisplays()[i]);
                countCity++;
            }
            hashMapCity.put(header.get(0), mb);
            ///////////////////////////////////////////////////////
            //get mien trung
            List<String> mt = new ArrayList<String>();
            for (int i = 0; i < myControlCity.trung.getLinks().length; i++) {
                mt.add(myControlCity.trung.getDisplays()[i]);
                countCity++;
            }
            hashMapCity.put(header.get(1), mt);
            ///////////////////////////////////////////////////////
            //get mien nam
            List<String> mn = new ArrayList<String>();
            for (int i = 0; i < myControlCity.nam.getLinks().length; i++) {
                mn.add(myControlCity.nam.getDisplays()[i]);
                countCity++;
            }
            hashMapCity.put(header.get(2), mn);

            expandableListView.setAdapter(adapterExpandableListView);
            //begin page
            pageNumberx = new int[countCity];
            for (int i = 0; i < countCity; i++) {
                pageNumberx[i] = 0;
            }
            dismissDialog(0);
        }
    }
}