package com.wenyuan.myandroiddemo.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.socks.library.KLog;
import com.wenyuan.myandroiddemo.utils.copy.ToastUtils;

/**
 * 用于启动 闹铃服务
 */
public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ServiceActivity.BROADCAST_ACTION)) {
            KLog.d("onReceive");
            //获取AlarmManager对象:
            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            //指定要启动的是Activity组件,通过PendingIntent调用getActivity来设置
            Intent sIntent = new Intent(context, MyServiceForAlarm.class);
            //待定的意图
            PendingIntent operation = PendingIntent.getService(context, 100, sIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            //设置一次执行的任务
            am.set(AlarmManager.RTC, System.currentTimeMillis(), operation);
            //设置多次执行的任务
            //am.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, operation);
            //am.setInexactRepeating(AlarmManager.RTC, System.currentTimeMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, operation);

            ToastUtils.show(context, "闹钟设置完毕");
            ToastUtils.show(context, "请等待。。。。。。");

            //取消闹铃服务
            //am.cancel(operation);
        }
    }
}
