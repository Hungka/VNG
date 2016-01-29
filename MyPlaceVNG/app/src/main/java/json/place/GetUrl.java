package json.place;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.example.tranmanhhung.myplacevng.Cafe;
import com.example.tranmanhhung.myplacevng.Food;
import com.example.tranmanhhung.myplacevng.General;
import com.example.tranmanhhung.myplacevng.Hospital;
import com.example.tranmanhhung.myplacevng.MainActivity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ItemClass.vng.ItemPlace;

/**
 * Created by TranManhHung on 27-Jan-16.
 */
public class GetUrl extends AsyncTask<String, Void, String>{
    Activity context;
    int stt;

    public GetUrl(int stt, Activity context)
    {
        this.stt =stt;
        this.context = context;
    }
    protected  String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb  = new StringBuffer();

            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Exception while downloading url", e.toString());
        }finally {
            try {
                iStream.close();
                urlConnection.disconnect();
            } catch (Exception e) {

            }
        }
        return data;
    }



    @Override
    protected String doInBackground(String... params) {

        String data = "";
        try {
            data = downloadUrl(params[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        JsonArray jsonArrayResults = null;
        try {
            MyGsonArr myGsonArr = gson.fromJson(data, MyGsonArr.class);
            Log.e("Day la Json Lay Duoc", myGsonArr.results.toString());
            jsonArrayResults = myGsonArr.results;
        }
        catch (Exception e)
        {

        }


try {
    for (JsonElement jsonElement : jsonArrayResults) {
        Gson gson1 = new Gson();

        try {
            results myresults = gson1.fromJson(jsonElement, results.class);
            String icon = myresults.icon.toString();
            String place_id = myresults.place_id.toString();

            String name = myresults.name.toString();
            String vicinity = myresults.vicinity.toString();

            JsonElement geometry = myresults.geometry;

            geometry geo = gson1.fromJson(geometry, json.place.geometry.class);

            JsonElement loc = geo.location;
            location locat = gson1.fromJson(loc, json.place.location.class);
            String lat = locat.lat.toString();
            String lng = locat.lng.toString();

            double templat = Double.parseDouble(lat);
            double templng = Double.parseDouble(lng);
            double a = Math.pow((templat - MainActivity.latitude), 2);
            double b = Math.pow((templng - MainActivity.longitude), 2);
            double kc = Math.sqrt(a + b);

            String khoangcach = "http://maps.googleapis.com/maps/api/directions/json?origin=" + templat + "," + templng + "&destination=" + MainActivity.latitude + "," + MainActivity.longitude + "&alternatives=true&language=vi-VN&sensor=false";
            ItemPlace itemPlace = new ItemPlace();
            itemPlace.setIcon(icon);
            itemPlace.setName(name);
            itemPlace.setPlace_id(place_id);
            itemPlace.setLat(lat);
            itemPlace.setLng(lng);
            itemPlace.setKhoangcach(khoangcach);
            itemPlace.setVicinity(vicinity);
            // arrayListItemPlace.add(itemPlace);

            if (stt == 0) {
                General.ArrayGeneral.add(itemPlace);
            } else if (stt == 1) {
                Food.ArrayFood.add(itemPlace);
            } else if (stt == 2) {
                Hospital.ArrayHospital.add(itemPlace);
            } else {
                Cafe.ArrayCafe.add(itemPlace);
            }

        } catch (Exception e) {
            e.printStackTrace();
            int w = 0;
        }
    }
}catch (Exception e)
{

}
        return data;
    }

    @Override
    protected void onPostExecute(String params) {
        super.onPostExecute(params);

        switch (stt)
        {
            case 0:
            {
                General.ArrdapterGeneral.notifyDataSetChanged();
                return;
            }
            case 1 :
            {
                Food.ArrAdapterFood.notifyDataSetChanged();   return;
            }
            case 2:
            {
                Hospital.ArrdapterHospital.notifyDataSetChanged();
                return;
            }
            case 3:
            {
                Cafe.ArrAdapterCafe.notifyDataSetChanged();
                return;
            }
        }
        //new ParseJson(stt).execute(params);
    }
}

