package com.wenyuan.myandroiddemo.other.socket;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.socks.library.KLog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by wenyuan on 2017/2/19 13:45.
 * Description:
 */

public class SocketServer {

    public ServerSocket mServer;
    public Socket mSocket;
    private BufferedReader in;

    public SocketServer(final Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mServer = new ServerSocket(SocketActivity.PORT);
                    KLog.d(SocketActivity.TAG, "server started");
                    mSocket = mServer.accept();
                    if (mSocket.isConnected())
                        KLog.d(SocketActivity.TAG, "有一个client连接 端口为：" + mSocket.getPort());

                    // 接收client的数据
                    in = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
                    // 发送数据给client
                    PrintWriter printWriter = new PrintWriter(mSocket.getOutputStream(), true);
                    printWriter.println("端口" + mSocket.getPort() + "上线");
                    printWriter.flush();
                    while (true) {
                        String str = in.readLine();
                        KLog.d(SocketActivity.TAG, str);
                        //将数据发送给activity显示
                        Message message = Message.obtain();
                        message.what = SocketActivity.SERVER_WHAT;
                        Bundle bundle = new Bundle();
                        bundle.putString(SocketActivity.KEY, str);
                        message.setData(bundle);
                        handler.sendMessage(message);
                        printWriter.println("has receive....");
                        printWriter.flush();
                        if (str != null && str.equals("end"))
                            break;
                    }
                    mSocket.close();
                    mServer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 发送给客户端
     *
     * @param txt
     */
    public void send2Client(final String txt) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    PrintWriter printWriter = new PrintWriter(mSocket.getOutputStream());
                    printWriter.println(txt);
                    printWriter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
