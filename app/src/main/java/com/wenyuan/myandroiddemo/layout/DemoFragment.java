package com.wenyuan.myandroiddemo.layout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.socks.library.KLog;
import com.wenyuan.myandroiddemo.BaseFragment;
import com.wenyuan.myandroiddemo.R;
import com.wenyuan.myandroiddemo.adapter.fortradition.ListViewAdapter;
import com.wenyuan.myandroiddemo.utils.copy.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * <h1>todo 使用PtrFrameLayout自定义刷新header ui</h1>
 * <p>
 * Ultra PTR 提供了两钟视图PtrFrameLayout和其子类PtrClassicFrameLayout
 * PtrClassicFrameLayout封装了经典的header ui可以直接使用
 * <p>
 * 阻尼系数
 * 默认: 1.7f，越大，感觉下拉时越吃力。
 * cube_ptr:ptr_resistance="1.7"
 * <p>
 * 触发刷新时移动的位置比例
 * 默认，1.2f，移动达到头部高度1.2倍时可触发刷新操作。
 * cube_ptr:ptr_ratio_of_header_height_to_refresh="1.2"
 * <p>
 * 回弹延时
 * 默认 200ms，回弹到刷新高度所用时间
 * cube_ptr:ptr_duration_to_close="300"
 * <p>
 * 头部回弹时间
 * 默认1000ms
 * cube_ptr:ptr_duration_to_close_header="2000"
 * <p>
 * 刷新是保持头部
 * 默认值 true.
 * cube_ptr:ptr_keep_header_when_refresh="true"
 * <p>
 * 下拉刷新 / 释放刷新
 * 默认为释放刷新
 * cube_ptr:ptr_pull_to_fresh="false" >
 */
public class DemoFragment extends BaseFragment implements AdapterView.OnItemClickListener, PtrHandler, PtrUIHandler {

    private PtrFrameLayout mPtrframelayout;
    private ListView mListView;
    private TextView mRefreshLoading;

    private ListViewAdapter<String> mAdapter;
    private int temp = 1;
    //private final StoreHouseHeader mHouseHeader = new StoreHouseHeader(getContext());

    public DemoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_demo;
    }

    @Override
    protected void initView(View view) {
        mPtrframelayout = (PtrFrameLayout) view.findViewById(R.id.ptrframelayout);
        //mPtrframelayout.setPinContent(true);//刷新时，保持内容不动，仅头部下移,
        mPtrframelayout.setPtrHandler(this);
        mPtrframelayout.addPtrUIHandler(this);//PtrUHandler这个接口功能更加强大
        mListView = (ListView) view.findViewById(R.id.list_view);
        mListView.setOnItemClickListener(this);
        mRefreshLoading = (TextView) view.findViewById(R.id.refresh_loading_view);
        initPtr();
    }

    /**
     * StoreHouse Style
     */
    private void initPtr() {
        //mHouseHeader.setPadding(0, 22, 0, 0);
        //mHouseHeader.initWithString("wenyuanjiang");
        //todo 需要进入页面自动刷新 且header ui下滑则需要延时执行自动刷新
        mPtrframelayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrframelayout.autoRefresh();//自动刷新
            }
        }, 100);
    }

    @Override
    protected void initObject() {

    }

    @Override
    protected void initData() {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            strings.add("【android-Ultra-Pull-To-Refresh】");
        }
        mAdapter = new ListViewAdapter<>(mContext, strings);
        mListView.setAdapter(mAdapter);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ToastUtils.show(mContext, String.valueOf(position));
    }

    /**
     * 检查是否可以执行下来刷新，比如列表为空或者列表第一项在最上面时。
     *
     * @param frame
     * @param content 如果content是一个TextView，则可以直接返回true，表示可以进入下拉状态；
     *                如果content是一个ListView，则当ListView的第一条Item在顶部的时候，返回true，表示可以进入下拉状态；
     *                如果content是一个ScrollView，则当ScrollView滑动到顶部顶部的时候，返回true，表示可以进入下拉状态；
     * @param header
     * @return
     */
    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
    }

    /**
     * 需要加载数据时触发
     *
     * @param frame
     */
    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
    }

//***********************************************************************************
//***********************************************************************************
    //使用PtrUIHandler接口

    /**
     * 刷新完成 Header ui到达top时调用，可以初始化ui
     *
     * @param frame
     */
    @Override
    public void onUIReset(PtrFrameLayout frame) {
        KLog.d("onUIReset");
        mRefreshLoading.setText("下拉刷新");
    }

    /**
     * 开始触发刷新功能时调用 Header ui开始出现
     *
     * @param frame
     */
    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        KLog.d("onUIRefreshPrepare");
    }

    /**
     * refreshing
     *
     * @param frame
     */
    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        KLog.d("onUIRefreshBegin");
        mRefreshLoading.setText("正在刷新");
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrframelayout.refreshComplete();
                ToastUtils.show(mContext, "refresh complete");
            }
        }, 1000);
    }

    /**
     * 调用refreshComplete()后刷新完成时调用
     *
     * @param frame
     */
    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        KLog.d("onUIRefreshComplete");
        mRefreshLoading.setText("刷新完成");
        List<String> strings = new ArrayList<>();
        if (temp % 2 != 0) {
            for (int i = 0; i < 50; i++)
                strings.add("可高度定制用于各种view下拉刷新库");
        } else {
            for (int i = 0; i < 50; i++)
                strings.add("【android-Ultra-Pull-To-Refresh】");
        }
        temp++;
        mAdapter.refreshData(strings);
        strings = null;
    }

    /**
     * Header ui改变时调用，下拉和回弹时会调用
     *
     * @param frame
     * @param isUnderTouch 手指是否触摸
     * @param status       2:刷新之前 3：刷新中   4：刷新完成
     * @param ptrIndicator
     */
    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        KLog.d("onUIPositionChange  isUnderTouch:" + isUnderTouch);
        float ratio = ptrIndicator.getRatioOfHeaderToHeightRefresh();
        float last = ptrIndicator.getLastPercent();
        float current = ptrIndicator.getCurrentPercent();

        if (ratio < current && last < ratio) {//表示下拉
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE)
                mRefreshLoading.setText("释放刷新");
        } else if (ratio < last && ratio > current)//表示 下拉拖动时向上推回到刷新界线以上
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE)
                mRefreshLoading.setText("下拉刷新");

    }
}
