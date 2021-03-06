package com.wenyuan.myandroiddemo.customview;

import android.os.Bundle;

import com.socks.library.KLog;
import com.wenyuan.myandroiddemo.BaseActivity;
import com.wenyuan.myandroiddemo.R;

/**
 * onFinishInflate() 回调方法，当应用从XML加载该组件并用它构建界面之后调用的方法
 * onMeasure() 检测View组件及其子组件的大小
 * onLayout() 当该组件需要分配其子组件的位置、大小时
 * onSizeChange() 当该组件的大小被改变时
 * onDraw() 当组件将要绘制它的内容时
 * onKeyDown 当按下某个键盘时
 * onKeyUp  当松开某个键盘时
 * onTrackballEvent 当发生轨迹球事件时
 * onTouchEvent 当发生触屏事件时
 * onWindowFocusChanged(boolean)  当该组件得到、失去焦点时
 * onAtrrachedToWindow() 当把该组件放入到某个窗口时
 * onDetachedFromWindow() 当把该组件从某个窗口上分离时触发的方法
 * onWindowVisibilityChanged(int): 当包含该组件的窗口的可见性发生改变时触发的方法
 * <p>
 * <p>
 * MeasureSpec.EXACTLY 意味着硬编码大小值，所以你应该设置指定的宽度或高度。
 * MeasureSpec.AT_MOST 用于表明你的View匹配父View的大小，所以它应该和他想要的大小一样大。
 * [译者注：此时View尺寸只要不超过父View允许的最大尺寸即可]
 * MeasureSpec.UNSPECIFIED 实际上是视图包装尺寸。因此，你可以使用上面计算所需的大小。
 */
public class CustomViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void getLayoutResource() {
        setContentView(R.layout.activity_custom_view);
    }

    @Override
    protected void initView() {
        setToolbar(true);
        KLog.d(getPackageName());
    }

    @Override
    protected void initData() {

    }
}
