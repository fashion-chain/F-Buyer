package com.hottop.core.feature.status;

import com.hottop.core.feature.status.exception.StatusNotFoundException;
import com.hottop.core.feature.status.exception.UnsupportedStatusEventException;

import java.util.HashMap;
import java.util.Set;

public class StatusStatusTracker implements IStatusTracker<Class<?>, Status> {

    private ClassStatusStatusFlow statusFlow;
    private HashMap<Enum, StatusStatusTracker> statusEventMapper;
    private Status status;

    public StatusStatusTracker(ClassStatusStatusFlow statusFlow, Status status) {
        this.statusFlow = statusFlow;
        this.statusEventMapper = new HashMap<>();
        this.status = status;
    }

    @Override
    public Class<?> entity() {
        return this.statusFlow.entity();
    }

    @Override
    public IStatusTracker<Class<?>, Status> registerEvent(Enum event, Status status) throws StatusNotFoundException {
        this.statusEventMapper.put(event, statusFlow.getTrackerByStatus(status.status()));
        return this;
    }

    @Override
    public StatusStatusTracker handle(Enum event) throws UnsupportedStatusEventException {
        if (statusEventMapper.containsKey(event)) {
            return statusEventMapper.get(event);
        }
        throw new UnsupportedStatusEventException(String.format("class: %s, status: %s, event: %s unsupported.", entity().getSimpleName(), status, event));
    }

    @Override
    public Status status() {
        return this.status;
    }

    @Override
    public Set<Enum> availableEvents() {
        return this.statusEventMapper.keySet();
    }

    public HashMap<Enum, StatusStatusTracker> getStatusEventMapper() {
        return this.statusEventMapper;
    }
}
