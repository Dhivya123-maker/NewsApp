package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import com.example.newsapp.databinding.ActivityMainBinding;
import com.example.newsapp.viewmodel.NewsListViewModel;
import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity {
     NewsAdapter newsAdapter;
    private NewsListViewModel viewModel;
    private List<NewsModel> newsList;
    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = ViewModelProviders.of(this).get(NewsListViewModel.class);


        binding.progressBar.setVisibility(View.VISIBLE);
        internet();

        binding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.refreshLayout.setRefreshing(false);
                internet();
            }
        });



        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });


    }
    private void filter(String text) {
        ArrayList<NewsModel> filteredlist = new ArrayList<NewsModel>();

        for (NewsModel item : newsList) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase()) || item.getTitle().toUpperCase().contains(text.toUpperCase())) {
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            Toast toast = Toast.makeText(getApplicationContext(), "No Match Found..", Toast.LENGTH_SHORT);
            toast.show();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    toast.cancel();
                }
            }, 1000);
        } else {
            newsAdapter.filterList(filteredlist);
        }
    }
    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }

    public void internet(){
        data();
        if(isConnected()){
            data();
        }else{
            RelativeLayout layout = findViewById(R.id.layout);
            Snackbar snackbar = Snackbar.make(layout, "Check your internet connection", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }
public void data(){
    newsAdapter =  new NewsAdapter(this, newsList);
    binding.recyclerView.setAdapter(newsAdapter);

    viewModel.getNewsListObserver().observe(this, new Observer<List<NewsModel>>() {
        @Override
        public void onChanged(List<NewsModel> newsModels) {
            if(newsModels != null) {
                newsList = newsModels;
                newsAdapter.setNewsList(newsModels);
                binding.progressBar.setVisibility(View.GONE);

            }
        }
    });
    viewModel.ApiCall();
}
}