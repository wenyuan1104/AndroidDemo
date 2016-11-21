package com.wenyuan.myandroiddemo.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.wenyuan.myandroiddemo.R;

/**
 * Created by wenyuan on 2016/11/21 0:30.
 * Description:
 */

public class FlowText extends View {

    private String mText;
    private Paint mPaint;
    private Mythread mMythread;
    private int mPosition;

    /**
     * java代码中new使用时调用
     *
     * @param context
     * @param text
     */
    public FlowText(Context context) {
        super(context);
    }

    /**
     * 在布局xml中使用时调用
     *
     * @param context
     * @param attrs
     */
    public FlowText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.wenyuan);
        mText = attr.getString(R.styleable.wenyuan_flowtext);
        //释放TypedArray
        attr.recycle();
    }

    /**
     * 初始化
     */
    private void init() {
        mPaint = new Paint();
        mPaint.setTextSize(100);
        mPaint.setColor(getResources().getColor(R.color.colorPrimary));
    }

    /**
     * 绘制
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawText(mText, mPosition, 100, mPaint);
        if (null == mMythread) {
            mMythread = new Mythread();
            mMythread.start();
        }
    }

    /**
     *
     */
    class Mythread extends Thread {
        @Override
        public void run() {
            while (true) {
                mPosition += 5;
                if (mPosition > getWidth())
                    mPosition = (int) (0 - mPaint.measureText(mText));
                postInvalidate();
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
