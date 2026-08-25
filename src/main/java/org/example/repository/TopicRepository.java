package org.example.repository;

import org.example.model.Topic;

import java.util.List;
import java.util.Optional;

public interface TopicRepository {
    Topic save(Topic topic);

    Optional<Topic> findById(Long id);

    List<Topic> findAll();

    void update(Topic topic);

    void deleteById(Long id);
}
