package com.gyh.wanandroid.view.fragment.amain;


import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.base.gyh.baselib.base.BaseFragment;
import com.gyh.wanandroid.R;
import com.gyh.wanandroid.databinding.FragmentAndroidArticleBinding;
import com.gyh.wanandroid.view.fragment.HomePageFragment;
import com.gyh.wanandroid.view.fragment.KnowledgeFragment;
import com.gyh.wanandroid.view.fragment.NavigationFragment;
import com.gyh.wanandroid.view.fragment.ProjectsFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class AndroidArticleFragment extends BaseFragment {


    private FragmentAndroidArticleBinding binding;

    public AndroidArticleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_android_article, container, false);
        binding = FragmentAndroidArticleBinding.bind(view);
        return  view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addFragment(HomePageFragment.class,R.id.android_article_frameLayout);
       binding.androidArticleBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.homgpage:
                        addFragment(HomePageFragment.class, R.id.android_article_frameLayout);
                        return true;
                    case R.id.projets:
                        addFragment(ProjectsFragment.class, R.id.android_article_frameLayout);

                        return true;
                    case R.id.knowledge:
                        addFragment(KnowledgeFragment.class, R.id.android_article_frameLayout);

                        return true;
                    case R.id.navigation:
                        addFragment(NavigationFragment.class, R.id.android_article_frameLayout);

                        return true;
                    default:
                        //nothing to do
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void loadData() {

    }

}
