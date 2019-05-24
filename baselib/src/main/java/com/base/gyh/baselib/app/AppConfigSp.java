package com.base.gyh.baselib.app;

import android.content.Context;

import java.util.Set;

import me.linkaipemg.autospannotation.AutoGenerateField;
import me.linkaipemg.autospannotation.AutoSharedPreferences;

/**
 * Created by GUOYH on 2019/4/29.
 * filedName 用于定义 SharedPreferences 中存储 key 的名字，默认值为变量名；
 * defaultStringValue、 defaultIntValue 等为指定各种类型的默认值；
 * commitType 为保存方式，有 commit 和 apply，默认值为 commit.
 */
@AutoSharedPreferences(mode = Context.MODE_PRIVATE)
public class AppConfigSp {
    @AutoGenerateField(filedName = "StudentName", defaultStringValue = "ddd", commitType = AutoGenerateField.CommitType.APPLY)
    private String name;

    @AutoGenerateField(defaultIntValue = -10)
    private int count;

    @AutoGenerateField(defaultLongValue = 90L, commitType = AutoGenerateField.CommitType.COMMIT)
    private long startTime;

    @AutoGenerateField(defaultBooleanValue = true)
    private boolean isClose;

    @AutoGenerateField(defaultFloatValue = 0.534534534f)
    private float price;

    @AutoGenerateField
    private Set<String> productSet;
}
/**
 * 使用：
 * 1.AutoSharedPreferenceConfig.getInstance().init(this); 在Application里进行注册
 * 2.AppConfigSpSP.getInstance().setName("name");
 * 3.AppConfigSpSP.getInstance().getName();
 */
