package com.wenyuan.myandroiddemo.jni;

import android.os.Bundle;

import com.wenyuan.myandroiddemo.BaseActivity;
import com.wenyuan.myandroiddemo.R;

public class JNIActivity extends BaseActivity {

    // 需要调用的.so文件名
    private static final String SO_FILE_NAME = "hello_jni";

    /**
     * 加载 .so文件
     * 加载完 点so文件后 就可以直接调用 so库里面的方法了
     */
    static {
        System.loadLibrary(SO_FILE_NAME);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void getLayoutResource() {
        setContentView(R.layout.activity_jni);
    }

    @Override
    protected void initView() {
        setToolbar(true);
    }

    @Override
    protected void initData() {

    }
}
