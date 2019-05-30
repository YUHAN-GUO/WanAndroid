package com.base.gyh.baselib.annotation;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.base.gyh.baselib.annotation.Constant.OnLoadType.frist;
import static com.base.gyh.baselib.annotation.Constant.OnLoadType.loadMore;
import static com.base.gyh.baselib.annotation.Constant.OnLoadType.refresh;

/**
 * Created by GUOYH on 2019/5/28.
 */

@IntDef({frist,refresh,loadMore})
@Retention(RetentionPolicy.SOURCE)
public @interface LoadType {

}