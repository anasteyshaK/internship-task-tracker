package org.example.repository;

import org.example.model.StudySession;
import org.example.model.Task;
import org.example.model.Topic;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryRepositoriesTest {
    @Test
    void taskRepositoryAssignsIdsAndSupportsReadUpdateAndDelete() {
        InMemoryTaskRepository repository = new InMemoryTaskRepository();
        Task first = new Task(null, "First", null, null, null);
        Task second = new Task(null, "Second", null, null, null);

        assertSame(first, repository.save(first));
        repository.save(second);
        assertEquals(1L, first.getId());
        assertEquals(2L, second.getId());
        assertEquals(2, repository.findAll().size());
        assertSame(first, repository.findById(first.getId()).orElseThrow());

        first.setTitle("Updated");
        repository.update(first);
        assertEquals("Updated", repository.findById(1L).orElseThrow().getTitle());
        repository.deleteById(1L);
        assertTrue(repository.findById(1L).isEmpty());
    }

    @Test
    void topicRepositoryAssignsIdsAndCanDelete() {
        InMemoryTopicRepositoryImpl repository = new InMemoryTopicRepositoryImpl();
        Topic topic = repository.save(new Topic(null, "Math"));

        assertEquals(1L, topic.getId());
        assertEquals("Math", repository.findById(1L).orElseThrow().getName());
        repository.deleteById(1L);
        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    void studySessionRepositoryAssignsIdsAndCanUpdate() {
        InMemoryStudySessionImpl repository = new InMemoryStudySessionImpl();
        LocalDateTime start = LocalDateTime.of(2026, 9, 1, 9, 0);
        StudySession session = repository.save(new StudySession(null, 2L, start, null));
        assertEquals(1L, session.getId());

        session.setEndTime(start.plusHours(1));
        repository.update(session);
        assertEquals(start.plusHours(1), repository.findById(1L).orElseThrow().getEndTime());
        repository.deleteById(1L);
        assertTrue(repository.findById(1L).isEmpty());
    }
}
