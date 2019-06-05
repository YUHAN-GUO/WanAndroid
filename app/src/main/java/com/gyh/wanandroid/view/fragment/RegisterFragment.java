package com.gyh.wanandroid.view.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.base.gyh.baselib.base.BaseFragment;
import com.base.gyh.baselib.widgets.netstatae.NetStateLayout;
import com.gyh.wanandroid.R;
import com.gyh.wanandroid.databinding.FragmentRegisterBinding;
import com.gyh.wanandroid.viewmodule.RegisterViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends BaseFragment  {


    private FragmentRegisterBinding binding;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        binding = FragmentRegisterBinding.bind(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.setRegisterViewModel(new RegisterViewModel(this, binding));
    }

    @Override
    protected void loadData() {

    }
}
