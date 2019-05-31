package com.gyh.wanandroid.viewmodule;

import android.os.Bundle;
import android.service.autofill.Dataset;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.base.gyh.baselib.annotation.Constant;
import com.base.gyh.baselib.annotation.LoadType;
import com.base.gyh.baselib.base.BaseFragment;
import com.base.gyh.baselib.base.IBaseHttpResultTypeCallBack;
import com.base.gyh.baselib.data.remote.retrofit.HttpUtils;
import com.base.gyh.baselib.utils.mylog.Logger;
import com.base.gyh.baselib.widgets.view.LoadingPage;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyh.wanandroid.data.bean.KnowledgeArticleBean;
import com.gyh.wanandroid.data.retrofit.DataService;
import com.gyh.wanandroid.databinding.FragmentKnowledgeArticleChildBinding;
import com.gyh.wanandroid.view.activity.WebActivity;
import com.gyh.wanandroid.view.adapter.KnowledgeArticleChildRlvAdapter;
import com.gyh.wanandroid.view.fragment.KnowledgeArticleChildFragment;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

/**
 * Created by GUOYH on 2019/5/31.
 */
public class KnowledgeArticleChildViewModel implements OnRefreshLoadMoreListener {
    private FragmentKnowledgeArticleChildBinding binding;
    private BaseFragment fragment;
    private int cid;
    private int page = 0;
    private KnowledgeArticleChildRlvAdapter adapter;
    private int sumItems =0;

    public KnowledgeArticleChildViewModel(FragmentKnowledgeArticleChildBinding binding, BaseFragment fragment, int cid) {
        this.binding = binding;
        this.fragment = fragment;
        this.cid = cid;
        fragment.showLoadingPage(LoadingPage.MODE_ONLY_SHOW_LOGING);
        binding.fragmentKnowledgeArticleChildSmart.setOnRefreshLoadMoreListener(this);
        getData(Constant.OnLoadType.frist);
    }

    private void getData(@LoadType int type) {
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
        HttpUtils.obserableUtils(DataService.getService().getKnowledgeArticles(page, cid), fragment, new IBaseHttpResultTypeCallBack<KnowledgeArticleBean>() {
            @Override
            public void onSuccess(KnowledgeArticleBean data, int type) {
                initRlv(data, type);
            }

            @Override
            public void onError(Throwable e) {
                Logger.d("%s+++++++%s", "guoyh", e.getMessage());
                fragment.removeLoadingPage();

            }
        }, type);
    }

    private void initRlv(KnowledgeArticleBean data, @LoadType int type) {
        sumItems +=data.getDatas().size();
        if (adapter == null) {
            adapter = new KnowledgeArticleChildRlvAdapter(data.getDatas());
            binding.fragmentKnowledgeArticleChildRlv.setLayoutManager(new LinearLayoutManager(fragment.getContext()));
            binding.fragmentKnowledgeArticleChildRlv.setAdapter(adapter);
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    List<KnowledgeArticleBean.DatasBean> datas = adapter.getData();
                    String link = datas.get(position).getLink();
                    Bundle bundle = new Bundle();
                    bundle.putString("url",link);
                    fragment.startActivity(WebActivity.class,bundle);
                }
            });
        }
        switch (type) {
            case Constant.OnLoadType.frist:
                fragment.removeLoadingPage();
                adapter.setNewData(data.getDatas());
                break;

            case Constant.OnLoadType.refresh:
                sumItems = 0;
                adapter.setNewData(data.getDatas());
                binding.fragmentKnowledgeArticleChildSmart.finishRefresh();
                break;

            case Constant.OnLoadType.loadMore:
                if (data.getCurPage()>=data.getPageCount()){
                    binding.fragmentKnowledgeArticleChildSmart.finishLoadMoreWithNoMoreData();
                }else{
                    binding.fragmentKnowledgeArticleChildSmart.finishLoadMore();
                }
                adapter.addData(data.getDatas());
                break;
            default:
                break;
        }

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        getData(Constant.OnLoadType.loadMore);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        getData(Constant.OnLoadType.refresh);

    }
}

