package com.surat.surate_app.Services;

import android.app.Notification;
import android.content.Intent;

import com.surat.surate_app.Menu_Utama_Activity;
import com.surat.surate_app.Model.Notif;
import com.surat.surate_app.Util.NotificationUtils;

public class AlarmService extends WakeIntentService {
    Notification myNotication;
    Notif notificationVO;
    NotificationUtils notificationUtils;
    Intent resultIntent;
    public AlarmService() {
        super("AlarmService");
    }

    @Override
    void doReminderWork(Intent intent) {
        notificationUtils = new NotificationUtils(this);
        notificationVO = new Notif();
        notificationVO.setTitle(intent.getExtras().getString("title"));
        notificationVO.setMessage(intent.getExtras().getString("message"));

        notificationVO.setTo_notif(intent.getExtras().getString("to_notif"));

        resultIntent = new Intent(getApplicationContext(), Menu_Utama_Activity.class);
        resultIntent.putExtra("title",notificationVO.getTitle());
        resultIntent.putExtra("message",notificationVO.getMessage());
        notificationUtils.playNotificationSound();
        notificationUtils.displayNotification(notificationVO,resultIntent);
    }
}
