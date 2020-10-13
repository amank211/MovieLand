package com.health.movieland.TV;

import android.content.Context;
import android.net.ConnectivityManager;
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
import com.health.movieland.TV.On_air.Airing;
import com.health.movieland.TV.On_air.Result_airing;
import com.health.movieland.TV.On_air.airingadapter;
import com.health.movieland.TV.Popular.PopularTvshows;
import com.health.movieland.TV.Popular.Popularadapter;
import com.health.movieland.TV.Popular.Result;
import com.health.movieland.TV.Today_airing.AiringToday;
import com.health.movieland.TV.Today_airing.Result_today_air;
import com.health.movieland.TV.Today_airing.today_airingadapter;
import com.health.movieland.TV.Top_rated.Result_topr;
import com.health.movieland.TV.Top_rated.TopratedTvshows;
import com.health.movieland.TV.Top_rated.Topratedadapter;

import java.util.ArrayList;
import java.util.List;

public class TV extends Fragment {

   private static final String URL_popular = "https://api.themoviedb.org/3/tv/popular?api_key=223444c9a68bd2763fbf89598bb95134&language=en-US&page=1";
    private static final String URL_today_airing = "https://api.themoviedb.org/3/tv/airing_today?api_key=223444c9a68bd2763fbf89598bb95134&language=en-US&page=1";
    private static final String URL_airing = "https://api.themoviedb.org/3/tv/popular?api_key=223444c9a68bd2763fbf89598bb95134&language=en-US&page=1";
    private static final String URL_toprated = "https://api.themoviedb.org/3/tv/top_rated?api_key=223444c9a68bd2763fbf89598bb95134&language=en-US&page=1";

    private List<Result> data1 = new ArrayList<>();
    private List<Result_today_air> data2 = new ArrayList<>();
    private List<Result_airing> data3 = new ArrayList<>();
    private List<Result_topr> data4 = new ArrayList<>();

    public TV(){}



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.tv, container, false);

       final RequestQueue queue = Volley.newRequestQueue(getContext());

        final RecyclerView trending = v.findViewById(R.id.trending);
        trending.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));

        final StringRequest request1 = new StringRequest(URL_popular, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                PopularTvshows users = gson.fromJson(response, PopularTvshows.class);
                // Stopping Shimmer Effect's animation after data is loaded to ListView
                data1 = users.getResults();
                trending.setAdapter(new Popularadapter(getContext(), data1));


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

        final StringRequest request2 = new StringRequest(URL_today_airing, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                AiringToday users = gson.fromJson(response,AiringToday.class);
                data2 = users.getResults();
                now_playing.setAdapter(new today_airingadapter(getContext(), data2));


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

        final RecyclerView upcoming = v.findViewById(R.id.upcoming);
        upcoming.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));



        final StringRequest request3 = new StringRequest(URL_airing, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                Airing users = gson.fromJson(response,Airing.class);
                data3 = users.getResults();
                upcoming.setAdapter(new airingadapter(getContext(), data3));

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
                TopratedTvshows movie = gson.fromJson(response,TopratedTvshows.class);
                data4 = movie.getResults();
                top_rated.setAdapter(new Topratedadapter(getContext(), data4));


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