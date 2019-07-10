package com.gyh.wanandroid.view.activity;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.base.gyh.baselib.adapter.vpager.MyFragmentVPAdapter;
import com.base.gyh.baselib.base.BaseActivity;
import com.base.gyh.baselib.base.IBaseHttpResultCallBack;
import com.base.gyh.baselib.data.remote.retrofit.HttpUtils;
import com.base.gyh.baselib.utils.PermissUtils;
import com.base.gyh.baselib.utils.mylog.Logger;
import com.base.gyh.baselib.widgets.view.ZQImageViewRoundOval;
import com.gyh.wanandroid.R;
import com.gyh.wanandroid.app.AppConstant;
import com.gyh.wanandroid.data.bean.LoginBean;
import com.gyh.wanandroid.data.retrofit.DataService;
import com.gyh.wanandroid.view.fragment.HomePageFragment;
import com.gyh.wanandroid.view.fragment.KnowledgeFragment;
import com.gyh.wanandroid.view.fragment.NavigationFragment;
import com.gyh.wanandroid.view.fragment.ProjectsFragment;
import com.gyh.wanandroid.view.fragment.amain.AndroidArticleFragment;
import com.gyh.wanandroid.view.fragment.amain.MySummaryFragment;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.util.ArrayList;

import cn.bingoogolapple.bgabanner.transformer.DefaultPageTransformer;
import cn.bingoogolapple.bgabanner.transformer.FlipPageTransformer;
import cn.bingoogolapple.bgabanner.transformer.RotatePageTransformer;
import cn.bingoogolapple.bgabanner.transformer.ZoomStackPageTransformer;
import me.linkaipeng.autosp.AppConfigSpSP;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private boolean isOpenDraw = false;
    private DrawerLayout drawer;
    private TextView nickName;
    private ZQImageViewRoundOval headImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager viewPager = findViewById(R.id.main_viewPager);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        nickName = headerView.findViewById(R.id.user_nickName);
        headImg = headerView.findViewById(R.id.user_headImg);
        nickName.setOnClickListener(this);
        AndroidArticleFragment androidArticleFragment = new AndroidArticleFragment();
        MySummaryFragment mySummaryFragment = new MySummaryFragment();

        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(androidArticleFragment);
        fragments.add(mySummaryFragment);

        MyFragmentVPAdapter myFragmentVPAdapter = new MyFragmentVPAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(myFragmentVPAdapter);
        viewPager.setPageTransformer(true,new FlipPageTransformer());

        headerView.setOnClickListener(this);
        if (AppConfigSpSP.getInstance().getIsLogin()){
            String nickNameS = AppConfigSpSP.getInstance().getNickName();
            nickName.setText(nickNameS);
        }
        navigationView.setNavigationItemSelectedListener(this);
        setListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.d("%s+++++++++++%s","guoyh",requestCode+"---------------"+resultCode);
    }

    private void setListener() {
        LiveEventBus.get().with(AppConstant.MAIN_FRAGMENT_BACK, int.class).observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        LiveEventBus.get().with(AppConstant.LiveEventBusKey.UP_USER_MSG,LoginBean.class).observe(this, new Observer<LoginBean>() {
            @Override
            public void onChanged(@Nullable LoginBean loginBean) {
                nickName.setText(loginBean.getUsername());
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

        } else if (id == R.id.nav_loginOut) {
            loginOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loginOut() {
        HttpUtils.obserableUtils(DataService.getService().loginOut(), this, new IBaseHttpResultCallBack<Object>() {
            @Override
            public void onSuccess(Object data) {
                startActivity(LoginAndRegisterActivity.class);
                AppConfigSpSP.getInstance().setIsLogin(false);
                AppConfigSpSP.getInstance().setNickName("请先登录");
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_nickName:
            case R.id.user_headImg:
                if (!AppConfigSpSP.getInstance().getIsLogin()){
                    startActivity(LoginAndRegisterActivity.class);
                }
                break;
            default:
                break;

        }
    }
}
