package com.example.newsapp.viewmodel;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.newsapp.NewsModel;
import com.example.newsapp.network.APIService;
import com.example.newsapp.network.RetroInstance;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsListViewModel extends ViewModel {

    private MutableLiveData<List<NewsModel>> newsList;

    public NewsListViewModel(){
        newsList = new MutableLiveData<>();
    }

    public MutableLiveData<List<NewsModel>> getNewsListObserver() {
        return newsList;

    }

    public void ApiCall() {
        APIService apiService = RetroInstance.getClient().create(APIService.class);
        Call<List<NewsModel>> call = apiService.getNews();
        call.enqueue(new Callback<List<NewsModel>>() {
            @Override
            public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {
                newsList.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<NewsModel>> call, Throwable t) {
                newsList.postValue(null);
            }
        });
    }
}
