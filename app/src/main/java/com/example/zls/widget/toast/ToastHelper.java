package com.example.zls.widget.toast;


import com.example.zls.application.App;

import android.annotation.SuppressLint;

/**
 * toast帮助类
 */
public class ToastHelper {
    @SuppressLint("StaticFieldLeak")
    private static AppToast appToast;

    public static void makeToast(String text) {
        if (appToast == null) {
            appToast = new AppToast(App.getInstance());
        }
        appToast.makeToast(text);
    }

}