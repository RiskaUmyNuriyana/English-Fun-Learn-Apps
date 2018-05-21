package com.synergics.ishom.englishapps.Controller.RestFullConfig;

import com.synergics.ishom.englishapps.Model.RestFullObject.GamesCategory;
import com.synergics.ishom.englishapps.Model.RestFullObject.Learn;
import com.synergics.ishom.englishapps.Model.RestFullObject.MiniGames;
import com.synergics.ishom.englishapps.Model.RestFullObject.ResponsePractice;
import com.synergics.ishom.englishapps.Model.RestFullObject.ResponseVideo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by asmarasusanto on 10/8/17.
 */

public interface ApiInterface {

    @GET("english_for_fun/api/games-category.php")
    Call<GamesCategory> getGamesCategory();

    @GET("english_for_fun/api/learn.php")
    Call<Learn> getLearn(@Query("id") String id);

    @GET("english_for_fun/api/mini-games.php")
    Call<MiniGames> getMiniGames(@Query("id") String id);

    @GET("english_for_fun/api/practice_category.php")
    Call<List<ResponsePractice>> getPractice(@Query("idc") String idc);

    @GET("getVideoByFeature.php")
    Call<List<ResponseVideo>> getVideo(@Query("idc") String id);

}
