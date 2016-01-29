package json.distance;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tranmanhhung.myplacevng.Cafe;
import com.example.tranmanhhung.myplacevng.Food;
import com.example.tranmanhhung.myplacevng.General;
import com.example.tranmanhhung.myplacevng.Hospital;
import com.example.tranmanhhung.myplacevng.MainActivity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ItemClass.vng.ItemPlace;
import json.place.MyGsonArr;
import json.place.geometry;
import json.place.location;
import json.place.results;

/**
 * Created by TranManhHung on 27-Jan-16.
 */
public class GetUrldistance extends AsyncTask<String, Void, String> {

    int stt;
    Context context;
    TextView t;
    public GetUrldistance( TextView textView, Context context) {
        this.context =context;
        t =textView;


    }

    protected String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
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

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception while downloading url", e.toString());
        } finally {
            try {
                iStream.close();
                urlConnection.disconnect();
            }
catch (Exception e)
{

}
        }
        return data;
    }


    @Override
    protected String doInBackground(String... params)
    {
        try {
            String result = "";
            try {
                result = downloadUrl(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Gson gson = new Gson();
            MyGsonDistance myGson = gson.fromJson(result, MyGsonDistance.class);
            Log.e("Ketqua", myGson.routes.toString());
            JsonArray mJsonArr = myGson.routes;


            for (int i = 0; i < mJsonArr.size(); i++)// lay du lieu vong 1
            {
                Gson gson1 = new Gson();
                Routes mRoutes = gson1.fromJson(mJsonArr.get(i), Routes.class);
                JsonArray mJsonArr2 = mRoutes.legs;
                for (int i2 = 0; i2 < mJsonArr2.size(); i2++)// lay du lieu vong 2
                {

                    Gson gson2 = new Gson();
                    Legs mLegs = gson2.fromJson(mJsonArr2.get(i2), Legs.class);
                    // lay khoang cach
                    JsonElement array = mLegs.distance;
                    String s = new Gson().fromJson(array, distance.class).text.toString();
                    return s;
                }
            }
        }catch (Exception e)
        {
            //Toast.makeText(context, "Kiểm tra kết nối Internet.", Toast.LENGTH_LONG).show();
        }
        return null;
    }
    @Override
    protected void onPostExecute(String params)
    {
        super.onPostExecute(params);

        t.setText(params);
        //new ParseJson(stt).execute(params);
    }
}

