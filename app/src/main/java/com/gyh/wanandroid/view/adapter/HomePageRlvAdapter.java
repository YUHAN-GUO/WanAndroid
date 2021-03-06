package com.gyh.wanandroid.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.android.databinding.library.baseAdapters.BR;
import com.base.gyh.baselib.utils.mylog.Logger;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gyh.wanandroid.R;
import com.gyh.wanandroid.data.bean.ArticleDataBean;
import com.gyh.wanandroid.databinding.ItemHomeFragmentRlvBinding;

import java.util.List;

/**
 * Created by GUOYH on 2019/5/28.
 */
public class HomePageRlvAdapter extends BaseQuickAdapter<ArticleDataBean.DatasBean, HomePageRlvAdapter.MyViewHolder> {

    private int cloocet;
    private boolean is;

    public HomePageRlvAdapter(@Nullable List<ArticleDataBean.DatasBean> data) {
        super(R.layout.item_home_fragment_rlv, data);
    }

    @Override
    protected void convert(MyViewHolder helper, ArticleDataBean.DatasBean item) {
        if (helper.getLayoutPosition() == cloocet) {
            item.setCollect(is);
        }
        ViewDataBinding binding = helper.getBinding();
        binding.setVariable(BR.datasBean,item);
        ImageView collect = helper.getView(R.id.item_home_rlv_img_collect);
        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.isCollect()){
                    onCollectListener.onCollectClick(item,helper.getLayoutPosition(), false);
                }else{
                    onCollectListener.onCollectClick(item,helper.getLayoutPosition(), true);

                }
            }
        });

        if (item.isCollect()){
            collect.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_favorite_black));
        }else {
            collect.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_favorite_border));
        }
    }

    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        ViewDataBinding binding = DataBindingUtil.inflate(mLayoutInflater, layoutResId, parent, false);
        if (binding == null) {
            return super.getItemView(layoutResId, parent);
        }
        View view = binding.getRoot();
        view.setTag(R.id.BaseQuickAdapter_databinding_support, binding);
        return view;

    }
    public static class MyViewHolder extends BaseViewHolder {

        public MyViewHolder(View view) {
            super(view);
        }

        public ViewDataBinding getBinding() {
            return (ViewDataBinding) itemView.getTag(R.id.BaseQuickAdapter_databinding_support);
        }
    }


    private OnCollectListener onCollectListener;

    public void setOnCollectListener(OnCollectListener onCollectListener) {
        this.onCollectListener = onCollectListener;
    }

    public void setCollect(int position, boolean isCheck) {
        cloocet = position;
        is = isCheck;
        notifyItemChanged(position);
    }

    public interface OnCollectListener {
        void onCollectClick(ArticleDataBean.DatasBean item,int position, boolean isCheck);
    }
}
