package org.example.service;

import org.example.exception.InvalidTaskException;
import org.example.exception.TaskNotFoundException;
import org.example.exception.TopicNotFoundException;
import org.example.model.Task;
import org.example.model.TaskStatus;
import org.example.model.Topic;
import org.example.repository.InMemoryTaskRepository;
import org.example.repository.InMemoryTopicRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceImplTest {
    private InMemoryTaskRepository tasks;
    private InMemoryTopicRepositoryImpl topics;
    private TaskServiceImpl service;

    @BeforeEach
    void setUp() {
        tasks = new InMemoryTaskRepository();
        topics = new InMemoryTopicRepositoryImpl();
        service = new TaskServiceImpl(tasks, topics);
    }

    @Test
    void createsTaskOnlyForExistingTopic() {
        Topic topic = topics.save(new Topic(null, "Java"));
        Task task = service.createTask("Practice", "", topic.getId(), null);

        assertEquals(1L, task.getId());
        assertEquals(topic.getId(), task.getTopicId());
        assertEquals(TaskStatus.TODO, task.getStatus());
        assertThrows(TopicNotFoundException.class, () -> service.createTask("Orphan", "", 999L, null));
    }

    @Test
    void validatesTitleAndReportsMissingTask() {
        assertThrows(InvalidTaskException.class, () -> service.createTask(" ", "", 1L, null));
        assertThrows(TaskNotFoundException.class, () -> service.getTaskById(404L));
    }

    @Test
    void filtersByStatusAndOverdueExcludesDoneAndFutureTasks() {
        Long topicId = topics.save(new Topic(null, "Java")).getId();
        Task overdue = service.createTask("Late", "", topicId, LocalDateTime.now().minusDays(2));
        Task completedLate = service.createTask("Done", "", topicId, LocalDateTime.now().minusDays(1));
        service.createTask("Future", "", topicId, LocalDateTime.now().plusDays(1));
        service.updateTaskStatus(completedLate.getId(), TaskStatus.DONE);
        service.updateTaskStatus(overdue.getId(), TaskStatus.IN_PROGRESS);

        assertEquals(1, service.findOverdue().size());
        assertEquals(overdue, service.findOverdue().get(0));
        assertEquals(1, service.findByStatus(TaskStatus.DONE).size());
    }

    @Test
    void deletesExistingTask() {
        Long topicId = topics.save(new Topic(null, "Java")).getId();
        Task task = service.createTask("Practice", "", topicId, null);
        service.deleteTask(task.getId());

        assertTrue(service.getAllTasks().isEmpty());
        assertThrows(TaskNotFoundException.class, () -> service.deleteTask(task.getId()));
    }
}
