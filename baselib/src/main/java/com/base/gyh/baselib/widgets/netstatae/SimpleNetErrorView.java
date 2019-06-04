package com.base.gyh.baselib.widgets.netstatae;

import android.content.Context;
import android.view.View;

import com.base.gyh.baselib.R;
import com.base.gyh.baselib.utils.mylog.Logger;


/**
 * Created by xingliuhua on 2018/7/31.
 */

public class SimpleNetErrorView implements INetErrorView {
    private OnRetryClickListener mRetryClickListener;
    private View mView;


    @Override
    public void setRetryClickListener(OnRetryClickListener retryClickListener) {
        this.mRetryClickListener = retryClickListener;
        if (mView != null) {
            mView.findViewById(R.id.btn_retry).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mRetryClickListener != null) {
                        mRetryClickListener.onRetryClicked();
                    }
                }
            });
        }
    }

    @Override
    public View getView(Context context) {
        if (mView == null) {
            mView = View.inflate(context, R.layout.state_loaderror, null);
        }
        return mView;
    }

    @Override
    public void hide() {
        if (mView != null) {
            mView.setVisibility(View.GONE);
        }
    }

    @Override
    public void show() {
        if (mView != null) {
            mView.setVisibility(View.VISIBLE);
        }
    }
}
