package com.project.digimagz.api;

import android.util.Log;

import com.google.gson.JsonObject;
import com.project.digimagz.model.CommentModel;
import com.project.digimagz.model.DefaultStructureComment;
import com.project.digimagz.model.DefaultStructureEmagz;
import com.project.digimagz.model.DefaultStructureLike;
import com.project.digimagz.model.DefaultStructureNews;
import com.project.digimagz.model.DefaultStructureNewsCoverStory;
import com.project.digimagz.model.DefaultStructureNewsSearch;
import com.project.digimagz.model.DefaultStructureStory;
import com.project.digimagz.model.DefaultStructureUser;
import com.project.digimagz.model.DefaultStructureVideo;
import com.project.digimagz.model.EmagzModel;
import com.project.digimagz.model.LikeModel;
import com.project.digimagz.model.NewsCoverStoryModel;
import com.project.digimagz.model.NewsModel;
import com.project.digimagz.model.StoryModel;
import com.project.digimagz.model.UserModel;
import com.project.digimagz.model.VideoModel;
import com.project.digimagz.model.ViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class InitRetrofit {

    private ApiInterface apiInterface;

    //Interface
    private OnRetrofitSuccess onRetrofitSuccess;

    public InitRetrofit() {
        //API
        Retrofit retrofit = ApiClient.getRetrofit();
        apiInterface = retrofit.create(ApiInterface.class);
    }

    public void getNewsFromApi() {
        apiInterface.getNews().enqueue(new Callback<DefaultStructureNews>() {
            @Override
            public void onResponse(@NotNull Call<DefaultStructureNews> call, @NotNull Response<DefaultStructureNews> response) {
                ArrayList<NewsModel> list = new ArrayList<>();
                if(response.code() == 200) {
                    assert response.body() != null;
                    list.addAll(response.body().getData());
                    Log.e("getNewsFromApi", String.valueOf(list.size()));
                }
                onRetrofitSuccess.onSuccessGetData(list);
            }

            @Override
            public void onFailure(@NotNull Call<DefaultStructureNews> call, @NotNull Throwable t) {
                Log.e("getNewsFromApi", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void getNewsFromApiWithParams(String params) {
        apiInterface.getNewsSearch(params).enqueue(new Callback<DefaultStructureNews>() {
            @Override
            public void onResponse(@NotNull Call<DefaultStructureNews> call, @NotNull Response<DefaultStructureNews> response) {
                ArrayList<NewsModel> list = new ArrayList<>();
                if(response.code() == 200) {
                    assert response.body() != null;
                    list.addAll(response.body().getData());
                    Log.e("getNewsWithParams", String.valueOf(list.size()));
                }
                onRetrofitSuccess.onSuccessGetData(list);
            }

            @Override
            public void onFailure(@NotNull Call<DefaultStructureNews> call, @NotNull Throwable t) {
                Log.e("getNewsFromApi", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void getNewsFromApiWithSearch(String idNews) {
        apiInterface.getNewsParam(idNews).enqueue(new Callback<DefaultStructureNewsSearch>() {
            @Override
            public void onResponse(Call<DefaultStructureNewsSearch> call, Response<DefaultStructureNewsSearch> response) {
                ArrayList<NewsModel> list = new ArrayList<>();
                if (response.code() == 200) {
                    assert  response.body() != null;
                    list.add(response.body().getData());
                    Log.e("getNewsFromApiSearch", String.valueOf(list.size()));
                }
                onRetrofitSuccess.onSuccessGetData(list);
            }

            @Override
            public void onFailure(Call<DefaultStructureNewsSearch> call, Throwable t) {
                Log.e("getNewsFromApiSearch", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void getNewsTrendingFromApi() {
        apiInterface.getNewsTrending("yes").enqueue(new Callback<DefaultStructureNews>() {
            @Override
            public void onResponse(@NotNull Call<DefaultStructureNews> call, @NotNull Response<DefaultStructureNews> response) {
                ArrayList<NewsModel> list = new ArrayList<>();
                if(response.code() == 200) {
                    assert response.body() != null;
                    list.addAll(response.body().getData());
                    Log.e("getNewsTrendingFromApi", String.valueOf(list.size()));
                }
                onRetrofitSuccess.onSuccessGetData(list);
            }

            @Override
            public void onFailure(@NotNull Call<DefaultStructureNews> call, @NotNull Throwable t) {
                Log.e("getNewsTrendingFromApi", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void getNewsRelatedFromApi(String idNews) {
        apiInterface.getNewsRelated(idNews).enqueue(new Callback<DefaultStructureNews>() {
            @Override
            public void onResponse(@NotNull Call<DefaultStructureNews> call, @NotNull Response<DefaultStructureNews> response) {
                ArrayList<NewsModel> list = new ArrayList<>();
                if(response.code() == 200) {
                    assert response.body() != null;
                    list.addAll(response.body().getData());
                    Log.e("getNewsRelatedFromApi", String.valueOf(list.size()));
                }
                onRetrofitSuccess.onSuccessGetData(list);
            }

            @Override
            public void onFailure(@NotNull Call<DefaultStructureNews> call, @NotNull Throwable t) {

            }
        });
    }

    public void getCommentFromApi(String idNews) {
        apiInterface.getComment(idNews).enqueue(new Callback<DefaultStructureComment>() {
            @Override
            public void onResponse(@NotNull Call<DefaultStructureComment> call, @NotNull Response<DefaultStructureComment> response) {
                ArrayList<CommentModel> list = new ArrayList<>();
                if(response.code() == 200) {
                    assert response.body() != null;
                    list.addAll(response.body().getData());
                    Log.e("getCommentFromApi", String.valueOf(list.size()));
                }
                onRetrofitSuccess.onSuccessGetData(list);
            }

            @Override
            public void onFailure(@NotNull Call<DefaultStructureComment> call, @NotNull Throwable t) {
                Log.e("getCommentFromApi", Objects.requireNonNull(t.getMessage()));
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
            public void onResponse(@NotNull Call<CommentModel> call, @NotNull Response<CommentModel> response) {
                Log.e("postCommentToApi", "Success");
            }

            @Override
            public void onFailure(@NotNull Call<CommentModel> call, @NotNull Throwable t) {
                Log.e("postCommentToApi", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void postLikeToApi(String idNews, String email) {
        JsonObject object = new JsonObject();
        object.addProperty("id_news", idNews);
        object.addProperty("email", email);

        apiInterface.postLike(object).enqueue(new Callback<LikeModel>() {
            @Override
            public void onResponse(@NotNull Call<LikeModel> call, @NotNull Response<LikeModel> response) {
                Log.e("postLikeToApi", "Success");
            }

            @Override
            public void onFailure(@NotNull Call<LikeModel> call, @NotNull Throwable t) {
                Log.e("postLikeToApi", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void deleteLikeFromApi(String idNews, String email) {
        apiInterface.deleteLike(idNews, email).enqueue(new Callback<LikeModel>() {
            @Override
            public void onResponse(@NotNull Call<LikeModel> call, @NotNull Response<LikeModel> response) {
                Log.e("deleteLikeFromApi", "Success");
            }

            @Override
            public void onFailure(@NotNull Call<LikeModel> call, @NotNull Throwable t) {
                Log.e("deleteLikeFromApi", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void getStoryFromApi() {
        apiInterface.getStory().enqueue(new Callback<DefaultStructureStory>() {
            @Override
            public void onResponse(@NotNull Call<DefaultStructureStory> call, @NotNull Response<DefaultStructureStory> response) {
                ArrayList<StoryModel> list = new ArrayList<>();
                if(response.code() == 200) {
                    assert response.body() != null;
                    list.addAll(response.body().getData());
                    Log.e("getStoryFromApi", String.valueOf(list.size()));
                }
                onRetrofitSuccess.onSuccessGetData(list);
            }

            @Override
            public void onFailure(@NotNull Call<DefaultStructureStory> call, @NotNull Throwable t) {
                Log.e("getStoryFromApi", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void getNewsCoverStoryFromApi(String id) {
        apiInterface.getNewsCoverStory(id).enqueue(new Callback<DefaultStructureNewsCoverStory>() {
            @Override
            public void onResponse(@NotNull Call<DefaultStructureNewsCoverStory> call, @NotNull Response<DefaultStructureNewsCoverStory> response) {
                ArrayList<NewsCoverStoryModel> list = new ArrayList<>();
                if (response.code() == 200) {
                    assert response.body() != null;
                    list.addAll(response.body().getData());
                    Log.e("getNewsCoverStory", String.valueOf(list.size()));
                }
                onRetrofitSuccess.onSuccessGetData(list);
            }

            @Override
            public void onFailure(@NotNull Call<DefaultStructureNewsCoverStory> call, @NotNull Throwable t) {

            }
        });
    }

    public void getSliderFromApi() {
        apiInterface.getSlider().enqueue(new Callback<DefaultStructureNews>() {
            @Override
            public void onResponse(@NotNull Call<DefaultStructureNews> call, @NotNull Response<DefaultStructureNews> response) {
                ArrayList<NewsModel> list = new ArrayList<>();
                if (response.code() == 200) {
                    assert response.body() != null;
                    list.addAll(response.body().getData());
                    Log.e("getSliderFromApi", String.valueOf(list.size()));
                }
                onRetrofitSuccess.onSuccessGetData(list);
            }

            @Override
            public void onFailure(@NotNull Call<DefaultStructureNews> call, @NotNull Throwable t) {
                Log.e("getSliderFromApi", Objects.requireNonNull(t.getMessage()));
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
            public void onResponse(@NotNull Call<UserModel> call, @NotNull Response<UserModel> response) {
                Log.e("postUserToApi", "Success");
            }

            @Override
            public void onFailure(@NotNull Call<UserModel> call, @NotNull Throwable t) {
                Log.e("postUserToApi", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void putUserToApi(String email, String name, String urlPic, String dateBirth, String gender) {

        apiInterface.putUser(email, name, urlPic, dateBirth, gender).enqueue(new Callback<DefaultStructureUser>() {
            @Override
            public void onResponse(@NotNull Call<DefaultStructureUser> call, @NotNull Response<DefaultStructureUser> response) {
                Log.e("putUserToApi", "Success");
            }

            @Override
            public void onFailure(@NotNull Call<DefaultStructureUser> call, @NotNull Throwable t) {
                Log.e("putUserToApi", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void getLikeFromApi(String idNews, String email) {
        apiInterface.getLikes(idNews, email).enqueue(new Callback<DefaultStructureLike>() {
            @Override
            public void onResponse(@NotNull Call<DefaultStructureLike> call, @NotNull Response<DefaultStructureLike> response) {
                ArrayList<String> list = new ArrayList<>();
                if (response.code() == 200) {
                    assert response.body() != null;
                    list.add(response.body().getData());
                }
                onRetrofitSuccess.onSuccessGetData(list);
            }

            @Override
            public void onFailure(@NotNull Call<DefaultStructureLike> call, @NotNull Throwable t) {
              //  Log.e("getLikeFromApi", t.getMessage());
            }
        });
    }

    public void getStatusCodeFromServer() {
        apiInterface.getNews().enqueue(new Callback<DefaultStructureNews>() {
            @Override
            public void onResponse(@NotNull Call<DefaultStructureNews> call, @NotNull Response<DefaultStructureNews> response) {
                ArrayList<Integer> list = new ArrayList<>();
                list.add(response.code());
                onRetrofitSuccess.onSuccessGetData(list);
            }

            @Override
            public void onFailure(@NotNull Call<DefaultStructureNews> call, @NotNull Throwable t) {
                ArrayList<Integer> list = new ArrayList<>();
                list.add(0);
                onRetrofitSuccess.onSuccessGetData(list);
                Log.e("serverDigi", Objects.requireNonNull(t.getMessage()));
                Log.e("serverDigi", Objects.requireNonNull(t.getClass().getName()));
            }
        });
    }

    public void getVideoFromApi() {
        apiInterface.getVideo().enqueue(new Callback<DefaultStructureVideo>() {
            @Override
            public void onResponse(@NotNull Call<DefaultStructureVideo> call, @NotNull Response<DefaultStructureVideo> response) {
                ArrayList<VideoModel> list = new ArrayList<>();
                if (response.code() == 200) {
                    assert response.body() != null;
                    list.addAll(response.body().getData());
                    Log.e("getVideoFromApi", String.valueOf(list.size()));
                }
                onRetrofitSuccess.onSuccessGetData(list);
            }

            @Override
            public void onFailure(@NotNull Call<DefaultStructureVideo> call, @NotNull Throwable t) {
                Log.e("getVideoFromApi", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void postViewToApi(String idNews, String email) {
        JsonObject object = new JsonObject();
        object.addProperty("id_news", idNews);
        object.addProperty("email", email);

        apiInterface.postView(object).enqueue(new Callback<ViewModel>() {
            @Override
            public void onResponse(@NotNull Call<ViewModel> call, @NotNull Response<ViewModel> response) {
                Log.e("postViewToApi", "Sukses");
            }

            @Override
            public void onFailure(@NotNull Call<ViewModel> call, @NotNull Throwable t) {
                Log.e("postViewToApi", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void postShareToApi(String idNews, String email) {
        JsonObject object = new JsonObject();
        object.addProperty("id_news", idNews);
        object.addProperty("email", email);

        apiInterface.postShare(object).enqueue(new Callback<ViewModel>() {
            @Override
            public void onResponse(@NotNull Call<ViewModel> call, @NotNull Response<ViewModel> response) {
                Log.e("postShareToApi", "Sukses");
            }

            @Override
            public void onFailure(@NotNull Call<ViewModel> call, @NotNull Throwable t) {
                Log.e("postShareToApi", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void getEmagzFromApi() {
        apiInterface.getEmagz().enqueue(new Callback<DefaultStructureEmagz>() {
            @Override
            public void onResponse(@NotNull Call<DefaultStructureEmagz> call, @NotNull Response<DefaultStructureEmagz> response) {
                ArrayList<EmagzModel> list = new ArrayList<>();
                if(response.code() == 200) {
                    assert response.body() != null;
                    list.addAll(response.body().getData());
                    Log.e("getEmagzFromApi", String.valueOf(list.size()));
                }
                onRetrofitSuccess.onSuccessGetData(list);
            }

            @Override
            public void onFailure(@NotNull Call<DefaultStructureEmagz> call, @NotNull Throwable t) {
                Log.e("getEmagzFromApi", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void getUserFromApi(String email) {
        apiInterface.getUser(email).enqueue(new Callback<DefaultStructureUser>() {
            @Override
            public void onResponse(@NotNull Call<DefaultStructureUser> call, @NotNull Response<DefaultStructureUser> response) {
                ArrayList<UserModel> list = new ArrayList<>();
                if(response.code() == 200) {
                    assert response.body() != null;
                    list.addAll(response.body().getData());
                    Log.e("getUserFromApi", String.valueOf(list.size()));
                }
                onRetrofitSuccess.onSuccessGetData(list);
            }

            @Override
            public void onFailure(@NotNull Call<DefaultStructureUser> call, @NotNull Throwable t) {
                Log.e("getUserFromApi", Objects.requireNonNull(t.getMessage()));
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
