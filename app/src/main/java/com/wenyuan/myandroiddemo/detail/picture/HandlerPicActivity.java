package com.wenyuan.myandroiddemo.detail.picture;

import android.os.Bundle;

import com.wenyuan.myandroiddemo.BaseActivity;
import com.wenyuan.myandroiddemo.R;

public class HandlerPicActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void getLayoutResource() {
        setContentView(R.layout.activity_handler_pic);
    }

    @Override
    protected void initView() {
        setToolbar(true);
    }

    @Override
    protected void initData() {
    }
}
