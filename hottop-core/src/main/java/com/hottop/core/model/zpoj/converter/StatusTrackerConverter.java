package com.hottop.core.model.zpoj.converter;

import com.google.gson.reflect.TypeToken;
import com.hottop.core.feature.status.StatusFactory;
import com.hottop.core.feature.status.StatusStatusTracker;

import javax.persistence.AttributeConverter;
import java.lang.reflect.Type;

public class StatusTrackerConverter implements AttributeConverter<StatusStatusTracker, String> {

    @Override
    public String convertToDatabaseColumn(StatusStatusTracker statusStatusTracker) {
        if (statusStatusTracker == null) {
            return null;
        }
        return statusStatusTracker.status().status();
    }

    @Override
    public StatusStatusTracker convertToEntityAttribute(String s) {
        try {
            return StatusFactory.tracker(s);
        } catch (Exception ex) {
            return StatusFactory.dummyTracker;
        }
    }
}
