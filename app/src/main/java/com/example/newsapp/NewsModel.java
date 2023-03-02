package com.example.newsapp;

import com.google.gson.annotations.SerializedName;

public class NewsModel {

    @SerializedName("title")
    private String title;

    @SerializedName("summary")
    private String summary;


    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }
}
