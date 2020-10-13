package com.health.movieland.Celebs;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.health.movieland.Home.Result;
import com.health.movieland.Home.TrendingAll;
import com.health.movieland.Home.trending_all_Adapter;
import com.health.movieland.R;

import java.util.ArrayList;
import java.util.List;

public class Celebs extends Fragment {

    private static String URL = "https://api.themoviedb.org/3/person/popular?api_key=223444c9a68bd2763fbf89598bb95134&language=en-US&page=1";
    List<Result_popular> data = new ArrayList<>();
    public Celebs(){}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.celebs, container, false);

        final RecyclerView popular = v.findViewById(R.id.popular);
        popular.setLayoutManager(new GridLayoutManager(getContext(),3,GridLayoutManager.VERTICAL, false));
        final RequestQueue queue = Volley.newRequestQueue(getContext());
               final  StringRequest request1 = new StringRequest(URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        Poppular users = gson.fromJson(response, Poppular.class);
                        // Stopping Shimmer Effect's animation after data is loaded to ListView
                        data = users.getResults();
                        popular.setAdapter(new popular_Adapter(getContext(), data));


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

        } else{Toast.makeText(getContext(), "Network Error", Toast.LENGTH_SHORT).show();}

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ConnectivityManager connectivityManager2 = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo2 = connectivityManager2.getActiveNetworkInfo();
                if(networkInfo2 != null && networkInfo2.isConnected()) {
                    data.clear();
                    queue.cancelAll(new RequestQueue.RequestFilter() {
                        @Override
                        public boolean apply(Request<?> request) {
                            return true;
                        }
                    });
                    queue.add(request1);
                } else{Toast.makeText(getContext(), "Network Error", Toast.LENGTH_SHORT).show();}
                refreshLayout.setRefreshing(false);

            }
        });

        return v;
    }
}
