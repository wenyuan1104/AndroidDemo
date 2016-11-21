package com.wenyuan.myandroiddemo.event;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by wenyuan on 2016/11/17 15:51.
 * Description:
 */

public class CView extends RelativeLayout {

    private MyCallBack mMyCallBack;

    public CView(Context context) {
        super(context);
    }

    public CView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setMyCallBack(MyCallBack back) {
        this.mMyCallBack = back;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        mMyCallBack.callShow("onInterceptTouchEvent-->C");
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mMyCallBack.callShow("onTouchEvent-->C");
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mMyCallBack.callShow("dispatchTouchEvent-->C");
        return false;
    }
}
