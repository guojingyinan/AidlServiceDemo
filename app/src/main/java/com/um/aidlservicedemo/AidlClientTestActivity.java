package com.um.aidlservicedemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

public class AidlClientTestActivity extends AppCompatActivity {
    private static String TAG = "AidlClientTestActivity";
    TextView textView;
    IMyService iMyService;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: ");
            iMyService = IMyService.Stub.asInterface(service);
            try {
                textView.setText("" + iMyService.add(1,2));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: ");
            iMyService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);
        Intent serviceIntent = new Intent();
        serviceIntent.setComponent(new ComponentName("com.um.aidlservicedemo","com.um.aidlservicedemo.MyService"));
        bindService(serviceIntent, serviceConnection, BIND_AUTO_CREATE);
        test();
    }

    private void test() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + "path"),"application/vnd.android.package-archive");
        getApplicationContext().startActivity(intent);
        WindowManager.LayoutParams mWindowLayoutParams =
                new WindowManager.LayoutParams();
        mWindowLayoutParams.flags=WindowManager.LayoutParams.FLAG_FULLSCREEN;
        WindowManager mWindowManager =(WindowManager) getSystemService(Context.WINDOW_SERVICE);
        TextView mTextView=new TextView(this);
        mWindowManager.addView(mTextView,mWindowLayoutParams);


    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
        unbindService(serviceConnection);
    }

}
