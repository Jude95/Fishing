package com.jude.fishing.config;


import com.jude.fishing.model.entities.Account;
import com.jude.fishing.model.entities.Article;
import com.jude.fishing.model.entities.Contact;
import com.jude.fishing.model.entities.Date;
import com.jude.fishing.model.entities.Evaluate;
import com.jude.fishing.model.entities.EvaluateDetail;
import com.jude.fishing.model.entities.Notification;
import com.jude.fishing.model.entities.PersonAround;
import com.jude.fishing.model.entities.PersonAvatar;
import com.jude.fishing.model.entities.PersonBrief;
import com.jude.fishing.model.entities.PersonDetail;
import com.jude.fishing.model.entities.PlaceBrief;
import com.jude.fishing.model.entities.PlaceDetail;
import com.jude.fishing.model.entities.PushSetting;
import com.jude.fishing.model.entities.Seed;
import com.jude.fishing.model.entities.SeedDetail;
import com.jude.fishing.model.entities.Token;
import com.jude.fishing.model.entities.UpdateInfo;

import java.util.List;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by Mr.Jude on 2015/8/23.
 * 服务器接口
 */
public interface Service {
     String BASE_URL = "http://114.215.86.90/index.php";
     String BASE_URL_DEBUG = "http://120.27.55.225/index.php";
     String SHARE = BASE_URL + "/common/share/";

    @GET("/common/qiniuToken")
    Observable<Token> getQiNiuToken();

    @GET("/common/version")
    Observable<UpdateInfo> getUpdateInfo();

    @FormUrlEncoded
    @POST("/place/getPlace")
    Observable<List<PlaceBrief>> syncPlace(
            @Field("time") String lastTime);

    @FormUrlEncoded
    @POST("/place/add")
    Observable<Object> publishPlace(
            @Field("id") int id,
            @Field("name") String name,
            @Field("preview") String preview,
            @Field("briefAddr") String briefAddr,
            @Field("address") String address,
            @Field("cost") int cost,
            @Field("costType") int costType,
            @Field("fishType") String fishType,
            @Field("poolType") int poolType,
            @Field("serviceType") String serviceType,
            @Field("tel") String tel,
            @Field("content") String content,
            @Field("picture") String picture,
            @Field("lat") double lat,
            @Field("lng") double lng,
            @Field("area") String area,
            @Field("deep") String deep,
            @Field("hole") int hole
            );

    @FormUrlEncoded
    @POST("/place/getItem")
    Observable<PlaceDetail> getPlaceDetail(@Field("id") int id);

    @FormUrlEncoded
    @POST("/place/scoreList")
    Observable<List<Evaluate>> getEvaluate(
            @Field("id") int id,
            @Field("page") int page);

    @FormUrlEncoded
    @POST("/place/scoreList")
    Observable<EvaluateDetail> getEvaluateDetail(
            @Field("id") int id);

    @FormUrlEncoded
    @POST("/place/scoreList")
    Observable<Object> publishEvaluate(
            @Field("pid") int pid,
            @Field("content") String content,
            @Field("images") String images,
            @Field("score") int score);

    @FormUrlEncoded
    @POST("/place/collect")
    Observable<Object> collectPlace(
            @Field("id") int id);


    @FormUrlEncoded
    @POST("/place/unCollect")
    Observable<Object> unCollectPlace(
            @Field("id") int id);

    @GET("/place/myCollect")
    Observable<List<PlaceBrief>> myPlaceCollect();

    @GET("/place/myPlace")
    Observable<List<PlaceBrief>> myPlace();


    @GET("/place/myScoreList")
    Observable<List<Evaluate>> myEvaluate();

    @GET("/user/getmyinfo")
    Observable<Account> updateMyInfo();

    @FormUrlEncoded
    @POST("/place/comment")
    Observable<Object> EvaluateComment(
            @Field("sid") int sid,
            @Field("fid") int fid,
            @Field("content") String content);

    @FormUrlEncoded
    @POST("/user/register")
    Observable<Object> register(@Field("tel") String tel,
                                @Field("password") String password,
                                @Field("code") String code);


    @FormUrlEncoded
    @POST("/user/updateAddr")
    Observable<Object> UpdateLocation(
            @Field("lat") double lat,
            @Field("lng") double lng,
            @Field("location") String address);

    @FormUrlEncoded
    @POST("/user/getNearby")
    Observable<List<PersonAround>> GetNearBy(
            @Field("lat") double lat,
            @Field("lng") double lng,
            @Field("page") int page
            );

    @FormUrlEncoded
    @POST("/user/findUser")
    Observable<List<PersonBrief>> FindUser(
            @Field("key") String key
    );

    @FormUrlEncoded
    @POST("/user/getBriefinfo")
    PersonAvatar GetUserAvatar(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("/user/login")
    Observable<Account> login(@Field("tel") String tel, @Field("password") String password);

    @FormUrlEncoded
    @POST("/user/getNotify")
    Observable<List<Notification>> getNotification(
            @Field("page") int page
    );


    /**
     * 修改用户数据
     * @param avatar
     * @param name
     * @param gender  0保密 1男 2女 //TODO 接口统一
     * @param address
     * @param age     钓龄
     * @param skill   擅长
     * @param sign    签名
     */
    @FormUrlEncoded
    @POST("/user/modInfo")
    Observable<Object> modInfo(@Field("avatar") String avatar,
                               @Field("name") String name,
                               @Field("gender") int gender,
                               @Field("address") String address,
                               @Field("age") String age,
                               @Field("skill") String skill,
                               @Field("sign") String sign);

    @FormUrlEncoded
    @POST("/user/modPass")
    Observable<Object> modPass(@Field("oldpwd") String oldpwd, @Field("newpwd") String newpwd);

    /**
     * 检查手机是否可用 201为已注册
     * @param tel
     * @return
     */
    @FormUrlEncoded
    @POST("/user/checkTel")
    Observable<Object> checkTel(@Field("tel") String tel);

    /**
     * 绑定手机
     * @param tel
     * @param code
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("/user/bindTel")
    Observable<Object> bindTel(@Field("tel")String tel,
                               @Field("code")String code,
                               @Field("password")String password);

    @FormUrlEncoded
    @POST("/user/resetPass")
    Observable<Object> resetPass(@Field("tel")String tel,
                                 @Field("code")String code,
                                 @Field("password")String password);

    @FormUrlEncoded
    @POST("/user/attend")
    Observable<Object> attend(@Field("id")int id);

    @FormUrlEncoded
    @POST("/user/unAttend")
    Observable<Object> unAttend(@Field("id")int id);

    @FormUrlEncoded
    @POST( "/user/myAttend")
    Observable<List<PersonBrief>> myAttend(@Field("id")int id);

    @FormUrlEncoded
    @POST("/user/myFans")
    Observable<List<PersonBrief>> myFans(@Field("id")int id);

    @FormUrlEncoded
    @POST("/user/getUserinfo")
    Observable<Account> getUserInfo(@Field("id")int id);

    @POST("/user/getmyinfo")
    Observable<PersonDetail> getMyInfo();

    /**
     * 获取广场微博
     * @param page 页码
     * @return
     */
    @FormUrlEncoded
    @POST("/weibo/getListGround")
    Observable<List<Seed>> getBlogGround(@Field("page")int page);

    @FormUrlEncoded
    @POST("/weibo/getListFriend")
    Observable<List<Seed>> getBlogFriend(@Field("page")int page);

    @FormUrlEncoded
    @POST("/weibo/getListNear")
    Observable<List<Seed>> getBlogNearby(@Field("page")int page,
                                          @Field("count")int count,
                                          @Field("lat") double lat,
                                          @Field("lng") double lng);

    @FormUrlEncoded
    @POST("/weibo/getList")
    Observable<List<Seed>> getUserBlog(@Field("id")int id,@Field("page")int page);

    @FormUrlEncoded
    @POST("/weibo/add")
    Observable<Object> addBlog(@Field("content")String content,
                                @Field("images")String images,
                                @Field("address")String address,
                                @Field("lng")double lng,
                                @Field("lat")double lat);

    @FormUrlEncoded
    @POST("/weibo/del")
    Observable<List<Seed>> deleteBlog(@Field("id")int id);

    @FormUrlEncoded
    @POST("/common/reportWeibo")
    Observable<List<Seed>> reportBlog(
            @Field("id")int id,
            @Field("content")String content);

    @FormUrlEncoded
    @POST("/weibo/getItem")
    Observable<SeedDetail> getBlogDetail(@Field("id")int id);

    @FormUrlEncoded
    @POST("/weibo/praise")
    Observable<Object> blogPraise(@Field("id")int id);

    @FormUrlEncoded
    @POST("/weibo/unPraise")
    Observable<Object> blogUnPraise(@Field("id")int id);

    /**
     * @param wid 微博id
     * @param fid 父id，如果是直接评论就是0
     * @param content 内容
     * @return
     */
    @FormUrlEncoded
    @POST("/weibo/comment")
    Observable<Object> blogComment(@Field("wid")int wid,@Field("fid")int fid,@Field("content")String content);

    @FormUrlEncoded
    @POST("/user/modBg")
    Observable<Object> changeUserBg(@Field("bg")String bg);

    @FormUrlEncoded
    @POST("/user/contact")
    Observable<List<Contact>> getContact(@Field("data")String data);

    @FormUrlEncoded
    @POST("/common/feedback")
    Observable<Object> feedback(@Field("content")String content);

    @FormUrlEncoded
    @POST("/yue/add")
    Observable<Object> addDateInfo(@Field("title")String title,
                                   @Field("address")String address,
                                   @Field("content")String content,
                                   @Field("acTime")long acTime);

    @FormUrlEncoded
    @POST("/yue/getList")
    Observable<List<Date>> getDateList(@Field("page")int page);

    @FormUrlEncoded
    @POST("/yue/item")
    Observable<Date> getDateItem(@Field("id")String id);

    @FormUrlEncoded
    @POST("/yue/item")
    Date getDateItemDirect(@Field("id")String id);

    @FormUrlEncoded
    @POST("/yue/enrollList")
    Observable<List<PersonBrief>> getDatePersonList(@Field("id")String id);

    @FormUrlEncoded
    @POST("/yue/enroll")
    Observable<Object> joinDate(@Field("id")String id);

    @GET("/user/refreshRong")
    Observable<Token> refreshRongYun();

    @GET("/yue/getMyList")
    Observable<List<Date>> getMyDateList();

    @GET("/user/clockIn")
    Observable<Object> signIn();

    @GET("/user/getPushset")
    Observable<PushSetting> getPushSetting();

    @FormUrlEncoded
    @POST("/user/pushSet")
    Observable<Object> uploadPushSetting(
            @Field("zan")int praise,
            @Field("comment")int comment,
            @Field("care")int care,
            @Field("place")int place);

    @FormUrlEncoded
    @POST("/article/getList")
    Observable<List<Article>> getArticles(
            @Field("type")int type,
            @Field("page")int page,
            @Field("count")int count);
}
