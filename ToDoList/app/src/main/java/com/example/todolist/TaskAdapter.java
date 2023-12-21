package com.example.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<Task> {

    public TaskAdapter(Context context, ArrayList<Task> tasks) {
        super(context, 0, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Task task = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_list_item, parent, false);
        }

        TextView textViewTaskName = convertView.findViewById(R.id.textViewTaskName);
        TextView textViewTaskTime = convertView.findViewById(R.id.textViewTaskTime);
        CheckBox checkBoxTaskCompleted = convertView.findViewById(R.id.checkBoxTaskCompleted);

        textViewTaskName.setText(task.getTaskName());
        textViewTaskTime.setText(task.getTime());
        checkBoxTaskCompleted.setChecked(task.isCompleted());

        checkBoxTaskCompleted.setOnCheckedChangeListener((buttonView, isChecked) -> {
            task.setCompleted(isChecked);
        });

        return convertView;
    }
}