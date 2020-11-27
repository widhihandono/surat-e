package com.surat.surate_app.Services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.constraintlayout.widget.Constraints;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.surat.surate_app.Menu_Utama_Activity;
import com.surat.surate_app.Model.Notif;
import com.surat.surate_app.Util.NotificationUtils;
import com.surat.surate_app.Util.OnAlarmReceiver;
import com.surat.surate_app.Util.SharedPref;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
private static final String Tag = "MyFirebaseMessagingService";
private static final String TOPIC_GLOBAL = "semua";
SharedPref sharedPref;
Intent resultIntent;

    private static final String TITLE = "title";
    private static final String EMPTY = "";
    private static final String MESSAGE = "message";
    private static final String ACTION = "action";
    private static final String DATA = "data";

    private static final String ID_DISPOSISI = "id_disposisi";
    private static final String NO_URUT = "no_urut";
    private static final String DARI = "dari";
    private static final String NOMOR = "nomor";
    private static final String TANGGAL_DITERIMA = "tanggal_diterima";
    private static final String TANGGAL_SURAT = "tanggal_surat";
    private static final String NOMOR_AGENDA = "nomor_agenda";
    private static final String PERIHAL = "perihal";
    private static final String STATUS = "status";
    private static final String ID_DOKUMEN = "id_dokumen";
    private static final String TANGGAL_KIRIM_TU_UMUM = "tanggal_kirim_tu_umum";
    private static final String TANGGAL_KIRIM_TU_BUPATI = "tanggal_kirim_tu_bupati";
    private static final String TANGGAL_KIRIM_AJUDAN = "tanggal_kirim_ajudan";
    private static final String TANGGAL_KIRIM_BUPATI = "tanggal_kirim_bupati";
    private static final String PATH_DISPOSISI = "path_disposisi";
    private static final String IMAGE_DISPOSISI = "image_disposisi";
    private static final String ID_DISPOSISI_UNTUK = "id_disposisi_untuk";
    private static final String ALASAN_DITOLAK_TU_BUPATI = "alasan_ditolak_tu_bupati";
    private static final String CREATE_BY = "created_by";
    private static final String PATH = "path";
    private static final String PATH_FILE = "path_file";
    private static final String TIME_DOKUMEN = "time_dokumen";
    private static final String ID_JENIS_DOKUMEN = "id_jenis_dokumen";
    private static final String JENIS_DOKUMEN = "jenis_dokumen";
    private static final String ID_SIFAT_DOKUMEN = "id_sifat_dokumen";
    private static final String SIFAT_DOKUMEN =  "sifat_dokumen";
    private static final String TO_NOTIF ="to_notif";

    private static final String ACTION_DESTINATION = "action_destination";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(Constraints.TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(Constraints.TAG, "Message data payload: " + remoteMessage.getData());
            Map<String, String> data = remoteMessage.getData();
            handleData(data);
            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
//                scheduleJob();
                handleData(data);
            } else {
                // Handle message within 10 seconds
//                handleNow();
                handleData(data);
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(Constraints.TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    private void handleNotification(RemoteMessage.Notification RemoteMsgNotification) {
        String message = RemoteMsgNotification.getBody();
        String title = RemoteMsgNotification.getTitle();
        Notif notificationVO = new Notif();
        notificationVO.setTitle(title);
        notificationVO.setMessage(message);

        Intent resultIntent = new Intent(getApplicationContext(), Menu_Utama_Activity.class);
        NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
        notificationUtils.displayNotification(notificationVO, resultIntent);
        notificationUtils.wakeUpLock();
        notificationUtils.playNotificationSound();
    }

    private void handleData(Map<String, String> data) {
        NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
        String title = data.get(TITLE);
        String message = data.get(MESSAGE);
        String action = data.get(ACTION);
        String actionDestination = data.get(ACTION_DESTINATION);
        String id_disposisi = data.get(ID_DISPOSISI);

        String id_dokumen = data.get(ID_DOKUMEN);
        String dari = data.get(DARI);
        String perihal = data.get(PERIHAL);
        String to_notif = data.get(TO_NOTIF);
        String tanggal_surat = data.get(TANGGAL_SURAT);
        String nomor = data.get(NOMOR);

        Notif notificationVO = new Notif();
        notificationVO.setTitle(title);
        notificationVO.setMessage(message);
        notificationVO.setAction(action);
        notificationVO.setActionDestination(actionDestination);

        notificationVO.setId_disposisi(id_disposisi);
        notificationVO.setId_dokumen(id_dokumen);
        notificationVO.setDari(dari);
        notificationVO.setPerihal(perihal);
        notificationVO.setTo_notif(to_notif);
        notificationVO.setTanggal_surat(tanggal_surat);

        sharedPref = new SharedPref(getApplicationContext());

//        resultIntent = new Intent(getApplicationContext(), List_Laporan_Acivity.class);
//        notificationUtils.playNotificationSound();
//        notificationUtils.displayNotification(notificationVO, resultIntent);
//        notificationUtils.wakeUpLock();

        if(to_notif.equals("bupati_1"))
        {
            notificationUtils.wakeUpLock();
            notificationUtils.playNotificationSound();
            notificationUtils.wakeUpLock();
            notificationUtils.playNotificationSound();

            alarm(notificationVO.getTitle(),notificationVO.getMessage(),notificationVO.getTo_notif());
                resultIntent = new Intent(getApplicationContext(), Menu_Utama_Activity.class);
                resultIntent.putExtra("title",notificationVO.getTitle());
                resultIntent.putExtra("message",notificationVO.getMessage());
                resultIntent.putExtra("to_notif",notificationVO.getTo_notif());

 //                notificationUtils.playNotificationSound();
                notificationUtils.displayNotification(notificationVO, resultIntent);
                notificationUtils.wakeUpLock();



        }
        else if(to_notif.equals("ajudan_1"))
        {
            notificationUtils.wakeUpLock();
            notificationUtils.playNotificationSound();
            notificationUtils.wakeUpLock();
            notificationUtils.playNotificationSound();

            alarm(notificationVO.getTitle(),notificationVO.getMessage(),notificationVO.getTo_notif());
            resultIntent = new Intent(getApplicationContext(), Menu_Utama_Activity.class);
            resultIntent.putExtra("title",notificationVO.getTitle());
            resultIntent.putExtra("message",notificationVO.getMessage());
            resultIntent.putExtra("to_notif",notificationVO.getTo_notif());

            //                notificationUtils.playNotificationSound();
            notificationUtils.displayNotification(notificationVO, resultIntent);
            notificationUtils.wakeUpLock();
        }
        else
        {
            Log.i("Failed","Gagal Kirim Notification");
        }


    }

    private void alarm(String title,String message,String to_notif)
    {
        Intent i = new Intent(this, OnAlarmReceiver.class);
        i.putExtra("title",title);
        i.putExtra("message",message);
        i.putExtra("to_notif",to_notif);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i,
                PendingIntent.FLAG_ONE_SHOT);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,Integer.parseInt(time().split(":")[0]));
        calendar.set(Calendar.MINUTE,Integer.parseInt(time().split(":")[1]));
        calendar.set(Calendar.SECOND, Integer.parseInt(time().split(":")[2]) + 7);
//        Toast.makeText(getApplicationContext(),String.valueOf(calendar.get(Calendar.SECOND)),Toast.LENGTH_LONG).show();
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
    }

    private String time()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return sdf.format(date);
    }
}
