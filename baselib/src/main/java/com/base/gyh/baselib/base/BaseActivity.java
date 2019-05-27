package com.base.gyh.baselib.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.base.gyh.baselib.R;
import com.base.gyh.baselib.broadcast.NetBroadcastReceiver;
import com.base.gyh.baselib.utils.ActivityUtil;
import com.base.gyh.baselib.utils.NetworkUtil;
import com.base.gyh.baselib.utils.StatusBarUtilTextColor;
import com.base.gyh.baselib.widgets.view.LoadingPage;
import com.jaeger.library.StatusBarUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.List;

/*
 * created by taofu on 2018/11/28
 **/
public abstract class BaseActivity extends RxAppCompatActivity implements NetBroadcastReceiver.NetChangeListener {

    public static NetBroadcastReceiver.NetChangeListener netEvent;// 网络状态改变监听事件
    protected LoadingPage mLoadingPage;//loading
    private FragmentManager mFragmentManager;//添加fragment 事务管理器
    private boolean isView =false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 添加到Activity工具类
        ActivityUtil.getInstance().addActivity(this);

        // 初始化netEvent
        netEvent = this;
        // 执行初始化方法
        NetBroadcastReceiver.registerReceiver(this);//网络变化广播注册
        setTranslucentStatus();
        ActivityUtil.getInstance().addActivity(this);
        mFragmentManager = getSupportFragmentManager();
    }


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        StatusBarUtil.setColorNoTranslucent(this,ContextCompat.getColor(this,R.color.color_blue));
//        StatusBarUtil.setTransparent(this);
        StatusBarUtilTextColor.setStatusBarMode(this,true);
    }
    @Override
    protected void onResume() {
        super.onResume();
        Resources resources = this.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.fontScale = 1;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    @Override
    protected void onDestroy() {
        // Activity销毁时，提示系统回收
        // System.gc();
        netEvent = null;
        // 移除Activity
        ActivityUtil.getInstance().removeActivity(this);
        NetBroadcastReceiver.unregisterReceiver(this);//网络变化
        super.onDestroy();
    }

    // 封装公共的添加Fragment的方法
    public void addFragment(Class<? extends BaseFragment> zClass,int layoutId){
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        String tag =  zClass.getName();

        // 从 fragmentManager中查找这个fragment是否存在，
        Fragment fragment = mFragmentManager.findFragmentByTag(tag);

        if(fragment != null){ // 如果存在就不用重新创建
            if(fragment.isAdded()){ // 如果 fragment 已经被添加
                if(fragment.isHidden()){ // 如果fragment 已经被添加，并且处于隐藏状态，那么显示
                    transaction.show(fragment); // 显示 fragment
                    hideOtherPage(transaction,fragment); // 隐藏其他fragment
                }
            }else{ // 如果 fragment存在，但是没有被添加到activity，那么执行下面添加，（这种一般发生在activity 横竖屏切换）
                transaction.add(layoutId, fragment);
                hideOtherPage(transaction, fragment);
            }
        }else{
            // 如果没有从fragmentManager 中通过tag 找到fragment,那么创建一个新的fragment 实例
            try {
                fragment = zClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            if(fragment != null){
                transaction.add(layoutId, fragment,tag);
                hideOtherPage(transaction, fragment);
            }
        }

        transaction.commit();
    }

    // 隐藏除了将要显示的fragment 以外的其他所有fragment
    private void hideOtherPage(FragmentTransaction transaction,Fragment willShowFragment){

        List<Fragment> fragments =  mFragmentManager.getFragments();
        for(Fragment fragment : fragments){
            if(fragment != willShowFragment){
                transaction.hide(fragment);
            }
        }

    }


    protected void showLoadingPage(int mode) {
        showLoadingPage(android.R.id.content,mode);
    }

    protected void showLoadingPage(int groupId,int mode) {
        View v = findViewById(groupId);
        if (v instanceof ViewGroup) {
            showLoadingPage((ViewGroup) v,mode);
        }
    }

    protected void showLoadingPage(ViewGroup group,int mode){
        if(mLoadingPage == null){
            mLoadingPage = (LoadingPage) LayoutInflater.from(this).inflate(R.layout.layout_laoding_page, group, false);
            group.addView(mLoadingPage);
        }
        mLoadingPage.startLoading(mode);
    }

    protected void onError(){

        if(mLoadingPage != null){
            mLoadingPage.onError();
        }

    }
    protected void onError(String msg){
        if(mLoadingPage != null){
            mLoadingPage.onError(msg);
        }
    }
    protected void onError(LoadingPage.OnReloadCallBack callBack ,String msg){
        if(mLoadingPage != null){
            mLoadingPage.onError(callBack,msg);
        }
    }
    protected void removeLoadingPage(){
        if(mLoadingPage != null){
            ((ViewGroup)mLoadingPage.getParent()).removeView(mLoadingPage);
            mLoadingPage = null;
        }

    }
    /**
     * 获取当前设备状态栏高度
     *
     * @return
     */
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
    /**
     * 设置状态栏透明
     *
     * @param on
     */
    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
    //判断当前设备版本号是否为4.4以上，如果是，则通过调用setTranslucentStatus让状态栏变透明
    protected void setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
    }

    protected void startActivity(Class<? extends Activity> zClass){
        startActivity(new Intent(this,zClass));
    }
    protected void startActivity(Class<? extends Activity> zClass, Bundle bundle){
        startActivity(new Intent(this,zClass).putExtras(bundle));
    }
    protected void startActivityForResult(Class<? extends Activity> zClass,int coode){
        startActivityForResult(new Intent(this,zClass),coode);
    }
    protected void startActivityForResult(Class<? extends Activity> zClass, Bundle bundle,int coode){
        startActivityForResult(new Intent(this,zClass).putExtras(bundle),coode);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 点击手机上的返回键，返回上一层
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 移除Activity
            ActivityUtil.getInstance().removeActivity(this);
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 权限检查方法，false代表没有该权限，ture代表有该权限
     */
    public boolean hasPermission(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    /**
     * 权限请求方法
     */
    public void requestPermission(int code, String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, code);
    }

    /**
     * 处理请求权限结果事件
     *
     * @param requestCode  请求码
     * @param permissions  权限组
     * @param grantResults 结果集
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doRequestPermissionsResult(requestCode, grantResults);
    }

    /**
     * 处理请求权限结果事件
     *
     * @param requestCode  请求码
     * @param grantResults 结果集
     */
    public void doRequestPermissionsResult(int requestCode, @NonNull int[] grantResults) {
    }
    /**
     * 网络状态改变时间监听
     *
     * @param netWorkState true有网络，false无网络
     */
    @Override
    public void onNetChange(boolean netWorkState) {
        if (netWorkState){
//            Toast.makeText(this, "有网toast在BaseActivity", Toast.LENGTH_SHORT).show();
        }else{
//            Toast.makeText(this, "没网toast在BaseActivity", Toast.LENGTH_SHORT).show();

        }
    }



    /**
     * 获取网络类型
     * @return
     */
    public int getNeTType(){
        return NetworkUtil.getNetworkType(this);
    }

    /**
     * 判断是否有网
     * @return
     */
    public boolean getNeTIs(){
        return NetworkUtil.isNetworkConnected(this);
    }
}
