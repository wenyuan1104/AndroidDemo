package com.wenyuan.myandroiddemo.event;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.socks.library.KLog;
import com.wenyuan.myandroiddemo.BaseActivity;
import com.wenyuan.myandroiddemo.R;
import com.wenyuan.myandroiddemo.utils.copy.ToastUtils;

/**
 * 事件拦截机制是由父视图开始发起对事件的拦截
 * 只有viewgroup才能重写这个方法:onInterceptTouchEvent
 * <p>
 * 参考：http://blog.csdn.net/chunqiuwei/article/details/41084921
 * 参考：http://www.cnblogs.com/yukino/p/4437874.html
 * <p>
 * 布局中：A包含B包含C包含D，A为最外层视图也是顶级父级视图
 * <p>
 * 原生视图
 * <p>
 * 相关事件的方法 只要他们返回的是true或者是阻止事件传递 会执行两次
 * <p>
 * Android系统中的每个View的子类都具有下面三个和TouchEvent处理密切相关的方法：
 * 1）public boolean dispatchTouchEvent(MotionEvent ev)  这个方法用来分发TouchEvent
 * 2）public boolean onInterceptTouchEvent(MotionEvent ev) 这个方法用来拦截TouchEvent
 * 3）public boolean onTouchEvent(MotionEvent ev) 这个方法用来处理TouchEvent
 */
public class EventActivity extends BaseActivity implements MyCallBack {

    private AView mA;
    private BView mB;
    private CView mC;
    private DView mD;
    private TextView mTextView3;
    private ScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void getLayoutResource() {
        setContentView(R.layout.activity_event);
    }

    @Override
    protected void initView() {
        setToolbar(true);
        mA = (AView) findViewById(R.id.a);
        mB = (BView) findViewById(R.id.b);
        mC = (CView) findViewById(R.id.c);
        mD = (DView) findViewById(R.id.d);
        mD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show(mContext, "DView点击事件没有被拦截");
            }
        });
        mTextView3 = (TextView) findViewById(R.id.textView3);
        mScrollView = (ScrollView) findViewById(R.id.scrollView);
        mScrollView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                mScrollView.scrollTo(0, (int) ((int) mTextView3.getPivotY() + mTextView3.getPivotY()));
                //mScrollView.scrollTo(0, bottom);
                KLog.d("left：" + left);
                KLog.d("top:" + top);
                KLog.d("right:" + right);
                KLog.d("bottom:" + bottom);
                KLog.d(mTextView3.getPivotY() * 2);
            }
        });
    }

    @Override
    protected void initData() {
        mA.setMyCallBack(this);
        mB.setMyCallBack(this);
        mC.setMyCallBack(this);
        mD.setMyCallBack(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void callShow(String txt) {
        mTextView3.append(txt.concat("\n"));
    }

    /**
     * @param event
     * @return 默认事件不消费
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mTextView3.append("事件来自activity".concat("\n"));
        return false;
    }

    ///**
    // * @param ev
    // * @return
    // */
    //@Override
    //public boolean dispatchTouchEvent(MotionEvent ev) {
    //    mTextView3.append("dispatchTouchEvent-->Activity".concat("\n"));
    //    return super.dispatchTouchEvent(ev);
    //}
}
