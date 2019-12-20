package com.project.digimagz.api;

import com.google.gson.JsonObject;
import com.project.digimagz.model.CommentModel;
import com.project.digimagz.model.DefaultStructureComment;
import com.project.digimagz.model.DefaultStructureEmagz;
import com.project.digimagz.model.DefaultStructureLike;
import com.project.digimagz.model.DefaultStructureNews;
import com.project.digimagz.model.DefaultStructureNewsCoverStory;
import com.project.digimagz.model.DefaultStructureStory;
import com.project.digimagz.model.DefaultStructureUser;
import com.project.digimagz.model.DefaultStructureVideo;
import com.project.digimagz.model.LikeModel;
import com.project.digimagz.model.UserModel;
import com.project.digimagz.model.ViewModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("dummy/index_get")
    Call<DefaultStructureNews> getNews();

    @GET("dummy/index_get")
    Call<DefaultStructureNews> getNewsSearch(@Query("q") String params);

    @GET("dummy/index_get")
    Call<DefaultStructureNews> getNewsTrending(@Query("trend") String params);

    @GET("related/index_get")
    Call<DefaultStructureNews> getNewsRelated(@Query("id") String idNews);

    @GET("slider/index_get")
    Call<DefaultStructureNews> getSlider();

    @GET("story/index_get")
    Call<DefaultStructureStory> getStory();

    @GET("emagz/index_get")
    Call<DefaultStructureEmagz> getEmagz();

    @GET("newscover/index_get")
    Call<DefaultStructureNewsCoverStory> getNewsCoverStory(@Query("id") String id);

    @GET("comments/index_get")
    Call<DefaultStructureComment> getComment(@Query("id_news") String idNews);

    @GET("user/index_get")
    Call<DefaultStructureUser> getUser(@Query("email") String email);

    @GET("likes/index_get")
    Call<DefaultStructureLike> getLikes(@Query("id_news") String idNews, @Query("email") String email);

    @GET("video/index_get")
    Call<DefaultStructureVideo> getVideo();

    @POST("comments/index_post")
    Call<CommentModel> postComment(@Body JsonObject jsonObject);

    @POST("likes/index_post")
    Call<LikeModel> postLike(@Body JsonObject jsonObject);

    @POST("user/index_post")
    Call<UserModel> postUser(@Body JsonObject jsonObject);

    @POST("dummy/click")
    Call<ViewModel> postView(@Body JsonObject jsonObject);

    @POST("dummy/share")
    Call<ViewModel> postShare(@Body JsonObject jsonObject);

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "likes/index_delete", hasBody = true)
    Call<LikeModel> deleteLike(@Field("id_news") String idNews, @Field("email") String email);

    @FormUrlEncoded
    @HTTP(method = "PUT", path = "user/index_put", hasBody = true)
    Call<DefaultStructureUser> putUserName(@Field("email") String email, @Field("name") String name);

    @FormUrlEncoded
    @HTTP(method = "PUT", path = "user/index_put", hasBody = true)
    Call<DefaultStructureUser> putUser(@Field("email") String email, @Field("name") String name, @Field("pic_url") String pic_url, @Field("date_birth") String date_birth, @Field("gender") String gender);

    @FormUrlEncoded
    @HTTP(method = "PUT", path = "user/index_put", hasBody = true)
    Call<DefaultStructureUser> putUserPhoto(@Field("email") String email, @Field("pic_url") String pic_url);

}
