package org.example;

import org.example.exception.InvalidTaskException;
import org.example.exception.StudySessionNotFoundException;
import org.example.exception.TaskNotFoundException;
import org.example.exception.TopicNotFoundException;
import org.example.model.StudySession;
import org.example.model.Task;
import org.example.model.TaskStatus;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import org.example.model.Topic;
import org.example.service.StudySessionService;
import org.example.service.TaskService;
import org.example.service.TopicService;

public class ConsoleMenu {
    private final TaskService taskService;
    private final TopicService topicService;
    private final StudySessionService sessionService;
    private final Scanner scanner;

    public ConsoleMenu(TaskService taskService, TopicService topicService, StudySessionService sessionService) {
        this.taskService = taskService;
        this.topicService = topicService;
        this.sessionService = sessionService;
        this.scanner = new Scanner(System.in);
    }

    public void run(){
        boolean running = true;
        while(running){
            printMenu();
            int choose = readInt();
            switch(choose){
                case 1 -> createTask();
                case 2 -> showAllTasks();
                case 3 -> findTaskById();
                case 4 -> updateStatus();
                case 5 -> deleteTask();
                case 6 -> findByStatus();
                case 7 -> showOverdue();
                case 8 -> createTopic();
                case 9 -> showAllTopics();
                case 10 -> updateTopic();
                case 11 -> deleteTopic();
                case 12 -> startSession();
                case 13 -> finishSession();
                case 14 -> showSessionsByTask();
                case 15 -> showTotalDuration();
                case 0 -> running = false;
                default -> System.out.println("Неизвестный выбор ");

            }
        }
        System.out.println("Выход из программы.");
    }

    private void printMenu() {
        System.out.println("======================================== === Task Tracker === ========================================\n");

        // Заголовки колонок
        System.out.printf("%-35s %-35s %-35s%n",
                "--- 📋 ЗАДАЧИ ---",
                "--- 📚 ТЕМЫ ---",
                "--- ⏱️ СЕССИИ ---");
        System.out.println("----------------------------------------------------------------------------------------------------");

        // Пункты меню построчно по колонкам
        System.out.printf("%-35s %-35s %-35s%n", "1. Создать задачу",           "8.  Создать тему",            "12. Начать сессию");
        System.out.printf("%-35s %-35s %-35s%n", "2. Показать все задачи",         "9.  Показать все темы",        "13. Завершить сессию");
        System.out.printf("%-35s %-35s %-35s%n", "3. Найти задачу по id",         "10. Изменить название темы",  "14. Показать сессии по задаче");
        System.out.printf("%-35s %-35s %-35s%n", "4. Изменить статус задачи",      "11. Удалить тему",            "15. Показать общее время");
        System.out.printf("%-35s %-35s %-35s%n", "5. Удалить задачу",              "",                            "");
        System.out.printf("%-35s %-35s %-35s%n", "6. Показать по статусу",         "",                            "");
        System.out.printf("%-35s %-35s %-35s%n", "7. Показать просроченные",       "",                            "");

        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.printf("%-35s%n", "0. Выход");
        System.out.print("Выбери пункт: ");
    }

    private int readInt(){
        while(!scanner.hasNextInt()){
            System.out.println("Введи число.");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    private void createTask(){
        System.out.println("Название задачи: ");
        String title = scanner.nextLine();

        System.out.println("Описание: ");
        String description = scanner.nextLine();

        System.out.println("Id темы: ");
        Long topicId = (long) readInt();

        System.out.println("Через сколько дней дедлайн ?(0-если его нет) :");
        int days = readInt();
        LocalDateTime deadline = days > 0 ? LocalDateTime.now().plusDays(days) : null;

        try{
            Task task = taskService.createTask(title,description,topicId,deadline);
            System.out.println("Создана задача: " + task);

        }catch(InvalidTaskException e){
            System.out.println("Ошибка:" + e.getMessage());
        }

    }

    private void showAllTasks(){
        List<Task> tasks = taskService.getAllTasks();
        if(tasks.isEmpty()){
            System.out.println("Задач пока нет.");
            return;
        }
        tasks.forEach(System.out::println);

    }

    private void findTaskById(){
        System.out.println("Введи id задачи: ");
        long id = readInt();
        try{
            Task task = taskService.getTaskById(id);
            System.out.println(task);
        }catch(TaskNotFoundException e){
            System.out.println("Ошибка: "+e.getMessage());
        }
    }

    private void updateStatus(){
        System.out.println("Введи id задачи: ");
        long id = readInt();

        System.out.println("Новый статус (0=TODO, 1=IN_PROGRESS, 2=DONE): ");
        int statusChoice = readInt();
        TaskStatus status = switch(statusChoice){
            case 0 -> TaskStatus.TODO;
            case 1 -> TaskStatus.IN_PROGRESS;
            case 2 -> TaskStatus.DONE;
            default -> null;
        };

        if(status == null){
            System.out.println("Некорректный статус.");
            return;
        }

        try{
            taskService.updateTaskStatus(id,status);
            System.out.println("Статус обновлен.");
        }catch (TaskNotFoundException e){
            System.out.println("Ошибка: "+e.getMessage());
        }
    }

    private void deleteTask(){
        System.out.println("Введи id задачи: ");
        long id = readInt();
        try{
            taskService.deleteTask(id);
            System.out.println("Задача удалена.");
        }catch (TaskNotFoundException e){
            System.out.println("Ошибка: "+e.getMessage());
        }
    }

    private void findByStatus(){
        System.out.println("Статус (0=TODO, 1=IN_PROGRESS, 2=DONE): ");
        int statusChoice = readInt();
        TaskStatus status = switch(statusChoice){
            case 0 -> TaskStatus.TODO;
            case 1 -> TaskStatus.IN_PROGRESS;
            case 2 -> TaskStatus.DONE;
            default -> null;
        };

        if(status == null){
            System.out.println("Некорректный статус.");
            return;
        }

        List<Task> tasks = taskService.findByStatus(status);
        if(tasks.isEmpty()){
            System.out.println("Задач с таким статусом нет.");
            return;
        }
        tasks.forEach(System.out::println);
    }

    private void showOverdue(){
        List<Task> overdue = taskService.findOverdue();
        if(overdue.isEmpty()){
            System.out.println("Просроченных задач нет.");
            return;
        }
        overdue.forEach(System.out::println);
    }

    private void createTopic() {
        System.out.println("Название темы: ");
        String name = scanner.nextLine();
        Topic topic = topicService.createTopic(name);
        System.out.println("Создана тема: " + topic);
    }

    private void showAllTopics() {
        List<Topic> topics = topicService.getAllTopics();
        if (topics.isEmpty()) {
            System.out.println("Тем пока нет.");
            return;
        }
        topics.forEach(System.out::println);
    }

    private void updateTopic() {
        System.out.println("Введи id темы: ");
        long id = readInt();
        System.out.println("Новое название: ");
        String newName = scanner.nextLine();
        try {
            topicService.updateTopicName(id, newName);
            System.out.println("Название обновлено.");
        } catch (TopicNotFoundException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void deleteTopic() {
        System.out.println("Введи id темы: ");
        long id = readInt();
        try {
            topicService.deleteTopic(id);
            System.out.println("Тема удалена.");
        } catch (TopicNotFoundException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void startSession() {
        System.out.println("Id задачи: ");
        Long taskId = (long) readInt();

        try {
            StudySession session = sessionService.createSession(taskId, LocalDateTime.now(), null);
            System.out.println("Сессия начата: " + session);
        } catch (TaskNotFoundException | IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void finishSession() {
        System.out.println("Id сессии: ");
        long id = readInt();

        try {
            StudySession session = sessionService.finishSession(id, LocalDateTime.now());
            System.out.println("Сессия завершена: " + session);
        } catch (StudySessionNotFoundException | IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void showSessionsByTask() {
        System.out.println("Id задачи: ");
        Long taskId = (long) readInt();

        List<StudySession> sessions = sessionService.getSessionsByTaskId(taskId);
        if (sessions.isEmpty()) {
            System.out.println("Сессий по этой задаче нет.");
            return;
        }
        sessions.forEach(System.out::println);
    }

    private void showTotalDuration() {
        System.out.println("Id задачи: ");
        Long taskId = (long) readInt();

        Duration total = sessionService.getTotalDurationByTaskId(taskId);
        System.out.printf("Общее время: %d ч %d мин%n", total.toHours(), total.toMinutesPart());
    }




}
