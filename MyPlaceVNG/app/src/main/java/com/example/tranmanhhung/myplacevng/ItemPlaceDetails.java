package com.example.tranmanhhung.myplacevng;

import android.app.ActionBar;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import XylyImage.ImageArrayAdapter;
import json.DetailsPlace.GetUrlDetails;
import json.distance.GetUrldistance;
// Ham nay xu ly khi chon vao 1 item trong List. Dung de xem thong tin chi tiet
public class ItemPlaceDetails extends AppCompatActivity {
    public  static TextView txtname;
    public static  TextView txtaddress;
    public  static TextView txtphonenumber;
    public  static  String linkDetails ="https://maps.googleapis.com/maps/api/place/details/json?key=AIzaSyAcxOofSTbqZJGN2WjsLdMcq8YiTXBZzbE&placeid=";
    public static ListView listViewImage;
    public static ArrayList<String> arrLinkImage;
    TextView txtkhoangcach;
    public static TextView txtWebsite;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_place_details);
        if(isOnline()== false)
        {
            Toast.makeText(getApplicationContext(),"Kiểm tra kết nối Internet.", Toast.LENGTH_LONG).show();
            // Toast.makeText(getApplicationContext(),"Nhấn Tải Thêm để load lại Trang", Toast.LENGTH_LONG).show();

        }

        //actionBar.setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("FromListItemSelect");
        String placeID = bundle.getString("place_id");
        String name = bundle.getString("name");
        String linkDistance = bundle.getString("distance");


        txtkhoangcach =(TextView)findViewById(R.id.txtkhoangcach);
        txtaddress = (TextView)findViewById(R.id.txtdetailsAddress);
        txtname = (TextView)findViewById(R.id.txtdetailName);
        txtphonenumber = (TextView)findViewById(R.id.txDetailsphone);
        txtWebsite = (TextView)findViewById(R.id.txtwebsiteDetails);
        listViewImage =(ListView)findViewById(R.id.listImageViewDetails);
        arrLinkImage = new ArrayList<>();

        //imageArrayAdapter = new ImageArrayAdapter(this, R.layout.image_layout_activity, arrLinkImage);
       // listViewImage.setAdapter(imageArrayAdapter);
//
        new GetUrlDetails(this).execute(linkDetails+placeID);

        txtname.setText(name);
        new GetUrldistance(txtkhoangcach,getApplicationContext()).execute(linkDistance);


    }
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
