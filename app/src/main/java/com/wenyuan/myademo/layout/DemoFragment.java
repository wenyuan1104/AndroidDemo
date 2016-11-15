package com.wenyuan.myademo.layout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wenyuan.myademo.BaseFragment;
import com.wenyuan.myademo.R;

public class DemoFragment extends BaseFragment {

    public DemoFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return mFragRootView;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_demo;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initObject() {

    }


}
