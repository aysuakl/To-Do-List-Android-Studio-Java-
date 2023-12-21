package com.example.todolist;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todolist.AlarmReceiver;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listViewTasks;
    private Button btnAddTask;

    private ArrayList<Task> taskList;
    private TaskAdapter adapter;

    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        listViewTasks = findViewById(R.id.listViewTasks);
        btnAddTask = findViewById(R.id.btnAddTask);

        taskList = new ArrayList<>();
        adapter = new TaskAdapter(this, taskList); // TaskAdapter oluşturma kodu


        listViewTasks.setAdapter(adapter);

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        listViewTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null) {
                String taskName = data.getStringExtra("task");
                String time = data.getStringExtra("time");

                // Yeni görevi listeye ekle
                Task newTask = new Task(taskName, time);
                taskList.add(newTask);

                adapter.notifyDataSetChanged();

                // Alarmı ayarla
                setAlarm(newTask);
            }
        }
    }

    private void setAlarm(Task task) {
        String[] splitTime = task.getTime().split(":");
        int hour = Integer.parseInt(splitTime[0]);
        int minute = Integer.parseInt(splitTime[1]);

        // Alarm tetiklenecek kod
        long alarmTriggerTime = getAlarmTriggerTime(hour, minute);


        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("task", task.getTaskName());
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // AlarmManager kullanarak alarmı ayarlamak
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTriggerTime, alarmIntent);
    }

    private long getAlarmTriggerTime(int hour, int minute) {

        long currentTime = System.currentTimeMillis();

        // Ayarlanan saat ve dakikayı kullanarak alarm tetiklenecek zamanı hesapla
        long alarmTriggerTime = currentTime + (hour * 60 * 60 * 1000) + (minute * 60 * 1000);

        return alarmTriggerTime;
    }
}

//Anlamadığım bir şey var PendingIntent versiyon sorunu veriyor arada bunu nasıl çözebilirim araştır. Yerine ne kullanabilirim??