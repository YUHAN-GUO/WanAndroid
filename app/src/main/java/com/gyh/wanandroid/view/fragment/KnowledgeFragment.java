package com.gyh.wanandroid.view.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.gyh.baselib.base.BaseFragment;
import com.gyh.wanandroid.R;
import com.gyh.wanandroid.databinding.FragmentKnowledgeBinding;
import com.gyh.wanandroid.viewmodule.KnowledgeViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class KnowledgeFragment extends BaseFragment {

    private FragmentKnowledgeBinding binding;

    public KnowledgeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_knowledge, container, false);
        binding = FragmentKnowledgeBinding.bind(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        KnowledgeViewModel viewModel = new KnowledgeViewModel(binding,KnowledgeFragment.this);
        binding.setViewModel(viewModel);
    }

    @Override
    public void loadData() {

    }

}
