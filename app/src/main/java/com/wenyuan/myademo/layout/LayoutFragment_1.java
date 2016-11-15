package com.wenyuan.myademo.layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wenyuan.myademo.BaseFragment;
import com.wenyuan.myademo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LayoutFragment_1 extends BaseFragment {


    public LayoutFragment_1() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return mFragRootView;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_layout_fragment_1;
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
