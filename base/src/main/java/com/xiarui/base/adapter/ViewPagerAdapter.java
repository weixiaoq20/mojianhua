package com.xiarui.base.adapter;

import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> list_fragments;
    private List<String> titles = new ArrayList<>();


    public ViewPagerAdapter(FragmentManager fm, List<Fragment> list_fragments) {
        super(fm);
        if (list_fragments == null) {
            this.list_fragments = new ArrayList<>();
        } else {
            this.list_fragments = list_fragments;
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public Fragment getItem(int arg0) {
        return list_fragments.get(arg0);
    }

    @Override
    public int getCount() {
        return list_fragments.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titles.size() > 0) {
            return titles.get(position);
        }
        return "";
    }

    public void addTitle(String title) {
        titles.add(title);
    }

    public void setTitleData(List<String> title) {
        this.titles = title;
    }

    public void updateTitle(String title, int position) {
        titles.set(position, title);
    }

}
