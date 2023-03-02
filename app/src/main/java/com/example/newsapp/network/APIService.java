package com.example.newsapp.network;

import com.example.newsapp.NewsModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {

    @GET("articles")
    Call<List<NewsModel>> getNews();
}
