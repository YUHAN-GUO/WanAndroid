package com.gyh.wanandroid.view.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.gyh.wanandroid.data.bean.KnowledgeBean;

import java.util.ArrayList;

/**
 * Created by GUOYH on 2019/5/31.
 */
public class KnowledgeArticleViewPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<KnowledgeBean.ChildrenBean> tab;
    private ArrayList<Fragment> fragments;

    public KnowledgeArticleViewPagerAdapter(FragmentManager fm, ArrayList<KnowledgeBean.ChildrenBean> tab, ArrayList<Fragment> fragments) {
        super(fm);
        this.tab = tab;
        this.fragments = fragments;
    }

    public KnowledgeArticleViewPagerAdapter(FragmentManager fm) {
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

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tab.get(position).getName();
    }
}
