package com.example.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder>{
    private List<NewsModel> newsList;
    private Context context;


    public NewsAdapter(Context context, List<NewsModel> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    public void setNewsList(List<NewsModel> newsList) {
        this.newsList = newsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.MyViewHolder holder, int position) {
        holder.title.setText(this.newsList.get(position).getTitle());
        holder.summary.setText(this.newsList.get(position).getSummary());

    }
    public void filterList(ArrayList<NewsModel> filterlist) {
        newsList = filterlist;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(this.newsList != null) {
            return this.newsList.size();
        }
        return 0;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title,summary;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.title_name);
            summary = (TextView)itemView.findViewById(R.id.description_name);

        }
    }


}
