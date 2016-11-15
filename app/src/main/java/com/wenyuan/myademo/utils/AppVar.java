package com.wenyuan.myademo.utils;

import android.content.Context;

/**
 * Created by www22_000 as wenyuan on 2016/11/2 23:32.
 * Email :wenyuan1104@163.com
 * Description:
 * <p>
 * 单例问题参考：http://www.race604.com/java-double-checked-singleton/
 */

public class AppVar {

    private static volatile AppVar sAppVar;// <<< 这里添加了 volatile

    /**
     * @param context
     */
    public AppVar(Context context) {

    }

    /**
     * 第三种：双重检测同步延迟加载
     * 为处理原版非延迟加载方式瓶颈问题，我们需要对 instance 进行第二次检查，
     * 目的是避开过多的同步（因为这里的同步只需在第一次创建实例时才同步，一旦创建成功，
     * 以后获取实例时就不需要同获取锁了），
     * 但在Java中行不通，
     * 因为同步块外面的if (instance == null)可能看到已存在，但不完整的实例。
     * JDK5.0以后版本若instance为volatile则可行：
     * <p>
     * 构造函数可能会被分为两块：先分配内存并赋值，再初始化
     *
     * @return
     */
    public static AppVar getInstance(Context context) {
        AppVar appVar = sAppVar;// <<< 在这里创建临时变量
        if (appVar == null) {
            synchronized (AppVar.class) {
                appVar = sAppVar;
                if (appVar == null) {
                    appVar = new AppVar(context);
                    sAppVar = appVar;
                }
            }
        }
        return appVar;// <<< 注意这里返回的是临时变量
    }
}
