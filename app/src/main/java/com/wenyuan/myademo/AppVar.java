package com.wenyuan.myademo;

import android.graphics.Bitmap;

/**
 * Created by www22_000 as wenyuan on 2016/11/2 23:32.
 * Email :wenyuan1104@163.com
 * Description:
 */

public class AppVar {

    private static AppVar sAppVar;

    private Bitmap customBitmap;

    public Bitmap getCustomBitmap() {
        return customBitmap;
    }

    public void setCustomBitmap(Bitmap customBitmap) {
        this.customBitmap = customBitmap;
    }

    /**
     * 第三种：双重检测同步延迟加载
     * 为处理原版非延迟加载方式瓶颈问题，我们需要对 instance 进行第二次检查，
     * 目的是避开过多的同步（因为这里的同步只需在第一次创建实例时才同步，一旦创建成功，
     * 以后获取实例时就不需要同获取锁了），但在Java中行不通，
     * 因为同步块外面的if (instance == null)可能看到已存在，但不完整的实例。
     * JDK5.0以后版本若instance为volatile则可行：
     *
     * @return
     */
    public static AppVar getInstance() {
        if (sAppVar == null) {
            synchronized (AppVar.class) {
                if (sAppVar == null)
                    sAppVar = new AppVar();
            }
        }
        return sAppVar;
    }
}
