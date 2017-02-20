package com.wenyuan.myandroiddemo.service;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.socks.library.KLog;
import com.wenyuan.myandroiddemo.BaseActivity;
import com.wenyuan.myandroiddemo.R;
import com.wenyuan.myandroiddemo.utils.AppInfos;

import java.util.List;

/**
 * 1 进程优先级等级一般分法
 * <p>
 * Activte process
 * Visible Process
 * Service process
 * Background process
 * Empty process
 * <p>
 * 2 Service技巧
 * onStartCommand返回START_STICKY
 * onDestroy中startself
 * Service后台变前置，setForground(true)
 * android:persistent = “true”
 */
public class ServiceActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener, RadioGroup.OnCheckedChangeListener {

    public static final String BROADCAST_ACTION = "broadcast_action";

    private Switch mSwitchService;
    private Switch mSwitchIntentService;
    private Switch mSwitchBindService;
    private static Switch mSwitchAlarmService;

    private MyServiceForBind mMyService;
    private ServiceConnection mConnection = new ServiceConnection() {
        /**
         * 简历连接
         * @param name
         * @param service
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //获取service的引用
            MyServiceForBind.MyServiceBind bind = (MyServiceForBind.MyServiceBind) service;
            mMyService = bind.getService();
        }

        /**
         * 断开连接
         * @param name
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mMyService = null;
        }
    };
    private RadioGroup mRadioGroup1;
    private MyReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册广播
        myReceiver = new MyReceiver();
        LocalBroadcastManager.getInstance(mContext).registerReceiver(
                myReceiver,
                new IntentFilter(BROADCAST_ACTION));
    }

    @Override
    protected void getLayoutResource() {
        setContentView(R.layout.activity_service);
    }

    @Override
    protected void initView() {
        setToolbar(true);
        mSwitchService = (Switch) findViewById(R.id.switch_service);
        mSwitchService.setOnCheckedChangeListener(this);
        mSwitchIntentService = (Switch) findViewById(R.id.switch_intent_service);
        mSwitchIntentService.setOnCheckedChangeListener(this);
        mSwitchBindService = (Switch) findViewById(R.id.switch_bind_service);
        mSwitchBindService.setOnCheckedChangeListener(this);
        mSwitchAlarmService = (Switch) findViewById(R.id.switch_alarm_service);
        mSwitchAlarmService.setOnCheckedChangeListener(this);
        //mSwitchBindService.setEnabled(false);
        mRadioGroup1 = (RadioGroup) findViewById(R.id.radio_group_1);
        mRadioGroup1.setOnCheckedChangeListener(this);
        //mRadioGroup1.setEnabled(false);
        mRadioGroup1.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myReceiver != null)
            LocalBroadcastManager.getInstance(mContext).unregisterReceiver(myReceiver);
    }

    /**
     * Switch 的事件
     *
     * @param buttonView
     * @param isChecked
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.switch_service://启动服务
                if (isChecked)
                    startService(new Intent(this, MyService.class));
                else
                    stopService(new Intent(this, MyService.class));
                break;
            case R.id.switch_intent_service://绑定服务
                break;
            case R.id.switch_bind_service:
                if (isChecked)
                    bindService(new Intent(this, MyServiceForBind.class), mConnection, BIND_AUTO_CREATE);
                else
                    unbindService(mConnection);
                break;
            case R.id.switch_alarm_service:
                //发送广播
                LocalBroadcastManager.
                        getInstance(mContext).
                        sendBroadcast(new Intent(BROADCAST_ACTION));
                break;
        }
        if (buttonView.getId() != R.id.radioButton && buttonView.getId() != R.id.radioButton2)
            updateView();
    }

    /**
     * 根据服务状态 设置view
     */
    private void updateView() {
        boolean serviceFlag = false;
        boolean serviceBindFlag = false;
        boolean serviceAlarmFlag = false;
        //todo 判断服务是否存活
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        //获取运行的服务，maxNum为设置的获取最大数目 (当maxNum 较小时，会出现不能匹配到我们的服务)
        //List<ActivityManager.RunningServiceInfo> services = manager.getRunningServices(30);
        List<ActivityManager.RunningServiceInfo> services = manager.getRunningServices(AppInfos.getInstance().getAppNum());
        KLog.d("当前手机安装多少个应用就获取多少个服务：".concat(String.valueOf(AppInfos.getInstance().getAppNum())));
        if (services.isEmpty()) return;
        for (int i = 0; i < services.size(); i++) {
            KLog.d(services.get(i).service.getClassName());
            if (!serviceFlag)
                serviceFlag = services.get(i).service.getClassName().equals("com.wenyuan.myandroiddemo.service.MyService");
            if (!serviceBindFlag)
                serviceBindFlag = services.get(i).service.getClassName().equals("com.wenyuan.myandroiddemo.service.MyServiceForBind");
            if (!serviceAlarmFlag)
                serviceAlarmFlag = services.get(i).service.getClassName().equals("com.wenyuan.myandroiddemo.service.MyServiceForAlarm");
            if (serviceFlag && serviceBindFlag && serviceAlarmFlag) break;

        }
        //todo 设置视图状态
        String serviceString = serviceFlag ? "运行中" : "not run";
        String serviceBString = serviceBindFlag ? "运行中" : "not run";
        String serviceAString = serviceAlarmFlag ? "闹铃启动的运行中" : "not run";
        mSwitchService.setText(serviceString);
        mSwitchService.setChecked(serviceFlag);
        mSwitchBindService.setChecked(serviceBindFlag);
        mSwitchBindService.setText(serviceBString);
        mSwitchAlarmService.setText(serviceAString);
        mSwitchAlarmService.setChecked(serviceAlarmFlag);
    }

    /**
     * RadioButton 事件
     *
     * @param group
     * @param checkedId
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
    }

    /**
     * 广播接收器
     * 内部类 定义的广播接收器需要使用static修饰
     */
    public static class MyReceiverForRX extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            KLog.d("onReceive");
            if (intent.getAction().equals("com.wenyuan.broadcastrecall.static")) {
                mSwitchAlarmService.setText("闹铃启动的运行中");
                mSwitchAlarmService.setChecked(true);
            }
        }
    }
}
