package com.gyh.wanandroid.view.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gyh.wanandroid.R;
import com.gyh.wanandroid.data.bean.KnowledgeBean;
import com.gyh.wanandroid.databinding.ItemKnowledgeFragmentBinding;

import java.util.List;

/**
 * Created by GUOYH on 2019/5/30.
 */
public class KnowledgeRlvAdapter extends BaseQuickAdapter<KnowledgeBean,KnowledgeRlvAdapter.MyHolder> {
    public KnowledgeRlvAdapter( @Nullable List<KnowledgeBean> data) {
        super(R.layout.item_knowledge_fragment, data);
    }

    @Override
    protected void convert(MyHolder helper, KnowledgeBean item) {
        ItemKnowledgeFragmentBinding bind = (ItemKnowledgeFragmentBinding) helper.getBind();
        bind.setBean(item);
        List<KnowledgeBean.ChildrenBean> children = item.getChildren();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < children.size(); i++) {
            sb.append(children.get(i).getName()).append(" ");
        }
        sb.substring(0,sb.length()-1);
        bind.itemKnowledgeContent.setText(sb);
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

    public class MyHolder extends BaseViewHolder {
        public MyHolder(View view) {
            super(view);
        }
        public ViewDataBinding getBind(){
            return (ViewDataBinding) itemView.getTag(R.id.BaseQuickAdapter_databinding_support);

        }
    }
}
