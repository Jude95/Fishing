package com.jude.fishing.config;

/**
 * Created by Mr.Jude on 2015/9/11.
 */
public class API {
    public static class URL{
        public static final String BASEURL = "http://123.56.225.103/api/index.php?s=/api/";
        public static final String UpdateLog = "https://raw.githubusercontent.com/Jude95/Fishing/master/update.txt";


    }

    public static class WRAPPER {
        public static final String STATUS = "status";
        public static final String INFO = "info";
        public static final String DATA = "data";
    }

    public static class CODE {
        public static final int SUCCEED = 0;
        public static final int PARAMS_ERROR = 1;
        public static final int SERVER_ERROR = 1001;
        public static final int LOGINSTATUS_DENIED = 1002;
        public static final int ACCOUNT_DENIED = 1002;
        public static final int PARAMS_INVALID = -1;
        public static final int PERMISSION_DENIED = -3;
    }
}
