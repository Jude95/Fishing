package com.jude.fishing.config;

/**
 * Created by Mr.Jude on 2015/9/11.
 */
public class API {
    public static class URL{
        public static final String BASE_URL = "http:small-fish.daoapp.io/index.php";
        public static final String QiNiuToken = "/common/qiniuToken";
        public static final String GetPlace = "/place/getPlace";
    }

    public static class WRAPPER {
        public static final String STATUS = "status";
        public static final String INFO = "info";
        public static final String DATA = "data";
    }

    public static class CODE {
        public static final int SUCCEED = 0;
        public static final int SERVER_ERROR = 1;
        public static final int PARAMS_ERROR = 2;
        public static final int PARAMS_INVALID = 3;
        public static final int USER_INVALID = 5;
        public static final int PERMISSION_DENIED = 6;
        public static final int ANALYSIS_ERROR = 8;
        public static final int NET_INVALID = 9;
    }
}
