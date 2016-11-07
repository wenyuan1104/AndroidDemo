package com.wenyuan.myademo.detail.permission;

import android.os.Bundle;

import com.wenyuan.myademo.BaseActivity;
import com.wenyuan.myademo.R;

public class PermissionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void getLayoutResource() {
        setContentView(R.layout.activity_permission);
    }

    @Override
    protected void initView() {
        setToolbar(true);
    }

    @Override
    protected void initData() {

    }
}
