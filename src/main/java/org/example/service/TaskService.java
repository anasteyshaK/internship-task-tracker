package org.example.service;

import org.example.model.Task;
import org.example.model.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskService {
    Task createTask(String title, String description, Long topicId, LocalDateTime deadline);
    Task getTaskById(Long id);
    List<Task> getAllTasks();
    void updateTaskStatus(Long id, TaskStatus status);
    void deleteTask(Long id);
    List<Task> findByStatus(TaskStatus status);
    List<Task> findOverdue();

}
