package com.example.tranmanhhung.myplacevng;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import ItemClass.vng.ItemPlace;
import ViewPaper.tab.vng.ItemAdapter;
import json.place.GetUrl;


public class General extends ListFragment {

    public static ListView listView;
    public  static ItemAdapter ArrdapterGeneral;
    public static String  linkGeneral = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?radius=5000&language=vi-VN&key="+MainActivity.keyAPI+"&location="+MainActivity.latitude+","+MainActivity.longitude;
    public static ArrayList<ItemPlace>ArrayGeneral = new ArrayList<>();
    public General() {
        new GetUrl(0,getActivity()).execute(linkGeneral);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       ArrdapterGeneral = new ItemAdapter(getContext(), R.layout.layout_customs_item, ArrayGeneral);
        setListAdapter(ArrdapterGeneral);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    @Override
    public void onStart() {
        super.onStart();
        /** Setting the multiselect choice mode for the listview */
        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

    }


}
