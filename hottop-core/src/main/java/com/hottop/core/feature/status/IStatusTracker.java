package com.hottop.core.feature.status;


import com.hottop.core.feature.status.exception.StatusNotFoundException;
import com.hottop.core.feature.status.exception.UnsupportedStatusEventException;

import java.util.Set;

public interface IStatusTracker<T, Y> {
    T entity();

    Y status();

    Set<Enum> availableEvents();

    IStatusTracker<T, Y> registerEvent(Enum event, Status status) throws StatusNotFoundException;

    IStatusTracker<T, Y> handle(Enum event) throws UnsupportedStatusEventException;

}
