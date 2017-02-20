package com.wenyuan.myandroiddemo.media.utils;

import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.ViewGroup;

import com.socks.library.KLog;
import com.wenyuan.myandroiddemo.utils.UIManager;

import java.io.IOException;

/**
 * Created by wenyuan on 2016/12/11 16:59.
 * Description:
 * <p>流程说明：
 * 1、先调用{@link #setContentView(MySurfaceView)}设置目标Surfaceview
 * 2、然后调用{@link #initPlayNewVideo(String)}设置播放地址，进行播放初始化操作
 * 3、最后调用{@link #start()}播放video
 * </p>
 */
public class VideoMediaOfSys extends BaseVideoCallback {

    private static volatile VideoMediaOfSys sVideoMediaOfSys;
    private MediaPlayer mMediaPlayer;
    private SurfaceHolder mDefaultSurfaceHolder;

    private boolean mSurfaceViewCreated;//surfaceview是否创建

    private MediaPlayer.OnPreparedListener mInternalOnPreparedListener;
    private OnSurfaceViewCreatedListener mOnSurfaceViewCreatedListener;
    private MySurfaceView mSurfaceView;

    /**
     * 安全的单例模式
     *
     * @return
     */
    public static VideoMediaOfSys getInstance() {
        VideoMediaOfSys instance = sVideoMediaOfSys;
        if (instance == null) {
            synchronized (VideoMediaOfSys.class) {
                instance = sVideoMediaOfSys;
                if (null == instance) {
                    instance = new VideoMediaOfSys();
                    sVideoMediaOfSys = instance;
                }
            }
        }
        return instance;
    }

    /**
     * 构造方法初始化 注册监听器
     */
    public VideoMediaOfSys() {
        initPlayerInternalListener();
    }

    /**
     * 获得surfaceview创建状态
     *
     * @return
     */
    public boolean isSurfaceViewCreated() {
        return mSurfaceViewCreated;
    }

    /**
     * 播放视频初始化，创建一个mediaplay
     *
     * @param path 播放地址
     */
    public void initPlayNewVideo(String path) {
        if (null != mMediaPlayer)
            release();//先释放
        mMediaPlayer = new MediaPlayer();
        prepareDefaultPlayer(path);
    }

    /**
     * 播放准备 初始化设置
     *
     * @param path 播放地址
     */
    public void prepareDefaultPlayer(String path) {
        if (null != mMediaPlayer && !TextUtils.isEmpty(path)) {
            try {
                //AssetManager assetMg = UIManager.getInstance().getAppContext().getAssets();
                //AssetFileDescriptor fileDescriptor = assetMg.openFd("demo.mp4");
                //mMediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(),
                //        fileDescriptor.getStartOffset(), fileDescriptor.getLength());
                mMediaPlayer.setDataSource(path);
                mMediaPlayer.setDisplay(this.mDefaultSurfaceHolder);
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mMediaPlayer.setScreenOnWhilePlaying(true);
                mMediaPlayer.prepareAsync();//当使用Prepare（）播放时，是同步播放视频 造成卡顿
                mMediaPlayer.setOnPreparedListener(mInternalOnPreparedListener);
                //mMediaPlayer.setOnCompletionListener(mInternalOnCompletionListener);
                //mMediaPlayer.setOnBufferingUpdateListener(mInternalOnBufferingUpdateListener);
                //mMediaPlayer.setOnInfoListener(mInternalOnInfoListener);
                //mMediaPlayer.setOnSeekCompleteListener(mOnSeekCompleteListener);
                //mMediaPlayer.setOnVideoSizeChangedListener(mOnVideoSizeChangedListener);
                //mMediaPlayer.setOnErrorListener(mOnErrorListener);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 释放资源
     */
    private void release() {
        stop();
        reset();
        if (null != mMediaPlayer) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    /**
     * 停止播放
     */
    public void stop() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
        }
    }

    /**
     * 重置资源
     */
    public void reset() {
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
        }

    }

    /**
     * 播放
     */
    public void start() {
        if (null != mMediaPlayer)
            mMediaPlayer.start();
        else
            throw new NullPointerException("播个鬼啊,播放器都是空的!");
    }

    /**
     * 设置播放器要显示的VideoView
     *
     * @param view
     */
    public void setContentView(MySurfaceView view) {
        this.mSurfaceView = view;
        setSurfaceViewShow();
        view.addCallBack(this);
    }

    private void setSurfaceViewShow() {
        if (Configuration.ORIENTATION_PORTRAIT == UIManager.getInstance().getAppContext().getResources().getConfiguration().orientation) {
            ViewGroup.LayoutParams lp = mSurfaceView.getLayoutParams();
            lp.width = UIManager.getInstance().getScreenWidth();
            lp.height = UIManager.getInstance().getScreenWidth() * 3 / 4;
            mSurfaceView.setLayoutParams(lp);
        } else if (Configuration.ORIENTATION_LANDSCAPE == UIManager.getInstance().getAppContext().getResources().getConfiguration().orientation) {

        }
        ////首先取得video的宽和高
        //int vWidth = mMediaPlayer.getVideoWidth();
        //int vHeight = mMediaPlayer.getVideoHeight();
        ////然后获取屏幕的宽高
        //int screenW = UIManager.getInstance().getScreenWidth();
        //int screenH = UIManager.getInstance().getScreenHeight();
        //if (vWidth > screenW || vHeight > screenH) {
        //    //如果video的宽或者高超出了当前屏幕的大小，则要进行缩放
        //    float wRatio = (float) vWidth / (float) screenW;
        //    float hRatio = (float) vHeight / (float) screenH;
        //    //选择大的一个进行缩放
        //    float ratio = Math.max(wRatio, hRatio);
        //    vWidth = (int) Math.ceil((float) vWidth / ratio);
        //    vHeight = (int) Math.ceil((float) vHeight / ratio);
        //    //设置surfaceView的布局参数
        //    mSurfaceView.setLayoutParams(new LinearLayout.LayoutParams(vWidth, vHeight));
        //}
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.mDefaultSurfaceHolder = holder;
        this.mSurfaceViewCreated = true;
        if (mOnSurfaceViewCreatedListener != null)
            mOnSurfaceViewCreatedListener.onSurfaceViewCreated();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        this.mSurfaceViewCreated = false;
        this.mDefaultSurfaceHolder = null;
        release();//释放播放器资源
    }


    /**
     * 初始化监听器
     */

    private void initPlayerInternalListener() {
        mInternalOnPreparedListener = new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mp) {
                KLog.d("加载");
            }
        };
    }

    @Override
    public void setOnSurfaceViewCreatedListener(OnSurfaceViewCreatedListener onSurfaceViewCreatedListener) {
        this.mOnSurfaceViewCreatedListener = onSurfaceViewCreatedListener;
    }
}
