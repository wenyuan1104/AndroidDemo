package com.wenyuan.myademo.layout;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.wenyuan.myademo.BaseActivity;
import com.wenyuan.myademo.BaseFragment;
import com.wenyuan.myademo.R;

public class LayoutActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private RelativeLayout mRelativeContent;
    private RadioButton mRadButHome;
    private RadioButton mRadButRedian;
    private RadioButton mRadButUser;
    private RadioGroup mRadGroup;
    private BaseFragment mHomeFragment, mReFragment, mUserFragment, mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void getLayoutResource() {
        setContentView(R.layout.activity_layout);
    }

    @Override
    protected void initView() {
        setToolbar(true);
        mRelativeContent = (RelativeLayout) findViewById(R.id.relative_content);
        mRadButHome = (RadioButton) findViewById(R.id.rad_but_home);
        mRadButRedian = (RadioButton) findViewById(R.id.rad_but_redian);
        mRadButUser = (RadioButton) findViewById(R.id.rad_but_user);
        mRadGroup = (RadioGroup) findViewById(R.id.rad_group);
        mRadGroup.setOnCheckedChangeListener(this);
        mRadGroup.check(R.id.rad_but_home);
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
        if (null != mCurrentFragment)//隐藏fragment
            fragmentManager.hide(mCurrentFragment);
        switch (checkedId) {
            case R.id.rad_but_home:
                if (null == mHomeFragment) {
                    mHomeFragment = new LayoutFragment_1();
                    fragmentManager.add(R.id.relative_content, mHomeFragment);
                } else {
                    fragmentManager.show(mHomeFragment);
                }
                setTheme(R.style.AppTheme);
                mToolbar.setVisibility(View.VISIBLE);
                mCurrentFragment = mHomeFragment;
                break;
            case R.id.rad_but_redian:
                if (null == mReFragment) {
                    mReFragment = new LayoutFragment_2();
                    fragmentManager.add(R.id.relative_content, mReFragment);
                } else {
                    fragmentManager.show(mReFragment);
                }
                setTheme(R.style.IndicatorTheme);//这是TabPageIndicator样式
                mToolbar.setVisibility(View.GONE);
                mCurrentFragment = mReFragment;
                break;
            case R.id.rad_but_user:
                if (null == mUserFragment) {
                    mUserFragment = new LayoutFragment_3();
                    fragmentManager.add(R.id.relative_content, mUserFragment);
                } else {
                    fragmentManager.show(mUserFragment);
                }
                setTheme(R.style.AppTheme);
                mToolbar.setVisibility(View.GONE);
                mCurrentFragment = mUserFragment;
                break;
        }
        //对于是否要加transaction.addToBackStack(null);也就是将Fragment加入到回退栈。官方的说法是取决于你是否要在回退的时候显示上一个Fragment。
        //fragmentManager.addToBackStack(null);
        if (!getSupportFragmentManager().isDestroyed())
            fragmentManager.commitAllowingStateLoss();
    }


}
