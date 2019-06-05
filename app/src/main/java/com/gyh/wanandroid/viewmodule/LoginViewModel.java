package com.gyh.wanandroid.viewmodule;

import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.gyh.wanandroid.app.AppConstant;
import com.gyh.wanandroid.databinding.FragmentLoginBinding;
import com.gyh.wanandroid.view.fragment.LoginFragment;
import com.jeremyliao.liveeventbus.LiveEventBus;

/**
 * Created by GUOYH on 2019/6/5.
 */
public class LoginViewModel {
    private LoginFragment fragment;
    private FragmentLoginBinding binding;

    public LoginViewModel(LoginFragment fragment, FragmentLoginBinding binding) {
        this.fragment = fragment;
        this.binding = binding;
        setListener();
    }

    private void setListener() {
        binding.loginOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
        binding.loginToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LiveEventBus.get().with(AppConstant.LiveEventBusKey.UP_TOOLBAR_TITLE,int.class).post(1);
            }
        });
    }
    private void submit() {
        // validate
        String userName = binding.loginEdUserName.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(fragment.getContext(), "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }

        String userPaw = binding.loginEdUserPaw.getText().toString().trim();
        if (TextUtils.isEmpty(userPaw)) {
            Toast.makeText(fragment.getContext(), "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


    }
}
