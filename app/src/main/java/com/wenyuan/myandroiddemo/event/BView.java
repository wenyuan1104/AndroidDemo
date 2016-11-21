package com.wenyuan.myandroiddemo.event;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by wenyuan on 2016/11/17 15:44.
 * Description:
 */

public class BView extends RelativeLayout {

    private MyCallBack mMyCallBack;

    public BView(Context context) {
        super(context);
    }

    public BView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setMyCallBack(MyCallBack back) {
        this.mMyCallBack = back;
    }

    /**
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        mMyCallBack.callShow("onInterceptTouchEvent-->B");
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mMyCallBack.callShow("onTouchEvent-->B");
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mMyCallBack.callShow("dispatchTouchEvent-->B");
        return false;
    }

}
