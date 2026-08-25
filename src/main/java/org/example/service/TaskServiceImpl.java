package org.example.service;
import org.example.exception.InvalidTaskException;
import org.example.exception.TaskNotFoundException;
import org.example.model.Task;
import org.example.model.TaskStatus;
import org.example.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TaskServiceImpl implements TaskService {

    private final TaskRepository repository;

    public TaskServiceImpl(TaskRepository repository){
        this.repository = repository;
    }


    @Override
    public Task createTask(String title, String description, Long topicId, LocalDateTime deadline) {
        if (title == null || title.isBlank()) {
            throw new InvalidTaskException("Title cannot be empty");
        }
        Task task = new Task(null,title,description,topicId,deadline);
        return repository.save(task);
    }

    @Override
    public Task getTaskById(Long id) {
        return repository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found: id="+ id));
    }

    @Override
    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    @Override
    public void updateTaskStatus(Long id, TaskStatus status) {
       Task task = getTaskById(id);
       task.setStatus(status);
       repository.update(task);
    }

    @Override
    public void deleteTask(Long id) {
        getTaskById(id);
        repository.deleteById(id);
    }

    @Override
    public List<Task> findByStatus(TaskStatus status) {

        return repository.findAll().stream().filter(task -> task.getStatus() == status).collect(Collectors.toList());
    }

    @Override
    public List<Task> findOverdue() {
        return repository.findAll().stream()
                .filter(task -> task.getDeadline() != null
                        && task.getDeadline().isBefore(LocalDateTime.now())
                        && task.getStatus() != TaskStatus.DONE)
                .collect(Collectors.toList());
    }
}
