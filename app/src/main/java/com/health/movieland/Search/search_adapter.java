package com.health.movieland.Search;

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
import com.health.movieland.PersonActivity;
import com.health.movieland.R;
import com.health.movieland.SearchActivity;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

public class search_adapter extends RecyclerView.Adapter<search_adapter.search_adapter_ViewHolder>{
    private Context context;
    private List<Result_query> data;

    public search_adapter(Context context, List<Result_query> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public search_adapter_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_search, parent, false);
        return new search_adapter_ViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull final search_adapter_ViewHolder holder, int position) {
        final Result_query movie = data.get(position);
        final String movie_id = movie.getId();
        if(Objects.equals(movie.getMediaType(),"movie")){
            holder.title.setText(movie.getTitle());
            holder.media_type.setText(movie.getMediaType());
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
        if(Objects.equals(movie.getMediaType(),"tv")){
            holder.title.setText(movie.getName());
            holder.media_type.setText("Tv Show");
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
        if(Objects.equals(movie.getMediaType(),"person")){
            holder.title.setText(movie.getName());
            holder.media_type.setText("Acting");
            final String cast_id  = movie.getId();
            Picasso.with(holder.poster.getContext()).load(movie.getProfilePath()).placeholder(R.drawable.movie_placeholder).into(holder.poster);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(context, PersonActivity.class);
                            intent.putExtra("person_id", cast_id);
                            context.startActivity(intent);
                        }
                    }).start();

                }
            });
        }


    }

    @Override
    public int getItemCount(){return data.size();}

    public class search_adapter_ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView poster;
        TextView media_type;
        public search_adapter_ViewHolder(View itemView){
            super(itemView);
            poster = itemView.findViewById(R.id.poster);
            title = itemView.findViewById(R.id.title);
            media_type = itemView.findViewById(R.id.media_type);

        }
    }

}
