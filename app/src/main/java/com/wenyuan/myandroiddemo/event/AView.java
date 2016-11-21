package com.wenyuan.myandroiddemo.event;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by www22_000 as wenyuan on 2016/11/17 15:37.
 * Description:
 */

public class AView extends RelativeLayout {

    private MyCallBack mMyCallBack;

    public AView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AView(Context context) {
        super(context);
    }

    public void setMyCallBack(MyCallBack back) {
        this.mMyCallBack = back;
    }

    /**
     * 只有viewgroup才能重写这个方法
     *
     * @param ev
     * @return true:事件拦截  flase:事件不拦截   默认事件拦截
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        mMyCallBack.callShow("onInterceptTouchEvent-->A");
        return false;
    }

    /**
     * @param event
     * @return true:事件处理 false事件继续传递    默认事件放行
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mMyCallBack.callShow("onTouchEvent-->A");
        //return true;
        return false;
    }

    /**
     * 事件分发
     *
     * @param ev
     * @return true:事件处理   false：事件传递  默认事件处理
     * 如果 return true，事件会分发给当前 View 并由 dispatchTouchEvent 方法进行消费，同时事件会停止向下传递
     * 所以会执行两次
     * 返回系统默认的 super.dispatchTouchEvent(ev)，事件会自动的分发给当前 View 的 onInterceptTouchEvent 方法。
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mMyCallBack.callShow("dispatchTouchEvent-->A");
        return super.dispatchTouchEvent(ev);
        //return true;
        //return onTouchEvent(ev);
    }
}
