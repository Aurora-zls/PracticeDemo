package com.example.zls.widget;

import android.text.TextUtils;

import timber.log.Timber;

public class CrashReportingTree extends Timber.Tree {

    @Override
    public void e(String message, Object... args) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        String log = message;
        if (args.length > 0) {
            log = String.format(message, args);
        }
//        Crashlytics.log(log);
    }

    @Override
    public void e(Throwable t, String message, Object... args) {
        if (!TextUtils.isEmpty(message)) {
//            String log = message;
//            if (args.length > 0) {
//                log = String.format(message, args);
//            }
//            Crashlytics.log(log);
//            if (t != null) {
//                Crashlytics.logException(new Throwable(log, t));
//            }
        }
    }

    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
    }
}
