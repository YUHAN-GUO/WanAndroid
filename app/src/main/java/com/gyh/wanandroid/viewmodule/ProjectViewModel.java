package com.gyh.wanandroid.viewmodule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.base.gyh.baselib.base.BaseFragment;
import com.base.gyh.baselib.base.IBaseHttpResultCallBack;
import com.base.gyh.baselib.data.remote.retrofit.HttpUtils;
import com.base.gyh.baselib.utils.mylog.Logger;
import com.gyh.wanandroid.data.bean.ProjectTreeBean;
import com.gyh.wanandroid.data.retrofit.DataService;
import com.gyh.wanandroid.databinding.FragmentProjectsBinding;
import com.gyh.wanandroid.view.adapter.ProjectVpAdapter;
import com.gyh.wanandroid.view.fragment.ProjectArticleChildFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GUOYH on 2019/6/1.
 */
public class ProjectViewModel {
    private FragmentProjectsBinding binding;
    private BaseFragment fragment;



    public ProjectViewModel(FragmentProjectsBinding binding, BaseFragment fragment) {
        this.binding = binding;
        this.fragment = fragment;

        getData();
    }

    private void getData() {
        HttpUtils.obserableUtils(DataService.getService().getProjectsTree(), fragment, new IBaseHttpResultCallBack<List<ProjectTreeBean>>() {
            @Override
            public void onSuccess(List<ProjectTreeBean> data) {
                initTab(data);
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(fragment.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                Logger.d("%s++++++++%s","guoyh",e.getMessage());
            }
        });
    }

    private void initTab(List<ProjectTreeBean> data) {
        ArrayList<Fragment> fragments = new ArrayList<>();
        String[] titles = new String[data.size()];
        for (int i = 0; i < data.size(); i++) {
            titles[i] = data.get(i).getName();
            ProjectArticleChildFragment childFragment = new ProjectArticleChildFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("id",data.get(i).getId());
            childFragment.setArguments(bundle);
            fragments.add(childFragment);
        }
        ProjectVpAdapter adapter = new ProjectVpAdapter(fragment.getChildFragmentManager(), fragments);
        binding.projectViewpager.setAdapter(adapter);
        binding.projectTablayout.setViewPager(binding.projectViewpager,titles);
//        binding.projectTablayout
    }
}
