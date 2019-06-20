package com.hottop.core;

public class BaseConstant {
<<<<<<< HEAD
    public static class Common {
        public static final String COMMA = ",";

        public static final String FILTER_OPERATION_SPLITTER = "_";

        public static final String FILTER_SPECIFICATION_AND_SPLITTER = ";";
        public static final String FILTER_SPECIFICATION_OR_SPLITTER = ",";
=======
    public static class Core {

        public static final String NAMESPACE_BACKSTAGE = "backstage";
        public static final String NAMESPACE_API = "api";

    }

    public static class Common {
        public static final String DEFAULT_ENCODE = "UTF-8";

        public static final String COMMA = ",";

        public static final String COMMON_SPLITTER = "_";
        public static final String FILTER_OPERATION_SPLITTER = "_";

        public static final String FILTER_SPECIFICATION_AND_SPLITTER = ",";
        public static final String FILTER_SPECIFICATION_OR_SPLITTER = ";";
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
        public static final String FILTER_SPECIFICATION_VALUE_SPLITTER = "'";

        public static final String SKU_KEY_SPLITTER = "_";
    }

    public static class Request {
        public static class Header {
            public static final String HEADER_BASE_AGENT = "b_agent";

            public static final String SPLITTER = "&";

            public static final String APP_VERSION = "av";
            public static final String SYSTEM_VERSION = "sv";
            public static final String DEVICE_MODEL = "dm";
            public static final String DEVICE_ID = "di";
            public static final String NETWORK_TYPE = "nt";
            public static final String RESOLUTION_RATIO = "rr";
            public static final String ADAPTER_WIDTH = "aw";
            public static final String LATITUDE_LONGITUDE = "ll";
            public static final String USER_ID = "ui";
        }

        public static class Argument {
<<<<<<< HEAD
            public static final String FLAG = "flag";
            public static final String SORT = "sort";
=======
            public static final String COMPONENT_TYPE = "ct";
            public static final String ACTION_TYPE = "at";

            public static final String FLAG = "flag";
            public static final String SORT = "sort";
            public static final String FIELDS = "fields";
            public static final String FILTER = "filter";
            public static final String VIEW = "view";
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
        }
    }

    public static class Response {
        public static final Integer PAGE_SIZE = 20;

    }
}
