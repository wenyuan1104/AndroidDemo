package com.wenyuan.myandroiddemo;

import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;

import com.wenyuan.myandroiddemo.animation.ActivityOptionsActivity;
import com.wenyuan.myandroiddemo.animation.FrameAnimActivity;
import com.wenyuan.myandroiddemo.animation.PropertyAnimActivity;
import com.wenyuan.myandroiddemo.animation.TweenAnimActivity;
import com.wenyuan.myandroiddemo.customview.CustomViewActivity;
import com.wenyuan.myandroiddemo.detail.permission.PermissionActivity;
import com.wenyuan.myandroiddemo.detail.picture.HandlerPicActivity;
import com.wenyuan.myandroiddemo.encrypt.EncyptActivity;
import com.wenyuan.myandroiddemo.event.EventActivity;
import com.wenyuan.myandroiddemo.hardware.camera.SysCameraActivity;
import com.wenyuan.myandroiddemo.layout.LayoutActivity;
import com.wenyuan.myandroiddemo.utils.AlertDialogV7Factory;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Button mButEncryption;
    private Button mButHardware;
    private Button mButAnimation;
    private Button mButCustomView;
    private Button mButEvent;
    private Button mButMaterial;
    private Button mButStatesbar;
    private Button mButThirdparty;

    private AlertDialogV7Factory mDialogFactory;
    private Button mButBitmap;
    private Button mButVersionM;
    private Button mButMvp;
    private Button mButMvvm;
    private Button mButFile;
    private Button mButWeb;
    private Button mButService;
    private int mContentViewHeight;
    private Button mButLayout;
    private Button mButMedia;
    private Button mButNotifications;
    private Button mButJni;
    private Button mButDownUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.anim_activity_start, 0);
        try {
            Thread.sleep(1500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void getLayoutResource() {
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initView() {
        setToolbar(false);
        mToolbar.setTitle(getString(R.string.app_name));
        mButEncryption = (Button) findViewById(R.id.but_encryption);
        mButEncryption.setOnClickListener(this);
        mButHardware = (Button) findViewById(R.id.but_hardware);
        mButHardware.setOnClickListener(this);
        mButAnimation = (Button) findViewById(R.id.but_animation);
        mButAnimation.setOnClickListener(this);
        mButCustomView = (Button) findViewById(R.id.but_custom_view);
        mButCustomView.setOnClickListener(this);
        mButEvent = (Button) findViewById(R.id.but_event);
        mButEvent.setOnClickListener(this);
        mButMaterial = (Button) findViewById(R.id.but_material);
        mButMaterial.setOnClickListener(this);
        mButStatesbar = (Button) findViewById(R.id.but_statesbar);
        mButStatesbar.setOnClickListener(this);
        mButThirdparty = (Button) findViewById(R.id.but_thirdparty);
        mButThirdparty.setOnClickListener(this);
        mButBitmap = (Button) findViewById(R.id.but_bitmap);
        mButBitmap.setOnClickListener(this);
        mButVersionM = (Button) findViewById(R.id.but_version_m);
        mButVersionM.setOnClickListener(this);
        mButMvp = (Button) findViewById(R.id.but_mvp);
        mButMvp.setOnClickListener(this);
        mButMvvm = (Button) findViewById(R.id.but_mvvm);
        mButMvvm.setOnClickListener(this);
        mButFile = (Button) findViewById(R.id.but_file);
        mButFile.setOnClickListener(this);
        mButWeb = (Button) findViewById(R.id.but_web);
        mButWeb.setOnClickListener(this);
        mButService = (Button) findViewById(R.id.but_service);
        mButService.setOnClickListener(this);
        mButLayout = (Button) findViewById(R.id.but_layout);
        mButLayout.setOnClickListener(this);
        mButMedia = (Button) findViewById(R.id.but_media);
        mButMedia.setOnClickListener(this);
        mButNotifications = (Button) findViewById(R.id.but_Notifications);
        mButNotifications.setOnClickListener(this);
        mButJni = (Button) findViewById(R.id.but_jni);
        mButJni.setOnClickListener(this);
        mButDownUp = (Button) findViewById(R.id.but_down_up);
        mButDownUp.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mDialogFactory = new AlertDialogV7Factory(mContext);
        /*Toolbar 开始出现动画*/
        ViewGroup.LayoutParams lp = mToolbar.getLayoutParams();
        lp.height = 700;
        mToolbar.setLayoutParams(lp);
        mToolbar.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        mToolbar.getViewTreeObserver().removeOnPreDrawListener(this);
                        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

                        mToolbar.measure(widthSpec, heightSpec);
                        mContentViewHeight = mToolbar.getHeight();
                        collapseToolbar();
                        return true;
                    }
                });
    }

    private void collapseToolbar() {
        int toolBarHeight;
        TypedValue tv = new TypedValue();
        getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true);
        toolBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        ValueAnimator valueHeightAnimator = ValueAnimator.ofInt(mContentViewHeight, toolBarHeight);
        valueHeightAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ViewGroup.LayoutParams lp = mToolbar.getLayoutParams();
                lp.height = (Integer) animation.getAnimatedValue();
                mToolbar.setLayoutParams(lp);
            }
        });
        valueHeightAnimator.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_about:
                mDialogFactory.showTextDialog("建议", getString(R.string.main), true);
                break;
            case R.id.action_more:
                startActivity(MoreActivity.class);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.but_encryption:
                startActivity(EncyptActivity.class);
                break;
            case R.id.but_hardware:
                selectListForHard();
                break;
            case R.id.but_animation:
                selectListForAnimation();
                break;
            case R.id.but_custom_view:
                startActivity(CustomViewActivity.class);
                break;
            case R.id.but_event:
                startActivity(EventActivity.class);
                break;
            case R.id.but_material:
                break;
            case R.id.but_statesbar:
                break;
            case R.id.but_thirdparty:
                break;
            case R.id.but_bitmap:
                startActivity(HandlerPicActivity.class);
                break;
            case R.id.but_version_m:
                startActivity(PermissionActivity.class);
                break;
            case R.id.but_mvp:
                break;
            case R.id.but_mvvm:
                break;
            case R.id.but_file:
                break;
            case R.id.but_web:
                break;
            case R.id.but_service:
                break;
            case R.id.but_layout:
                startActivity(LayoutActivity.class);
                break;
            case R.id.but_media:
                break;
            case R.id.but_Notifications:
                break;
            case R.id.but_jni:
                break;
            case R.id.but_down_up:
                break;
        }
    }

    /**
     * activity转场动画：startActivity(Intent) or finish()之后被调用
     * fragment转场动画
     */
    private void selectListForAnimation() {
        String[] strings = {"TweenAnimation", "frameAnimation", "LayoutAnimation", "PropertyAnimation", "自定义动画", "ActivityOptions"};
        mDialogFactory.showSingleListDialog("Animation", true, -1, strings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        startActivity(TweenAnimActivity.class);
                        //activity转场动画 第二种方式：在style.xml文件中定义
                        overridePendingTransition(R.anim.anim_tween_group_3, R.anim.anim_tween_group_2);
                        break;
                    case 1:
                        startActivity(FrameAnimActivity.class);
                        break;
                    case 3:
                        startActivity(PropertyAnimActivity.class);
                        break;
                    case 4:
                        break;
                    case 5:
                        ActivityOptionsCompat compat = ActivityOptionAnim(null);
                        startActivity(ActivityOptionsActivity.class, compat.toBundle());
                        break;
                }
            }
        });
    }

    /**
     * ActivityOptions android 5.0 activity转场动画
     *
     * @param v
     * @return
     */
    private ActivityOptionsCompat ActivityOptionAnim(View v) {
        ActivityOptionsCompat compat = null;
        //1
        //compat = ActivityOptionsCompat.makeCustomAnimation(
        //        mContext, R.anim.transition_enter, R.anim.transition_exit);
        //2
        //compat = ActivityOptionsCompat.makeScaleUpAnimation(v, v.getWidth() / 2, v.getHeight() / 2, 0, 0);
        ////3
        //compat = ActivityOptionsCompat.makeThumbnailScaleUpAnimation();
        ////4
        compat = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
        ////5
        //compat = ActivityOptionsCompat.makeSceneTransitionAnimation();
        return compat;
    }

    /**
     * 硬件列表选择
     */
    private void selectListForHard() {
        String[] strings = {"相机", "传感器", "定位"};
        mDialogFactory.showSingleListDialog("Hardware", true, 0, strings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        startActivity(SysCameraActivity.class);
                        break;
                }
            }
        });
    }
}
