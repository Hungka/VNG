package json.DetailsPlace;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.tranmanhhung.myplacevng.ItemPlaceDetails;
import com.example.tranmanhhung.myplacevng.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import XylyImage.ImageArrayAdapter;


/**
 * Created by TranManhHung on 27-Jan-16.
 */
public class GetUrlDetails extends AsyncTask<String, Void, String>{

Context mContext;
    String address;
    String phone;

    String website;
    ArrayList<String> arrlinksImage;
    ImageArrayAdapter imageArrayAdapter;
   public GetUrlDetails(Context context)
    {
        mContext =context;
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
        }finally{
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
    protected String doInBackground(String... params) {
        String data = "";
        try {
            data = downloadUrl(params[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        arrlinksImage = new ArrayList<>();
        try {
            Gson gson = new Gson();
            MyGsonDetails myGsonDetails = gson.fromJson(data, MyGsonDetails.class);
            Log.e("Day la Json Lay Duoc", myGsonDetails.result.toString());
            JsonElement jsonElementDetails = myGsonDetails.result;



            try {
                results myresults = gson.fromJson(jsonElementDetails, results.class);
                //String icon = myresults.formatted_address.toString();
                // String place_id = myresults.place_id.toString();

                website = myresults.website.toString();
                phone = myresults.formatted_phone_number.toString();
                address = myresults.formatted_address.toString();
                JsonArray reviews = myresults.reviews;
                JsonArray linkImage = myresults.photos;


                for(JsonElement jsonElement : linkImage)
                {
                    Gson gson1 = new Gson();
                    String photo_references = gson1.fromJson(jsonElement , photos.class).photo_reference.toString();
                    while (photo_references .contains("\"")) {
                        photo_references  = photo_references .replace("\"", "");
                    }
                    String link = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+photo_references+"&key=AIzaSyA61FgkFX4r0131E59W_zxANonP2BF_FV8";
                    arrlinksImage.add(link);
                    int m=0;
                }

            } catch (Exception e) {
                e.printStackTrace();

            }

        }catch (Exception e)
        {

        }
        return data;
    }

    @Override
    protected void onPostExecute(String params) {
        super.onPostExecute(params);
        //new ParseJsonDetails(mContext).execute(params);\
        ItemPlaceDetails.txtphonenumber.setText(phone);
        ItemPlaceDetails.txtaddress.setText(address);
        ItemPlaceDetails.txtWebsite.setText(website);

        imageArrayAdapter = new ImageArrayAdapter(mContext, R.layout.image_layout_activity, arrlinksImage);
        ItemPlaceDetails.listViewImage.setAdapter(imageArrayAdapter);
    }
}

