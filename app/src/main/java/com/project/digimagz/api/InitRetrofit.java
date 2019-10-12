package com.project.digimagz.api;

import android.util.Log;

import com.google.gson.JsonObject;
import com.project.digimagz.Constant;
import com.project.digimagz.model.CommentModel;
import com.project.digimagz.model.DefaultStructureComment;
import com.project.digimagz.model.DefaultStructureLike;
import com.project.digimagz.model.DefaultStructureNews;
import com.project.digimagz.model.DefaultStructureNewsCoverStory;
import com.project.digimagz.model.DefaultStructureStory;
import com.project.digimagz.model.DefaultStructureUser;
import com.project.digimagz.model.LikeModel;
import com.project.digimagz.model.NewsCoverStoryModel;
import com.project.digimagz.model.NewsModel;
import com.project.digimagz.model.StoryModel;
import com.project.digimagz.model.UserModel;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class InitRetrofit {

    //API
    private Retrofit retrofit;
    private ApiInterface apiInterface;

    //Interface
    private OnRetrofitSuccess onRetrofitSuccess;

    public InitRetrofit() {
        retrofit = ApiClient.getRetrofit();
        apiInterface = retrofit.create(ApiInterface.class);
    }

    public void getNewsFromApi() {
        apiInterface.getNews().enqueue(new Callback<DefaultStructureNews>() {
            @Override
            public void onResponse(Call<DefaultStructureNews> call, Response<DefaultStructureNews> response) {
                if(response.isSuccessful()) {
                    ArrayList<NewsModel> list = response.body().getData();
//                    if (!list.isEmpty()) {
//                        onRetrofitSuccess.onSuccessGetData(list);
//                    }else {
//                        Log.e("getNewsFromApi", "Empty List");
//                    }
                    onRetrofitSuccess.onSuccessGetData(list);
                    Log.e("getNewsFromApi", "Empty List");
                }
            }

            @Override
            public void onFailure(Call<DefaultStructureNews> call, Throwable t) {
                Log.e("getNewsFromApi", t.getMessage());
            }
        });
    }

    public void getNewsFromApiWithParams(String params) {
        apiInterface.getNewsSearch(params).enqueue(new Callback<DefaultStructureNews>() {
            @Override
            public void onResponse(Call<DefaultStructureNews> call, Response<DefaultStructureNews> response) {
                if(response.isSuccessful()) {
                    ArrayList<NewsModel> list = response.body().getData();
//                    if (!list.isEmpty()) {
//                        onRetrofitSuccess.onSuccessGetData(list);
//                    }else {
//                        Log.e("getNewsWithParams", "Empty List");
//                    }
                    onRetrofitSuccess.onSuccessGetData(list);
                    Log.e("getNewsWithParams", "Empty List");
                }
            }

            @Override
            public void onFailure(Call<DefaultStructureNews> call, Throwable t) {
                Log.e("getNewsFromApi", t.getMessage());
            }
        });
    }

    public void getNewsTrendingFromApi() {
        apiInterface.getNewsTrending("yes").enqueue(new Callback<DefaultStructureNews>() {
            @Override
            public void onResponse(Call<DefaultStructureNews> call, Response<DefaultStructureNews> response) {
                if(response.isSuccessful()) {
                    ArrayList<NewsModel> list = response.body().getData();
//                    if (!list.isEmpty()) {
//                        onRetrofitSuccess.onSuccessGetData(list);
//                    }else {
//                        Log.e("getNewsWithParams", "Empty List");
//                    }
                    onRetrofitSuccess.onSuccessGetData(list);
                    Log.e("getNewsTrendingFromApi", "Empty List");
                }
            }

            @Override
            public void onFailure(Call<DefaultStructureNews> call, Throwable t) {
                Log.e("getNewsTrendingFromApi", t.getMessage());
            }
        });
    }

    public void getNewsRelatedFromApi(String idNews) {
        apiInterface.getNewsRelated(idNews).enqueue(new Callback<DefaultStructureNews>() {
            @Override
            public void onResponse(Call<DefaultStructureNews> call, Response<DefaultStructureNews> response) {
                ArrayList<NewsModel> list = response.body().getData();
                onRetrofitSuccess.onSuccessGetData(list);
                Log.e("getNewsRelatedFromApi", "Empty List");
            }

            @Override
            public void onFailure(Call<DefaultStructureNews> call, Throwable t) {

            }
        });
    }

    public void getCommentFromApi(String idNews) {
        apiInterface.getComment(idNews).enqueue(new Callback<DefaultStructureComment>() {
            @Override
            public void onResponse(Call<DefaultStructureComment> call, Response<DefaultStructureComment> response) {
                ArrayList<CommentModel> list = response.body().getData();
//                if (!list.isEmpty()) {
//                    onRetrofitSuccess.onSuccessGetData(list);
//                } else {
//                    Log.e("getCommentFromApi", "Empty List");
//                }
                onRetrofitSuccess.onSuccessGetData(list);
                Log.e("getCommentFromApi", "Empty List");
            }

            @Override
            public void onFailure(Call<DefaultStructureComment> call, Throwable t) {
                Log.e("getCommentFromApi", t.getMessage());
            }
        });
    }

    public void postCommentToApi(String idNews, String email, String comment) {
        JsonObject object = new JsonObject();
        object.addProperty("id_news", idNews);
        object.addProperty("email", email);
        object.addProperty("comments", comment);

        apiInterface.postComment(object).enqueue(new Callback<CommentModel>() {
            @Override
            public void onResponse(Call<CommentModel> call, Response<CommentModel> response) {
                Log.e("postCommentToApi", "Success");
            }

            @Override
            public void onFailure(Call<CommentModel> call, Throwable t) {
                Log.e("postCommentToApi", t.getMessage());
            }
        });
    }

    public void postLikeToApi(String idNews, String email) {
        JsonObject object = new JsonObject();
        object.addProperty("id_news", idNews);
        object.addProperty("email", email);

        apiInterface.postLike(object).enqueue(new Callback<LikeModel>() {
            @Override
            public void onResponse(Call<LikeModel> call, Response<LikeModel> response) {
                Log.e("postLikeToApi", "Success");
            }

            @Override
            public void onFailure(Call<LikeModel> call, Throwable t) {
                Log.e("postLikeToApi", t.getMessage());
            }
        });
    }

    public void deleteLikeFromApi(String idNews, String email) {
        apiInterface.deleteLike(idNews, email).enqueue(new Callback<LikeModel>() {
            @Override
            public void onResponse(Call<LikeModel> call, Response<LikeModel> response) {
                Log.e("deleteLikeFromApi", "Success");
            }

            @Override
            public void onFailure(Call<LikeModel> call, Throwable t) {
                Log.e("deleteLikeFromApi", t.getMessage());
            }
        });
    }

    public void getStoryFromApi() {
        apiInterface.getStory().enqueue(new Callback<DefaultStructureStory>() {
            @Override
            public void onResponse(Call<DefaultStructureStory> call, Response<DefaultStructureStory> response) {
                ArrayList<StoryModel> list = response.body().getData();
                onRetrofitSuccess.onSuccessGetData(list);
            }

            @Override
            public void onFailure(Call<DefaultStructureStory> call, Throwable t) {
                Log.e("getStoryFromApi", t.getMessage());
            }
        });
    }

    public void getNewsCoverStoryFromApi(String id) {
        apiInterface.getNewsCoverStory(id).enqueue(new Callback<DefaultStructureNewsCoverStory>() {
            @Override
            public void onResponse(Call<DefaultStructureNewsCoverStory> call, Response<DefaultStructureNewsCoverStory> response) {
                ArrayList<NewsCoverStoryModel> list = response.body().getData();
                onRetrofitSuccess.onSuccessGetData(list);
            }

            @Override
            public void onFailure(Call<DefaultStructureNewsCoverStory> call, Throwable t) {

            }
        });
    }

    public void getSliderFromApi() {
        apiInterface.getSlider().enqueue(new Callback<DefaultStructureNews>() {
            @Override
            public void onResponse(Call<DefaultStructureNews> call, Response<DefaultStructureNews> response) {
                ArrayList<NewsModel> list = response.body().getData();
                onRetrofitSuccess.onSuccessGetData(list);
            }

            @Override
            public void onFailure(Call<DefaultStructureNews> call, Throwable t) {
                Log.e("getSliderFromApi", t.getMessage());
            }
        });
    }

    public void getUserFromApi(String email) {
        apiInterface.getUser(email).enqueue(new Callback<DefaultStructureUser>() {
            @Override
            public void onResponse(Call<DefaultStructureUser> call, Response<DefaultStructureUser> response) {
                ArrayList<UserModel> list = response.body().getData();
                onRetrofitSuccess.onSuccessGetData(list);
            }

            @Override
            public void onFailure(Call<DefaultStructureUser> call, Throwable t) {
                Log.e("getUserFromApi", t.getMessage());
            }
        });
    }

    public void postUserToApi(String email, String name, String urlPic) {
        JsonObject object = new JsonObject();
        object.addProperty("email", email);
        object.addProperty("name", name);
        object.addProperty("pic_url", urlPic);

        apiInterface.postUser(object).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                Log.e("postUserToApi", "Success");
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.e("postUserToApi", t.getMessage());
            }
        });
    }

    public void putUserToApi(String email, String name, String pic_url) {
        apiInterface.putUser(email, name, pic_url).enqueue(new Callback<LikeModel>() {
            @Override
            public void onResponse(Call<LikeModel> call, Response<LikeModel> response) {
                Log.e("putUserToApi", "Success");
            }

            @Override
            public void onFailure(Call<LikeModel> call, Throwable t) {
                Log.e("putUserToApi", t.getMessage());
            }
        });
    }

    public void getLikeFromApi(String idNews, String email) {
        apiInterface.getLikes(idNews, email).enqueue(new Callback<DefaultStructureLike>() {
            @Override
            public void onResponse(Call<DefaultStructureLike> call, Response<DefaultStructureLike> response) {
                ArrayList<String> list = new ArrayList<String>();
                list.add(response.body().getData());
                onRetrofitSuccess.onSuccessGetData(list);
            }

            @Override
            public void onFailure(Call<DefaultStructureLike> call, Throwable t) {
                Log.e("getLikeFromApi", t.getMessage());
            }
        });
    }

    //Interface
    public interface OnRetrofitSuccess {
        void onSuccessGetData(ArrayList arrayList);
    }

    public void setOnRetrofitSuccess(OnRetrofitSuccess onRetrofitSuccess) {
        this.onRetrofitSuccess = onRetrofitSuccess;
    }
}
