package com.example.zls.utils;

import com.example.zls.R;

import android.net.Uri;

/**
 * author : created by zhaolansheng
 * time   : on 2020/5/13-[11:51 AM]
 */
public final class Constants {

    private Constants() {
    }

    public interface NotificationId {

        int DEFAULT = 0;
        int CUSTOM = 1;
    }

    public interface NotificationChannel {

        String SYSTEM = "system";
        String SUBSCRIBE = "subscribe";
        String DEFAULT = "default";
        String CUSTOM = "custom";
        String VOICE = "voice";
    }

    public interface NotificationAction{

        String PRESS_PAUSE_OR_PLAY_BUTTON = "PressPauseOrPlayButton";
        String PRESS_NEXT_BUTTON = "PressNextButton";
        String PRESS_LAST_BUTTON = "PressLastButton";
    }

    public interface UriPath {

        Uri NOTIFICATION_SOUND = Uri
                .parse("android.resource://com.example.practicedemo/" + R.raw.money);
    }
}
