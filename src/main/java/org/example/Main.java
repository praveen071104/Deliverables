package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
// Main.java
// Console-based Employee Task Tracker
// Clean code: small main, helper methods for each action, validation, clear messages.

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final int one = 1;
    private static final int two = 2;
    private static final int three = 3;
    private static final int four = 4;
    private static final int five = 5;

    private final TaskService taskService;
    private final Scanner scanner;

    public Main() {
        this.taskService = new TaskService();
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        boolean flag = true;
        while (flag) {
            printMenu();
            int choice = readInt("Choose an option: ");

            switch (choice) {
                case one:
                    handleAddTask();
                    break;
                case two:
                    handleViewTasks();
                    break;
                case three:
                    handleMarkTaskCompleted();
                    break;
                case four:
                    handleDeleteTask();
                    break;
                case five:
                    System.out.println("Thank You !");
                    flag = false;
                    break;
                default:
                    System.out.println("Invalid option. Please choose a number between 1 and 5.\n");
            }
        }
    }

    private void printMenu() {
        System.out.println("===== Employee Task Tracker =====");
        System.out.println("1. Add Task");
        System.out.println("2. View Tasks");
        System.out.println("3. Mark Task as Completed");
        System.out.println("4. Delete Task");
        System.out.println("5. Exit");
    }

    private void handleAddTask() {
        System.out.println("\n-- Add Task --");
        String title;
        while (true) {
            System.out.print("Title (required): ");
            title = scanner.nextLine().trim();
            if (!title.isEmpty()) break;
            System.out.println("Title cannot be empty. Please enter a valid title.");
        }
        System.out.print("Description (optional): ");
        String description = scanner.nextLine().trim();
        try {
            Task task = taskService.addTask(title, description);
            System.out.println("Task added successfully with ID " + task.getTaskId() + ".\n");
        } catch (IllegalArgumentException ex) {
            System.out.println("Failed to add task: " + ex.getMessage() + "\n");
        }
    }

    private void handleViewTasks() {
        System.out.println("\n-- All Tasks --");
        List<Task> tasks = taskService.getAllTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks available. Use 'Add Task' to create one.\n");
            return;
        }
        for (Task t : tasks) {
            System.out.println(formatTask(t));
        }
        System.out.println();
    }

    private String formatTask(Task t) {
        StringBuilder sb = new StringBuilder();
        sb.append("[#").append(t.getTaskId()).append("] ")
                .append(t.getTitle()).append(" | Status: ")
                .append(t.getStatus());
        if (t.getDescription() != null && !t.getDescription().isEmpty()) {
            sb.append("\n    ").append(t.getDescription());
        }
        return sb.toString();
    }

    private void handleMarkTaskCompleted() {
        System.out.println("\n-- Mark Task as Completed --");
        int id = readInt("Enter task ID: ");
        boolean updated = taskService.markTaskAsCompleted(id);
        if (updated) {
            System.out.println("Task #" + id + " marked as COMPLETED.\n");
        } else {
            System.out.println("Task #" + id + " not found or already COMPLETED.\n");
        }
    }

    private void handleDeleteTask() {
        System.out.println("\n-- Delete Task --");
        int id = readInt("Enter task ID: ");
        boolean removed = taskService.deleteTask(id);
        if (removed) {
            System.out.println("Task #" + id + " deleted successfully.\n");
        } else {
            System.out.println("Task #" + id + " not found.\n");
        }
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine();
            try {
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}