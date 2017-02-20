package com.wenyuan.myandroiddemo.storage;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.wenyuan.myandroiddemo.BaseActivity;
import com.wenyuan.myandroiddemo.BaseFragment;
import com.wenyuan.myandroiddemo.R;


public class StorageActivity extends BaseActivity implements SelectFragment.OnButtonListener {

    private BaseFragment mFileFragment, mDatabaseFr, mToPFragmetn, mPFFragment;
    private SelectFragment mFramentLeft;
    private FrameLayout mFramentRight;
    private BaseFragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void getLayoutResource() {
        setContentView(R.layout.activity_storage);
    }

    @Override
    protected void initView() {
        setToolbar(true);
        mFramentLeft = (SelectFragment) getSupportFragmentManager().findFragmentById(R.id.frament_left);
        mFramentRight = (FrameLayout) findViewById(R.id.frament_right);
        mFramentLeft.setOnButtonListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void ButtonClickForFile() {
        showFragmentForRight(0);
    }

    @Override
    public void ButtonClickForDatabase() {
        showFragmentForRight(1);
    }

    @Override
    public void ButtonClickForToP() {
        showFragmentForRight(2);
    }

    @Override
    public void ButtonClickForToPF() {
        showFragmentForRight(3);
    }

    /**
     *
     */
    public void showFragmentForRight(int position) {
        findViewById(R.id.text_hint).setVisibility(8);
        FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
        if (null != mCurrentFragment)//隐藏fragment
            fragmentManager.hide(mCurrentFragment);
        switch (position) {
            case 0:
                if (null == mFileFragment) {
                    mFileFragment = new FileFragment();
                    fragmentManager.add(R.id.frament_right, mFileFragment);
                } else {
                    fragmentManager.show(mFileFragment);
                }
                mCurrentFragment = mFileFragment;
                break;
            case 1:
                if (null == mDatabaseFr) {
                    mDatabaseFr = new DataBaseFragment();
                    fragmentManager.add(R.id.frament_right, mDatabaseFr);
                } else {
                    fragmentManager.show(mDatabaseFr);
                }
                mCurrentFragment = mDatabaseFr;
                break;
            case 2:
                if (null == mToPFragmetn) {
                    mToPFragmetn = new ProviderFragment();
                    fragmentManager.add(R.id.frament_right, mToPFragmetn);
                } else {
                    fragmentManager.show(mToPFragmetn);
                }
                mCurrentFragment = mToPFragmetn;
                break;
            case 3:
                if (null == mPFFragment) {
                    mPFFragment = new PreferenceFragment();
                    fragmentManager.add(R.id.frament_right, mPFFragment);
                } else {
                    fragmentManager.show(mPFFragment);
                }
                mCurrentFragment = mPFFragment;
                break;
        }
        //对于是否要加transaction.addToBackStack(null);也就是将Fragment加入到回退栈。官方的说法是取决于你是否要在回退的时候显示上一个Fragment。
        //fragmentManager.addToBackStack(null);
        if (!getSupportFragmentManager().isDestroyed())
            fragmentManager.commitAllowingStateLoss();
    }
}
