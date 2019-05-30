package com.gyh.wanandroid.viewmodule;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
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
import com.gyh.wanandroid.data.bean.KnowledgeBean;
import com.gyh.wanandroid.data.retrofit.DataService;
import com.gyh.wanandroid.databinding.FragmentKnowledgeBinding;
import com.gyh.wanandroid.view.activity.KnowledgeArticleActivity;
import com.gyh.wanandroid.view.adapter.KnowledgeRlvAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.List;

/**
 * Created by GUOYH on 2019/5/30.
 */
public class KnowledgeViewModel implements OnRefreshLoadMoreListener {
    private FragmentKnowledgeBinding binding;
    private BaseFragment rxFragment;
    private List<KnowledgeBean> datas;
    private final KnowledgeRlvAdapter rlvAdapter;

    public KnowledgeViewModel(FragmentKnowledgeBinding binding, BaseFragment fragment) {
        this.binding = binding;
        this.rxFragment = fragment;
        binding.knowledgeRlv.setLayoutManager(new LinearLayoutManager(fragment.getContext()));
        rlvAdapter = new KnowledgeRlvAdapter(datas);
        binding.knowledgeRlv.setAdapter(rlvAdapter);
        binding.knowledgeRefresh.setRefreshFooter(new ClassicsFooter(rxFragment.getContext()));
        binding.knowledgeRefresh.setEnableHeaderTranslationContent(true);
        binding.knowledgeRefresh.setOnRefreshLoadMoreListener(this);
        getKnowData(0);
        rxFragment.showLoadingPage(LoadingPage.MODE_ONLY_SHOW_LOGING);
        setListener();
    }

    private void setListener() {
        rlvAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                rxFragment.startActivity(KnowledgeArticleActivity.class);
            }
        });
    }

    private void getKnowData(int ty) {
        HttpUtils.obserableUtils(DataService.getService().getKnowledgeData(), rxFragment, new IBaseHttpResultTypeCallBack<List<KnowledgeBean>>() {
            @Override
            public void onSuccess(List<KnowledgeBean> data, int type) {
                rlvAdapter.setNewData(data);
                if (ty ==1){
                    binding.knowledgeRefresh.finishRefresh();
                }else{
                    rxFragment.removeLoadingPage();

                }
            }

            @Override
            public void onError(Throwable e) {
                if (ty ==1){
                    binding.knowledgeRefresh.finishRefresh();
                }else{
                    rxFragment.removeLoadingPage();

                }
            }
        },Constant.OnLoadType.frist);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        binding.knowledgeRefresh.finishLoadMoreWithNoMoreData();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        getKnowData(1);
    }
}
