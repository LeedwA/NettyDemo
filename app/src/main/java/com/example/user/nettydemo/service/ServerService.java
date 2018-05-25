package com.example.user.nettydemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.user.nettydemo.NettyServer;
import com.example.user.nettydemo.protosuff.MyServer;

/**
 * Created by user on 2016/10/27.
 */

public class ServerService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        NettyServer.getInstance().init();
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                int port;
//                port = 8888;
//                new MyServer(port).start();
//            }
//        }).start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NettyServer.getInstance().shutDown();
    }
}
