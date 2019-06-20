package com.hottop.core.feature.status;

import com.hottop.core.feature.status.exception.StatusNotFoundException;

import java.util.Set;

public interface IStatusFlow<T, Y> {
    T entity();

    IStatusTracker<T, Y> newTracker(Y status);

    Set<Enum> availableEvents(Y status) throws StatusNotFoundException;

    IStatusFlow<T, Y> registerStatuses(Y... status);

    IStatusTracker<T, Y> getTrackerByStatus(String status) throws StatusNotFoundException;
}
