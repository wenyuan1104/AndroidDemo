package com.wenyuan.myandroiddemo.media;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import com.wenyuan.myandroiddemo.BaseActivity;
import com.wenyuan.myandroiddemo.BaseFragment;
import com.wenyuan.myandroiddemo.R;

import java.lang.ref.WeakReference;

public class MediaActivity extends BaseActivity {

    private BaseFragment mLocalFragment, mOnlineFragment;
    private VoiceFragment mVoiceFragment;
    private FragmentManager mManager;
    private FragmentTransaction mTransaction;

    private BaseFragment mCurrentFramgnt;
    private int mCurrentShowView;
    private final int LOCAL_VIDEO = 0x01;
    private final int ONLINE_VIDEO = 0x02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void getLayoutResource() {
        setContentView(R.layout.activity_media);
    }

    @Override
    protected void initView() {
        setToolbar(true);
        mManager = getSupportFragmentManager();
        mTransaction = mManager.beginTransaction();
        initFragment();
    }

    private void initFragment() {
        mVoiceFragment = (VoiceFragment) mManager.findFragmentById(R.id.frament_voice);
        if (null == mLocalFragment) {
            mLocalFragment = new LocalVideoFragment();
            mTransaction.add(R.id.content_up, mLocalFragment);
        } else {
            mTransaction.show(mLocalFragment);
        }
        if (!getSupportFragmentManager().isDestroyed()) {
            mTransaction.commitAllowingStateLoss();
            mCurrentShowView = LOCAL_VIDEO;
            mCurrentFramgnt = mLocalFragment;
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mediaplayer, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_media_switch) {
            if (mCurrentFramgnt != null) {//移除当前的fragment
                mTransaction.remove(mCurrentFramgnt);
                //mTransaction.commit();// todo 同一个事物实例不能 执行提交两次
            }
            WeakReference<FragmentTransaction> weakReference = new WeakReference<>(getSupportFragmentManager().beginTransaction());
            FragmentTransaction bt = weakReference.get();
            //FragmentTransaction bt = getSupportFragmentManager().beginTransaction();
            if (mCurrentShowView == LOCAL_VIDEO) {//显示下一个fragment
                if (mOnlineFragment == null)
                    mOnlineFragment = new OnlineVideoFragment();
                bt.replace(R.id.content_up, mOnlineFragment);
                mCurrentShowView = ONLINE_VIDEO;
                mCurrentFramgnt = mOnlineFragment;
            } else if (mCurrentShowView == ONLINE_VIDEO) {
                if (mLocalFragment == null)
                    mLocalFragment = new LocalVideoFragment();
                bt.replace(R.id.content_up, mLocalFragment);
                mCurrentShowView = LOCAL_VIDEO;
                mCurrentFramgnt = mLocalFragment;
            }
            if (!getSupportFragmentManager().isDestroyed()) {
                bt.commitAllowingStateLoss();
                mTransaction = bt;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
