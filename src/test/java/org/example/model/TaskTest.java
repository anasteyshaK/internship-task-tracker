package org.example.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    @Test
    void createsTaskWithTodoStatusAndValues() {
        LocalDateTime deadline = LocalDateTime.of(2026, 10, 1, 12, 0);
        Task task = new Task(3L, "Read", "Chapter one", 8L, deadline);

        assertEquals(3L, task.getId());
        assertEquals("Read", task.getTitle());
        assertEquals("Chapter one", task.getDescription());
        assertEquals(8L, task.getTopicId());
        assertEquals(TaskStatus.TODO, task.getStatus());
        assertEquals(deadline, task.getDeadline());
        assertNotNull(task.getCreatedAT());
    }

    @Test
    void rejectsNullOrBlankTitle() {
        assertThrows(IllegalArgumentException.class, () -> new Task(null, null, null, null, null));
        assertThrows(IllegalArgumentException.class, () -> new Task(null, "  ", null, null, null));
    }

    @Test
    void titleSetterRejectsBlankTitleAndStatusCanBeChanged() {
        Task task = new Task(1L, "Read", null, 2L, null);
        assertThrows(IllegalArgumentException.class, () -> task.setTitle("\t"));

        task.setStatus(TaskStatus.DONE);
        assertEquals(TaskStatus.DONE, task.getStatus());
    }

    @Test
    void equalityIsBasedOnId() {
        assertEquals(new Task(1L, "A", null, null, null), new Task(1L, "B", null, null, null));
        assertNotEquals(new Task(1L, "A", null, null, null), new Task(2L, "A", null, null, null));
    }
}
