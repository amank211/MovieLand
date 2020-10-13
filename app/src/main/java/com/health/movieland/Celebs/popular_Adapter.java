package com.health.movieland.Celebs;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.health.movieland.PersonActivity;
import com.health.movieland.R;
import com.squareup.picasso.Picasso;

import java.util.List;
public class popular_Adapter extends RecyclerView.Adapter<popular_Adapter.popular_ViewHolder>{
    private Context context;
    private List<Result_popular> data;

    public popular_Adapter(Context context, List<Result_popular> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public popular_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.popular, parent, false);
        return new popular_ViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull final popular_ViewHolder holder, int position) {
        final Result_popular movie = data.get(position);
        holder.name.setText(movie.getName());
        final String person_id = String.valueOf(movie.getId());
        Picasso.with(holder.profile.getContext()).load(movie.getProfilePath()).placeholder(R.drawable.movie_placeholder).into(holder.profile);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PersonActivity.class);
                intent.putExtra("person_id", person_id);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount(){return data.size();}

    public class popular_ViewHolder extends RecyclerView.ViewHolder{
        ImageView profile;
        TextView name;
        public popular_ViewHolder(View itemView){
            super(itemView);
            profile = itemView.findViewById(R.id.avatar);
            name = itemView.findViewById(R.id.name);

        }
    }

}
