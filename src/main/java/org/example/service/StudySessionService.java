package org.example.service;

import org.example.model.StudySession;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public interface StudySessionService {
    StudySession createSession(Long taskId, LocalDateTime startTime,LocalDateTime endTime);
    StudySession getSessionById(Long id);
    List<StudySession> getAllSessions();
    List<StudySession> getSessionsByTaskId(Long taskId);
    void deleteSession(Long id);
    StudySession finishSession(Long id,LocalDateTime endTime);
    Duration getTotalDurationByTaskId(Long taskId);

}
