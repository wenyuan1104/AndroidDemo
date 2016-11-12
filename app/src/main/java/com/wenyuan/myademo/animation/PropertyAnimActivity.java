package com.wenyuan.myademo.animation;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.socks.library.KLog;
import com.wenyuan.myademo.BaseActivity;
import com.wenyuan.myademo.R;

/**
 * 属性动画
 * Duration动画的持续时间，默认300ms。
 */
public class PropertyAnimActivity extends BaseActivity implements View.OnClickListener {

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void getLayoutResource() {
        setContentView(R.layout.activity_property_anim);
    }

    @Override
    protected void initView() {
        setToolbar(true);
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                rotationAnim(v);
                break;
        }
    }

    /**
     * 旋转动画
     */
    private void rotationAnim(final View view) {
        ObjectAnimator anim = ObjectAnimator
                //.ofFloat(view, "rotationX", 0.0f, 180.0f)//动画作用的元素、作用的属性、动画开始、结束
                .ofFloat(view, "zhy", 1.0f, 0.0f)//动画作用的元素、作用的属性、动画开始、结束
                .setDuration(1000);//持续时间
        anim.start();
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float temp = (float) animation.getAnimatedValue();
                KLog.d(temp);
                view.setAlpha(temp);
                view.setScaleX(temp);
                view.setScaleY(temp);
            }
        });
    }
}
