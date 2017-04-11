package com.example.rednone.softteco;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<DataModel> list;

    public ViewPagerAdapter(FragmentManager fm, List<DataModel> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public int getCount() {
        int count = list.size() / 6;
        if ((list.size() % 6) == 0) {
            return count;
        } else {
            return ++count;
        }
    }

    @Override
    public Fragment getItem(int position) {
        List<DataModel> data = new ArrayList<>();


        if (position == 0) {

            for (int i = position; i < 6; i++) {
                data.add(list.get(i));
                if (i + 1 == list.size()) {
                    return GridViewFragment.getNewInstance(data);
                }
            }


        } else {
            int i = position * 6;
            int g = i + 6;
            for (; i < g; i++) {
                data.add(list.get(i));

                if (i + 1 == list.size()) {
                    return GridViewFragment.getNewInstance(data);
                }

            }
        }

        return GridViewFragment.getNewInstance(data);

    }


}