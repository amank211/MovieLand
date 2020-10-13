package com.health.movieland.TV.Popular;


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
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

public class Popularadapter extends RecyclerView.Adapter<Popularadapter.Popularadapter_ViewHolder>{
    private Context context;
    private List<Result> data;

    public Popularadapter(Context context, List<Result> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public Popularadapter_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.trending_all, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = (int) (parent.getWidth() * 0.34);
        layoutParams.height = (int) (parent.getHeight() * 0.90);
        view.setLayoutParams(layoutParams);
        return new Popularadapter_ViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull final Popularadapter_ViewHolder holder, int position) {
        final Result movie = data.get(position);
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

    public class Popularadapter_ViewHolder extends RecyclerView.ViewHolder{
        ImageView poster;
        TextView title;
        public Popularadapter_ViewHolder(View itemView){
            super(itemView);
            poster = itemView.findViewById(R.id.poster);
            title = itemView.findViewById(R.id.title);

        }
    }

}

