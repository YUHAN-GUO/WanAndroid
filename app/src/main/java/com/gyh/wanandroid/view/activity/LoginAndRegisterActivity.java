package com.gyh.wanandroid.view.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.base.gyh.baselib.base.BaseActivity;
import com.base.gyh.baselib.widgets.view.MyToolbar;
import com.gyh.wanandroid.R;
import com.gyh.wanandroid.databinding.ActivityLoginAndRegisterBinding;
import com.gyh.wanandroid.view.fragment.LoginFragment;
import com.gyh.wanandroid.viewmodule.LoginRegisterViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginAndRegisterActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginAndRegisterBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login_and_register);
        binding.setLoginRegisterModel(new LoginRegisterViewModel(this,binding));
//        loginRegisterToolbar.setOnBreakOrMenuClickListener(new MyToolbar.OnBreakOrMenuClickListener() {
//            @Override
//            public void onClick(int type) {
//                if (type==MyToolbar.BACK){
//                    finish();
//                }
//            }
//        });
    }

}
