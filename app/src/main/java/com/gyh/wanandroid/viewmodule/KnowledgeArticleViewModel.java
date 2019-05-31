package com.gyh.wanandroid.viewmodule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.base.gyh.baselib.base.BaseActivity;
import com.base.gyh.baselib.base.BaseFragment;
import com.base.gyh.baselib.data.remote.retrofit.HttpUtils;
import com.base.gyh.baselib.widgets.view.MyToolbar;
import com.gyh.wanandroid.data.bean.KnowledgeBean;
import com.gyh.wanandroid.data.retrofit.DataService;
import com.gyh.wanandroid.databinding.ActivityKnowledgeArticleBinding;
import com.gyh.wanandroid.view.adapter.KnowledgeArticleViewPagerAdapter;
import com.gyh.wanandroid.view.fragment.KnowledgeArticleChildFragment;

import java.util.ArrayList;

/**
 * Created by GUOYH on 2019/5/31.
 */
public class KnowledgeArticleViewModel {
    private ActivityKnowledgeArticleBinding binding;
    private BaseActivity activity;
    private ArrayList<KnowledgeBean.ChildrenBean> tab;
    private int cid;
    private int page = 0;
    public KnowledgeArticleViewModel(ActivityKnowledgeArticleBinding binding, BaseActivity activity,ArrayList<KnowledgeBean.ChildrenBean> tab) {
        this.binding = binding;
        this.activity = activity;
        this.tab = tab;
        initView();
    }

    private void initView() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (KnowledgeBean.ChildrenBean childrenBean : tab) {
            KnowledgeArticleChildFragment childFragment = new KnowledgeArticleChildFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("cid",childrenBean.getId());
            childFragment.setArguments(bundle);
            fragments.add(childFragment);
        }
        KnowledgeArticleViewPagerAdapter pagerAdapter = new KnowledgeArticleViewPagerAdapter(activity.getSupportFragmentManager(), tab, fragments);
        binding.knowledgeArticleViewpager.setAdapter(pagerAdapter);
        binding.knowledgeArticleTablayout.setupWithViewPager(binding.knowledgeArticleViewpager);
        binding.knowledgeArticleMytoolbar.setOnBreakOrMenuClickListener(new MyToolbar.OnBreakOrMenuClickListener() {
            @Override
            public void onClick(int type) {
                if (type==MyToolbar.BACK){
                    activity.finish();
                }
            }
        });
    }

}
