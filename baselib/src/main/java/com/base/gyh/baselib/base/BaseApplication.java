package com.base.gyh.baselib.base;

import android.app.Application;

import com.base.gyh.baselib.app.AppConfigSp;
import com.jeremyliao.liveeventbus.LiveEventBus;

import me.linkaipeng.autosp.AutoSharedPreferenceConfig;

/**
 * Created by GUOYH on 2019/5/5.
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();


        AutoSharedPreferenceConfig.getInstance().init(this);
        //
        //LiveEvent配置
        LiveEventBus.get()
                .config()
                .supportBroadcast(this)
                .lifecycleObserverAlwaysActive(true);
    }
}
