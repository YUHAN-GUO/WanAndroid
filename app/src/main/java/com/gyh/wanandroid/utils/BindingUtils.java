package com.gyh.wanandroid.utils;

import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.base.gyh.baselib.data.https.GlideApp;
import com.gyh.wanandroid.R;

/**
 * Created by GUOYH on 2019/6/1.
 */
public class BindingUtils {
    @BindingAdapter({"imageUrl"})
    public static void loadWelfareImage(ImageView imgView, String url) {
        if(!TextUtils.isEmpty(url)) {
            Log.i("zpy",url);
//            int columnWidth = AppUtil.getColumnWidth(imgView.getContext(), 2, 16);
//            url = AppUtil.buildRequestImageParam(imgView.getContext(), url, columnWidth);
//            imgView.setLayoutParams(new FrameLayout.LayoutParams(columnWidth, columnWidth));
            GlideApp.with(imgView.getContext()).load(url).centerCrop()
//                    .placeholder(R.color.c_fafafa).error(R.color.c_fafafa)
                    .into(imgView);
        }
    }
}
