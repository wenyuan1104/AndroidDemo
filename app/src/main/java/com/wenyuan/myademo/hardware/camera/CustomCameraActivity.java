package com.wenyuan.myademo.hardware.camera;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.wenyuan.myademo.BaseActivity;
import com.wenyuan.myademo.R;
import com.wenyuan.myademo.utils.AlertDialogV7Factory;
import com.wenyuan.myademo.utils.ToastUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.hardware.Camera.open;

/***
 * surfaceHolder他是系统提供的一个用来设置surfaceView的一个对象，而它通过surfaceView.getHolder()这个方法来获得。
 * Camera提供一个setPreviewDisplay(SurfaceHolder)的方法来连接。
 * SurfaceHolder.Callback,这是个holder用来显示surfaceView 数据的接口,他必须实现以下3个方法
 * <p>
 * SurfaceView可以在多线程中被访问。
 * 注 意：一个SurfaceView只在SurfaceHolder.Callback.surfaceCreated()
 * 和SurfaceHolder.Callback.surfaceDestroyed()
 * 调用之间是可用的，其他时间是得不到它的Canvas对象的 （null）。
 */
public class CustomCameraActivity extends BaseActivity implements SurfaceHolder.Callback, View.OnClickListener {

    public static final String MY_APP_POTO_DRI = "MyAndroidDemo";

    private Camera mCamera;
    private SurfaceView mCameraView;
    private SurfaceHolder mHolder;
    private Button mButCamera;
    private Button mButFlashLight;
    private Button mButVideo;

    private boolean is_takingPhoto;//防止连拍
    private int mCurrentType;//当前摄像头位置。0：后置，1：前置
    private boolean mIsFlashLight;//闪光灯是否打开
    private boolean mIsLackPermission;//是否拥有需要的权限

    private AlertDialogV7Factory mDialogV7Factory;

    /**
     * 拍照声音
     */
    private ToneGenerator mTone;
    private Camera.ShutterCallback mShutterCallback = new Camera.ShutterCallback() {
        @Override
        public void onShutter() {
            if (null == mTone)
                mTone = new ToneGenerator(
                        AudioManager.AUDIOFOCUS_REQUEST_GRANTED,
                        ToneGenerator.MIN_VOLUME);
            mTone.startTone(ToneGenerator.TONE_PROP_BEEP);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHolder = mCameraView.getHolder();//获得SurfaceView
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);//surfaceview不维护自己的缓冲区，等待屏幕渲染引擎将内容推送到用户面前
        mHolder.setKeepScreenOn(true);//屏幕常亮
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
        mButFlashLight = (Button) findViewById(R.id.but_flash_light);
        mButFlashLight.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mDialogV7Factory = new AlertDialogV7Factory(mContext);
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
            case R.id.but_flash_light:
                changeFlashLight();
                break;
        }
    }

    /**
     * 点击快门拍照
     */
    private void shutterPress() {
        if (!is_takingPhoto) {
            is_takingPhoto = true;
            mCamera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, Camera camera) {
                    if (success) {
                        setCameraParam(camera);
                        //判断当前系统声音是否关闭 0代表静音或者震动
                        AudioManager am = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
                        int state = am.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
                        camera.takePicture(state == 0 ? null : mShutterCallback,
                                new Camera.PictureCallback() {
                                    @Override
                                    public void onPictureTaken(byte[] data, Camera camera) {
                                        //在这里 可以获取 raw 原始图片数据
                                    }
                                },
                                new Camera.PictureCallback() {
                                    @Override
                                    public void onPictureTaken(byte[] data, Camera camera) {
                                        //图片太大不能使用intent传递  保存在file 或者 保存在缓存中
                                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                        String filePath = SavePotoToFile(bitmap);
                                        if (!TextUtils.isEmpty(filePath)) {
                                            setResult(2, new Intent().putExtra("file_path", filePath));
                                            CustomCameraActivity.this.finish();
                                        }
                                    }
                                });
                        //有人说加这行可以使无法对焦的机器去自动对焦，在小米手机上试了好用，但是不知道其他机器
                        mCamera.cancelAutoFocus();
                    } else {
                        ToastUtils.show(mContext, "对焦拍照失败", Toast.LENGTH_LONG);
                        is_takingPhoto = false;
                    }
                }
            });
        }
    }

    /**
     * 保存拍摄的图片保存到文件中
     *
     * @param data
     */
    private String SavePotoToFile(Bitmap bitmap) {
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
            return "";
        // 在公共文件夹Pictures下新建自己的文件夹MyAndroidDemo
        File fileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), MY_APP_POTO_DRI);
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }
        String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        File file = new File(fileDir.getPath(), fileName.concat(".jpg"));
        try {
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
            // 参数二：压缩大小 0-100   0:体积最小
            //质量压缩方法，这里100表示不压缩，把压缩后的数据存放到bstream中
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.flush();
            stream.close();
            bitmap.recycle();
            bitmap = null;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return file.getPath();
    }

    /**
     * 控制闪光灯
     */
    private void changeFlashLight() {
        if (mCamera == null) return;
        Camera.Parameters cp = mCamera.getParameters();
        if (cp == null) return;
        //是否支持闪光灯
        List<String> listFlash = cp.getSupportedFlashModes();
        if (listFlash == null && listFlash.size() <= 0) return;
        String flash = cp.getFlashMode();
        if (mIsFlashLight) {//关闭
            if (!Camera.Parameters.FLASH_MODE_OFF.equals(flash)) {
                if (listFlash.contains(Camera.Parameters.FLASH_MODE_OFF)) {
                    cp.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    mIsFlashLight = !mIsFlashLight;
                }
            }
        } else {//开启
            if (!Camera.Parameters.FLASH_MODE_TORCH.equals(flash)) {
                if (listFlash.contains(Camera.Parameters.FLASH_MODE_TORCH)) {
                    cp.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    mIsFlashLight = !mIsFlashLight;
                }
            }
        }
        mCamera.setParameters(cp);
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
                    changeCameraType(i);
                    break;
                }
            } else if (mCurrentType == 1) {
                if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    changeCameraType(i);
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
    private void changeCameraType(int i) {
        mCamera.stopPreview();//停掉原来摄像头的预览
        mCamera.release();//释放资源
        mCamera = null;//取消原来摄像头
        mCamera = Camera.open(i);//打开当前选中的摄像头
        mCamera.setDisplayOrientation(90);
        try {
            mCamera.setPreviewDisplay(mHolder);//通过surfaceview显示取景画面
            mCamera.startPreview();
            mCurrentType = i;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 设置camera 参数
     *
     * @param camera
     */
    private void setCameraParam(Camera camera) {
        if (null == camera)
            camera = mCamera;
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
    }

    /**
     * 开始创建 surfaceview
     *
     * @param holder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (mCamera == null && !mIsLackPermission) {
            try {
                mCamera = open();//创建camera
                mCamera.setDisplayOrientation(90);
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (RuntimeException e) {
                mIsLackPermission = true;
                mDialogV7Factory.showTextDialog("警告！", "权限缺失，请进入系统设置“权限管理”开启相机权限！", false);
                return;
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
        if (!mIsLackPermission) {
            setCameraParam(mCamera);
            mCamera.startPreview();
        }

    }

    /**
     * surfaceview销毁事件处理
     *
     * @param holder
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (!mIsLackPermission) {
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
}
