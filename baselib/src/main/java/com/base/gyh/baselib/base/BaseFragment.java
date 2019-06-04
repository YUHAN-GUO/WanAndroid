package com.base.gyh.baselib.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.support.RxFragment;

/*
 * created by taofu on 2018/11/28
 **/
public abstract class BaseFragment extends RxFragment {

    private View view;
    private BaseActivity mBaseActivity;
    /**
     * 是否初始化过布局
     */
    protected boolean isViewInitiated;
    /**
     * 当前界面是否可见
     */
    protected boolean isVisibleToUser;
    /**
     * 是否加载过数据
     */
    protected boolean isDataInitiated;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof BaseActivity) {
            mBaseActivity = (BaseActivity) activity;
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = super.onCreateView(inflater, container, savedInstanceState);;
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        //加载数据
        prepareFetchData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.view = null;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if (isVisibleToUser) {
            prepareFetchData();
        }
    }

    public void prepareFetchData() {
        prepareFetchData(false);
    }

    /**
     * 判断懒加载条件
     *
     * @param forceUpdate 强制更新，好像没什么用？
     */
    public void prepareFetchData(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            loadData();
            isDataInitiated = true;
        }
    }
    /**
     * 懒加载
     */
    protected abstract void loadData();

    public void addFragment(Class<? extends BaseFragment> zClass, int layoutId) {
        if (mBaseActivity != null) {
            mBaseActivity.addFragment(zClass, layoutId);
        }
    }

    public void startActivity(Class<? extends Activity> zClass,Bundle bundle){
        if (mBaseActivity!=null){
            mBaseActivity.startActivity(zClass,bundle);
        }
    }
    public void startActivity(Class<? extends Activity> zClass){
        if (mBaseActivity!=null){
            mBaseActivity.startActivity(zClass);
        }
    }
    public void startActivityForResult(Class<? extends Activity> zClass,int code){
        startActivityForResult(new Intent(getContext(),zClass),code);

    }

    public void closeActivity() {

        if (mBaseActivity != null) {
            mBaseActivity.finish();
        }
    }

}
