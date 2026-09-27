package org.example;

import org.example.model.Task;
import org.example.model.Topic;
import org.example.repository.*;
import org.example.service.*;

import java.time.LocalDateTime;
import java.util.List;

import java.util.Scanner;





public class Main {
    public static void main(String[] args) {
        TaskRepository taskRepository = new InMemoryTaskRepository();
        TopicRepository topicRepository = new InMemoryTopicRepositoryImpl();
        TaskService taskService = new TaskServiceImpl(taskRepository,topicRepository);
        TopicService topicService = new TopicServiceImpl(topicRepository);
        StudySessionRepository studySessionRepository = new InMemoryStudySessionImpl();
        StudySessionService studySessionService = new StudySessionServiceImpl(studySessionRepository,taskRepository);


        Topic javaTopic = topicService.createTopic("Java Backend");

        Task task1 = taskService.createTask("Learn Stream API", "Повторить filter, map, collect",
                javaTopic.getId(), LocalDateTime.now().plusDays(3));
        Task task2 = taskService.createTask("Write JUnit tests", "Покрыть TaskServiceImpl тестами",
                javaTopic.getId(), LocalDateTime.now().minusDays(1));

       ConsoleMenu menu = new ConsoleMenu(taskService,topicService,studySessionService);
       menu.run();
        }


    }
