package org.example.service;

import org.example.exception.TopicNotFoundException;
import org.example.model.Topic;
import org.example.repository.TopicRepository;

import java.util.List;

public class TopicServiceImpl implements TopicService {
    private final TopicRepository repository;

    public TopicServiceImpl(TopicRepository repository) {
        this.repository = repository;
    }

    @Override
    public Topic createTopic(String name) {
        Topic topic = new Topic(null,name);
        return repository.save(topic);
    }

    @Override
    public Topic getTopicById(Long id) {
        return repository.findById(id).orElseThrow(() -> new TopicNotFoundException("Topic not found: id="+ id));
    }

    @Override
    public List<Topic> getAllTopics() {
        return repository.findAll();
    }

    @Override
    public void updateTopicName(Long id, String newName) {
        Topic topic = getTopicById(id);
        topic.setName(newName);
        repository.update(topic);
    }

    @Override
    public void deleteTopic(Long id) {
        getTopicById(id);
        repository.deleteById(id);
    }
}
