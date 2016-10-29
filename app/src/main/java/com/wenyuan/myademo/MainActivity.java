package com.wenyuan.myademo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wenyuan.myademo.encrypt.EncyptActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Button mButEncryption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void getLayoutResource() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initView() {
        setToolbar(false);
        mToolbar.setTitle(getString(R.string.app_name));
        mButEncryption = (Button) findViewById(R.id.but_encryption);
        mButEncryption.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.but_encryption:
                startActivity(EncyptActivity.class);
                break;
        }
    }
}
