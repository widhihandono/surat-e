package com.surat.surate_app.Util;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.surat.surate_app.Services.AlarmService;
import com.surat.surate_app.Services.WakeIntentService;

public class OnAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        WakeIntentService.acquireStaticLock(context);
        Intent i = new Intent(context, AlarmService.class);
        i.putExtra("title",intent.getExtras().getString("title"));
        i.putExtra("message",intent.getExtras().getString("message"));
        i.putExtra("to_notif",intent.getExtras().getString("to_notif"));
        context.startService(i);
    }
}
