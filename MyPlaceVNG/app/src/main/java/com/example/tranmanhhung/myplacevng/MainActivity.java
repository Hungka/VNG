package com.example.tranmanhhung.myplacevng;


import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import GetGPS.GPSTracker;


public class MainActivity extends FragmentActivity {

    GPSTracker gps;
    public static  double latitude;
    public  static  double longitude;
    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    public  static String keyAPI ="AIzaSyAcxOofSTbqZJGN2WjsLdMcq8YiTXBZzbE";
    //public static String  link = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?radius=5000&language-vn-vi&key=AIzaSyA61FgkFX4r0131E59W_zxANonP2BF_FV8&location=10.844191,%20106.767974";
    TextView textViewToadoX;
    TextView textViewToadoY;

    String []tabsTitles = {"General", "Food", "Hospital", "Cafe"};

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(isOnline()== false)
        {
            Toast.makeText(getApplicationContext(),"Kiểm tra kết nối Internet, thoát và vào lại ứng dụng.", Toast.LENGTH_LONG).show();
           // Toast.makeText(getApplicationContext(),"Nhấn Tải Thêm để load lại Trang", Toast.LENGTH_LONG).show();

        }

        gps = new GPSTracker(this);


        // check if GPS enabled
        if(gps.canGetLocation()){

            latitude = gps.getLatitude();
             longitude = gps.getLongitude();

            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
        final TabHost tabHost=(TabHost)findViewById(android.R.id.tabhost);
        tabHost.setup();

        // Add TabHost
        for (int i = 0; i < tabsTitles.length; i++) {

            String tabName = tabsTitles[i];
            TabHost.TabSpec spec = tabHost.newTabSpec(tabName);
            spec.setContent(R.id.fakeTabContent);
            spec.setIndicator(tabName);
            tabHost.addTab(spec);
        }

        viewPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager(), tabsTitles.length);

        viewPager.setAdapter(mAdapter);
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabId) {
                for (int i = 0; i < tabsTitles.length; i++) {
                    if (tabId.equals(tabsTitles[i])) {
                        viewPager.setCurrentItem(i);
                        break;
                    }
                }
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                tabHost.setCurrentTab(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
            }
        });
        textViewToadoX =(TextView)findViewById(R.id.left_txtToaDoX);
        textViewToadoY =(TextView)findViewById(R.id.left_txtToaDoY);
        textViewToadoX.setText("- Latitude : " + String.valueOf(latitude));
        textViewToadoY.setText("- Longitude : " + String.valueOf(longitude));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
