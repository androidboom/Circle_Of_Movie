package com.example.liubo.world_of_movie.Login;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Liubo on 2018/3/5.
 */

public interface GetRequest_Interface {

    /* 注解里传入 网络请求 的部分URL地址
     * Retrofit把网络请求的URL分成了两部分：一部分放在Retrofit对象里，另一部分放在网络请求接口里
     * 如果接口里的url是一个完整的网址，那么放在Retrofit对象里的URL可以忽略
     * getCall()是接受网络请求数据的方法*/

    @GET("login")
    Call<String> getString(@Query("userid") String userid, @Query("password") String password);

//    @GET("signUser")
//    Call<String> getString(@Query("userid") String userid, @Query("password") String password,@Query("username") String username);

    @POST("signUser")
    @FormUrlEncoded
    Call<String>getString(@Field("userid") String userid, @Field("password") String password,@Field("username") String username,@Field("psw_answer")String psw_answer);

    @GET("signIMUser")
    //@FormUrlEncoded
    Call<String>getString(@Query("userid") String userid, @Query("IM_userid") String IM_userid,@Query("IM_userpw") String IM_userpw);

//    @POST("alterPSW")
//    @FormUrlEncoded
//    Call<String> getString(@Field("userid") String userid, @Field("password") String password,@Field("username") String username);


}
