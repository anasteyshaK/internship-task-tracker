package org.example.service;

import org.example.exception.StudySessionNotFoundException;
import org.example.exception.TaskNotFoundException;
import org.example.model.StudySession;
import org.example.model.Task;
import org.example.repository.InMemoryStudySessionImpl;
import org.example.repository.InMemoryTaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class StudySessionServiceImplTest {
    private InMemoryStudySessionImpl sessions;
    private InMemoryTaskRepository tasks;
    private StudySessionServiceImpl service;
    private final LocalDateTime start = LocalDateTime.of(2026, 9, 1, 9, 0);

    @BeforeEach
    void setUp() {
        sessions = new InMemoryStudySessionImpl();
        tasks = new InMemoryTaskRepository();
        service = new StudySessionServiceImpl(sessions, tasks);
    }

    @Test
    void createsSessionForExistingTaskAndRejectsMissingTask() {
        Task task = tasks.save(new Task(null, "Study", null, null, null));
        StudySession session = service.createSession(task.getId(), start, null);

        assertEquals(1L, session.getId());
        assertEquals(task.getId(), session.getTaskId());
        assertThrows(TaskNotFoundException.class, () -> service.createSession(404L, start, null));
    }

    @Test
    void finishesAndTotalsOnlyCompletedSessionsForTask() {
        Task task = tasks.save(new Task(null, "Study", null, null, null));
        Task otherTask = tasks.save(new Task(null, "Other study", null, null, null));
        StudySession first = service.createSession(task.getId(), start, null);
        service.createSession(task.getId(), start.plusHours(2), start.plusHours(3));
        service.createSession(otherTask.getId(), start, start.plusHours(5));
        service.finishSession(first.getId(), start.plusMinutes(30));

        assertEquals(Duration.ofMinutes(90), service.getTotalDurationByTaskId(task.getId()));
        assertEquals(2, service.getSessionsByTaskId(task.getId()).size());
    }

    @Test
    void missingSessionRaisesExceptionAndDeleteRemovesSession() {
        Task task = tasks.save(new Task(null, "Study", null, null, null));
        StudySession session = service.createSession(task.getId(), start, null);
        assertThrows(StudySessionNotFoundException.class, () -> service.getSessionById(404L));

        service.deleteSession(session.getId());
        assertTrue(service.getAllSessions().isEmpty());
        assertThrows(StudySessionNotFoundException.class, () -> service.deleteSession(session.getId()));
    }
}
