package com.example.zls.application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.zls.BuildConfig;
import com.example.zls.utils.ScreenUtils;
import com.example.zls.utils.TimeUtil;
import com.example.zls.utils.ToastUtils;
import com.example.zls.widget.CrashReportingTree;
import com.facebook.drawee.backends.pipeline.Fresco;

import android.app.Application;

import timber.log.Timber;

public class App extends Application {

    private static Application mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        //init timber
        initTimber();

        // Init ARouter
        initARouter();
        ARouter.init(this);
        //init screenUtils
//        SystemUtils.createNotificationChannel(this);
        ScreenUtils.setContext(this);
        ToastUtils.setContext(this);

        //初始化fresco
        Fresco.initialize(this);
        TimeUtil.setContext(this);
    }

    private void initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }
    }

    private void initARouter() {
        if (BuildConfig.DEBUG) {
            // Should set log and debug before init
            ARouter.openLog();
            ARouter.openDebug();
            ARouter.printStackTrace(); // 打印日志的时候打印线程堆栈
        }
    }

    public static Application getInstance(){
        return mInstance;
    }
}
