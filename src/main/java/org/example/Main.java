package org.example;

import org.example.model.Task;
import org.example.repository.*;
import org.example.service.*;

import java.time.LocalDateTime;
import java.util.List;

import java.util.Scanner;





public class Main {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        TaskRepository taskRepository = new InMemoryTaskRepository();
        TaskService taskService = new TaskServiceImpl(taskRepository);
        TopicRepository topicRepository = new InMemoryTopicRepositoryImpl();
        TopicService topicService = new TopicServiceImpl(topicRepository);
        StudySessionRepository studySessionRepository = new InMemoryStudySessionImpl();
        StudySessionService studySessionService = new StudySessionServiceImpl(studySessionRepository,taskRepository);


        Task task1 = taskService.createTask("Learn Stream API","Повторить filter, map, collect",1L,LocalDateTime.now().plusDays(3));
        Task task2 = taskService.createTask("Write JUnit tests","Покрыть TaskServiceImpl тестами",1L,LocalDateTime.now().minusDays(1));

       ConsoleMenu menu = new ConsoleMenu(taskService,topicService,studySessionService);
       menu.run();
        }


    }
