package com.example.zls.utils;

import com.example.zls.R;
import com.example.zls.application.App;
import com.facebook.common.util.UriUtil;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;

import java.io.File;
import java.io.IOException;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

/**
 * Created by chaichuanfa on 2019-08-22
 */
public final class SystemUtils {

    private static Process mProcess;

    private SystemUtils() {
    }

    /**
     * 注意
     *
     * 要了解NotificationChannel的机制
     * 通过channelId标识一个 channel
     * 一旦一个channel通过notificationManager.createNotificationChannel()首次创建提交
     * 这个channel的很多配置都不能进行更改了，大多数属性都只支持首次提交前配置更改
     * 具体的机制 见 createNotificationChannel（）方法源码中的注释
     *
     * 用同样的channelID创建新的 NotificationChannel，和配置，使用createNotificationChannel重新创建提交，大部分属性配置都会被忽略，用的还是首次提交的配置
     *
     * NotificationChannel的各个属性支不支持重新配置
     * 找对应的set方法，看源码注释
     * 如果注释包含 Only modifiable before the channel is submitted to 则不支持提交后更改
     */
    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();

            NotificationChannel system = new NotificationChannel(
                    Constants.NotificationChannel.SYSTEM,
                    context.getString(R.string.notification_channel_system),
                    NotificationManager.IMPORTANCE_HIGH);
            system.enableLights(true);
            system.enableVibration(true);
            system.setLightColor(ContextCompat.getColor(context, R.color.colorAccent));
            system.setShowBadge(true);
            system.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);

            NotificationChannel subscribe = new NotificationChannel(
                    Constants.NotificationChannel.SUBSCRIBE,
                    context.getString(R.string.notification_channel_subscribe),
                    NotificationManager.IMPORTANCE_DEFAULT);
            subscribe.enableLights(true);
            subscribe.enableVibration(false);
            subscribe.setLightColor(ContextCompat.getColor(context, R.color.colorAccent));
            subscribe.setShowBadge(true);
            subscribe.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);

            NotificationChannel _default = new NotificationChannel(
                    Constants.NotificationChannel.DEFAULT,
                    context.getString(R.string.notification_channel_default),
                    NotificationManager.IMPORTANCE_HIGH);
            _default.enableLights(true);
            _default.enableVibration(true);
            _default.setLightColor(ContextCompat.getColor(context, R.color.colorAccent));
            _default.setShowBadge(true);
//            _default.setSound(UriUtil.getUriForResourceId(R.raw.money.ogg), audioAttributes);
//            _default.setSound(Constants.UriPath.NOTIFICATION_SOUND, audioAttributes);
            _default.setSound(UriUtil.getUriForResourceId(R.raw.coin_fall), audioAttributes);
//            _default.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE), audioAttributes);
            _default.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);

            NotificationChannel custom = new NotificationChannel(
                    Constants.NotificationChannel.CUSTOM,
                    context.getString(R.string.notification_channel_custom),
                    NotificationManager.IMPORTANCE_HIGH);
            custom.enableLights(true);
            custom.enableVibration(true);
            custom.setLightColor(ContextCompat.getColor(context, R.color.colorAccent));
            custom.setShowBadge(true);
            custom.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);

            NotificationChannel voice = new NotificationChannel(
                    Constants.NotificationChannel.VOICE,
                    context.getString(R.string.notification_channel_voice),
                    NotificationManager.IMPORTANCE_HIGH);
            voice.enableLights(true);
            //android O it seems the Android API no longer allows customizing the blink pattern.
            //The LED light for notifications is turned on by the OS in the device only if the notification is triggered while the device screen is off.
            voice.setLightColor(Color.GREEN);
            voice.enableVibration(true);
            //模式 延时/振动/延时/振动/。。。/延时/振动/延时/振动/ 数组第一个元素为停顿时间，然后振动和延时交替
            voice.setVibrationPattern(
                    new long[]{0, 500, 100, 500, 100, 50, 50, 100, 50, 50, 100, 100});
            voice.setLightColor(ContextCompat.getColor(context, R.color.colorAccent));
            voice.setShowBadge(true);
            voice.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);
            voice.setSound(Constants.UriPath.NOTIFICATION_SOUND, audioAttributes);

            NotificationManager notificationManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(system);
                notificationManager.createNotificationChannel(subscribe);
                notificationManager.createNotificationChannel(_default);
                notificationManager.createNotificationChannel(custom);
                notificationManager.createNotificationChannel(voice);
            }
        }
    }

    /**
     * >= android 0 没有channel不能成功发通知
     * 注意 此处的删除channel的坑
     *
     * 调用 deleteNotificationChannel() 删除通知通道时，
     * 其实系统里除了给这个通道打个 “deleted” 的标签外，
     * 啥也没干。。。 作为垃圾预防机制 系统会把你删除通道的这个行为记录下来，用小字儿在你APP的通知设置页面显示出来 —— "n categories deleted"
     * 当你再次创建相同 ChannelId 的通道时，它只是把旧的那个捞出来，去掉 “deleted” 标签继续用
     *
     * 想彻底删除已经创建注册的Channel，只有
     * 【清除应用数据】或者【卸载应用】
     *
     * Android官方是这么解释这个设计的：
     * NotificationChannel 就像是开发者送给用户的一个精美礼物，一旦送出去，控制权就在用户那里了。
     * 即使用户把通知铃声设置成《江南style》，你可以知道，但不可以更改。
     *
     * 看看deleteNotificationChannel（）方法的注释，也差不多是这么个意思
     *
     * Deletes the given notification channel.
     * <p>If you {#createNotificationChannel(NotificationChannel) create} a new channel with
     * this same id, the deleted channel will be un-deleted with all of the same settings it
     * had before it was deleted.
     * （对于要被删除的这个channel，如果之前你用同样的ID创建过一个新channel，那么这个channel在它被删之前将保留其已有的所有设置）
     */
    public static void deleteNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager
                        .deleteNotificationChannel(Constants.NotificationChannel.DEFAULT);
                notificationManager
                        .deleteNotificationChannel(Constants.NotificationChannel.CUSTOM);
                notificationManager
                        .deleteNotificationChannel(Constants.NotificationChannel.SYSTEM);
                notificationManager
                        .deleteNotificationChannel(Constants.NotificationChannel.SUBSCRIBE);
                notificationManager
                        .deleteNotificationChannel(Constants.NotificationChannel.VOICE);
            }
        }
    }

    public static void goNotificationChannelSetting(Context context, String channelId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
            intent.putExtra(Settings.EXTRA_CHANNEL_ID, channelId);
            context.startActivity(intent);
        }
    }

    public static void startLogToFile(Context context) {
        File outputFile = new File(context.getExternalFilesDir(PathUtil.APP_NAME) + File.separator
                + PathUtil.SYSTEM_LOG_FILE);
        try {
            mProcess = Runtime.getRuntime()
                    .exec("logcat -f " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void stopLogToFile() {
        if (mProcess != null) {
            try {
                mProcess.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mProcess = null;
            }
        }
    }

    public static boolean isLogToFileStart() {
        return mProcess != null;
    }

    /**
     * 唤醒手机屏幕并解锁
     */
    public static void wakeUp() {
        // 获取电源管理器对象
        PowerManager pm = (PowerManager) App.getInstance().getSystemService(Context.POWER_SERVICE);
        boolean screenOn = false;
        if (pm != null) {
            screenOn = pm.isInteractive();
            if (!screenOn) {
                // 获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
                PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP,
                        "test demo:turn screen on");
                wl.acquire(10 * 60 * 1000L /*10 minutes*/); // 点亮屏幕
                wl.release(); // 释放
            }
        }
    }
}
