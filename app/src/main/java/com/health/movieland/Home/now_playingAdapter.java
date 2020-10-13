package com.health.movieland.Home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.health.movieland.MediaActivity;
import com.health.movieland.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class now_playingAdapter extends RecyclerView.Adapter<now_playingAdapter.Adapter2ViewHolder> {
    private Context context;
    private List<Result_nowplaying> data;
    public now_playingAdapter(Context context, List<Result_nowplaying> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public Adapter2ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.now_playing_all, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = (int) (parent.getWidth() * 0.305);
        layoutParams.height = (int) (parent.getHeight() * 0.80);
        view.setLayoutParams(layoutParams);
        return new Adapter2ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(Adapter2ViewHolder holder, int position){
        Result_nowplaying movie = data.get(position);
        final String movie_id = String.valueOf(movie.getId());
        holder.title.setText(movie.getTitle());
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

    public class Adapter2ViewHolder extends RecyclerView.ViewHolder{
        ImageView poster;
        TextView title;
        public Adapter2ViewHolder(View itemView){
            super(itemView);
            poster = itemView.findViewById(R.id.poster);
            title = itemView.findViewById(R.id.title);

        }
    }


}
