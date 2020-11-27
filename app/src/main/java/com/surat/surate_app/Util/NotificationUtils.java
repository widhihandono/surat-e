package com.surat.surate_app.Util;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;


import androidx.core.app.NotificationCompat;

import com.surat.surate_app.Menu_Utama_Activity;
import com.surat.surate_app.Model.Notif;
import com.surat.surate_app.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class NotificationUtils {

    private static final int  NOTIFICATION_ID = 200;
    private static final String PUSH_NOTIFICATION = "pushNotification";
    private static final String CHANNEL_ID = "myChannel";
    private static final String URL = "url";
    private static final String ACTIVITY = "activity";
    Map<String, Class> activityMap = new HashMap<>();
    private Context mContext;
    LayoutInflater layoutInflater;

    public NotificationUtils(Context mContext) {
        this.mContext = mContext;
        //Populate activity map
        layoutInflater = LayoutInflater.from(mContext);
        activityMap.put("Menu_Utama", Menu_Utama_Activity.class);
//        activityMap.put("JenisActivity", JenisActivity.class);
    }

    /**
     * Displays notification based on parameters
     *
     * @param notificationVO
     * @param resultIntent
     */
    @SuppressLint("WrongConstant")
    public void displayNotification(Notif notificationVO, Intent resultIntent) {
        {
            String message = notificationVO.getMessage();
            String title = notificationVO.getTitle();
            String action = notificationVO.getAction();
            String destination = notificationVO.getActionDestination();


            final int icon = R.drawable.logo_e_biru;

            PendingIntent resultPendingIntent;

            if (URL.equals(action)) {
                Intent notificationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(destination));

                resultPendingIntent = PendingIntent.getActivity(mContext, 0, notificationIntent, 0);
            } else if (ACTIVITY.equals(action) && activityMap.containsKey(destination)) {
                resultIntent = new Intent(mContext, activityMap.get(destination));

                resultPendingIntent =
                        PendingIntent.getActivity(
                                mContext,
                                0,
                                resultIntent,
                                PendingIntent.FLAG_CANCEL_CURRENT
                        );
            } else {
                resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                resultPendingIntent =
                        PendingIntent.getActivity(
                                mContext,
                                0,
                                resultIntent,
                                PendingIntent.FLAG_CANCEL_CURRENT
                        );
            }


            final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                    mContext, CHANNEL_ID);

            Notification notification;

                //If Bitmap is created from URL, show big icon
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
                NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
                bigPictureStyle.setBigContentTitle(title);
                notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
                        .setAutoCancel(true)
                        .setShowWhen(true)
                        .setContentTitle(title)
                        .setContentIntent(resultPendingIntent)
                        .setStyle(inboxStyle)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setVisibility(Notification.VISIBILITY_PUBLIC)
                        .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
                        .setSmallIcon(R.drawable.logo_e_biru)
                        .setSound(Uri.parse("android.resource://com.surat.surate_app/raw/notification"))
                        .setContentText(message)
                        .build();


            wakeUpLock();
            playNotificationSound();
            NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            playNotificationSound();
            playNotificationSound();
            notificationManager.notify(NOTIFICATION_ID, notification);


        }
    }

    /**
     * Downloads push notification image before displaying it in
     * the notification tray
     *
     * @param strURL : URL of the notification Image
     * @return : BitMap representation of notification Image
     */
    private Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Playing notification sound
     */
    public void playNotificationSound() {
        try {
//            Uri alarmSound = Uri.parse(Api_client.AUDIO_URL+"Sirine.mp3");
            Ringtone r = RingtoneManager.getRingtone(mContext, Uri.parse("android.resource://com.surat.surate_app/raw/notification"));
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
        MediaPlayer mediaPlayer = MediaPlayer.create(mContext,R.raw.notification);
        mediaPlayer.start();


    }

    public void StopNotificationSound() {
        try {
//            Uri alarmSound = Uri.parse(Api_client.AUDIO_URL+"Sirine.mp3");
            Ringtone r = RingtoneManager.getRingtone(mContext, Uri.parse("android.resource://com.surat.surate_app/raw/notification"));
            r.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
        MediaPlayer mediaPlayer = MediaPlayer.create(mContext,R.raw.notification);
        mediaPlayer.stop();


    }

    //to Stop Sirine
//    mediaPlayer.stop()

    public void wakeUpLock()
    {


        PowerManager pm = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);

        boolean isScreenOn = pm.isScreenOn();

        Log.i(TAG, "screen on: "+ isScreenOn);

        if(isScreenOn==false)
        {
            Log.i(TAG, "screen on if: "+ isScreenOn);

            @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK |PowerManager.ACQUIRE_CAUSES_WAKEUP |PowerManager.ON_AFTER_RELEASE,"MyLock");

            wl.acquire(5000);

            @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl_cpu = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"MyCpuLock");

            wl_cpu.acquire(5000);
        }


    }




}


