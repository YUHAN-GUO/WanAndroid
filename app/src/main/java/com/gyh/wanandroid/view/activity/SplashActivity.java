package com.gyh.wanandroid.view.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.base.gyh.baselib.base.BaseActivity;
import com.gyh.wanandroid.R;
import com.gyh.wanandroid.databinding.ActivitySplashBinding;

import java.util.List;

import io.reactivex.Observable;

public class SplashActivity extends BaseActivity {

    private ActivitySplashBinding dataBinding;
    private boolean isTo = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        dataBinding.splashJump.postDelayed(new Runnable() {
            @Override
            public void run() {
                startToMain();
            }
        },5000);
        dataBinding.splashJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startToMain();
            }
        });
    }
    private void startToMain(){
        if (isTo){
            startActivity(MainActivity.class);
            isTo = false;
        }
    }
}
