package com.health.movieland.Person_Activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.health.movieland.MediaActivity;
import com.health.movieland.Person_Activity.Person_movie.Cast;
import com.health.movieland.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class person_movieAdapter extends RecyclerView.Adapter<person_movieAdapter.person_movie_viewholder> {
    private Context context;
    private List<Cast> data;
    public person_movieAdapter(Context context, List<Cast> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public person_movieAdapter.person_movie_viewholder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.now_playing_all, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = (int) (parent.getWidth() * 0.34);
        layoutParams.height = (int) (parent.getHeight() * 0.90);
        view.setLayoutParams(layoutParams);
        return new person_movieAdapter.person_movie_viewholder(view);

    }

    @Override
    public void onBindViewHolder(person_movieAdapter.person_movie_viewholder holder, int position){
        Cast movie = data.get(position);
        holder.title.setText(movie.getTitle());
        Picasso.with(holder.poster.getContext()).load(movie.getPosterPath()).placeholder(R.drawable.movie_placeholder).into(holder.poster);
        final String movie_id = String.valueOf(movie.getId());
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

    public class person_movie_viewholder extends RecyclerView.ViewHolder{
        ImageView poster;
        TextView title;
        public person_movie_viewholder(View itemView){
            super(itemView);
            poster = itemView.findViewById(R.id.poster);
            title = itemView.findViewById(R.id.title);
        }
    }


}
