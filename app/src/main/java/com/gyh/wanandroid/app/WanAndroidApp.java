package com.gyh.wanandroid.app;

import android.app.Application;

import com.base.gyh.baselib.base.BaseApplication;
import com.qw.soul.permission.SoulPermission;

/**
 * Created by GUOYH on 2019/5/25.
 */
public class WanAndroidApp extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
//        SoulPermission.init(this);
    }
}