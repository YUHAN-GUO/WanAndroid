package com.base.gyh.baselib.widgets.netstatae;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.base.gyh.baselib.R;
import com.base.gyh.baselib.utils.mylog.Logger;


/**
 * Created by xlh on 2017/8/16.
 */

public class NetStateLayout extends FrameLayout {
    private INetErrorView mNetErrorView;
    private INetLoadingView mNetLoadingView;
    private INetEmptyView mNetEmptyView;
    private String netEmptyClassName;
    private String netErrorClassName;
    private String netLoadingClassName;

    /**
     * show customized network error view
     */
    public static final int CONTENT_STATE_SHOW_NET_ERROR = 0x1;
    /**
     * show customized network loading view
     */
    public static final int CONTENT_STATE_SHOW_LOADING = 0x2;
    /**
     * hide customized view,show your data content
     */
    public static final int CONTENT_STATE_HIDE = 0x3;

    /**
     * show customized network empty view
     */
    public static final int CONTENT_STATE_EMPTY = 0x4;

    private int mContentState = CONTENT_STATE_HIDE;

    /**
     * set customized network error view.
     *
     * @param netErrorView
     * @see SimpleNetErrorView  SimpleNetErrorView
     */
    public void setNetErrorView(INetErrorView netErrorView) {
        if (netErrorView == null) {
            return;
        }
        if (mNetErrorView != null) {
            removeView(mNetErrorView.getView(getContext()));
        }
        this.mNetErrorView = netErrorView;
        addView(netErrorView.getView(getContext()));
        mNetErrorView.hide();
    }

    /**
     * 设置空布局
     * @param netEmptyView
     */
    public void setNetEmptyView(INetEmptyView netEmptyView){
        if (netEmptyView==null){
            return;
        }
        if (mNetEmptyView!=null){
            removeView(mNetEmptyView.getView(getContext()));
        }
        this.mNetEmptyView = netEmptyView;
        addView(netEmptyView.getView(getContext()));
        mNetEmptyView.hide();
    }
    /**
     * set customized network loading view.
     *
     * @param netLoadingView
     * @see SimpleNetLoadingView SimpleNetLoadingView
     */
    public void setNetLoadingView(INetLoadingView netLoadingView) {
        if (netLoadingView == null) {
            return;
        }
        if (mNetLoadingView != null) {
            removeView(mNetLoadingView.getView(getContext()));
        }
        this.mNetLoadingView = netLoadingView;
        addView(netLoadingView.getView(getContext()));
        mNetLoadingView.hide();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }




    public NetStateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.NetStateLayout);
        netErrorClassName = ta.getString(R.styleable.NetStateLayout_net_error);
        netLoadingClassName = ta.getString(R.styleable.NetStateLayout_net_loading);
        setNetLoadingView(new SimpleNetLoadingView());
        setNetErrorView(new SimpleNetErrorView());
        setNetEmptyView(new SimpleNetEmptyView());
        ta.recycle();
    }


    /**
     * get current state
     *
     * @return {@link #CONTENT_STATE_SHOW_NET_ERROR} ,{@link #CONTENT_STATE_SHOW_LOADING},{@link #CONTENT_STATE_HIDE}
     */
    public int getContentState() {
        return mContentState;
    }

    /**
     * @param contentState {@linkplain #CONTENT_STATE_SHOW_NET_ERROR} ,{@link #CONTENT_STATE_SHOW_LOADING},{@link #CONTENT_STATE_HIDE}
     */
    public void setContentState(int contentState) {
        if (mNetErrorView == null) {
            try {
                Class<?> netErrorClass = Class.forName(netErrorClassName);
                mNetErrorView = (INetErrorView) netErrorClass.newInstance();
                addView(mNetErrorView.getView(getContext()), new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (mNetLoadingView == null) {
            try {
                Class<?> netLoadingClass = Class.forName(netLoadingClassName);
                mNetLoadingView = (INetLoadingView) netLoadingClass.newInstance();
                addView(mNetLoadingView.getView(getContext()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mContentState = contentState;
        switch (contentState) {
            case CONTENT_STATE_SHOW_NET_ERROR:
                if (mNetErrorView != null) {
                    mNetErrorView.show();
                }
                if (mNetLoadingView != null) {
                    mNetLoadingView.hide();
                }
                break;
            case CONTENT_STATE_SHOW_LOADING:
                if (mNetErrorView != null) {
                    mNetErrorView.hide();
                }
                if (mNetLoadingView != null) {
                    mNetLoadingView.show();
                }
                break;
            case CONTENT_STATE_HIDE:
                if (mNetErrorView != null) {
                    mNetErrorView.hide();
                }
                if (mNetLoadingView != null) {
                    mNetLoadingView.hide();
                }
                break;
            default:
                break;
        }
    }


    public void setOnErrorRetryClickListener(INetErrorView.OnRetryClickListener onRetryClickListener) {
        if (mNetErrorView == null) {
            try {
                Class<?> netErrorClass = Class.forName(netErrorClassName);
                mNetErrorView = (INetErrorView) netErrorClass.newInstance();
                addView(mNetErrorView.getView(getContext()), new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (mNetErrorView != null) {
            mNetErrorView.setRetryClickListener(onRetryClickListener);
        }
    }
    public void setOnEmptyRetryClickListener(INetEmptyView.OnRetryClickListener onRetryClickListener) {
        if (mNetEmptyView == null) {
            try {
                Class<?> netEmptyClass = Class.forName(netEmptyClassName);
                mNetEmptyView = (INetEmptyView) netEmptyClass.newInstance();
                addView(mNetErrorView.getView(getContext()), new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (mNetEmptyView != null) {
            mNetEmptyView.setRetryClickListener(onRetryClickListener);
        }
    }

    public  interface OnEmptyAndErrorRetryClickListener{
        void emptyClick(INetEmptyView.OnRetryClickListener onEmptyRetryClickListener);
        void errorClick(INetErrorView.OnRetryClickListener onErrorRetryClickListener);
    }
    private OnEmptyAndErrorRetryClickListener mOnEmptyAndErrorRetryClickListener;
    public void setOnEmptyAndErrorRetryClickListener(OnEmptyAndErrorRetryClickListener onEmptyAndErrorRetryClickListener){
        if (mNetErrorView == null) {
            try {
                Class<?> netErrorClass = Class.forName(netErrorClassName);
                mNetErrorView = (INetErrorView) netErrorClass.newInstance();
                addView(mNetErrorView.getView(getContext()), new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (mNetEmptyView == null) {
            try {
                Class<?> netEmptyClass = Class.forName(netEmptyClassName);
                mNetEmptyView = (INetEmptyView) netEmptyClass.newInstance();
                addView(mNetErrorView.getView(getContext()), new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        onEmptyAndErrorRetryClickListener.emptyClick(new INetEmptyView.OnRetryClickListener() {
            @Override
            public void onRetryClicked() {
                mNetEmptyView.setRetryClickListener(this);
            }
        });
        onEmptyAndErrorRetryClickListener.errorClick(new INetErrorView.OnRetryClickListener() {
            @Override
            public void onRetryClicked() {
                mNetErrorView.setRetryClickListener(this);
            }
        });
    }
}
