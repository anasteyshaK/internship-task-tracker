package org.example.service;

import org.example.exception.StudySessionNotFoundException;
import org.example.exception.TaskNotFoundException;
import org.example.model.StudySession;
import org.example.repository.StudySessionRepository;
import org.example.repository.TaskRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class StudySessionServiceImpl implements StudySessionService {

    private final StudySessionRepository repository;
    private final TaskRepository taskRepository;

    public StudySessionServiceImpl(StudySessionRepository repository, TaskRepository taskRepository) {
        this.repository = repository;
        this.taskRepository = taskRepository;
    }

    @Override
    public StudySession createSession(Long taskId, LocalDateTime startTime, LocalDateTime endTime) {
        taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task not found: id=" + taskId));
        StudySession session = new StudySession(null,taskId,startTime,endTime);
        return repository.save(session);
    }

    @Override
    public StudySession getSessionById(Long id) {
        return repository.findById(id).orElseThrow(()-> new StudySessionNotFoundException("Session is not found: id="+ id));
    }

    @Override
    public List<StudySession> getAllSessions() {
        return repository.findAll();
    }

    @Override
    public List<StudySession> getSessionsByTaskId(Long taskId) {
        return repository.findAll().stream().filter(session -> session.getTaskId().equals(taskId)).toList();
    }

    @Override
    public void deleteSession(Long id) {
        getSessionById(id);
        repository.deleteById(id);
    }

    @Override
    public StudySession finishSession(Long id, LocalDateTime endTime) {
        StudySession session = getSessionById(id);
        session.setEndTime(endTime);
        repository.update(session);
        return session;


    }

    @Override
    public Duration getTotalDurationByTaskId(Long taskId) {
        return getSessionsByTaskId(taskId).stream().map(StudySession::getDuration).filter(Optional::isPresent).map(Optional::get).reduce(Duration.ZERO,Duration::plus);
    }
}
