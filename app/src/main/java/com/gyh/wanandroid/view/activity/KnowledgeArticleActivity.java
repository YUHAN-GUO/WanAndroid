package com.gyh.wanandroid.view.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.base.gyh.baselib.base.BaseActivity;
import com.base.gyh.baselib.utils.mylog.Logger;
import com.gyh.wanandroid.R;
import com.gyh.wanandroid.data.bean.KnowledgeBean;
import com.gyh.wanandroid.databinding.ActivityKnowledgeArticleBinding;
import com.gyh.wanandroid.viewmodule.KnowledgeArticleViewModel;

import java.util.ArrayList;

public class KnowledgeArticleActivity extends BaseActivity {

    private ActivityKnowledgeArticleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<KnowledgeBean.ChildrenBean> tab = getIntent().getParcelableArrayListExtra("tab");
        binding = DataBindingUtil.setContentView(this, R.layout.activity_knowledge_article);
        KnowledgeArticleViewModel viewModel = new KnowledgeArticleViewModel(binding, this,tab);
        binding.setViewModel(viewModel);
    }
}
