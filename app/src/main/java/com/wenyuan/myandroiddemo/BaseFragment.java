package com.wenyuan.myandroiddemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.socks.library.KLog;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by www22_000 as wenyuan on 2016/11/13 23:15.
 * Email :wenyuan1104@163.com
 * Description:
 */

public abstract class BaseFragment extends Fragment {
    /**
     * 日志输出标志
     **/
    protected final String TAG = this.getClass().getSimpleName();

    /**
     * 宿主activity
     */
    protected Activity mActivity;
    protected Context mContext;
    protected View mFragRootView;


    public BaseFragment() {
    }

    @Override
    public void onAttach(Context context) {
        KLog.i(TAG, "onAttach context  " + TAG);
        super.onAttach(context);
        this.mContext = context;

    }

    @Override
    public void onAttach(Activity activity) {
        KLog.i(TAG, "onAttach activity  " + TAG);
        super.onAttach(activity);
        this.mActivity = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        KLog.i(TAG, "onCreate  " + TAG);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        KLog.i(TAG, "onCreateView  " + TAG);
        mFragRootView = inflater.inflate(getLayoutResourceId(), container, false);
        initView(mFragRootView);
        initObject();
        initData();
        return mFragRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        KLog.i(TAG, "onActivityCreated  " + TAG);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        KLog.i(TAG, "onStart  " + TAG);
        super.onStart();
    }

    @Override
    public void onResume() {
        KLog.i(TAG, "onResume  " + TAG);
        super.onResume();
    }

    @Override
    public void onPause() {
        KLog.i(TAG, "onPause  " + TAG);
        super.onPause();
    }

    @Override
    public void onStop() {
        KLog.i(TAG, "onStop  " + TAG);
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        KLog.i(TAG, "onDestroyView  " + TAG);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        KLog.i(TAG, "onDestroy  " + TAG);
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        KLog.i(TAG, "onDetach  " + TAG);
        super.onDetach();
    }

    /**
     * 抽象方法
     *
     * @return
     */
    protected abstract int getLayoutResourceId();

    protected abstract void initView(View view);

    protected abstract void initObject();

    protected abstract void initData();

    /**
     * 检查当前网络是否可用
     *
     * @return
     */
    public boolean checkNetwork() {
        mContext.getSystemService(CONNECTIVITY_SERVICE);
        return false;
    }

    /**
     * 数据为空时，显示的提示视图
     */
    public void showEmptyView() {
    }

    /**
     * 隐藏空白视图
     */
    public void hideEmptyView() {
    }

    /**
     * 数据为空时，显示的提示视图
     */
    public void showErrorView() {
    }

    /**
     * 隐藏空白视图
     */
    public void hideErrorView() {
    }

    /**
     * @param title
     * @param message
     */
    public void showLoadingDialog(String title, String message) {

    }

    public void hideLoadingDialog() {

    }

    /**
     * [页面跳转]
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        mActivity.startActivity(new Intent(mActivity, clz));
    }

    /**
     * 携带数据页面跳转
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(mActivity, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        mActivity.startActivity(intent);
    }

    /**
     * 含有Bundle通过Class打开编辑界面
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(mActivity, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        mActivity.startActivityForResult(intent, requestCode);
    }

}
