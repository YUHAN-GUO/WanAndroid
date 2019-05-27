package com.gyh.wanandroid.viewmodule;

import android.databinding.ObservableBoolean;

import com.base.gyh.baselib.base.BaseFragment;
import com.base.gyh.baselib.base.IBaseHttpResultCallBack;
import com.base.gyh.baselib.data.remote.retrofit.HttpUtils;
import com.base.gyh.baselib.widgets.view.LoadingPage;
import com.gyh.wanandroid.data.bean.ArticleDataBean;
import com.gyh.wanandroid.data.bean.BannerDataBean;
import com.gyh.wanandroid.data.retrofit.DataService;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.List;

/**
 * Created by GUOYH on 2019/5/25.
 */
public class HomePageViewModel {

    public  interface OnHomeDataListener{
        void onError(String msg);
        void onArticle(ArticleDataBean data);
        void onBanner(List<BannerDataBean> data);
    }
    public final ObservableBoolean mBannerIsSuccess = new ObservableBoolean(false);
    private OnHomeDataListener onHomeDataListener;
    private BaseFragment rxFragment;
    private int page = 0;

    public void setOnHomeDataListener(OnHomeDataListener onHomeDataListener) {
        this.onHomeDataListener = onHomeDataListener;

    }


    public HomePageViewModel(BaseFragment rxFragment, OnHomeDataListener onHomeDataListener) {
        this.onHomeDataListener = onHomeDataListener;
        this.rxFragment = rxFragment;
        getHomeData();

    }
    public void getHomeData(){
        rxFragment.showLoadingPage(LoadingPage.MODE_ONLY_SHOW_LOGING);
        HttpUtils.obserableUtils(DataService.getService().getHomeBannerData(), rxFragment, new IBaseHttpResultCallBack<List<BannerDataBean>>() {
            @Override
            public void onSuccess(List<BannerDataBean> data) {
                onHomeDataListener.onBanner(data);
                mBannerIsSuccess.set(true);
                rxFragment.removeLoadingPage();
            }

            @Override
            public void onError(Throwable e) {
                rxFragment.removeLoadingPage();
                onHomeDataListener.onError(e.getMessage());
            }
        });
        HttpUtils.obserableUtils(DataService.getService().getArticlesData(page), rxFragment, new IBaseHttpResultCallBack<ArticleDataBean>() {
            @Override
            public void onSuccess(ArticleDataBean data) {
                onHomeDataListener.onArticle(data);
                rxFragment.removeLoadingPage();

            }

            @Override
            public void onError(Throwable e) {
                onHomeDataListener.onError(e.getMessage());
                rxFragment.removeLoadingPage();

            }
        });
    }


}
