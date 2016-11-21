package com.wenyuan.myandroiddemo.animation;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.wenyuan.myandroiddemo.BaseActivity;
import com.wenyuan.myandroiddemo.R;

/**
 * 1.
 * ActivityOptionsCompat.makeCustomAnimation(Context context, int enterResId, int exitResId)
 * <p>
 * 2.
 * ActivityOptionsCompat.makeScaleUpAnimation(View source,int startX, int startY, int startWidth, int startHeight)
 * <p>
 * 3.
 * ActivityOptionsCompat.makeThumbnailScaleUpAnimation(View source,Bitmap thumbnail, int startX, int startY)
 * <p>
 * 4.
 * ActivityOptionsCompat.makeSceneTransitionAnimation(Activity activity, View sharedElement, String sharedElementName)
 * <p>
 * 5.
 * ActivityOptionsCompat.makeSceneTransitionAnimation(Activity activity,Pair<View, String>… sharedElements)
 * <p>
 * 恩，就这5个方法可以调用，也就是说为我们提供了这么5中过度方式，在使用的时候我们需要在theme指定:
 * <p>
 * <style name="AppTheme" parent="Theme.AppCompat">
 * <item name="android:windowContentTransitions">true</item>
 * </style>
 */
public class ActivityOptionsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void getLayoutResource() {
        setContentView(R.layout.activity_options);
    }

    @Override
    protected void initView() {
        setToolbar(true);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在退出的时候调用ActivityCompat.finishAfterTransition(this)进行退出动画。
        ActivityCompat.finishAfterTransition(this);
    }
}
