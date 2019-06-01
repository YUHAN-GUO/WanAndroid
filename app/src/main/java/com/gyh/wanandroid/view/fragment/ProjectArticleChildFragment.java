package com.gyh.wanandroid.view.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.gyh.baselib.base.BaseFragment;
import com.gyh.wanandroid.R;
import com.gyh.wanandroid.databinding.FragmentProjectArticleChildBinding;
import com.gyh.wanandroid.viewmodule.ProjectChildViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectArticleChildFragment extends BaseFragment {


    private int id;
    private FragmentProjectArticleChildBinding binding;

    public ProjectArticleChildFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_project_article_child, container, false);
        binding = FragmentProjectArticleChildBinding.bind(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle!=null){
            id = bundle.getInt("id");
            binding.setProjectChildViewModel(new ProjectChildViewModel(binding,this,id));
        }
    }

    @Override
    protected void loadData() {

    }
}
