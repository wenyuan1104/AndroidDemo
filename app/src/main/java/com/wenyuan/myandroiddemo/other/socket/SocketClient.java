package com.wenyuan.myandroiddemo.other.socket;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.socks.library.KLog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;


/**
 * Created by wenyuan on 2017/2/19 16:09.
 * Description:
 */

public class SocketClient {

    public Socket mClient;
    private BufferedReader reader;

    public SocketClient(final Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    KLog.d(SocketActivity.TAG, "本地地址：" + InetAddress.getLocalHost());
                    mClient = new Socket(InetAddress.getLocalHost(), SocketActivity.PORT);
                    KLog.d(SocketActivity.TAG, "本地端口：" + mClient.getLocalPort());
                    reader = new BufferedReader(new InputStreamReader(mClient.getInputStream()));
                    String strs = reader.readLine();
                    KLog.d("来自服务器端的消息" + strs);
                    Message message = Message.obtain();
                    message.what = SocketActivity.CLIENT_WHAT;
                    Bundle bundle = new Bundle();
                    bundle.putString(SocketActivity.KEY, strs);
                    message.setData(bundle);
                    handler.sendMessage(message);
                    while (true) {
                        String string = reader.readLine();
                        Message message1 = Message.obtain();
                        message1.what = SocketActivity.CLIENT_WHAT;
                        Bundle bundle1 = new Bundle();
                        bundle1.putString(SocketActivity.KEY, string);
                        message1.setData(bundle1);
                        handler.sendMessage(message1);
                        if (string.equals("end"))
                            break;
                    }
                    mClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 上传
     *
     * @param txt
     */
    public void upload(final String txt) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    PrintWriter printWriter = new PrintWriter(mClient.getOutputStream());
                    printWriter.println(txt);
                    printWriter.flush();
                    String s = reader.readLine();
                    KLog.d(SocketActivity.TAG, s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}
