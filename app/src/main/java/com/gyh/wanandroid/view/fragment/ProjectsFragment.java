package com.gyh.wanandroid.view.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.gyh.baselib.base.BaseFragment;
import com.gyh.wanandroid.R;
import com.gyh.wanandroid.databinding.FragmentProjectsBinding;
import com.gyh.wanandroid.viewmodule.ProjectViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectsFragment extends BaseFragment {


    private FragmentProjectsBinding binding;

    public ProjectsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_projects, container, false);
        binding = FragmentProjectsBinding.bind(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.setProjectViewModel(new ProjectViewModel(binding,this));
    }

    @Override
    public void loadData() {

    }

}
