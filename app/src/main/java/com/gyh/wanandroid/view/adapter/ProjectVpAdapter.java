package com.gyh.wanandroid.view.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.gyh.wanandroid.data.bean.KnowledgeBean;
import com.gyh.wanandroid.data.bean.ProjectTreeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GUOYH on 2019/6/1.
 */
public class ProjectVpAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> fragments;

    public ProjectVpAdapter(FragmentManager fm,  ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    public ProjectVpAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

}
