package com.wenyuan.myandroiddemo.utils;

import android.app.Activity;

import com.socks.library.KLog;

import java.util.Stack;

/**
 * Created by wenyuan on 2016/12/3 0:07.
 * Description:保存所有的activity
 */

public class ActivityStack {

    private static volatile ActivityStack sActivityStack;

    private Stack<Activity> mStack;

    /**
     * 安全的单例
     *
     * @return
     */
    public static ActivityStack getInstance() {
        ActivityStack activityStack = sActivityStack;
        if (activityStack == null) {
            synchronized (ActivityStack.class) {
                activityStack = sActivityStack;
                if (activityStack == null) {
                    activityStack = new ActivityStack();
                    sActivityStack = activityStack;
                }
            }
        }
        return activityStack;
    }

    /**
     * @param activity
     */
    public void popActivity(Activity activity) {
        if (activity == null)
            KLog.d();
        else
            mStack.add(activity);
    }

    /**
     * @return 返回当前所在activity的实例
     */
    public Activity currentActivity() {
        return mStack.lastElement();
    }

    public Activity topActivity() {
        return mStack.lastElement();
    }
}
