package ViewPaper.tab.vng;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tranmanhhung.myplacevng.ItemPlaceDetails;
import com.example.tranmanhhung.myplacevng.R;

import java.util.List;

import ItemClass.vng.ItemPlace;
import XylyImage.DownloadIcon;
import json.distance.GetUrldistance;

/**
 * Created by TranManhHung on 27-Jan-16.
 */
public class ItemAdapter extends ArrayAdapter<ItemPlace> {
    Context mContext;
    List<ItemPlace> Listtemps;
    int inId;
    public ItemAdapter(Context context, int resource, List<ItemPlace> objects) {
        super(context, resource,  objects);
        mContext =context;
        Listtemps = objects;
        inId = resource;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(inId, null);

         //   convertView.setTag(image);
        }
        final ItemPlace itemPlace = Listtemps.get(position);

        // EditText editText = (EditText)convertView.findViewById(R.id.editText);
        TextView textten = (TextView) convertView.findViewById(R.id.txtten);
        TextView textDiaChi = (TextView) convertView.findViewById(R.id.txtdiachi);
       // TextView textKhoangCach = (TextView)convertView.findViewById(R.id.txtkhoangcach);
       // textKhoangCach.setText(String.valueOf(itemPlace.getKhoangcach()));
        ImageView imageViewIcon = (ImageView) convertView.findViewById(R.id.imageIcon);
        String icon = itemPlace.getIcon();


       // new GetUrldistance(textKhoangCach).execute(itemPlace.getKhoangcach());
        while (icon.contains("\""))
        {
            icon=icon.replace("\"", "");
        }
        new DownloadIcon(imageViewIcon).execute(icon);
        textten.setText(itemPlace.getName());
        textDiaChi.setText(itemPlace.getVicinity());

        textten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                String placeID = Listtemps.get(position).getPlace_id();
                //xoa dau ""

                while (placeID.contains("\"")) {
                    placeID = placeID.replace("\"", "");
                }
                bundle.putString("place_id", placeID);
                bundle.putString("name", Listtemps.get(position).getName());
                bundle.putString("distance", Listtemps.get(position).getKhoangcach());
                Intent intent = new Intent(getContext(), ItemPlaceDetails.class);
                intent.putExtra("FromListItemSelect", bundle);
                mContext.startActivity(intent);
            }
        });
        textDiaChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                String placeID = Listtemps.get(position).getPlace_id();
                //xoa dau ""

                while (placeID.contains("\"")) {
                    placeID = placeID.replace("\"", "");
                }
                bundle.putString("place_id", placeID);

                bundle.putString("name", Listtemps.get(position).getName());
                Intent intent = new Intent(getContext(), ItemPlaceDetails.class);
                intent.putExtra("FromListItemSelect", bundle);
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }
}
