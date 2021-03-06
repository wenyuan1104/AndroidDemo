package com.wenyuan.myandroiddemo;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.socks.library.KLog;
import com.wenyuan.myandroiddemo.utils.AlertDialogV7Factory;

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
    protected boolean mIsAndroid_M;//是否是android 6.0
    protected AlertDialogV7Factory mDialogV7Factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KLog.i("onCreate  " + TAG);
        setupSatusBarViewLOLLIPOP();
        getLayoutResource();
        this.mContext = this;
        initView();
        initData();
        mDialogV7Factory = new AlertDialogV7Factory(mContext);
    }

    /**
     * 实现状态栏 颜色不同应用 为了 API21
     */
    private void setupSatusBarViewLOLLIPOP() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
            //底部导航栏
            //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
        }
    }

    /**
     * 实现状态栏 颜色不同应用 为了 API19
     *
     * @param on
     */
    private void setupStatusBarViewKITKAT(boolean on) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.colorPrimary);//通知栏所需颜色
        }
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
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
        mIsAndroid_M = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                ? true : false;
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
     * @param isHomeBut
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
    protected void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        this.startActivityForResult(intent, requestCode);
    }
}
