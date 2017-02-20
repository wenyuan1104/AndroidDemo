package com.wenyuan.myandroiddemo.thirdparty.viewframe.pullrefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by wenyuan on 2016/12/6 0:02.
 * Description: 对下拉刷新框架 自定义下拉header ui及控制器
 */

public class UltraPTRFragmentLayout extends PtrFrameLayout implements PtrHandler {

    private UltraPTRHeader mUltraPTRHeader;
    private PullRefreshListener mPullRefreshListener;

    public void setPullRefreshListener(PullRefreshListener pullRefreshListener) {
        mPullRefreshListener = pullRefreshListener;
    }

    public UltraPTRFragmentLayout(Context context) {
        super(context);
        initViews();
    }

    public UltraPTRFragmentLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public UltraPTRFragmentLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews();
    }

    private void initViews() {
        mUltraPTRHeader = new UltraPTRHeader(getContext());
        setHeaderView(mUltraPTRHeader);
        addPtrUIHandler(mUltraPTRHeader);
        setPtrHandler(this);
    }

    public UltraPTRHeader getHeader() {
        return mUltraPTRHeader;
    }

    /**
     * @param frame
     * @param content
     * @param header
     * @return
     */
    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        this.mPullRefreshListener.PullRefreshOperation(frame);
    }

    /**
     * 正在刷新时调用
     */
    public interface PullRefreshListener {
        void PullRefreshOperation(PtrFrameLayout frame);
    }

}
