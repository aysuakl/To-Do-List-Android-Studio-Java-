package com.example.todolist;

public class Task {
    private String taskName;
    private String time;
    private boolean completed;

    public Task(String taskName, String time) {
        this.taskName = taskName;
        this.time = time;
        this.completed = false;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTime() {
        return time;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}