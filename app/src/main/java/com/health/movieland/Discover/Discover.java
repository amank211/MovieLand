package com.health.movieland.Discover;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.health.movieland.Celebs.Poppular;
import com.health.movieland.Celebs.popular_Adapter;
import com.health.movieland.Media_Activity.genre_adapter;
import com.health.movieland.R;

import java.util.ArrayList;
import java.util.List;

public class Discover extends Fragment {
    private static String URL_tv = "https://api.themoviedb.org/3/discover/tv?api_key=223444c9a68bd2763fbf89598bb95134&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1";
    private static String URL_movie = "https://api.themoviedb.org/3/discover/movie?api_key=223444c9a68bd2763fbf89598bb95134&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1";
    private static String URL_genre = "https://api.themoviedb.org/3/genre/movie/list?api_key=223444c9a68bd2763fbf89598bb95134&language=en-US";
    private List<Result_movie> data1 = new ArrayList<>();
    private List<Result_tv> data2 = new ArrayList<>();

    public Discover(){}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.discover, container, false);


        final RecyclerView genre_list = v.findViewById(R.id.genres_rec);
        genre_list.setLayoutManager(new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL, false));


        final RequestQueue queue = Volley.newRequestQueue(getContext());
        final StringRequest request1 = new StringRequest(URL_genre, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                GenreList users = gson.fromJson(response, GenreList.class);
                // Stopping Shimmer Effect's animation after data is loaded to ListView

                genre_list.setAdapter(new Genre_ListAdapter(getContext(), users.getGenres()));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_SHORT).show();
            }
        });


        final RecyclerView discover_movie = v.findViewById(R.id.movies);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        final StringRequest request = new StringRequest(URL_movie, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                DiscoverMovie users = gson.fromJson(response, DiscoverMovie.class);
                // Stopping Shimmer Effect's animation after data is loaded to ListView
                data1 = users.getResults();
                final discover_movie_Adapter adapter_movie = new discover_movie_Adapter(getContext(), data1);
                discover_movie.setLayoutManager(manager);
                discover_movie.setAdapter(adapter_movie);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_SHORT).show();
            }
        });



        final RecyclerView discover_tv = v.findViewById(R.id.tv_shows);
        discover_tv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        final StringRequest request2 = new StringRequest(URL_tv, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                DiscoverTv users = gson.fromJson(response, DiscoverTv.class);
                // Stopping Shimmer Effect's animation after data is loaded to ListView
                data2 = users.getResults();
                discover_tv.setAdapter(new discover_tv_Adapter(getContext(), data2));


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

        final SwipeRefreshLayout refreshLayout = v.findViewById(R.id.refresh_page);

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()){
            queue.add(request1);
            queue.add(request2);
            queue.add(request);

        } else{Toast.makeText(getContext(), "Network Error", Toast.LENGTH_SHORT).show();}

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ConnectivityManager connectivityManager2 = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo2 = connectivityManager2.getActiveNetworkInfo();
                if(networkInfo2 != null && networkInfo2.isConnected()) {
                    data1.clear();
                    data2.clear();
                    queue.cancelAll(new RequestQueue.RequestFilter() {
                        @Override
                        public boolean apply(Request<?> request) {
                            return true;
                        }
                    });
                    queue.add(request1);
                    queue.add(request2);
                    queue.add(request);
                } else{Toast.makeText(getContext(), "Network Error", Toast.LENGTH_SHORT).show();}
                refreshLayout.setRefreshing(false);

            }
        });

        return v;
    }
}
