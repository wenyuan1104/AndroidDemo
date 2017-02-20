package com.wenyuan.myandroiddemo.animation;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.wenyuan.myandroiddemo.BaseActivity;
import com.wenyuan.myandroiddemo.R;
import com.wenyuan.myandroiddemo.utils.copy.ToastUtils;

/**
 * Tween动画是操作某个控件让其展现出旋转、渐变、移动、缩放的这么一种转换过程，
 * 我们称为补间动画
 * 使用XML文件格式编写方式
 * <p>
 * animation的公共属性：
 * android:duration      setDuration(long)	 动画持续时间，以毫秒为单位
 * android:fillAfter     setFillAfter(boolean)	如果设置为true，控件动画结束时，将保持动画最后时的状态
 * android:fillBefore    setFillBefore(boolean)	如果设置为true,控件动画结束时，还原到开始动画前的状态
 * android:fillEnabled   setFillEnabled(boolean)	与android:fillBefore 效果相同，都是在动画结束时，将控件还原到初始化状态
 * android:repeatCount   setRepeatCount(int)	重复次数
 * android:repeatMode    setRepeatMode(int)	重复类型，有reverse和restart两个值，取值为RESTART或 REVERSE，必须与repeatCount一起使用才能看到效果。因为这里的意义是重复的类型，即回放时的动作。
 * android:interpolator  setInterpolator(Interpolator) 设定插值器，其实就是指定的动作效果，比如弹跳效果等
 * <p>
 * AccelerateDecelerateInterpolator============动画开始与结束的地方速率改变比较慢，在中间的时候加速。
 * AccelerateInterpolator===================动画开始的地方速率改变比较慢，然后开始加速。
 * AnticipateInterpolator ==================开始的时候向后然后向前甩。
 * AnticipateOvershootInterpolator=============开始的时候向后然后向前甩一定值后返回最后的值。
 * BounceInterpolator=====================动画结束的时候弹起。
 * CycleInterpolator======================动画循环播放特定的次数，速率改变沿着正弦曲线。
 * DecelerateInterpolator===================在动画开始的地方快然后慢。
 * LinearInterpolator======================以常量速率改变。
 * OvershootInterpolator====================向前甩一定值后再回到原来位置。
 * PathInterpolator==============新增的，就是可以定义路径坐标，然后可以按照路径坐标来跑动；
 * 注意其坐标并不是 XY，而是单方向，也就是我可以从0~1，然后弹回0.5 然后又弹到0.7 有到0.3，直到最后时间结束。
 * <p>
 * 效果	代码中方法	xml中属性
 * 越来越快	AccelerateInterpolator()	@android:anim/accelerate_interpolator
 * 越来越慢	DecelerateInterpolator()	@android:anim/decelerate_interpolator
 * 先快后慢	AccelerateDecelerateInterpolator()	@android:anim/accelerate_decelerate_interpolator
 * 先后退一小步然后向前加速	AnticipateInterpolator()	@android:anim/anticipate_interpolator
 * 快速到达终点超出一小步然后回到终点	OvershootInterpolator()	@android:anim/overshoot_interpolator
 * 到达终点超出一小步然后回到终点	AnticipateOvershootInterpolator()	@android:anim/anticipate_overshoot_interpolator
 * 弹球效果，弹几下回到终点	BounceInterpolator()	@android:anim/bounce_interpolator
 * 均匀速度	LinearInterpolator()	@android:anim/linear_interpolator
 */
public class TweenAnimActivity extends BaseActivity implements Animation.AnimationListener, View.OnClickListener {

    private ImageView mImgForAnim;
    private FloatingActionButton mButForAnim;
    private RelativeLayout mActivityTweenAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void getLayoutResource() {
        setContentView(R.layout.activity_tween_anim);
    }

    @Override
    protected void initView() {
        setToolbar(true);
        mImgForAnim = (ImageView) findViewById(R.id.img_for_anim);
        mImgForAnim.setOnClickListener(this);
        mButForAnim = (FloatingActionButton) findViewById(R.id.but_for_anim);
        mButForAnim.setOnClickListener(this);
        mActivityTweenAnim = (RelativeLayout) findViewById(R.id.activity_tween_anim);
    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_animtion_tween, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_anim_alpha:
                alphaAnimation();
                break;
            case R.id.action_anim_scale:
                scaleAnimation();
                break;
            case R.id.action_anim_translate:
                translateAnimation();
                break;
            case R.id.action_anim_rotate:
                rotateAnimation();
                break;
            case R.id.action_group_1:
                setAnimation1();
                break;
            case R.id.action_group_2:
                curveAnimation();
                break;
            case R.id.action_group_3:
                break;
            case R.id.action_issue:
                mDialogV7Factory.showTextDialog("issue", getString(R.string.issue_animation), true);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 抛物线 动画
     */
    private void curveAnimation() {
    }

    /**
     * 组合动画
     */
    private void setAnimation1() {
        AnimationSet set = new AnimationSet(true);//参数：是否添加插值器
        //透明度动画
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.3f);
        alphaAnimation.setDuration(3000);
        set.addAnimation(alphaAnimation);
        //平移动画
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, -300.0f, 0.0f, 0.0f);
        translateAnimation.setDuration(3000);
        //开始组合动画
        set.setInterpolator(mContext, android.R.anim.accelerate_decelerate_interpolator);
        set.addAnimation(translateAnimation);
        mImgForAnim.startAnimation(set);
    }

    /**
     * 透明度动画
     * AnimationUtils.loadAnimation的返回的类型根据xml文件的 “根目录”
     */
    private void alphaAnimation() {
        //java代码创建动画
        //AlphaAnimation alphaAnim = new AlphaAnimation(1.0f, 0.5f);
        //从xml文件中加载动画文件
        Animation alphaAnim = AnimationUtils.loadAnimation(mContext, R.anim.anim_tween_alpha);
        //设置动画的相关元素
        //alphaAnim.setFillAfter(true);//控件保留动画结束后的状态
        //alphaAnim.setFillBefore(true);//动画结束控件回到初始状态
        //alphaAnim.setStartOffset();//设置动画开始延时时间
        //alphaAnim.setRepeatCount();//重复次数
        //alphaAnim.setRepeatMode();//重复类型，有reverse和restart两个值
        //Interpolator 时间插值类，定义动画变换的速度。能够实现alpha/scale/translate/rotate动画的加速、减速和重复等
        alphaAnim.setInterpolator(mContext, android.R.anim.accelerate_decelerate_interpolator);
        //为视图设置动画并开始
        mImgForAnim.startAnimation(alphaAnim);
        mButForAnim.startAnimation(alphaAnim);
        //监听动画状态
        alphaAnim.setAnimationListener(this);
    }

    /**
     * 平移动画
     */
    private void translateAnimation() {
        //TranslateAnimation translateAnim = new TranslateAnimation();
        TranslateAnimation translateAnim = (TranslateAnimation) AnimationUtils.loadAnimation(
                mContext, R.anim.anim_tween_translate);
        mButForAnim.startAnimation(translateAnim);
        translateAnim.setAnimationListener(this);
    }

    /**
     * 旋转动画
     */
    private void rotateAnimation() {
        //RotateAnimation rotateAnim = new RotateAnimation();
        RotateAnimation rotateAnim = (RotateAnimation) AnimationUtils.loadAnimation(
                mContext, R.anim.anim_tween_rotate);
        mImgForAnim.startAnimation(rotateAnim);
        rotateAnim.setAnimationListener(this);
    }

    /**
     * 伸缩动画
     * pivotXType,它的取值有三个，
     * Animation.ABSOLUTE、
     * Animation.RELATIVE_TO_SELF
     * Animation.RELATIVE_TO_PARENT；
     * TODO 构造函数中没有使用pivotXType则 pivotX使用绝对数值
     */
    private void scaleAnimation() {
        //ScaleAnimation scaleAnim = new ScaleAnimation();
        ScaleAnimation scaleAnim = (ScaleAnimation) AnimationUtils.loadAnimation(mContext, R.anim.anim_tween_scale);
        mImgForAnim.startAnimation(scaleAnim);
        scaleAnim.setAnimationListener(this);
    }

    /**
     * 动画开始
     *
     * @param animation
     */
    @Override
    public void onAnimationStart(Animation animation) {
        ToastUtils.show(mContext, "animation start");
    }

    /**
     * 动画结束
     *
     * @param animation
     */
    @Override
    public void onAnimationEnd(Animation animation) {
        ToastUtils.show(mContext, "animation end");
        //if (animation instanceof ScaleAnimation)
    }

    /**
     * 动画 重复调用
     *
     * @param animation
     */
    @Override
    public void onAnimationRepeat(Animation animation) {
        ToastUtils.show(mContext, "animation Repeat(重复)");
    }

    @Override
    public void onClick(View v) {
        ToastUtils.show(mContext, "点击干什么啊！(╯▔皿▔)╯");
    }
}
