package com.gyh.wanandroid.view.activity;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.base.gyh.baselib.base.BaseActivity;
import com.base.gyh.baselib.widgets.view.ZQImageViewRoundOval;
import com.gyh.wanandroid.R;
import com.gyh.wanandroid.app.AppConstant;
import com.gyh.wanandroid.view.fragment.HomePageFragment;
import com.gyh.wanandroid.view.fragment.KnowledgeFragment;
import com.gyh.wanandroid.view.fragment.NavigationFragment;
import com.gyh.wanandroid.view.fragment.ProjectsFragment;
import com.jeremyliao.liveeventbus.LiveEventBus;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private boolean isOpenDraw = false;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        FrameLayout mFrameLayout = (FrameLayout) findViewById(R.id.main_frameLayout);
        BottomNavigationView mMainbottomNavigationView = (BottomNavigationView) findViewById(R.id.main_bottomNavigationView);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView nickName = headerView.findViewById(R.id.user_nickName);
        ZQImageViewRoundOval headImg = headerView.findViewById(R.id.user_headImg);
        nickName.setOnClickListener(this);
        headerView.setOnClickListener(this);
        navigationView.setNavigationItemSelectedListener(this);
        addFragment(HomePageFragment.class, R.id.main_frameLayout);
        mMainbottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.homgpage:
                        addFragment(HomePageFragment.class, R.id.main_frameLayout);
                        return true;
                    case R.id.projets:
                        addFragment(ProjectsFragment.class, R.id.main_frameLayout);

                        return true;
                    case R.id.knowledge:
                        addFragment(KnowledgeFragment.class, R.id.main_frameLayout);

                        return true;
                    case R.id.navigation:
                        addFragment(NavigationFragment.class, R.id.main_frameLayout);

                        return true;
                    default:
                        //nothing to do
                        break;
                }
                return false;
            }
        });
        setListener();
    }

    private void setListener() {
        LiveEventBus.get().with(AppConstant.MAIN_FRAGMENT_BACK, int.class).observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_nickName:
            case R.id.user_headImg:
                startActivity(LoginAndRegisterActivity.class);
                break;
            default:
                break;

        }
    }
}
