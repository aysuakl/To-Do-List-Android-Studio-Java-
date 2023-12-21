package com.example.todolist;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.util.Random;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = "AlarmReceiver";
    private static final String CHANNEL_ID = "MyChannel";

    @Override
    public void onReceive(Context context, Intent intent) {
        String task = intent.getStringExtra("task");

        // Log ekle
        Log.d(TAG, "onReceive: Task received - " + task);

        // Bildirim gösterme işlemi
        showNotification(context, task);
    }

    private void showNotification(Context context, String task) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "My Channel", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        // Bildirim oluştur
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Görev Zamanı!")
                .setContentText(task)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Bildirim ID'sini belirli bir öğeden oluştur
        int notificationId = generateNotificationId(task);

        // Bildirimi göster
        notificationManager.notify(notificationId, builder.build());

        // Bildirim logu
        Log.d(TAG, "showNotification: Notification shown for task - " + task + ", Notification ID: " + notificationId);
    }

    private int generateNotificationId(String task) {
        // Görev adını kullanarak benzersiz bir bildirim ID'si oluştur
        return task.hashCode();
    }
}
//Bildirim kodu yazdığım izinleri açtığım halde bildirimler görünmüyor.