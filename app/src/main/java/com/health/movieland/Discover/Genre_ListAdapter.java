package com.health.movieland.Discover;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.health.movieland.MediaActivity;
import com.health.movieland.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Genre_ListAdapter extends RecyclerView.Adapter<Genre_ListAdapter.Genre_ListViewHolder>{
    private Context context;
    private List<Genre> data;

    public Genre_ListAdapter(Context context, List<Genre> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public Genre_ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.genre_layout, parent, false);
        return new Genre_ListViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final Genre_ListViewHolder holder, int position) {
        final Genre genre = data.get(position);
        holder.genre.setText(genre.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //Intent intent = new Intent(context, MediaActivity.class);
                        //intent.putExtra("movie_id", movie_id);
                        //intent.putExtra("media_type", "tv");
                        //context.startActivity(intent);
                    }
                }).start();

            }
        });

    }

    @Override
    public int getItemCount(){return data.size();}

    public class Genre_ListViewHolder extends RecyclerView.ViewHolder{
        Button genre;
        public Genre_ListViewHolder(View itemView){
            super(itemView);
            genre = itemView.findViewById(R.id.genre_button);
        }
    }

}
