package com.wenyuan.myademo.hardware.camera;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.wenyuan.myademo.AppVar;
import com.wenyuan.myademo.BaseActivity;
import com.wenyuan.myademo.R;
import com.wenyuan.myademo.utils.ToastUtils;

import java.io.IOException;
import java.util.List;

import static android.hardware.Camera.open;

/***
 * surfaceHolder他是系统提供的一个用来设置surfaceView的一个对象，而它通过surfaceView.getHolder()这个方法来获得。
 * Camera提供一个setPreviewDisplay(SurfaceHolder)的方法来连接。
 * SurfaceHolder.Callback,这是个holder用来显示surfaceView 数据的接口,他必须实现以下3个方法
 */
public class CustomCameraActivity extends BaseActivity implements SurfaceHolder.Callback, View.OnClickListener {

    private Camera mCamera;
    private SurfaceView mCameraView;
    private SurfaceHolder mHolder;
    private Button mButCamera;
    private Button mButVideo;

    private int mCurrentType;//当前摄像头位置。0：后置，1：前置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHolder = mCameraView.getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    protected void getLayoutResource() {
        setContentView(R.layout.activity_custom_camera);
    }

    @Override
    protected void initView() {
        setToolbar(true);
        mCameraView = (SurfaceView) findViewById(R.id.camera_view);
        mButCamera = (Button) findViewById(R.id.but_camera);
        mButCamera.setOnClickListener(this);
        mButVideo = (Button) findViewById(R.id.but_change);
        mButVideo.setOnClickListener(this);
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_camera://快门拍照
                shutterPress();
                break;
            case R.id.but_change:
                changeCameraType();
                break;
        }
    }

    /**
     * 点击快门拍照
     */
    private void shutterPress() {
        mCamera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                if (success) {
                    Camera.Parameters cp = camera.getParameters();
                    cp.setPictureFormat(PixelFormat.JPEG);

                    List<Camera.Size> sizeList = camera.getParameters().getSupportedPreviewSizes();
                    Camera.Size baseSize = sizeList.get(0);
                    for (int i = 0; i < sizeList.size(); i++) {
                        if (sizeList.get(i).width * sizeList.get(i).height > baseSize.width * baseSize.height)
                            baseSize = sizeList.get(i);
                    }
                    cp.setPreviewSize(baseSize.width, baseSize.height);
                    camera.setParameters(cp);
                    camera.takePicture(null, null, new Camera.PictureCallback() {
                        @Override
                        public void onPictureTaken(byte[] data, Camera camera) {
                            //图片太大不能使用intent传递  需要对bitmap进行压缩
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            if (data != null) {
                                if (data.length / 1024 > 350
                                        && data.length / 1024 < 450) {
                                    options.inSampleSize = 3;
                                } else if (data.length / 1024 > 450) {
                                    options.inSampleSize = 4;
                                }
                            }
                            options.inPurgeable = true;//内存不足时可被回收
                            options.inInputShareable = true;
                            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);//此bitmap作为预览时候使用，用完一定注意回收


                            //Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                            //Bitmap.createBitmap();
                            //setResult(2, new Intent().putExtra("poto", bitmap));
                            AppVar.getInstance().setCustomBitmap(bitmap);
                            CustomCameraActivity.this.finish();
                        }
                    });
                } else {
                    ToastUtils.show(mContext, "拍照失败", Toast.LENGTH_LONG);
                }
            }
        });
    }

    /**
     * 后置，前置摄像头切换 的操作
     * 代表摄像头的方位，CAMERA_FACING_FRONT前置
     * CAMERA_FACING_BACK后置
     */
    private void changeCameraType() {
        Camera.CameraInfo info = new Camera.CameraInfo();
        int cameraCount = Camera.getNumberOfCameras();//获得摄像头个数
        for (int i = 0; i < cameraCount; i++) {
            Camera.getCameraInfo(i, info);
            if (mCurrentType == 0) {//后切前
                if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    changeCameraType(i, 1);
                    break;
                }
            } else if (mCurrentType == 1) {
                if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    changeCameraType(i, 0);
                    break;
                }
            }

        }
    }

    /**
     * 切换摄像头
     *
     * @param i
     * @param currentType
     */
    private void changeCameraType(int i, int currentType) {
        mCamera.stopPreview();//停掉原来摄像头的预览
        mCamera.release();//释放资源
        mCamera = null;//取消原来摄像头
        mCamera = Camera.open(i);//打开当前选中的摄像头
        mCamera.setDisplayOrientation(90);
        try {
            mCamera.setPreviewDisplay(mHolder);//通过surfaceview显示取景画面
            mCamera.startPreview();
            mCurrentType = currentType;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 开始创建 surfaceview
     *
     * @param holder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (mCamera == null) {
            try {
                mCamera = open();
                mCamera.setDisplayOrientation(90);
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * surfaceview改变事件处理
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

        //设置以下 从拍照后台切换回可见时这个activity销毁
        if (mHolder.getSurface() == null)
            return;
        mCamera.stopPreview();
        //mCamera = Camera.open();
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * surfaceview销毁事件处理
     *
     * @param holder
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        try {
            mCamera.stopPreview();
            mCamera.setPreviewDisplay(null);
            mCamera.release();
            mCamera = null;
            mCameraView = null;
            mHolder = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
