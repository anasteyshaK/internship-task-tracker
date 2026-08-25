package org.example.repository;

import org.example.model.StudySession;

import java.util.List;
import java.util.Optional;

public interface StudySessionRepository {
    StudySession save(StudySession studySession);
    Optional<StudySession> findById(Long id);
    List<StudySession> findAll();
    void update(StudySession studySession);
    void deleteById(Long id);
}
