package com.health.movieland.Media_Activity.tv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.health.movieland.Media_Activity.Genre;
import com.health.movieland.R;

import java.util.List;

public class genretv_adapter extends RecyclerView.Adapter<genretv_adapter.genretv_adapterViewHolder> {
    private Context context;
    private List<Genre_tv> data;
    private int image;
    public genretv_adapter(Context context, List<Genre_tv> data){
        this.context = context;
        this.data = data;
    }



    @Override
    public genretv_adapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.genre, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        view.setLayoutParams(layoutParams);
        return new genretv_adapterViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull genretv_adapterViewHolder holder, int position){
        Genre_tv movie = data.get(position);
        holder.genre.setText(movie.getName());
    }

    @Override
    public int getItemCount(){return data.size();}

    public class genretv_adapterViewHolder extends RecyclerView.ViewHolder{
        TextView genre;
        public genretv_adapterViewHolder(View itemView){
            super(itemView);
            genre = itemView.findViewById(R.id.genre);

        }
    }


}
