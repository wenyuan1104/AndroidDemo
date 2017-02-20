package com.wenyuan.myandroiddemo.utils;

import android.content.Context;

/**
 * Created by wenyuan on 2016/11/24 23:58.
 * Description:
 */

public class UIManager {

    private static volatile UIManager mInstance;

    private Context mContext;//应用全局上下文

    private int mScreenWidth; //手机屏幕的宽度
    private int mScreenHeight;//手机屏幕的高度

    /**
     * 安全的单例模式
     *
     * @return
     */
    public static UIManager getInstance() {
        UIManager instance = mInstance;
        if (null == instance) {
            synchronized (UIManager.class) {
                instance = mInstance;
                if (null == instance) {
                    instance = new UIManager();
                    mInstance = instance;
                }
            }
        }
        return instance;
    }

    /**
     * 设置app全局上下文
     *
     * @return
     */
    public Context getAppContext() {
        return mContext;
    }

    /**
     * 获得app全局上下文
     *
     * @param context
     */
    public void setAppContext(Context context) {
        mContext = context;
    }

    /**
     * The absolute width of the available display size in pixels
     *
     * @return
     */
    public int getScreenWidth() {
        return mScreenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        mScreenWidth = screenWidth;
    }

    /**
     * The absolute height of the available display size in pixels
     *
     * @return
     */
    public int getScreenHeight() {
        return mScreenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        mScreenHeight = screenHeight;
    }
}
