package com.hottop.core.feature.status;

import com.hottop.core.BaseConstant;
import org.apache.commons.lang.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class StatusFactory {
    public static HashMap<Class<?>, ClassStatusStatusFlow> statusFlowMapper = new HashMap<>();
    public static HashMap<String, Class<?>> symbolMapper = new HashMap<>();

    public static StatusStatusTracker dummyTracker = new StatusStatusTracker(new ClassStatusStatusFlow(Object.class), new Status(""));

    public static IStatusFlow<Class<?>, Status> registerClazz(Class<?> clazz, String symbol) {
        statusFlowMapper.put(clazz, new ClassStatusStatusFlow(clazz));
        symbolMapper.put(symbol, clazz);
        return statusFlowMapper.get(clazz);
    }

    private static Class<?> getClassBySymbol(String statusString) throws Exception {
        String[] statusParts = StringUtils.split(statusString, BaseConstant.Common.COMMON_SPLITTER);
        return symbolMapper.get(statusParts[0]);
    }

    public static StatusStatusTracker tracker(String status) throws Exception {
        return statusFlowMapper.get(getClassBySymbol(status)).getTrackerByStatus(status);
    }

    public static StatusStatusTracker tracker(Class<?> clazz, Status status) throws Exception {
        return statusFlowMapper.get(clazz).getTrackerByStatus(status.status());
    }

    public static Set<Enum> availableEvents(Class<?> clazz, Status status) throws Exception {
        return statusFlowMapper.get(clazz).availableEvents(status);
    }

    public static Status newStatus(Class<?> clazz, String... parts) {
        String symbol = null;
        for (Map.Entry<String, Class<?>> entry: symbolMapper.entrySet()) {
            if (clazz == entry.getValue()) {
                symbol = entry.getKey();
            }
        }
        return new Status(symbol, parts);
    }

    public static Status getStatus(Class<?> clazz, String... parts) throws Exception {
        String status = newStatus(clazz, parts).status();
        return statusFlowMapper.get(clazz).getTrackerByStatus(status).status();
    }

    public static Map<String, StatusStatusTracker> getStatusMap(Class<?> clazz) {
        ClassStatusStatusFlow classStatusStatusFlow = statusFlowMapper.get(clazz);
        List<StatusStatusTracker> trackers = classStatusStatusFlow.getTrackers();
        HashMap<String, StatusStatusTracker> result = new HashMap<>();
        for(StatusStatusTracker t : trackers) {
            result.put(t.status().status(), t);
        }
        return result;
    }
}
