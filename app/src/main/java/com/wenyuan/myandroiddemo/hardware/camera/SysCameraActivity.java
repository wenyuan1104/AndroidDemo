package com.wenyuan.myandroiddemo.hardware.camera;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.socks.library.KLog;
import com.wenyuan.myandroiddemo.BaseActivity;
import com.wenyuan.myandroiddemo.R;
import com.wenyuan.myandroiddemo.utils.AlertDialogV7Factory;
import com.wenyuan.myandroiddemo.utils.copy.ToastUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * MediaStore.ACTION_IMAGE_CAPTURE 拍照；
 * MediaStore.ACTION_VIDEO_CAPTURE 录像。
 */
public class SysCameraActivity extends BaseActivity implements View.OnClickListener {

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    private Button but_system_camera;
    private Button but_custom_camera;
    private ImageView img_show_poto;
    private TextView mTextShowPath;

    private Uri fileUr;
    private AlertDialogV7Factory mDialogFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void getLayoutResource() {
        setContentView(R.layout.activity_sys_camera);
    }

    @Override
    protected void initView() {
        setToolbar(true);
        but_system_camera = (Button) findViewById(R.id.but_system_camera);
        but_system_camera.setOnClickListener(this);
        but_custom_camera = (Button) findViewById(R.id.but_custom_camera);
        but_custom_camera.setOnClickListener(this);
        img_show_poto = (ImageView) findViewById(R.id.img_show_poto);
        mTextShowPath = (TextView) findViewById(R.id.text_show_path);
    }

    @Override
    protected void initData() {
        mDialogFactory = new AlertDialogV7Factory(mContext);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_camera, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_issue:
                mDialogFactory.showTextDialog("有关于相机调用的问题", getString(R.string.issue_camera), true);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_system_camera:
                checkPermissionforM(1);
                break;
            case R.id.but_custom_camera:
                checkPermissionforM(0);
                break;
        }
    }

    /**
     * 检查android 6.0 及以上版本权限申请
     *
     * @return
     */
    private void checkPermissionforM(int type) {
        if (!mIsAndroid_M) {
            if (type == 0)
                startActivityForResult(CustomCameraActivity.class, null, 101);
            else if (type == 1)
                invokeSysCamera();
            return;
        }
        //检查权限
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {//没有授予权限
            //申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, type);
        } else {//不是android6.0时 没有授予权限也不会走上面的if
            if (type == 0)
                startActivityForResult(CustomCameraActivity.class, null, 101);
            else if (type == 1)
                invokeSysCamera();
        }
    }

    /**
     * 处理权限申请回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
            case 0:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (requestCode == 0)
                        startActivityForResult(CustomCameraActivity.class, null, 101);
                    else if (requestCode == 1)
                        invokeSysCamera();
                } else {//没有授权
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                        ToastUtils.show(mContext, "表明用户没有彻底禁止弹出权限请求,不用跳转设置", Toast.LENGTH_LONG);
                    } else {//表明用户已经彻底禁止弹出权限请求
                        StringBuilder builder = new StringBuilder();
                        for (String permission : permissions) {
                            if (permission.equals(Manifest.permission.CAMERA))
                                builder.append("相机权限\n");
                        }
                        mDialogFactory.showForPermissionOfDialog("权限缺失", builder.toString());
                    }
                }
                break;
            default:
        }
    }

    /**
     * 利用系统自带的相机应用：拍照
     * ACTION_IMAGE_CAPTURE_SECURE
     * 它返回照相机拍摄到的图像，设备被固定
     * ACTION_VIDEO_CAPTURE
     * 它调用已有的视频应用程序在Android中捕获视频
     */
    private void invokeSysCamera() {
        //先判断手机中是否存在 拍照App
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        PackageManager manager = mContext.getPackageManager();
        List list = manager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() <= 0) {
            ToastUtils.show(mContext, "没有找到拍照应用");
            return;
        }
        // 设置存储位置  此处两句话句intent的值设置关系到后面的onActivityResult中会进入那个分支，
        //即关系到data是否为null，如果此处指定，则后来的data为null，可以拿到图片并进行操作*/
        fileUr = Uri.fromFile(getOutputMediaFile(MEDIA_TYPE_IMAGE));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUr);
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);

        startActivityForResult(intent, 100);
    }

    /**
     * Create a File for saving an image or video
     */
    private File getOutputMediaFile(int type) {
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
            ToastUtils.show(mContext, "没有SD卡");
        File mediaStorageDir = null;
        //这个方法返回图像和视频的标准共享位置，别的应用也可以访问，如果你的应用被卸载了，这个路径下的文件是会保留的。
        mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "MyCameraApp");
        //这个方法返回的路径是和你的应用相关的一个存储图像和视频的方法。
        //如果应用被卸载，这个路径下的东西全都会被删除。
        //这个路径没有什么安全性限制，别的应用也可以自由访问里面的文件。
        //mediaStorageDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                ToastUtils.show(mContext, "【检查你的权限】failed to create directory, check if you have the WRITE_EXTERNAL_STORAGE permission");
                KLog.d("【检查你的权限】failed to create directory, check if you have the WRITE_EXTERNAL_STORAGE permission");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }
        return mediaFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (100 == requestCode && RESULT_OK == resultCode) {//系统拍照
            if (null != data) {
                KLog.d("没有指定特定的存储路径,当前路径: " + data.getData());
                if (data.hasExtra("data"))
                    img_show_poto.setImageBitmap((Bitmap) data.getParcelableExtra("data"));
            } else {
                showPoto(fileUr.getPath());
            }
        } else if (101 == requestCode && resultCode == 2) {//自定义拍照
            String path = data.getStringExtra("file_path");
            if (!TextUtils.isEmpty(path))
                showPoto(path);
        }
    }

    /**
     * 显示 图片压缩
     * 图片按比例大小压缩方法
     *
     * @param path
     */
    private void showPoto(String path) {
        int width = img_show_poto.getWidth();
        int height = img_show_poto.getHeight();//想要设置目标的宽和高

        BitmapFactory.Options factoryOptions = new BitmapFactory.Options();
        factoryOptions.inJustDecodeBounds = true;//不会完全解码图像,//不加载图片到内存，仅获得图片宽高
        BitmapFactory.decodeFile(path, factoryOptions);//此时返回的是一个null的Bitamp

        int imageWidth = factoryOptions.outWidth;
        int imageHeight = factoryOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(imageWidth / width, imageHeight / height);

        // Decode the image file into a Bitmap sized to fill the
        factoryOptions.inJustDecodeBounds = false;//现在bitmap将加载到内存中
        factoryOptions.inSampleSize = scaleFactor;//设置压缩的图片，缩小几倍，原来的几分之一，值是一个接近2的倍数
        //factoryOptions.inDither = false;    /*不进行图片抖动处理*/
        //factoryOptions.inPreferredConfig = null;  /*设置让解码器以最佳方式解码*/
        ///* 下面两个字段需要组合使用 */
        factoryOptions.inPurgeable = true;//内存不足时可以回收
        factoryOptions.inInputShareable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(path, factoryOptions);
        mTextShowPath.setText(path);
        img_show_poto.setImageBitmap(bitmap);
    }
}
