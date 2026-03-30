package org.example;



public class Task {
    private  int taskId;
    private String title;
    private  String description;
    private TaskStatus  status;

    public Task () {

    }

    public Task(int taskId, String title, String description) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Task title cannot be empty");
        }
        this.taskId = taskId;
        this.title = title.trim();
        this.description = description == null ? "" : description.trim();
        this.status = TaskStatus.PENDING;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void markCompleted() {
        this.status = TaskStatus.COMPLETED;
    }
}
