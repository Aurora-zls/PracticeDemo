package com.example.zls.fragment.test_demo_fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.zls.R;
import com.example.zls.broadcast_receiver.PushBroadcastReceiver;
import com.example.zls.controller.second_floor.PullActivity;
import com.example.zls.controller.test_demo.TestDemoActivity;
import com.example.zls.databinding.FragmentThirteenthBinding;
import com.example.zls.utils.Constants;
import com.example.zls.utils.SystemUtils;
import com.example.zls.utils.ToastUtils;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.databinding.DataBindingUtil;
import me.yokeyword.fragmentation.SupportFragment;

@Route(path = "/thirteenth/fragment")
public class ThirteenthFragment extends SupportFragment {

    private static final int MESSAGE_DELAY_DEFAULT = 112;

    private static final int MESSAGE_DELAY_CUSTOM = 113;

    //直接使用NotificationManager 会报异常 NotificationService: No Channel found for 。。。
//    private NotificationManager mNotificationManager;

    private NotificationManagerCompat mNotificationManagerCompat;

    private PushBroadcastReceiver mPushBroadcastReceiver;

    private Handler mHandler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        mHandler = new Handler(Looper.getMainLooper(), msg -> {
            if (msg.what == MESSAGE_DELAY_CUSTOM) {
                return true;
            } else if (msg.what == MESSAGE_DELAY_DEFAULT) {
                return true;
            }
            return false;
        });
        initBroadcast();
        if (getActivity() instanceof TestDemoActivity) {
//            mNotificationManager = (NotificationManager) getActivity()
//                    .getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManagerCompat = NotificationManagerCompat.from(getActivity());
        }
        View view = inflater.inflate(R.layout.fragment_thirteenth, container, false);
        FragmentThirteenthBinding binding = DataBindingUtil.bind(view);
        if (binding != null) {
            binding.addBonus.setOnClickListener(v -> {
                binding.gameBonus.setText(new StringBuilder(binding.gameBonus.getText())
                        .append(binding.gameBonus.getText()));
            });
            binding.addName.setOnClickListener(v -> {
                binding.gameName.setText(new StringBuilder(binding.gameName.getText())
                        .append(binding.gameName.getText()));
            });
            binding.defaultNotification.setOnClickListener(v -> {
                createDefaultNotification();
            });
            binding.customNotification.setOnClickListener(v -> {
                createCustomNotification();
            });
            binding.deleteChannel.setOnClickListener(v -> {
                SystemUtils.deleteNotificationChannel(getActivity());
                ToastUtils.toastWarning("删除渠道完成");
            });
            binding.createChannel.setOnClickListener(v -> {
                SystemUtils.createNotificationChannel(getActivity());
                ToastUtils.toastSuccess("创建渠道完成");
            });
            binding.settingChannel.setOnClickListener(v -> {
                SystemUtils.goNotificationChannelSetting(getActivity(),
                        binding.channelInput.getText().toString());
                ToastUtils.toastSuccess(
                        String.format("前往渠道%s", binding.channelInput.getText().toString()));
            });
            binding.delayDefaultNotification.setOnClickListener(v -> {
                binding.delayDefaultNotification.postDelayed(this::createDefaultNotification,
                        Integer.parseInt(binding.delay.getText().toString()));
//                SystemUtils.wakeUp();
            });
            binding.delayCustomNotification.setOnClickListener(v -> {
                binding.delayDefaultNotification.postDelayed(this::createCustomNotification,
                        Integer.parseInt(binding.delay.getText().toString()));
            });
        }
        return view;
    }

    private void initBroadcast() {
        if (getActivity() instanceof TestDemoActivity) {
            mPushBroadcastReceiver = new PushBroadcastReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(Constants.NotificationAction.PRESS_LAST_BUTTON);
            intentFilter.addAction(Constants.NotificationAction.PRESS_NEXT_BUTTON);
            intentFilter.addAction(Constants.NotificationAction.PRESS_PAUSE_OR_PLAY_BUTTON);
            getActivity().registerReceiver(mPushBroadcastReceiver, intentFilter);
        }
    }

    private void createCustomNotification() {
        if (getActivity() instanceof TestDemoActivity) {
            Intent pauseOrPlay = new Intent(
                    Constants.NotificationAction.PRESS_PAUSE_OR_PLAY_BUTTON);
            PendingIntent pauseOrPlayPI = PendingIntent
                    .getBroadcast(getActivity(), 0, pauseOrPlay, 0);
            Intent next = new Intent(Constants.NotificationAction.PRESS_NEXT_BUTTON);
            PendingIntent nextPI = PendingIntent.getBroadcast(getActivity(), 0, next, 0);
            Intent last = new Intent(Constants.NotificationAction.PRESS_LAST_BUTTON);
            PendingIntent lastPI = PendingIntent.getBroadcast(getActivity(), 0, last, 0);
            /*
             * 通知布局如果使用自定义布局文件中的话要通过RemoteViews类来实现，
             * 其实无论是使用系统提供的布局还是自定义布局，都是通过RemoteViews类实现，如果使用系统提供的布局，
             * 系统会默认提供一个RemoteViews对象。如果使用自定义布局的话这个RemoteViews对象需要我们自己创建，
             * 并且加入我们需要的对应的控件事件处理，然后通过setContent(RemoteViews remoteViews)方法传参实现
             *
             * 注意：
             * RemoteViews is limited to support for the following layouts:
             * AdapterViewFlipper
             * FrameLayout
             * GridLayout
             * GridView
             * LinearLayout
             * ListView
             * RelativeLayout
             * StackView
             * ViewFlipper
             *
             * And the following widgets:
             *
             * AnalogClock
             * Button
             * Chronometer
             * ImageButton
             * ImageView
             * ProgressBar
             * TextClock
             * TextView
             * Descendants of these classes are not supported.
             */
            RemoteViews remoteViews = new RemoteViews(getActivity().getPackageName(),
                    R.layout.ui_custom_notification);

            /*
             * 对于自定义布局文件中的控件通过RemoteViews类的对象进行事件处理
             */
            remoteViews.setOnClickPendingIntent(R.id.notif_last, lastPI);
            remoteViews.setOnClickPendingIntent(R.id.notif_next, nextPI);
            remoteViews.setOnClickPendingIntent(R.id.notif_play, pauseOrPlayPI);

            RemoteViews remoteBigViews = new RemoteViews(getActivity().getPackageName(),
                    R.layout.ui_custom_big_notification);
            remoteBigViews.setOnClickPendingIntent(R.id.notif_last, lastPI);
            remoteBigViews.setOnClickPendingIntent(R.id.notif_next, nextPI);
            remoteBigViews.setOnClickPendingIntent(R.id.notif_play, pauseOrPlayPI);

            Notification notification = new NotificationCompat.Builder(getActivity(),
                    Constants.NotificationChannel.CUSTOM)
//                    .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                    .setContentTitle("自定义通知测试") // 创建通知的标题
                    .setContentText("成功创建第一个自定义通知") // 创建通知的内容
                    .setSmallIcon(R.drawable.ic_stat_ic_notification) // 创建通知的小图标
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                            R.drawable.music)) // 创建通知的大图标
                    /*
                     * 是使用自定义视图还是系统提供的视图，上面4的属性一定要设置，不然这个通知显示不出来
                     */
                    .setDefaults(Notification.DEFAULT_ALL)  // 设置通知提醒方式为系统默认的提醒方式
                    // 通过设置RemoteViews对象来设置通知的布局，这里我们设置为自定义布局
//                    .setContent(remoteViews) //建议使用下面的接口设置，兼容性更好
                    .setCustomContentView(remoteViews)
                    .setCustomBigContentView(remoteBigViews)
                    .build(); // 创建通知（每个通知必须要调用这个方法来创建）

            if (mNotificationManagerCompat != null) {
                mNotificationManagerCompat.notify(Constants.NotificationId.CUSTOM, notification);
            }
        }
    }

    private void createDefaultNotification() {
        if (getActivity() instanceof TestDemoActivity) {
            Intent intent = new Intent(getActivity(), PullActivity.class);
            /*
             * 调用PendingIntent的静态方法创建一个 PendingIntent对象用于点击通知之后执行的操作，
             * PendingIntent可以理解为延时的Intent，在这里即为点击通知之后执行的Intent
             * 这里调用getActivity(Context context, int requestCode, Intent intent, int flag)方法
             * 表示这个PendingIntent对象启动的是Activity，类似的还有getService方法、getBroadcast方法
             */
            PendingIntent pi = PendingIntent.getActivity(getActivity(), 0, intent, 0);

            Notification notification = new NotificationCompat.Builder(getActivity(),
                    Constants.NotificationChannel.VOICE)
                    .setContentTitle("默认通知测试") // 创建通知的标题
                    .setContentText("成功创建第一个默认通知") // 创建通知的内容
                    .setSmallIcon(R.drawable.ic_stat_ic_notification) // 创建通知的小图标
                    .setLargeIcon(
                            BitmapFactory
                                    .decodeResource(getResources(), R.drawable.timg)) // 创建通知的大图标
                    /*
                     * 首先，无论你是使用自定义视图还是系统提供的视图，上面4的属性一定要设置，不然这个通知显示不出来
                     */
                    .setWhen(System.currentTimeMillis()) // 设定通知显示的时间
                    .setContentIntent(pi) // 设定点击通知之后启动的内容，这个内容由方法中的参数：PendingIntent对象决定
                    .setPriority(NotificationCompat.PRIORITY_MAX) // 设置通知的优先级
                    .setAutoCancel(true) // 设置点击通知之后通知是否消失
//                    .setSound(UriUtil.getUriForResourceId(R.raw.coin_fall)) // 设置声音
                    .setSound(Constants.UriPath.NOTIFICATION_SOUND) // 设置声音
//                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)) // 设置声音
                    /*
                     * 设置震动，用一个 long 的数组来表示震动状态，这里表示的是先震动1秒、静止1秒、再震动1秒，这里以毫秒为单位
                     * 如果要设置先震动1秒，然后停止0.5秒，再震动2秒则可设置数组为：long[]{1000, 500, 2000}。
                     * 别忘了在AndroidManifest配置文件中申请震动的权限
                     */
                    .setVibrate(new long[]{1000, 0, 1000, 0, 1000, 0, 1000})
                    /*
                     * 设置手机的LED灯为蓝色并且灯亮2秒，熄灭1秒，达到灯闪烁的效果，不过这些效果在模拟器上是看不到的，
                     * 需要将程序安装在真机上才能看到对应效果，如果不想设置这些通知提示效果，
                     * 可以直接设置：setDefaults(Notification.DEFAULT_ALL);
                     * 意味将通知的提示效果设置为系统的默认提示效果
                     */
                    .setLights(Color.BLUE, 2000, 1000)
                    .build(); // 创建通知（每个通知必须要调用这个方法来创建）
            /*
             * 使用从系统服务获得的通知管理器发送通知，第一个参数是通知的id，不同的通知应该有不同的id，
             * 这样当我们要取消哪条通知的时候我们调用notificationManager（通知管理器）.cancel(int id)
             * 方法并传入发送通知时的对应id就可以了。在这里如果我们要取消这条通知，
             * 我们调用notificationManager.cancel(1);就可以了
             * 第二个参数是要发送的通知对象
             */
            if (mNotificationManagerCompat != null) {
                mNotificationManagerCompat.notify(Constants.NotificationId.DEFAULT, notification);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (getActivity() instanceof TestDemoActivity) {
            getActivity().unregisterReceiver(mPushBroadcastReceiver);
        }
    }
}
