package XylyImage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.net.URL;

/**
 * Created by TranManhHung on 28-Jan-16.
 */
public class DownloadIcon extends AsyncTask<String, Void,String > {
    ImageView mImage;
    Bitmap bitmap;
   public DownloadIcon(ImageView imageView)
    {
        mImage = imageView;
    }
    @Override
    protected String doInBackground(String... params) {
        String image = params[0];

        try {
            URL imageURL = new URL(image);
            bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
        } catch (IOException e) {
            // TODO: handle exception
            Log.e("error", "Downloading Image Failed");
            bitmap = null;
        }

        return image;

    }

    @Override
    protected void onPostExecute(String image) {
        super.onPostExecute(image);

        mImage.setImageBitmap(bitmap);

    }
}
