package com.wenyuan.myademo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.wenyuan.myademo.animation.PropertyAnimActivity;
import com.wenyuan.myademo.animation.TweenAnimActivity;
import com.wenyuan.myademo.detail.permission.PermissionActivity;
import com.wenyuan.myademo.detail.picture.HandlerPicActivity;
import com.wenyuan.myademo.encrypt.EncyptActivity;
import com.wenyuan.myademo.hardware.camera.SysCameraActivity;
import com.wenyuan.myademo.utils.AlertDialogV7Factory;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Button mButEncryption;
    private Button mButHardware;
    private Button mButAnimation;
    private Button mButCustomView;
    private Button mButEvent;
    private Button mButMaterial;
    private Button mButStatesbar;
    private Button mButThirdparty;

    private AlertDialogV7Factory mDialogFactory;
    private Button mButBitmap;
    private Button mButVersionM;
    private Button mButMvp;
    private Button mButMvvm;
    private Button mButFile;
    private Button mButWeb;
    private Button mButService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void getLayoutResource() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initView() {
        setToolbar(false);
        mToolbar.setTitle(getString(R.string.app_name));
        mButEncryption = (Button) findViewById(R.id.but_encryption);
        mButEncryption.setOnClickListener(this);
        mButHardware = (Button) findViewById(R.id.but_hardware);
        mButHardware.setOnClickListener(this);
        mButAnimation = (Button) findViewById(R.id.but_animation);
        mButAnimation.setOnClickListener(this);
        mButCustomView = (Button) findViewById(R.id.but_custom_view);
        mButCustomView.setOnClickListener(this);
        mButEvent = (Button) findViewById(R.id.but_event);
        mButEvent.setOnClickListener(this);
        mButMaterial = (Button) findViewById(R.id.but_material);
        mButMaterial.setOnClickListener(this);
        mButStatesbar = (Button) findViewById(R.id.but_statesbar);
        mButStatesbar.setOnClickListener(this);
        mButThirdparty = (Button) findViewById(R.id.but_thirdparty);
        mButThirdparty.setOnClickListener(this);
        mButBitmap = (Button) findViewById(R.id.but_bitmap);
        mButBitmap.setOnClickListener(this);
        mButVersionM = (Button) findViewById(R.id.but_version_m);
        mButVersionM.setOnClickListener(this);
        mButMvp = (Button) findViewById(R.id.but_mvp);
        mButMvp.setOnClickListener(this);
        mButMvvm = (Button) findViewById(R.id.but_mvvm);
        mButMvvm.setOnClickListener(this);
        mButFile = (Button) findViewById(R.id.but_file);
        mButFile.setOnClickListener(this);
        mButWeb = (Button) findViewById(R.id.but_web);
        mButWeb.setOnClickListener(this);
        mButService = (Button) findViewById(R.id.but_service);
        mButService.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mDialogFactory = new AlertDialogV7Factory(mContext);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_about:
                mDialogFactory.showTextDialog("建议", getString(R.string.main), true);
                break;
            case R.id.action_more:
                startActivity(MoreActivity.class);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.but_encryption:
                startActivity(EncyptActivity.class);
                break;
            case R.id.but_hardware:
                selectListForHard();
                break;
            case R.id.but_animation:
                selectListForAnimation();
                break;
            case R.id.but_custom_view:
                break;
            case R.id.but_event:
                break;
            case R.id.but_material:
                break;
            case R.id.but_statesbar:
                break;
            case R.id.but_thirdparty:
                break;
            case R.id.but_bitmap:
                startActivity(HandlerPicActivity.class);
                break;
            case R.id.but_version_m:
                startActivity(PermissionActivity.class);
                break;
            case R.id.but_mvp:
                break;
            case R.id.but_mvvm:
                break;
            case R.id.but_file:
                break;
            case R.id.but_web:
                break;
            case R.id.but_service:
                break;
        }
    }

    /**
     *
     */
    private void selectListForAnimation() {
        String[] strings = {"TweenAnimation", "frameAnimation", "LayoutAnimation", "PropertyAnimation", "自定义动画"};
        mDialogFactory.showSingleListDialog("Animation", true, -1, strings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        startActivity(TweenAnimActivity.class);
                        break;
                    case 3:
                        startActivity(PropertyAnimActivity.class);
                        break;
                }
            }
        });
    }

    /**
     * 硬件列表选择
     */
    private void selectListForHard() {
        String[] strings = {"相机", "传感器", "定位"};
        mDialogFactory.showSingleListDialog("Hardware", true, 0, strings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        startActivity(SysCameraActivity.class);
                        break;
                }
            }
        });
    }
}
