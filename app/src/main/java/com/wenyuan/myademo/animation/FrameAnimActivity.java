package com.wenyuan.myademo.animation;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.wenyuan.myademo.BaseActivity;
import com.wenyuan.myademo.R;

public class FrameAnimActivity extends BaseActivity {

    private ImageView mImgShowLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.anim_tween_group_3, 0);
        super.onCreate(savedInstanceState);
        startFrameAnim();
    }

    @Override
    protected void getLayoutResource() {
        setContentView(R.layout.activity_frame_anim);
    }

    @Override
    protected void initView() {
        setToolbar(true);
        mImgShowLoading = (ImageView) findViewById(R.id.img_show_loading);
    }

    @Override
    protected void initData() {
    }


    /**
     * 我们需要把这段代码放在onWindowFocusChanged方法中，
     * 当Activity展示给用户时，onWindowFocusChanged方法就会被调用，我们正是在这个时候实现我们的动画效果
     *
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //startFrameAnim();
    }

    /**
     * 使用xml的方式
     */
    private void startFrameAnim() {
        mImgShowLoading.setBackgroundResource(R.drawable.anim_frame_loading);
        AnimationDrawable drawable = (AnimationDrawable) mImgShowLoading.getBackground();
        drawable.start();
        //drawable.isRunning()//动画是否在运行
        //drawable.stop()//动画停止
    }

    public void runFrame(View view) {
        //完全编码实现的动画效果
        AnimationDrawable anim = new AnimationDrawable();
        for (int i = 1; i <= 8; i++) {
            //根据资源名称和目录获取R.java中对应的资源ID
            int id = getResources().getIdentifier("progressbar_" + i, "mipmap", getPackageName());
            //根据资源ID获取到Drawable对象
            Drawable drawable = getResources().getDrawable(id);
            //将此帧添加到AnimationDrawable中
            anim.addFrame(drawable, 300);
        }
        anim.setOneShot(false); //设置为loop
        mImgShowLoading.setBackgroundDrawable(anim);  //将动画设置为ImageView背景
        anim.start();   //开始动画
    }
}
