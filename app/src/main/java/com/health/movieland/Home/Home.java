package com.health.movieland.Home;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.health.movieland.R;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment {

    private static final String URL_trending = "https://api.themoviedb.org/3/trending/movie/day?api_key=223444c9a68bd2763fbf89598bb95134&language=en-US&page=1";
    private static final String URL_nowplaying = "https://api.themoviedb.org/3/movie/now_playing?api_key=223444c9a68bd2763fbf89598bb95134&language=en-US&page=1";
    private static final String URL_upcoming = "https://api.themoviedb.org/3/movie/upcoming?api_key=223444c9a68bd2763fbf89598bb95134&language=en-US&page=1";
    private static final String URL_toprated = "https://api.themoviedb.org/3/movie/top_rated?api_key=223444c9a68bd2763fbf89598bb95134&language=en-US&page=1";

    private List<Result> data1 = new ArrayList<>();
    private List<Result_nowplaying> data2 = new ArrayList<>();
    private List<Result_nowplaying> data3 = new ArrayList<>();
    private List<Result> data4 = new ArrayList<>();

    public Home(){}



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.home, container, false);

        final RequestQueue queue = Volley.newRequestQueue(getContext());

        final RecyclerView trending = v.findViewById(R.id.trending);
        trending.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));

            final StringRequest request1 = new StringRequest(URL_trending, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Log.d("movies", response);
                    Gson gson = gsonBuilder.create();
                    TrendingAll users = gson.fromJson(response, TrendingAll.class);
                    // Stopping Shimmer Effect's animation after data is loaded to ListView
                    data1 = users.getResults();
                    trending.setAdapter(new trending_all_Adapter(getContext(), data1));


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_SHORT).show();
                }
            });

        final SwipeRefreshLayout refreshLayout = v.findViewById(R.id.refresh_page);


        final RecyclerView now_playing = v.findViewById(R.id.now_playing);
        now_playing.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        final StringRequest request2 = new StringRequest(URL_nowplaying, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                nowplaying users = gson.fromJson(response,nowplaying.class);
                data2 = users.getResults();
                now_playing.setAdapter(new now_playingAdapter(getContext(), data2));


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

        final RecyclerView upcoming = v.findViewById(R.id.upcoming);
        upcoming.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));



        final StringRequest request3 = new StringRequest(URL_upcoming, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                nowplaying users = gson.fromJson(response,nowplaying.class);
                data3 = users.getResults();
                upcoming.setAdapter(new now_playingAdapter(getContext(), data3));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

        final RecyclerView top_rated = v.findViewById(R.id.top_rated);
        top_rated.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));



        final StringRequest request4 = new StringRequest(URL_toprated, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                TrendingAll movie = gson.fromJson(response,TrendingAll.class);
                data4 = movie.getResults();
                top_rated.setAdapter(new trending_all_Adapter(getContext(), data4));


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()){
            queue.add(request1);
            queue.add(request2);
            queue.add(request3);
            queue.add(request4);

        } else{Toast.makeText(getContext(), "Network Error", Toast.LENGTH_SHORT).show();}

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ConnectivityManager connectivityManager2 = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo2 = connectivityManager2.getActiveNetworkInfo();
                if(networkInfo2 != null && networkInfo2.isConnected()) {
                    data1.clear();
                    data2.clear();
                    data3.clear();
                    data4.clear();
                    queue.cancelAll(new RequestQueue.RequestFilter() {
                        @Override
                        public boolean apply(Request<?> request) {
                            return true;
                        }
                    });
                    queue.add(request1);
                    queue.add(request2);
                    queue.add(request3);
                    queue.add(request4);
                } else{Toast.makeText(getContext(), "Network Error", Toast.LENGTH_SHORT).show();}
                refreshLayout.setRefreshing(false);

            }
        });

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