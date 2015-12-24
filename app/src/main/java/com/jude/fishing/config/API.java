package com.jude.fishing.config;

/**
 * Created by Mr.Jude on 2015/9/11.
 */
public class API {

    public static class WRAPPER {
        public static final String STATUS = "status";
        public static final String INFO = "info";
        public static final String DATA = "data";
    }

    public static class CODE {
        public static final int SUCCEED = 0;

        public static final int ANALYSIS_ERROR = -1;
        public static final int NET_INVALID = -2;


        public static final int SMS_ERROR = 1;
        public static final int RONG_ERROR = 2;
        public static final int QINIU_ERROR = 3;
        public static final int PARAMS_ERROR = 9;
        public static final int PARAMS_INVALID = 10;

        public static final int SERVER_ERROR = 100;

        public static final int LOGIN_INVALID= 400;
        public static final int PERMISSION_DENIED = 401;

        public static final int USER_INVALID = 1001;

    }
}
