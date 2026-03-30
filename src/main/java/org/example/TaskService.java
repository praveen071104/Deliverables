package org.example;

// TaskService.java

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class TaskService {
    private final List<Task> tasks = new ArrayList<>();
    private int nextId = 1;

    public Task addTask(String title, String description) {
        Task task = new Task(nextId++, title, description);
        tasks.add(task);
        return task;
    }

    public List<Task> getAllTasks() {
        return Collections.unmodifiableList(tasks);
    }

    public boolean markTaskAsCompleted(int taskId) {
        Optional<Task> taskOpt = findById(taskId);
        if (taskOpt.isPresent()) {
            Task t = taskOpt.get();
            if (t.getStatus() == TaskStatus.COMPLETED) return false; // already completed
            t.markCompleted();
            return true;
        }
        return false;
    }

    public boolean deleteTask(int taskId) {
        Iterator<Task> it = tasks.iterator();
        while (it.hasNext()) {
            if (it.next().getTaskId() == taskId) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    private Optional<Task> findById(int id) {
        return tasks.stream().filter(t -> t.getTaskId() == id).findFirst();
    }
}
