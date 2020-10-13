package com.health.movieland.Home;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.health.movieland.MediaActivity;
import com.health.movieland.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

public class trending_all_Adapter extends RecyclerView.Adapter<trending_all_Adapter.trending_all_ViewHolder>{
    private Context context;
    private List<Result> data;

    public trending_all_Adapter(Context context, List<Result> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public trending_all_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.trending_all, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = (int) (parent.getWidth() * 0.305);
        layoutParams.height = (int) (parent.getHeight() * 0.80);
        view.setLayoutParams(layoutParams);
        return new trending_all_ViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull final trending_all_ViewHolder holder, int position) {
        final Result movie = data.get(position);
        final String movie_id = movie.getId();
        holder.title.setText(movie.getTitle());
        holder.rating.setText(String.valueOf(movie.getVoteAverage()));
        holder.pop.setText(String.valueOf(movie.getPopularity().intValue()));

        Picasso.with(holder.poster.getContext()).load(movie.getPosterPath()).placeholder(R.drawable.movie_placeholder).into(holder.poster);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(context, MediaActivity.class);
                        intent.putExtra("movie_id", movie_id);
                        intent.putExtra("media_type", "movie");
                        context.startActivity(intent);
                    }
                }).start();

            }
        });

    }

    @Override
    public int getItemCount(){return data.size();}

    public class trending_all_ViewHolder extends RecyclerView.ViewHolder{
        ImageView poster;
        TextView title;
        TextView rating;
        TextView pop;
        public trending_all_ViewHolder(View itemView){
            super(itemView);
            poster = itemView.findViewById(R.id.poster);
            title = itemView.findViewById(R.id.title);
            rating = itemView.findViewById(R.id.rating);
            pop = itemView.findViewById(R.id.popular);

        }
    }

}
