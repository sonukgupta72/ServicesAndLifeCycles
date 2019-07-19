package com.sonukgupta72.servicesandlfecycles;

import android.app.Service;
import android.content.Intent;
import android.database.Observable;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    private CountDownTimer countDownTimer;
    private LocalBinder binder = new LocalBinder();
    private Observable<Float> pressureObservable;
    int timer;


    public MyService() {
    }


    @Override
    public void onCreate() {
        printLog("OnCreate");

//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        countDownTimer = new CountDownTimer(11*1000, 1000) {
            @Override
            public void onTick(long l) {

                printLog((l/1000)+" Sec");

                timer = (int) (l/1000);

            }

            @Override
            public void onFinish() {

                stopSelf();
            }
        };

        countDownTimer.start();

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        printLog("OnStartCommand");


        //return super.onStartCommand(intent, flags, startId);

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        printLog("OnBind");
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        printLog("OnUnbind");
        return false;
    }
    @Override
    public void onRebind(Intent intent) {
        // A client is binding to the service with bindService(),
        // after onUnbind() has already been called
    }

    @Override
    public void onDestroy() {
        printLog("OnDestroy");
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer.onFinish();
        }

        super.onDestroy();
    }

    private void printLog(String str) {
        Log.d("LIFE_CYCLE", str);
    }


    public class LocalBinder extends Binder {
        MyService getService() {
            // Return this instance of LocalService so clients can call public methods
            return MyService.this;
        }
    }

    public int getTime() {
        return timer;
    }
}
