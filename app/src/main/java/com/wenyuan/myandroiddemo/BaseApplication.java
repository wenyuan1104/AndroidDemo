package com.wenyuan.myandroiddemo;

import android.app.Application;

import com.socks.library.KLog;
import com.wenyuan.myandroiddemo.utils.UIManager;

/**
 * Created by wenyuan on 2016/11/22 0:36.
 * Description:
 */

public class BaseApplication extends Application {

    private static volatile BaseApplication mInstance;

    /**
     * 安全的单例模式
     *
     * @return
     */
    public static BaseApplication getInstance() {
        BaseApplication instance = mInstance;
        if (instance == null) {
            synchronized (BaseApplication.class) {
                instance = mInstance;
                if (null == instance) {
                    instance = new BaseApplication();
                    mInstance = instance;
                }
            }
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        KLog.init(BuildConfig.KLOG_DEBUG);//是否打开KLog日志
        UIManager.getInstance().setAppContext(getApplicationContext());
    }

    /**
     * 这个函数是模拟一个过程环境，在真机中永远也不会被调用。
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    /**
     * memory不足时调用
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

}
