package com.jude.fishing.config;

/**
 * Created by Mr.Jude on 2015/9/11.
 */
public class API {
    public static class URL{
        public static final String BASE_URL = "http://120.27.55.225/index.php";
        public static final String QiNiuToken = "/common/qiniuToken";
        public static final String GetPlace = "/place/getPlace";
        public static final String AddPlace = "/place/add";
        public static final String GetPlaceDetail = "/place/getItem";
        public static final String GetEvaluate = "/place/scoreList";
        public static final String GetEvaluateDetail = "/place/scoreDetail";
        public static final String PublishEvaluate = "/place/score";
        public static final String CollectPlace = "/place/collect";
        public static final String UnCollectPlace = "/place/unCollect";
        public static final String MyColectPlace = "/place/myCollect";
        public static final String MyEvaluate = "/place/myCollect";
        public static final String EvaluateComment = "/place/comment";
        public static final String FindUser = "/user/findUser";

        public static final String CheckTel = "/user/checkTel";
        public static final String Register = "/user/register";
        public static final String Login = "/user/login";
        public static final String ModInfo = "/user/modInfo";
        public static final String ModPass = "/user/modPass";
        public static final String BindTel = "/user/bindTel";
        public static final String ResetPass = "/user/resetPass";
        public static final String GetUserInfo = "/user/getUserinfo";
        public static final String GetMyInfo = "/user/getmyinfo";
        public static final String UpdateLocation = "/user/updateAddr";
        public static final String GetNearBy = "/user/getNearby";
        public static final String ModBg = "/user/modBg";
        public static final String UpdateMyInfo = "/user/getmyinfo";


        public static final String Attend = "/user/attend";
        public static final String UnAttend = "/user/unAttend";

        public static final String MyAttend = "/user/myAttend";
        public static final String MyFans = "/user/myFans";

        public static final String GetBlogGround = "/weibo/getListGround";
        public static final String GetBlogFriend = "/weibo/getListFriend";
        public static final String GetBlogNearby = "/weibo/getListNear";
        public static final String GetUserBlog = "/weibo/getList";
        public static final String AddBlog = "/weibo/add";
        public static final String GetBlogDetail = "/weibo/getItem";

        public static final String BlogPraise = "/weibo/praise";
        public static final String BlogUnPraise = "/weibo/unPraise";
        public static final String BlogComment = "/weibo/comment";
    }

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
