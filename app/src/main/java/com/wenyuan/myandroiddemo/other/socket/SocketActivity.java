package com.wenyuan.myandroiddemo.other.socket;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.common.base.Preconditions;
import com.wenyuan.myandroiddemo.BaseActivity;
import com.wenyuan.myandroiddemo.R;

import java.io.IOException;

/**
 * socket 本地传输数据demo
 * socket 为长链接
 * <p>
 * 退出activity的时候要close掉Socket
 */
public class SocketActivity extends BaseActivity implements View.OnClickListener {

    public static final String TAG = SocketActivity.class.getSimpleName();
    public static final String KEY = "socket_data";
    public static final int PORT = 8888;//端口
    public static final int SERVER_WHAT = 0X01;//hander what标识
    public static final int CLIENT_WHAT = 0X02;

    private EditText mEditSocketServer;
    private Button mButSocketServer;
    private TextView mTextSocketClient;
    private EditText mEditSocketClient;
    private TextView mTextSocketServer;
    private Button mButSocketClient;

    private SocketClient mSocketClient;
    private SocketServer mSocketServer;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SERVER_WHAT://服务端接收到的数据
                    mTextSocketServer.setText(msg.getData().getString(KEY));
                    break;
                case CLIENT_WHAT://客户端接收到的数据
                    mTextSocketClient.setText(msg.getData().getString(KEY));
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void getLayoutResource() {
        setContentView(R.layout.activity_socket);
    }

    @Override
    protected void initView() {
        setToolbar(true);
        mEditSocketServer = (EditText) findViewById(R.id.edit_socket_server);
        mEditSocketServer.setOnClickListener(this);
        mButSocketServer = (Button) findViewById(R.id.but_socket_server);
        mButSocketServer.setOnClickListener(this);
        mTextSocketClient = (TextView) findViewById(R.id.text_socket_client);
        mTextSocketClient.setOnClickListener(this);
        mEditSocketClient = (EditText) findViewById(R.id.edit_socket_client);
        mEditSocketClient.setOnClickListener(this);
        mButSocketClient = (Button) findViewById(R.id.but_socket_client);
        mButSocketClient.setOnClickListener(this);
        mTextSocketServer = (TextView) findViewById(R.id.text_socket_server);
        mTextSocketServer.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mSocketServer = new SocketServer(mHandler);
        mSocketClient = new SocketClient(mHandler);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_socket_server:
                String txt = mEditSocketServer.getText().toString();
                mSocketServer.send2Client(Preconditions.checkNotNull(txt));
                break;
            case R.id.but_socket_client:
                String txt2 = mEditSocketClient.getText().toString();
                mSocketClient.upload(Preconditions.checkNotNull(txt2));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Main thread not allowed to quit. 主线程不能quit
//        if (mHandler.getLooper() != null)
//            mHandler.getLooper().quit();
        try {
            Preconditions.checkNotNull(mSocketClient.mClient).close();
            Preconditions.checkNotNull(mSocketServer.mSocket).close();
            Preconditions.checkNotNull(mSocketServer.mServer).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
