package org.example.repository;

import org.example.model.Topic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryTopicRepositoryImpl implements TopicRepository {

    private final Map<Long,Topic> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator =  new AtomicLong(0);

    @Override
    public Topic save(Topic topic) {
        Long newId = idGenerator.incrementAndGet();
        topic.setId(newId);
        storage.put(newId,topic);
        return topic;
    }

    @Override
    public Optional<Topic> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Topic> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void update(Topic topic) {
        storage.put(topic.getId(),topic);
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id);
    }
}
