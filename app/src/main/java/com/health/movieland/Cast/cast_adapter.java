package com.health.movieland.Cast;

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
import com.squareup.picasso.Picasso;

public class cast_adapter extends RecyclerView.Adapter<cast_adapter.cast_adapterViewHolder> {
    private Context context;
    private Cast_[] data;
    private int image;
    public cast_adapter(Context context, Cast_[] data){
        this.context = context;
        this.data = data;
    }



    @Override
    public cast_adapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cast, parent, false);
        View imageview = view.findViewById(R.id.poster);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        view.setLayoutParams(layoutParams);
        return new cast_adapterViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull cast_adapterViewHolder holder, int position){
        Cast_ movie = data[position];
        final String cast_id = String.valueOf(movie.getId());
        holder.name.setText(movie.getName());
        holder.char_name.setText(movie.getCharacter());
        Picasso.with(holder.avatar.getContext()).load(movie.getProfilePath()).placeholder(R.drawable.movie_placeholder).into(holder.avatar);
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

    @Override
    public int getItemCount(){return data.length;}

    public class cast_adapterViewHolder extends RecyclerView.ViewHolder{
        ImageView avatar;
        TextView name;
        TextView char_name;
        public cast_adapterViewHolder(View itemView){
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar);
            name = itemView.findViewById(R.id.real_name);
            char_name = itemView.findViewById(R.id.char_name);

        }
    }


}
