package org.example.repository;

import org.example.model.StudySession;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryStudySessionImpl implements StudySessionRepository{

    private final Map<Long,StudySession> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    @Override
    public StudySession save(StudySession studySession) {
        Long newId = idGenerator.incrementAndGet();
        studySession.setId(newId);
        storage.put(newId,studySession);
        return studySession;
    }

    @Override
    public Optional<StudySession> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<StudySession> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void update(StudySession studySession) {
        storage.put(studySession.getId(),studySession);
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id);
    }
}
