package com.wenyuan.myandroiddemo.utils;

import android.graphics.drawable.Drawable;

/**
 * Created by wenyuan on 2017/1/8 16:43.
 * Description:
 */
public class AppInfos {

    private volatile static AppInfos mInstance;

    private int appNum;
    //图标
    private Drawable app_icon;
    //应用名称
    private String app_name;
    //应用版本号
    private String app_version;
    //应用包名
    private String packagename;
    //是否是用户app
    private boolean isUserApp;

    /**
     * 安全的单例模式
     *
     * @return
     */
    public static AppInfos getInstance() {
        AppInfos instance = mInstance;
        if (null == instance) {
            synchronized (AppInfos.class) {
                instance = mInstance;
                if (null == instance) {
                    instance = new AppInfos();
                    mInstance = instance;
                }
            }
        }
        return instance;
    }

    public int getAppNum() {
        return appNum;
    }

    public void setAppNum(int appNum) {
        this.appNum = appNum;
    }

    public Drawable getApp_icon() {
        return app_icon;
    }

    public void setApp_icon(Drawable app_icon) {
        this.app_icon = app_icon;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public String getPackagename() {
        return packagename;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }

    public boolean isUserApp() {
        return isUserApp;
    }

    public void setUserApp(boolean userApp) {
        isUserApp = userApp;
    }
}
