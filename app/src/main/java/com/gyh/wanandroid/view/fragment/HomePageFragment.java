package com.gyh.wanandroid.view.fragment;


import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.base.gyh.baselib.base.BaseFragment;
import com.base.gyh.baselib.utils.mylog.Logger;
import com.bumptech.glide.Glide;
import com.gyh.wanandroid.R;
import com.gyh.wanandroid.data.bean.ArticleDataBean;
import com.gyh.wanandroid.data.bean.BannerDataBean;
import com.gyh.wanandroid.databinding.FragmentHomePage3Binding;
import com.gyh.wanandroid.viewmodule.HomePageViewModel;
import com.jaeger.library.StatusBarUtil;

import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomePageFragment extends BaseFragment implements HomePageViewModel.OnHomeDataListener {


    private FragmentHomePage3Binding bindView;

    public HomePageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_page3, container, false);
        //得到当前界面的装饰视图
        bindView = FragmentHomePage3Binding.bind(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();

    }

    private void initData() {
        HomePageViewModel viewModel = new HomePageViewModel(this, this);
        bindView.setViewModel(viewModel);

    }

    @Override
    public void loadData() {

    }

    @Override
    public void onError(String msg) {
        Logger.d("%s+++++++%s","guoyhonError",msg);
    }

    @Override
    public void onArticle(ArticleDataBean data) {
        Logger.d("%s+++++++%s","guoyhArticleDataBean",data.getDatas().get(0).getDesc());

    }

    @Override
    public void onBanner(List<BannerDataBean> data) {
        bindView.homePageBanner.setAdapter(new BGABanner.Adapter<ImageView,BannerDataBean>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, @Nullable BannerDataBean model, int position) {
                Glide.with(getActivity()).load(model.getImagePath()).into(itemView);
            }


        });
        bindView.homePageBanner.setData(data,null);

    }

}