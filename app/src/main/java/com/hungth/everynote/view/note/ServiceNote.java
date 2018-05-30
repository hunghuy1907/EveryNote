package com.hungth.everynote.view.note;

import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.hungth.everynote.R;
import com.hungth.everynote.model.NoteDatabase;

import java.util.Calendar;

public class ServiceNote extends Service {

    private static final int NOTIFICATION_ID = 100;
    private int idAlarm;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);

    }

    private String getTimeCurrent() {
        String date;
        String time;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        if (dayOfWeek == 1) {
            date = "chủ nhật " + " " + dayOfMonth + "/" + month + "/" + year;
        } else {
            date = "Thứ " + " " + dayOfWeek + dayOfMonth + "/" + month + "/" + year;
        }

        if (minute < 10) {
            time = hour + " : 0" + minute;
        } else {
            time = hour + " : " + minute;
        }
        return date + time;
    }

    private void showNotification() {
        Intent intent = new Intent();
        idAlarm = Integer.parseInt(intent.getStringExtra("alarm"));

        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("bacdđ")
                .setContentText("AA")
                .setStyle(new android.support.v4.media.app.NotificationCompat.MediaStyle())
                .setOngoing(true)
                .setAutoCancel(false)
                .setShowWhen(false)
                .build();


        startForeground(NOTIFICATION_ID, notification);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private class AlarmReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == null) {
                return;
            }

            switch (intent.getAction()) {
                case "intentAlarm":
                    idAlarm = Integer.parseInt(intent.getStringExtra("alarm"));
                    break;

                default:
                    break;

            }
        }
    }
}
