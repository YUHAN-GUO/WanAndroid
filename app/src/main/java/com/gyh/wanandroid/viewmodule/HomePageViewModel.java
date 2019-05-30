package com.gyh.wanandroid.viewmodule;

import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.base.gyh.baselib.annotation.Constant;
import com.base.gyh.baselib.annotation.LoadType;
import com.base.gyh.baselib.base.BaseFragment;
import com.base.gyh.baselib.base.IBaseHttpResultCallBack;
import com.base.gyh.baselib.base.IBaseHttpResultTypeCallBack;
import com.base.gyh.baselib.data.remote.retrofit.HttpUtils;
import com.base.gyh.baselib.utils.mylog.Logger;
import com.base.gyh.baselib.widgets.view.LoadingPage;
import com.bumptech.glide.Glide;
import com.gyh.wanandroid.app.AppConstant;
import com.gyh.wanandroid.data.bean.ArticleDataBean;
import com.gyh.wanandroid.data.bean.BannerDataBean;
import com.gyh.wanandroid.data.retrofit.DataService;
import com.gyh.wanandroid.databinding.FragmentHomePage3Binding;
import com.gyh.wanandroid.view.activity.MainActivity;
import com.gyh.wanandroid.view.adapter.HomePageRlvAdapter;
import com.gyh.wanandroid.view.fragment.HomePageFragment;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by GUOYH on 2019/5/25.
 */
public class HomePageViewModel {


    public final ObservableBoolean mBannerIsSuccess = new ObservableBoolean(false);
    private BaseFragment rxFragment;
    private int page = 0;
    private FragmentHomePage3Binding binding;
    private HomePageRlvAdapter rlvAdapter;
    private List<ArticleDataBean.DatasBean> datasBeanList;

    public HomePageViewModel(FragmentHomePage3Binding binding, BaseFragment rxFragment) {
        this.binding = binding;
        this.rxFragment = rxFragment;
        binding.homeContent.setLayoutManager(new LinearLayoutManager(rxFragment.getContext()));
        rlvAdapter=new HomePageRlvAdapter(datasBeanList);
        binding.homeContent.setAdapter(rlvAdapter);
        binding.homeRefresh.setRefreshFooter(new ClassicsFooter(rxFragment.getContext()));
        binding.homeRefresh.setEnableHeaderTranslationContent(true);
        getHomeData();
        setListener();
    }

    private void setListener() {
        binding.homeToolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //home页toolbar返回键的点击
                LiveEventBus.get().with(AppConstant.MAIN_FRAGMENT_BACK, int.class).post(0);
            }
        });
    }

    public void getHomeData() {
        getArticel(Constant.OnLoadType.frist);
        getBanner();
    }

    private void getArticel(@LoadType int type) {
        switch (type) {
            case Constant.OnLoadType.frist:
                page = 0;
                break;
            case Constant.OnLoadType.refresh:
                page = 0;
                break;
            case Constant.OnLoadType.loadMore:
                page++;
                break;
            default:
                break;
        }
        HttpUtils.obserableUtils(DataService.getService().getArticlesData(page), rxFragment, new IBaseHttpResultTypeCallBack<ArticleDataBean>() {
            @Override
            public void onSuccess(ArticleDataBean data, int type) {
                rxFragment.removeLoadingPage();
                initRlv(data,type);
            }

            @Override
            public void onError(Throwable e) {
                rxFragment.removeLoadingPage();

            }
        },type);
    }

    private void getBanner() {
        rxFragment.showLoadingPage(LoadingPage.MODE_ONLY_SHOW_LOGING);
        HttpUtils.obserableUtils(DataService.getService().getHomeBannerData(), rxFragment, new IBaseHttpResultCallBack<List<BannerDataBean>>() {
            @Override
            public void onSuccess(List<BannerDataBean> data) {
                mBannerIsSuccess.set(true);
                rxFragment.removeLoadingPage();
                initBanner(data);
            }

            @Override
            public void onError(Throwable e) {
                rxFragment.removeLoadingPage();
            }
        });
    }

    private void initRlv(ArticleDataBean data,int type) {
        if (datasBeanList==null){
            datasBeanList = new ArrayList<>();
        }
        List<ArticleDataBean.DatasBean> datas = data.getDatas();
        if (rlvAdapter==null){
            rlvAdapter = new HomePageRlvAdapter(datasBeanList);
        }
        switch (type) {
            case Constant.OnLoadType.frist:
                datasBeanList.clear();
                datasBeanList.addAll(datas);
                break;
            case Constant.OnLoadType.refresh:
                binding.homeRefresh.finishRefresh();
                datasBeanList.clear();
                datasBeanList.addAll(datas);
                break;
            case Constant.OnLoadType.loadMore:
                binding.homeRefresh.finishLoadMore();
                datasBeanList.addAll(datas);
                break;
            default:
                break;
        }
        rlvAdapter.setNewData(datasBeanList);
        refrashAndLoadMoreListener();
    }

    private void refrashAndLoadMoreListener() {
        rlvAdapter.setOnCollectListener(new HomePageRlvAdapter.OnCollectListener() {
            @Override
            public void onCollectClick(int position, boolean isChecked) {
                if (binding.homeContent.getScrollState() == RecyclerView.SCROLL_STATE_IDLE && (!binding.homeContent.isComputingLayout())) {
                    rlvAdapter.setCollect(position, isChecked);
                }
            }
        });
        binding.homeRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getArticel(Constant.OnLoadType.loadMore);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getArticel(Constant.OnLoadType.refresh);
            }
        });
    }

    private void initBanner(List<BannerDataBean> data) {
        binding.homePageBanner.setAdapter(new BGABanner.Adapter<ImageView, BannerDataBean>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, @Nullable BannerDataBean model, int position) {
                Glide.with(binding.getRoot()).load(model.getImagePath()).into(itemView);
            }
        });
        binding.homePageBanner.setData(data, null);
    }

}
