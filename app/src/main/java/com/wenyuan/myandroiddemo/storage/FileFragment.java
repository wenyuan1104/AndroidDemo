package com.wenyuan.myandroiddemo.storage;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wenyuan.myandroiddemo.BaseFragment;
import com.wenyuan.myandroiddemo.R;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * A simple {@link Fragment} subclass.
 */
public class FileFragment extends BaseFragment implements View.OnClickListener {

    private final String File_OPERATION_FOR_INTERIOR_STORE_NAME_KEY = "file_operation_for_interior_store_name_key";
    private final String File_OPERATION_FOR_EXTERNAL_STORE_NAME_KEY = "file_operation_for_external_store_name_key";

    private Button mButInteriorSet;
    private TextView mTextInteriorSet;
    private Button mButInteriorGet;
    private TextView mTextInteriorGet;
    private Button mButExternalSet;
    private TextView mTextExternalSet;
    private Button mButExternalGet;
    private ImageView mImageView;

    public FileFragment() {
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_file;
    }

    @Override
    protected void initView(View view) {
        mButInteriorSet = (Button) view.findViewById(R.id.but_interior_set);
        mButInteriorSet.setOnClickListener(this);
        mTextInteriorSet = (TextView) view.findViewById(R.id.text_interior_set);
        mButInteriorGet = (Button) view.findViewById(R.id.but_interior_get);
        mButInteriorGet.setOnClickListener(this);
        mTextInteriorGet = (TextView) view.findViewById(R.id.text_interior_get);
        mButExternalSet = (Button) view.findViewById(R.id.but_external_set);
        mButExternalSet.setOnClickListener(this);
        mTextExternalSet = (TextView) view.findViewById(R.id.text_external_set);
        mButExternalGet = (Button) view.findViewById(R.id.but_external_get);
        mButExternalGet.setOnClickListener(this);
        mImageView = (ImageView) view.findViewById(R.id.img_external_get);
    }

    @Override
    protected void initObject() {
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_interior_set:
                setForInteriorStore();
                break;
            case R.id.but_interior_get:
                getForInteriorStore();
                break;
            case R.id.but_external_set:
                setForExternalStore();
                break;
            case R.id.but_external_get:
                getForExternalStore();
                break;
        }
    }

    /**
     * 持久化：外部文件
     */
    private void setForExternalStore() {
        //1、首先检查权限
        //2、然后检查是否挂在sd卡
        //3、最后操作
    }

    /**
     * 获取：外部文件
     */
    public void getForExternalStore() {
    }

    /**
     * 保存：内部file
     * MODE_APPEND：以追加的方式打开一个文件，使用此模式写入的内容均追加在原本内容的后面。
     * MODE_PRIVATE：私有模式（默认），如果文件已经存在会重新创建并替换原文件，如果不存在直接创建。
     * MODE_WORLD_READABLE：以只读的方式打开文件。
     * MODE_WORLD_WRITEABLE：以只写的方式打开文件。
     * <p>
     * File getFIlesDir()：获取文件系统的绝对路径。
     * boolean deleteFile(String name)：删除一个指定文件名为name的文件。
     * String[] fileList()：当前应用内部存储路径下的所有文件名。
     */
    private void setForInteriorStore() {
        try {
            //File file = new File(mContext.getFilesDir(), filename);
            //FileOutputStream fos = new FileOutputStream(file);
            //fos.write(mContext.getBytes());
            //fos.close();
            //数据
            String data = "Interior Store";
            FileOutputStream fileOutput = mContext.openFileOutput(File_OPERATION_FOR_INTERIOR_STORE_NAME_KEY, Context.MODE_APPEND);
            fileOutput.write(data.getBytes());
            //关闭流
            fileOutput.close();
            mTextInteriorSet.setText("当前系统的绝对路径：" + mContext.getFilesDir().toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取：内部file
     */
    public void getForInteriorStore() {
        try {
            FileInputStream inputStream = mContext.openFileInput(File_OPERATION_FOR_INTERIOR_STORE_NAME_KEY);
            //方法1
            //装饰器模式
            //BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            //StringBuilder builder = new StringBuilder();
            //char[] buf = new char[4096];
            //int size;
            //while (-1 != (size = reader.read(buf))) {
            //    builder.append(buf, 0, size);
            //}
            //reader.close();//关闭流
            //mTextInteriorGet.setText(builder.toString());
            //方法2
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            byte[] buf = new byte[4096];
            int size;
            while (-1 != (size = inputStream.read(buf))) {
                arrayOutputStream.write(buf, 0, size);
            }
            mTextInteriorGet.setText(new String(arrayOutputStream.toByteArray()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
