package com.um.aidlservicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class MyService extends Service {
    public MyService() {
    }

    // 创建一个继承自IMyService.Stub的内部类
    public class MyServiceImpl extends IMyService.Stub {

        // 必须实现AIDL文件中的接口
        @Override
        public int add(int arg1, int arg2) throws RemoteException {
            return arg1 + arg2;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return new MyServiceImpl();
    }
}
