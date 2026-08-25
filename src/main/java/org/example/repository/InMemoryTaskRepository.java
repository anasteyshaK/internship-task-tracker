package org.example.repository;

import org.example.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryTaskRepository implements TaskRepository {

    private final Map<Long,Task> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    @Override
    public Task save(Task task) {
        Long newId = idGenerator.incrementAndGet();
        task.setId(newId);
        storage.put(newId,task);
        return task;
    }

    @Override
    public Optional<Task> findById(Long id) {
       return Optional.ofNullable(storage.get(id));

    }

    @Override
    public List<Task> findAll() {

        return new ArrayList<>(storage.values());
    }

    @Override
    public void update(Task task) {
        storage.put(task.getId(), task);

    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id);
    }
}
