package com.example.zls.broadcast_receiver;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.zls.utils.Constants.NotificationAction;
import com.example.zls.utils.ToastUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

/**
 * author : created by zhaolansheng
 * time   : on 2020/5/13-[7:38 PM]
 */
public class PushBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (TextUtils.equals(NotificationAction.PRESS_LAST_BUTTON,action)) {
            ToastUtils.toastSuccess("你成功点击了上一曲");
        }
        if (TextUtils.equals(NotificationAction.PRESS_NEXT_BUTTON,action)) {
            ToastUtils.toastSuccess("你成功点击了下一曲");
        }
        if (TextUtils.equals(NotificationAction.PRESS_PAUSE_OR_PLAY_BUTTON,action)) {
            ToastUtils.toastSuccess("你成功启动了PullActivity");
//            context.startActivity(new Intent(context, PullActivity.class));
            ARouter.getInstance().build("/pull/activity").navigation();
        }
    }
}
