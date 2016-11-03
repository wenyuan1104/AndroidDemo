package com.wenyuan.myademo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.socks.library.KLog;

/**
 * Created by www22_000 as wenyuan on 2016/10/9 22:51.
 * Email :wenyuan1104@163.com
 * Description:
 */

public abstract class BaseActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    protected Context mContext;
    protected Toolbar mToolbar;
    private boolean mIsHomeBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KLog.i("onCreate  " + TAG);
        getLayoutResource();
        this.mContext = this;
        initView();
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        KLog.i("onStart  " + TAG);
    }

    @Override
    protected void onResume() {
        super.onResume();
        KLog.i("onResume  " + TAG);
    }

    @Override
    protected void onPause() {
        super.onPause();
        KLog.d("onPause  " + TAG);
    }

    @Override
    protected void onStop() {
        super.onStop();
        KLog.i("onStop  " + TAG);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KLog.i("onDestroy  " + TAG);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mIsHomeBut && item.getItemId() == android.R.id.home)
            this.finish();
        return super.onOptionsItemSelected(item);
    }

    protected abstract void getLayoutResource();

    protected abstract void initView();

    protected abstract void initData();

    /**
     * 使用Toolbar,并显示时 调用这个方法
     *
     * @param isHomebut
     */
    protected void setToolbar(boolean isHomeBut) {
        mIsHomeBut = isHomeBut;
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(TAG);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(isHomeBut);

    }

    /**
     * [页面跳转]
     *
     * @param clz
     */
    protected void startActivity(Class<?> clz) {
        this.startActivity(new Intent(this, clz));
    }

    /**
     * 携带数据页面跳转
     *
     * @param clz
     * @param bundle
     */
    protected void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        this.startActivity(intent);
    }

    /**
     * 含有Bundle通过Class打开编辑界面
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    protected void startActivityForResult(Class<?> cls, Bundle bundle,
                                          int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        this.startActivityForResult(intent, requestCode);
    }
}
