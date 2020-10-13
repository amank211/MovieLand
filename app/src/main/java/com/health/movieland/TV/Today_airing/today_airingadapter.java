package com.health.movieland.TV.Today_airing;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.health.movieland.MediaActivity;
import com.health.movieland.R;
import com.health.movieland.TV.Popular.Result;
import com.squareup.picasso.Picasso;

import java.util.List;

public class today_airingadapter extends RecyclerView.Adapter<today_airingadapter.airing_todayadapter_ViewHolder>{
    private Context context;
    private List<Result_today_air> data;

    public today_airingadapter(Context context, List<Result_today_air> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public airing_todayadapter_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.trending_all, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = (int) (parent.getWidth() * 0.34);
        layoutParams.height = (int) (parent.getHeight() * 0.90);
        view.setLayoutParams(layoutParams);
        return new airing_todayadapter_ViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull final airing_todayadapter_ViewHolder holder, int position) {
        final Result_today_air movie = data.get(position);
        final String movie_id = String.valueOf(movie.getId());
        holder.title.setText(movie.getName());

        Picasso.with(holder.poster.getContext()).load(movie.getPosterPath()).placeholder(R.drawable.movie_placeholder).into(holder.poster);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(context, MediaActivity.class);
                        intent.putExtra("movie_id", movie_id);
                        intent.putExtra("media_type", "tv");
                        context.startActivity(intent);
                    }
                }).start();

            }
        });

    }

    @Override
    public int getItemCount(){return data.size();}

    public class airing_todayadapter_ViewHolder extends RecyclerView.ViewHolder{
        ImageView poster;
        TextView title;
        public airing_todayadapter_ViewHolder(View itemView){
            super(itemView);
            poster = itemView.findViewById(R.id.poster);
            title = itemView.findViewById(R.id.title);

        }
    }

}

