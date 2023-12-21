package com.example.todolist;

import static android.app.PendingIntent.*;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todolist.AlarmReceiver;

public class AddTaskActivity extends AppCompatActivity {

    private EditText editTextTask, editTextTime;
    private Button btnSaveTask;

    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        editTextTask = findViewById(R.id.editTextTask);
        editTextTime = findViewById(R.id.editTextTime);
        btnSaveTask = findViewById(R.id.btnSaveTask);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String task = editTextTask.getText().toString();
                String time = editTextTime.getText().toString();

                if (!task.isEmpty() && !time.isEmpty()) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("task", task);
                    resultIntent.putExtra("time", time);

                    // Alarmı ayarla
                    setAlarm(task, time);

                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                } else {
                    // Kullanıcıya gerekli alanları doldurması gerektiğini bildirin
                }
            }
        });
    }

    private void setAlarm(String task, String time) {
        // Örneğin, zaman formatı olarak "saat:dakika" şeklinde geliyor varsayalım.
        String[] splitTime = time.split(":");
        int hour = Integer.parseInt(splitTime[0]);
        int minute = Integer.parseInt(splitTime[1]);

        // Alarm tetiklenecek zamanı ayarla
        long alarmTriggerTime = getAlarmTriggerTime(hour, minute);


        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("task", task);
        alarmIntent = getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // AlarmManager kullanarak alarmı ayarla
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTriggerTime, alarmIntent);
    }

    private long getAlarmTriggerTime(int hour, int minute) {
        // Şu anki zamanı al
        long currentTime = System.currentTimeMillis();

        // Ayarlanan saat ve dakikayı kullanarak alarm tetiklenecek zamanı hesapla
        long alarmTriggerTime = currentTime + (hour * 60 * 60 * 1000) + (minute * 60 * 1000);

        return alarmTriggerTime;
    }
}