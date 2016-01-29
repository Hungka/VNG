package XylyImage;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.tranmanhhung.myplacevng.R;

import java.util.ArrayList;

/**
 * Created by TranManhHung on 17-Dec-15.
 */
public class ImageArrayAdapter extends ArrayAdapter<String> {
    public static ArrayList<String> imageURLArray;
    public static LayoutInflater inflater;



    public ImageArrayAdapter(Context context, int textViewResourceId,  ArrayList<String> imageArray) {
        super(context, textViewResourceId, imageArray);

        inflater = ((Activity)context).getLayoutInflater();
        imageURLArray = imageArray;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Image image = null;
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.image_layout_activity, null);

            image = new Image();
            image.imageView = (ImageView)convertView.findViewById(R.id.imageView);
            convertView.setTag(image);
        }

        image = (Image)convertView.getTag();
        image.imageURL = imageURLArray.get(position);
        new DowloadImage().execute(image);
        return convertView;

    }
}
