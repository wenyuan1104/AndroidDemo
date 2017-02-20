package com.wenyuan.myandroiddemo.media;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.wenyuan.myandroiddemo.BaseFragment;
import com.wenyuan.myandroiddemo.R;
import com.wenyuan.myandroiddemo.media.utils.BaseVideoCallback;
import com.wenyuan.myandroiddemo.media.utils.MySurfaceView;
import com.wenyuan.myandroiddemo.media.utils.VideoMediaOfSys;
import com.wenyuan.myandroiddemo.utils.copy.ToastUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class OnlineVideoFragment extends BaseFragment implements View.OnClickListener, BaseVideoCallback.OnSurfaceViewCreatedListener {

    private String url = "http://v2.mukewang.com/38716012-acb2-44b9-9ccb-fa5675441444/L.mp4?auth_key=1481467853-0-0-0855f6be87913e823a6c59f8a89bb297";
    private VideoMediaOfSys mVideoMediaOfSys;//播放器

    private MySurfaceView mSurfaceView;
    private ImageView mImgPlay;


    public OnlineVideoFragment() {
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_online_video;
    }

    @Override
    protected void initView(View view) {
        mImgPlay = (ImageView) view.findViewById(R.id.img_play_for_online);
        mImgPlay.setOnClickListener(this);
    }

    @Override
    protected void initObject() {
        mVideoMediaOfSys = VideoMediaOfSys.getInstance();
        mVideoMediaOfSys.setOnSurfaceViewCreatedListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        mSurfaceView = (MySurfaceView) mFragRootView.findViewById(R.id.surface_online_video);
        mSurfaceView.setOnClickListener(this);
        mVideoMediaOfSys.setContentView(mSurfaceView);
        mImgPlay.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.surface_online_video:
                ToastUtils.show(mContext, "点我干啥");
                break;
            case R.id.img_play_for_online:
                if (mVideoMediaOfSys.isSurfaceViewCreated())
                    mVideoMediaOfSys.start();
                mImgPlay.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    @Override
    public void onSurfaceViewCreated() {
        mVideoMediaOfSys.initPlayNewVideo(url);
    }
}
