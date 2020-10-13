package com.health.movieland.Media_Activity.SimilarMovies;

import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.health.movieland.Cast.Cast;
import com.health.movieland.Cast.cast_adapter;
import com.health.movieland.MediaActivity;
import com.health.movieland.R;

public class SimilarMovies extends Fragment {

    private String URLtosim1 = "https://api.themoviedb.org/3/movie/";
    private String URLtosim2 = "/similar?api_key=223444c9a68bd2763fbf89598bb95134&language=en-US&page=1";
    private String URLtosim;
    private String URLtosimtv1 = "https://api.themoviedb.org/3/tv/";
    private String URLtosimtv2 = "/similar?api_key=223444c9a68bd2763fbf89598bb95134&language=en-US&page=1";
    private String URLtosimtv;
    private String media_type;

    public SimilarMovies(){}



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.similar_movies, container, false);
        final RecyclerView similar = v.findViewById(R.id.similar_movies);
        similar.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false));
        RequestQueue queue = Volley.newRequestQueue(getContext());
        media_type = ((MediaActivity) getActivity()).getmedia_type();
        switch(media_type) {
            case "movie":

                URLtosim = URLtosim1 + ((MediaActivity) getActivity()).getmovie_id() + URLtosim2;


                StringRequest request2 = new StringRequest(URLtosim, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("gghgh", response);
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        SimilarMovieslist users = gson.fromJson(response, SimilarMovieslist.class);
                        similar.setAdapter(new similarmoviesAdapter(getContext(), users.getResults()));
                        similar.setHasFixedSize(true);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });

                queue.add(request2);
                break;

            case "tv":
                URLtosimtv = URLtosimtv1 + ((MediaActivity) getActivity()).getmovie_id() + URLtosimtv2;


                StringRequest request3 = new StringRequest(URLtosimtv, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("IDDDDDD", URLtosimtv);
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        SimilarTvList users = gson.fromJson(response, SimilarTvList.class);
                        similar.setAdapter(new SimilartvAdapter(getContext(), users.getResults()));

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });

                queue.add(request3);
                break;
        }
        return v;
    }
    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {

        super.onPause();
    }
}
