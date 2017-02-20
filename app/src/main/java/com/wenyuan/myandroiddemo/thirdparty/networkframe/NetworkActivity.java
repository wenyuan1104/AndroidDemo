package com.wenyuan.myandroiddemo.thirdparty.networkframe;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.wenyuan.myandroiddemo.BaseActivity;
import com.wenyuan.myandroiddemo.R;
import com.wenyuan.myandroiddemo.thirdparty.networkframe.retrofit.RetrofitFragment;

public class NetworkActivity extends BaseActivity {

    private FrameLayout mFramentNetContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void getLayoutResource() {
        setContentView(R.layout.activity_network);
    }

    @Override
    protected void initView() {
        setToolbar(true);
        mFramentNetContent = (FrameLayout) findViewById(R.id.frament_net_content);
        initFragment();
    }

    private void initFragment() {
        FragmentTransaction bt = getSupportFragmentManager().beginTransaction();
        RetrofitFragment retrofit = new RetrofitFragment();
        bt.add(R.id.frament_net_content, retrofit);
        bt.show(retrofit);
        bt.commit();
    }

    @Override
    protected void initData() {
    }
}
