package com.example.taskmanagement.service;

import com.example.taskmanagement.model.Task;
import com.example.taskmanagement.model.TaskExecution;
import com.example.taskmanagement.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(String id) {
        return taskRepository.findById(id);
    }

    public Task createTask(Task task) {
            if (task == null) {
                throw new IllegalArgumentException("Task object must not be null");
            }
            if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
                throw new IllegalArgumentException("Task title is required");
            }
            return taskRepository.save(task);
    }

    public void deleteTask(String id) {
        taskRepository.deleteById(id);
    }

    public List<Task> findTasksByName(String titleSubstring) {
        return taskRepository.findByTitleContainingIgnoreCase(titleSubstring);
    }

    public TaskExecution executeTask(String id) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Task not found with id: " + id));

        TaskExecution execution = new TaskExecution();
        execution.setStartTime(java.time.Instant.now());
        execution.setEndTime(java.time.Instant.now());
        execution.setOutput("Executed at runtime: SUCCESS");
        if (task.getTaskExecutions() == null) {
            throw new IllegalStateException("Task executions list is not initialized");
        }
        task.getTaskExecutions().add(execution);
        taskRepository.save(task);
        return execution;
    }
}
