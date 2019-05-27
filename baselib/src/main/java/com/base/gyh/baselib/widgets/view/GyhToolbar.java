package com.base.gyh.baselib.widgets.view;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.gyh.baselib.R;

/**
 * Created by GUOYH on 2019/5/24.
 */
public class GyhToolbar extends Toolbar implements View.OnClickListener {
    public static final  int BACK = 0x10;
    public static final  int MENU = 0x20;
    public GyhToolbar(Context context) {
        super(context);
        init(context);
    }


    public GyhToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public GyhToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }


    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.gyhtoolbar, this, true);
        ViewHolder holder = new ViewHolder(view);
        holder.gyh_toolbar_back.setOnClickListener(this);
        holder.gyh_toolbar_menu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.gyh_toolbar_back) {
               onBreakOrMenuClickListener.onClick(BACK);
        } else if (i == R.id.gyh_toolbar_menu) {
                onBreakOrMenuClickListener.onClick(MENU);
        }
    }


    private OnBreakOrMenuClickListener onBreakOrMenuClickListener;

    public void setOnBreakOrMenuClickListener(OnBreakOrMenuClickListener onBreakOrMenuClickListener) {
        this.onBreakOrMenuClickListener = onBreakOrMenuClickListener;
    }

    public interface OnBreakOrMenuClickListener{
        void onClick(int type);
    }

    public static class ViewHolder {
        public View rootView;
        public TextView gyh_toolbar_title;
        public ImageView gyh_toolbar_back;
        public ImageView gyh_toolbar_menu;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.gyh_toolbar_title = (TextView) rootView.findViewById(R.id.gyh_toolbar_title);
            this.gyh_toolbar_back = (ImageView) rootView.findViewById(R.id.gyh_toolbar_back);
            this.gyh_toolbar_menu = (ImageView) rootView.findViewById(R.id.gyh_toolbar_menu);
        }

    }
}
