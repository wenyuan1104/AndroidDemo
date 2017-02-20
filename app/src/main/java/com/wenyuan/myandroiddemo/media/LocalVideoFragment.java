package com.wenyuan.myandroiddemo.media;


import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.wenyuan.myandroiddemo.BaseFragment;
import com.wenyuan.myandroiddemo.R;
import com.wenyuan.myandroiddemo.media.utils.BaseVideoCallback;
import com.wenyuan.myandroiddemo.media.utils.MySurfaceView;
import com.wenyuan.myandroiddemo.media.utils.VideoMediaOfSys;
import com.wenyuan.myandroiddemo.utils.copy.ToastUtils;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 * <p>
 * TextureView的兄弟SurfaceView
 * 应用程序的视频或者opengl内容往往是显示在一个特别的UI控件中：SurfaceView。
 * SurfaceView的工作方式是创建一个置于应用窗口之后的新窗口。这种方式的效率非常高，
 * 因为SurfaceView窗口刷新的时候不需要重绘应用程序的窗口（android普通窗口的视图绘制机制是一层一层的，
 * 任何一个子元素或者是局部的刷新都会导致整个视图结构全部重绘一次，因此效率非常低下，不过满足普通应用界面的需求还是绰绰有余），
 * <p>
 * 但是SurfaceView也有一些非常不便的限制。
 * 因为SurfaceView的内容不在应用窗口上，所以不能使用变换（平移、缩放、旋转等）。
 * 也难以放在ListView或者ScrollView中，不能使用UI控件的一些特性比如View.setAlpha()。
 * </p>
 */
public class LocalVideoFragment extends BaseFragment implements View.OnClickListener, BaseVideoCallback.OnSurfaceViewCreatedListener {

    private VideoMediaOfSys mVideoMediaOfSys;//播放器

    private MySurfaceView mSurfaceView;
    private ImageView mImgPlay;

    public LocalVideoFragment() {
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_local_video;
    }

    @Override
    protected void initView(View view) {
        mImgPlay = (ImageView) mFragRootView.findViewById(R.id.img_play);
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
        mSurfaceView = (MySurfaceView) mFragRootView.findViewById(R.id.surface_local_video);
        mSurfaceView.setOnClickListener(this);
        mVideoMediaOfSys.setContentView(mSurfaceView);
        mImgPlay.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.surface_local_video:
                ToastUtils.show(mContext, "点我干啥");
                break;
            case R.id.img_play:
                if (mVideoMediaOfSys.isSurfaceViewCreated())
                    mVideoMediaOfSys.start();
                mImgPlay.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    /**
     * 获得播放地址
     *
     * @return
     */
    public String getVideoPath() {
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
            return "";
        String path = Environment.getExternalStorageDirectory().getPath() + "/my_demo" + "/demo.mp4";
        if (!new File(path).exists()) {
            ToastUtils.show(mContext, "local video：你没有这个video文件,请打开手机文件管理器新建一个“my_demo”文件夹，然后再这个文件下存储一个“demo.mp4”视频文件。", 3000);
            return "";
        }
        return path;
    }

    /**
     * surfaceview 创建完毕
     * <p>
     * 当surfaceview创建完毕后，进行mediaplayer设置，防止播放只有声音没有画面
     */
    @Override
    public void onSurfaceViewCreated() {
        mVideoMediaOfSys.initPlayNewVideo(getVideoPath());
    }
}
