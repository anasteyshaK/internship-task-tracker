package org.example.model;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class StudySessionTest {
    private final LocalDateTime start = LocalDateTime.of(2026, 9, 1, 9, 0);

    @Test
    void requiresTaskAndStartAndEndMustFollowStart() {
        assertThrows(IllegalArgumentException.class, () -> new StudySession(null, null, start, null));
        assertThrows(IllegalArgumentException.class, () -> new StudySession(null, 1L, null, null));
        assertThrows(IllegalArgumentException.class, () -> new StudySession(null, 1L, start, start));
        assertThrows(IllegalArgumentException.class, () -> new StudySession(null, 1L, start, start.minusMinutes(1)));
    }

    @Test
    void openSessionHasNoDurationAndFinishedSessionReportsDuration() {
        StudySession session = new StudySession(1L, 7L, start, null);
        assertTrue(session.getDuration().isEmpty());

        session.setEndTime(start.plusMinutes(45));
        assertEquals(Duration.ofMinutes(45), session.getDuration().orElseThrow());
    }

    @Test
    void rejectsNullTaskIdWhenUpdated() {
        StudySession session = new StudySession(1L, 7L, start, null);
        assertThrows(IllegalArgumentException.class, () -> session.setTaskId(null));
    }
}
