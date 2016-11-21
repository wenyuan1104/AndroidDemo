package com.wenyuan.myandroiddemo.event;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by www22_000 as wenyuan on 2016/11/17 15:15.
 * Description:
 */
public class DView extends TextView {

    private MyCallBack mMyCallBack;

    public DView(Context context) {
        super(context);
    }

    public DView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setMyCallBack(MyCallBack back) {
        this.mMyCallBack = back;
    }

    /**
     * @param event
     * @return true:事件处理 false事件继续传递
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mMyCallBack.callShow("onTouchEvent-->D");
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mMyCallBack.callShow("dispatchTouchEvent-->D");
        return false;
    }

}
