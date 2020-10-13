package com.health.movieland.Media_Activity.SimilarMovies;

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

public class SimilartvAdapter extends RecyclerView.Adapter<SimilartvAdapter.SimilartvAdapterViewHolder>{
    private Context context;
    private List<Result_simtv> data;

    public SimilartvAdapter(Context context, List<Result_simtv> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public SimilartvAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.similar_movie_item, parent, false);
        return new SimilartvAdapterViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull final SimilartvAdapterViewHolder holder, int position) {
        final Result_simtv movie = data.get(position);
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

    public class SimilartvAdapterViewHolder extends RecyclerView.ViewHolder{
        ImageView poster;
        TextView title;
        public SimilartvAdapterViewHolder(View itemView){
            super(itemView);
            poster = itemView.findViewById(R.id.poster);
            title = itemView.findViewById(R.id.title);

        }
    }

}