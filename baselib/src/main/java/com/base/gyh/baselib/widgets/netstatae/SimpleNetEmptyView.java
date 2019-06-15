package com.base.gyh.baselib.widgets.netstatae;

import android.content.Context;
import android.view.View;

import com.base.gyh.baselib.R;

/**
 * Created by GUOYH on 2019/6/15.
 */
public class SimpleNetEmptyView implements INetEmptyView {
    private OnRetryClickListener mRetryClickListener;
    private View mView;
    @Override
    public void setRetryClickListener(OnRetryClickListener retryClickListener) {
        this.mRetryClickListener = retryClickListener;
        if (mView != null) {
            mView.findViewById(R.id.empty_reload).setOnClickListener(new View.OnClickListener() {
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
            mView = View.inflate(context, R.layout.state_load_empty, null);
        }
        return mView;
    }

    @Override
    public void hide() {
        mView.setVisibility(View.GONE);
    }

    @Override
    public void show() {
        mView.setVisibility(View.VISIBLE);
    }
}
