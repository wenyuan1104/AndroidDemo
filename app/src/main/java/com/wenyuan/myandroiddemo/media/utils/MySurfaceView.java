package com.wenyuan.myandroiddemo.media.utils;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by wenyuan on 2016/12/11 17:29.
 * Description:
 */

public class MySurfaceView extends SurfaceView {

    private SurfaceHolder holder;

    public MySurfaceView(Context context) {
        super(context);
        init();
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    /**
     * 初始化surfaceview操作
     */
    private void init() {
        holder = this.getHolder();
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
        this.requestFocus();
    }

    /**
     * 添加回调
     *
     * @param mediaplayer
     */
    public void addCallBack(SurfaceHolder.Callback mediaplayer) {
        holder.addCallback(mediaplayer);
    }
}
