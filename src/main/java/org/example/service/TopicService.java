package org.example.service;
import java.util.List;
import org.example.model.Topic;


public interface TopicService {
    Topic createTopic(String name);
    Topic getTopicById(Long id);
    List<Topic> getAllTopics();
    void updateTopicName(Long id,String newName);
    void deleteTopic(Long id);

}
