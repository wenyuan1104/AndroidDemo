package com.wenyuan.myandroiddemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.socks.library.KLog;

/**
 * 该服务能够被其他应用程序组件调用或跟它交互
 * 因为 android:exported="true" 在清单文件中
 * <p>
 * Service运行在主线程中
 */
public class MyService extends Service {

    public MyService() {
        KLog.d("MyService()");
    }

    /**
     * 只有当第一次绑定或者启动 service的时候才会执行 onCreate()方法
     */
    @Override
    public void onCreate() {
        super.onCreate();
        KLog.d("onCreate()");
    }

    /**
     * 程序运行在API level 5以上执行 onStartCommand
     * 多次启动服务 调用一次 会执行一次这个方法
     *
     * @param intent
     * @param flags   todo START_STICKY:亲测当service因内存不足被kill，当内存又有的时候，service又被重新创建，比较不错
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        KLog.d("onStartCommand()");
        return super.onStartCommand(intent, START_STICKY, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        KLog.d("onBind()");
        return new MyServiceBind();
    }

    /**
     * @param intent
     * @return TODO 解除绑定返回true会调用重新绑定
     */
    @Override
    public boolean onUnbind(Intent intent) {
        KLog.d("onUnbind()");
        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        KLog.d("onRebind()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        KLog.d("onDestroy()");
    }


    /**
     *
     */
    public class MyServiceBind extends Binder {
        public MyService getService() {
            //返回当前服务
            return MyService.this;
        }
    }
}
