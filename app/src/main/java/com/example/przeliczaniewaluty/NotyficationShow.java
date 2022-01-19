package com.example.przeliczaniewaluty;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Build;

public class NotyficationShow extends ContextWrapper {

    private NotificationManager mManager;


    public NotyficationShow(Context ctx) {
        super(ctx);

        createChannels();

        String groupId = "group_id_101";
//        CharSequence groupName = "Channel Name";
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mNotificationManager.createNotificationChannelGroup(new NotificationChannelGroup(groupId, getString(R.string.ANDROID_NAME)));
        }


    }

    public void createChannels() {

        // create android channel
        NotificationChannel androidChannel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            androidChannel = new NotificationChannel(getString(R.string.CHANNEL_ID),
                    getString(R.string.ANDROID_NAME), NotificationManager.IMPORTANCE_DEFAULT);
        }
        // Sets whether notifications posted to this channel should display notification lights
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            androidChannel.enableLights(true);
        }
        // Sets whether notification posted to this channel should vibrate.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            androidChannel.enableVibration(true);
        }
        // Sets the notification light color for notifications posted to this channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            androidChannel.setLightColor(Color.GREEN);
        }
        // Sets whether notifications posted to this channel appear on the lockscreen or not
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            androidChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getManager().createNotificationChannel(androidChannel);
        }


    }

    public Notification.Builder getAndroidChannelNotification(String title, String body) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return new Notification.Builder(getApplicationContext(),getString(R.string.CHANNEL_ID))
                    .setContentTitle(title)
                    .setContentText(body)
                    .setSmallIcon(android.R.drawable.sym_def_app_icon)
                    .setAutoCancel(true);


        }
        return null;
    }



    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    public void deleteNotificationChannel(String channelId) {

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mNotificationManager.deleteNotificationChannel(channelId);
        }
    }
}
