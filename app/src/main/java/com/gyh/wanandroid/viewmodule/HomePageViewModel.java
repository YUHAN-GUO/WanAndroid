package com.gyh.wanandroid.viewmodule;

import android.databinding.ObservableBoolean;
import android.os.Bundle;
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
import com.base.gyh.baselib.widgets.netstatae.INetErrorView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyh.wanandroid.app.AppConstant;
import com.gyh.wanandroid.data.bean.ArticleDataBean;
import com.gyh.wanandroid.data.bean.BannerDataBean;
import com.gyh.wanandroid.data.retrofit.DataService;
import com.gyh.wanandroid.databinding.FragmentHomePage3Binding;
import com.gyh.wanandroid.view.activity.WebActivity;
import com.gyh.wanandroid.view.adapter.HomePageRlvAdapter;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

import static com.base.gyh.baselib.widgets.netstatae.NetStateLayout.CONTENT_STATE_HIDE;
import static com.base.gyh.baselib.widgets.netstatae.NetStateLayout.CONTENT_STATE_SHOW_LOADING;
import static com.base.gyh.baselib.widgets.netstatae.NetStateLayout.CONTENT_STATE_SHOW_NET_ERROR;

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
    private String n;

    public HomePageViewModel(FragmentHomePage3Binding binding, BaseFragment rxFragment) {
        this.binding = binding;
        this.rxFragment = rxFragment;
        binding.homeContent.setLayoutManager(new LinearLayoutManager(rxFragment.getContext()));
        rlvAdapter = new HomePageRlvAdapter(datasBeanList);
        binding.homeContent.setAdapter(rlvAdapter);
        binding.homeRefresh.setRefreshFooter(new ClassicsFooter(rxFragment.getContext()));
        binding.homeRefresh.setEnableHeaderTranslationContent(true);
        getHomeData();
        setListener();
    }

    private void setListener() {
        binding.homePageState.setOnRetryClickListener(new INetErrorView.OnRetryClickListener() {
            @Override
            public void onRetryClicked() {
                getHomeData();
            }
        });
        binding.homeToolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //home页toolbar返回键的点击
                LiveEventBus.get().with(AppConstant.MAIN_FRAGMENT_BACK, int.class).post(0);
            }
        });
    }

    public void getHomeData() {
        stateShow(CONTENT_STATE_SHOW_LOADING);
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
                initRlv(data, type);
                binding.homeContentView.setVisibility(View.VISIBLE);
                stateShow(CONTENT_STATE_HIDE);
            }

            @Override
            public void onError(Throwable e) {
                binding.homeContentView.setVisibility(View.GONE);

                stateShow(CONTENT_STATE_SHOW_NET_ERROR);
            }
        }, type);
    }

    private void getBanner() {
        HttpUtils.obserableUtils(DataService.getService().getHomeBannerData(), rxFragment, new IBaseHttpResultCallBack<List<BannerDataBean>>() {
            @Override
            public void onSuccess(List<BannerDataBean> data) {
                mBannerIsSuccess.set(true);
                stateShow(CONTENT_STATE_HIDE);
                initBanner(data);
                binding.homeContentView.setVisibility(View.VISIBLE);

            }

            @Override
            public void onError(Throwable e) {
                binding.homeContentView.setVisibility(View.GONE);
                stateShow(CONTENT_STATE_SHOW_NET_ERROR);

            }
        });
    }

    private void stateShow(int type) {
        binding.homePageState.setContentState(type);
    }

    private void initRlv(ArticleDataBean data, int type) {
        if (datasBeanList == null) {
            datasBeanList = new ArrayList<>();
        }
        List<ArticleDataBean.DatasBean> datas = data.getDatas();
        if (rlvAdapter == null) {
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
        binding.homePageBanner.setDelegate(new BGABanner.Delegate<ImageView, BannerDataBean>() {
            @Override
            public void onBannerItemClick(BGABanner banner, ImageView itemView, @Nullable BannerDataBean model, int position) {
                String url = model.getUrl();
                Bundle bundle = new Bundle();
                bundle.putString("url", url);
                rxFragment.startActivity(WebActivity.class, bundle);
            }

        });
        rlvAdapter.setOnCollectListener(new HomePageRlvAdapter.OnCollectListener() {
            @Override
            public void onCollectClick(int position, boolean isChecked) {
                if (binding.homeContent.getScrollState() == RecyclerView.SCROLL_STATE_IDLE && (!binding.homeContent.isComputingLayout())) {
                    rlvAdapter.setCollect(position, isChecked);
                }
            }
        });
        rlvAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<ArticleDataBean.DatasBean> data = adapter.getData();
                String link = data.get(position).getLink();
                Bundle bundle = new Bundle();
                bundle.putString("url", link);
                rxFragment.startActivity(WebActivity.class, bundle);
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
