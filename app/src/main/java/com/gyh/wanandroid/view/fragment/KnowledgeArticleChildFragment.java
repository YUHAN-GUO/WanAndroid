package com.gyh.wanandroid.view.fragment;


import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.gyh.baselib.base.BaseFragment;
import com.gyh.wanandroid.R;
import com.gyh.wanandroid.databinding.FragmentKnowledgeArticleChildBinding;
import com.gyh.wanandroid.databinding.FragmentKnowledgeBinding;
import com.gyh.wanandroid.viewmodule.KnowledgeArticleChildViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class KnowledgeArticleChildFragment extends BaseFragment {

    private FragmentKnowledgeArticleChildBinding binding;

    public KnowledgeArticleChildFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_knowledge_article_child, container, false);
        binding = FragmentKnowledgeArticleChildBinding.bind(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        int cid = bundle.getInt("cid");
        KnowledgeArticleChildViewModel viewModel = new KnowledgeArticleChildViewModel(binding, this, cid);
        binding.setViewModel(viewModel);
    }

    @Override
    protected void loadData() {

    }
}
