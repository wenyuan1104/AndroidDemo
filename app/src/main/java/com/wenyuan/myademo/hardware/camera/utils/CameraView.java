package com.wenyuan.myademo.hardware.camera.utils;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by www22_000 as wenyuan on 2016/11/1 16:23.
 * Email :wenyuan1104@163.com
 * Description:
 */

public class CameraView extends SurfaceView implements SurfaceHolder.Callback, Camera.PictureCallback {

    private Context mContext;
    private SurfaceHolder mHolder;

    private Camera mCamera;

    public CameraView(Context context) {
        super(context);
        this.mContext = context;
        this.mHolder = getHolder();//生成 surface holder
        this.mHolder.addCallback(this);
        //surfaceview不维护自己的缓冲区，等待屏幕渲染引擎将内容推送到用户面前
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS); //指定 push buffer
    }

    public CameraView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        this.mHolder = getHolder();//生成 surface holder
        this.mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS); //指定 push buffer
    }

    public CameraView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mContext = context;
        this.mHolder = getHolder();//生成 surface holder
        this.mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS); //指定 push buffer
    }

    /**
     * surface生成事件处理
     *
     * @param holder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera = Camera.open();//初始化摄像头
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Surface改变事件的处理
     *
     * @param holder
     * @param format
     * @param width
     * @param height
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //Camera.Parameters cp = mCamera.getParameters();
        //cp.setPreviewSize(width, height);
        //mCamera.setParameters(cp);//设置参数
        //mCamera.startPreview();//开始预览
    }

    /**
     * surface 销毁时处理
     *
     * @param holder
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mCamera.setPreviewCallback(null);
        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {

    }
}
