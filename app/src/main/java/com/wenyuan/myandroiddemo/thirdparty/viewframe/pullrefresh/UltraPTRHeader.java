package com.wenyuan.myandroiddemo.thirdparty.viewframe.pullrefresh;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wenyuan.myandroiddemo.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Created by wenyuan on 2016/12/6 0:03.
 * Description: 对下拉刷新框架 自定义下拉header ui及控制器
 */

public class UltraPTRHeader extends FrameLayout implements PtrUIHandler {

    private TextView mTextView;

    public UltraPTRHeader(Context context) {
        super(context);
        initViews(null);
    }

    public UltraPTRHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(attrs);
    }

    public UltraPTRHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public UltraPTRHeader(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initViews(attrs);
    }

    /**
     * header UI
     *
     * @param attrs
     */
    private void initViews(AttributeSet attrs) {
        View header = LayoutInflater.from(getContext()).inflate(R.layout.header_pull_custom_ui, this);
        mTextView = (TextView) header.findViewById(R.id.text_pull_flag);
    }


    /**
     * @param frame
     */
    @Override
    public void onUIReset(PtrFrameLayout frame) {
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        mTextView.setText("下拉刷新");
    }

    @Override
    public void onUIRefreshBegin(final PtrFrameLayout frame) {
        mTextView.setText("正在刷新");
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        mTextView.setText("刷新完成");
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        final int mOffsetToRefresh = frame.getOffsetToRefresh();
        final int currentPos = ptrIndicator.getCurrentPosY();
        final int lastPos = ptrIndicator.getLastPosY();

        if (currentPos < mOffsetToRefresh && lastPos >= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE)
                mTextView.setText("下拉刷新");
        } else if (currentPos > mOffsetToRefresh && lastPos <= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE)
                mTextView.setText("释放刷新");
        }
    }

}
