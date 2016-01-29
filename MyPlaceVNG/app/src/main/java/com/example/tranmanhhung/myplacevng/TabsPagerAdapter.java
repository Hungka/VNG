package com.example.tranmanhhung.myplacevng;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by TranManhHung on 02-Dec-15.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {
    private int totalTabs ;
    public TabsPagerAdapter(FragmentManager fm, int lenght) {
        super(fm);
        totalTabs = lenght;

    }

    @Override
    public Fragment getItem(int position) {
        Bundle data = new Bundle();
        switch(position) {
            case 0: {
                General general= new General();
                general.setArguments(data);
                return general;
            }
            case 1: {
                Food food = new Food();
                food.setArguments(data);
                return food;
            }

            case 2: {
                Hospital hospital = new Hospital();
                hospital.setArguments(data);
                return hospital;
            }
            case 3:
            {
               Cafe cafe = new Cafe();
                cafe.setArguments(data);
                return cafe;
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
