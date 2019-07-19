package com.sonukgupta72.servicesandlfecycles;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Intent intent;
    boolean mBound = false;
    MyService mService;
    TextView tvCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnStartServices = findViewById(R.id.btnStartServices);
        Button btnBindServices = findViewById(R.id.btnBindServices);
        Button btnUnbindServices = findViewById(R.id.btnUnbindServices);
        Button btnStopServices = findViewById(R.id.btnStopServices);
        tvCounter = findViewById(R.id.tvCounter);

        btnStartServices.setOnClickListener(this);
        btnBindServices.setOnClickListener(this);
        btnUnbindServices.setOnClickListener(this);
        btnStopServices.setOnClickListener(this);
        tvCounter.setOnClickListener(this);

        intent = new Intent(this, MyService.class);
    }

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MyService.LocalBinder binder = (MyService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnStartServices:
                startService(intent);
                break;
            case R.id.btnBindServices:
                bindService(intent, connection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btnUnbindServices:
                unbind();
                break;
            case R.id.btnStopServices:
                stopService(intent);
                break;
            case R.id.tvCounter:
                setText();
                break;
        }
    }

    private void setText() {
        if (mService != null)
        tvCounter.setText(mService.getTime() + "");
    }

    private void unbind() {

        if (!mBound) return;

        unbindService(connection);
        mBound = false;

    }
}
