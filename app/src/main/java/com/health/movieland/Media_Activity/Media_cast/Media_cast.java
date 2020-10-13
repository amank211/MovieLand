package com.health.movieland.Media_Activity.Media_cast;

import android.os.Bundle;
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

public class Media_cast extends Fragment {

    private String URLtocast1 = "https://api.themoviedb.org/3/movie/";
    private String URLtocast2 = "/credits?api_key=223444c9a68bd2763fbf89598bb95134";
    private String URLtocast;

    private String URLtocasttv1 = "https://api.themoviedb.org/3/tv/";
    private String URLtocasttv2 = "/credits?api_key=223444c9a68bd2763fbf89598bb95134";
    private String URLtocasttv;

    public Media_cast(){}



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.media_cast, container, false);
        final String media_tyoe = ((MediaActivity) getActivity()).getmedia_type();

        final RequestQueue queue = Volley.newRequestQueue(getContext());

        final RecyclerView media_cast = v.findViewById(R.id.media_cast);
        media_cast.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false));


        switch (media_tyoe) {

            case "movie":

                URLtocast = URLtocast1 + ((MediaActivity) getActivity()).getmovie_id() + URLtocast2;


                StringRequest request2 = new StringRequest(URLtocast, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        Cast users = gson.fromJson(response, Cast.class);
                        media_cast.setAdapter(new cast_adapter(getContext(), users.getCast()));
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

                URLtocasttv = URLtocasttv1 + ((MediaActivity) getActivity()).getmovie_id() + URLtocasttv2;

                StringRequest request3 = new StringRequest(URLtocasttv, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        Cast users = gson.fromJson(response, Cast.class);
                        media_cast.setAdapter(new cast_adapter(getContext(), users.getCast()));
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
