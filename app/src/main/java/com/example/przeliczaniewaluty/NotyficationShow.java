package com.example.przeliczaniewaluty;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;


public class NotyficationShow  {

    private NotificationManager mManager;
    private Context ctx;

    public NotyficationShow(Context ctx) {

        this.ctx = ctx;
        createChannels();

        String groupId = "group_id_101";

        NotificationManager mNotificationManager =
                (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mNotificationManager.createNotificationChannelGroup(new NotificationChannelGroup(groupId, ctx.getString(R.string.ANDROID_NAME)));
        }


    }



    public void createChannels() {

        // create android channel
        NotificationChannel androidChannel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            androidChannel = new NotificationChannel(ctx.getString(R.string.CHANNEL_ID),
                    ctx.getString(R.string.ANDROID_NAME), NotificationManager.IMPORTANCE_DEFAULT);
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
            Intent intent = new Intent(ctx, MainActivity.class);
            PendingIntent settingsIntent = PendingIntent.getActivity(ctx, 0, intent, 0);
            return new Notification.Builder(ctx,ctx.getString(R.string.CHANNEL_ID))
                    .setContentTitle(title)
                    .setContentText(body)
                    .setSmallIcon(R.mipmap.ic_launcher_dollar_foreground)
                    .setLargeIcon(BitmapFactory.decodeResource(ctx.getResources(),
                            R.mipmap.ic_launcher_dollar_foreground))
                    .setAutoCancel(true)
                    .setContentIntent(settingsIntent);


        }
        return null;
    }



    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    public void deleteNotificationChannel(String channelId) {

        NotificationManager mNotificationManager =
                (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mNotificationManager.deleteNotificationChannel(channelId);
        }
    }
}
