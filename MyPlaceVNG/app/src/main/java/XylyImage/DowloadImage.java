package XylyImage;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.URL;

/**
 * Created by TranManhHung on 17-Dec-15.
 */
public class DowloadImage extends AsyncTask<Image, Void,Image > {
    @Override
    protected Image doInBackground(Image... params) {
        Image image = params[0];
        try {
            URL imageURL = new URL(image.imageURL);
            image.bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
        } catch (IOException e) {
            // TODO: handle exception
            Log.e("error", "Downloading Image Failed");
            image.bitmap = null;
        }

        return image;

    }

    @Override
    protected void onPostExecute(Image image) {
        super.onPostExecute(image);

            image.imageView.setImageBitmap(image.bitmap);

    }
}
