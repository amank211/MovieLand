package com.health.movieland.Media_Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.health.movieland.R;

public class genre_adapter extends RecyclerView.Adapter<genre_adapter.genre_adapterViewHolder> {
    private Context context;
    private Genre[] data;
    private int image;
    public genre_adapter(Context context, Genre[] data){
        this.context = context;
        this.data = data;
    }



    @Override
    public genre_adapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.genre, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        view.setLayoutParams(layoutParams);
        return new genre_adapterViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull genre_adapterViewHolder holder, int position){
        Genre movie = data[position];
        holder.genre.setText(movie.getName());
    }

    @Override
    public int getItemCount(){return data.length;}

    public class genre_adapterViewHolder extends RecyclerView.ViewHolder{
        TextView genre;
        public genre_adapterViewHolder(View itemView){
            super(itemView);
            genre = itemView.findViewById(R.id.genre);

        }
    }


}
