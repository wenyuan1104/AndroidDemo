package com.wenyuan.myandroiddemo.media.utils;

import android.view.SurfaceHolder;

/**
 * Created by wenyuan on 2016/12/11 17:39.
 * Description:
 */

public abstract class BaseVideoCallback implements SurfaceHolder.Callback {


    public abstract void setOnSurfaceViewCreatedListener(OnSurfaceViewCreatedListener onSurfaceViewCreatedListener);

    /**
     * surfaceview 创建完毕 执行后续操作
     */
    public interface OnSurfaceViewCreatedListener {
        void onSurfaceViewCreated();
    }


}
