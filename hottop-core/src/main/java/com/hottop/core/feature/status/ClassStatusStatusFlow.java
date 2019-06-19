package com.hottop.core.feature.status;

import com.hottop.core.feature.status.exception.StatusNotFoundException;

import java.util.*;

public class ClassStatusStatusFlow implements IStatusFlow<Class<?>, Status> {
    private Class<?> clazz;
    private HashMap<String, StatusStatusTracker> statusTrackerMapper;

    public ClassStatusStatusFlow(Class<?> clazz) {
        this.clazz = clazz;
        this.statusTrackerMapper = new HashMap<>();
    }

    @Override
    public Class<?> entity() {
        return clazz;
    }

    @Override
    public ClassStatusStatusFlow registerStatuses(Status... statuses) {
        for (Status status: statuses) {
            statusTrackerMapper.put(status.status(), new StatusStatusTracker(this, status));
        }
        return this;
    }

    @Override
    public StatusStatusTracker getTrackerByStatus(String status) throws StatusNotFoundException {
        if (statusTrackerMapper.containsKey(status)) {
            return statusTrackerMapper.get(status);
        }
        throw new StatusNotFoundException(String.format("status: %s not found", status));
    }

    public List<StatusStatusTracker> getTrackers() {
        return new ArrayList<StatusStatusTracker>(statusTrackerMapper.values());
    }

    @Override
    public Set<Enum> availableEvents(Status status) throws StatusNotFoundException {
        return getTrackerByStatus(status.status()).availableEvents();
    }

    @Override
    public IStatusTracker<Class<?>, Status> newTracker(Status status) {
        return new StatusStatusTracker(this, status);
    }
}
