package com.health.movieland;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.health.movieland.Home.TrendingAll;
import com.health.movieland.Home.trending_all_Adapter;
import com.health.movieland.Search.Result_query;
import com.health.movieland.Search.SearchInfo;
import com.health.movieland.Search.search_adapter;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private List<Result_query> data1;

    private static String URL_search_i = "https://api.themoviedb.org/3/search/multi?api_key=223444c9a68bd2763fbf89598bb95134&language=en-US&page=1&include_adult=false&query=";
    private String query;
    private static String URL_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final ImageButton go_back = findViewById(R.id.go_back);
        final RequestQueue queue = Volley.newRequestQueue(SearchActivity.this);
        final androidx.appcompat.widget.SearchView searchView = findViewById(R.id.search_text);
        searchView.setIconified(false);
        searchView.setIconifiedByDefault(false);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final RecyclerView search_result = findViewById(R.id.search_result_view);
        search_result.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));


        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query_string) {
                query = query_string;
                URL_search = URL_search_i + query;

                final StringRequest request1 = new StringRequest(URL_search, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        SearchInfo users = gson.fromJson(response, SearchInfo.class);
                        // Stopping Shimmer Effect's animation after data is loaded to ListView
                        data1 = users.getResults();
                        search_result.setAdapter(new search_adapter(SearchActivity.this, data1));

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SearchActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(request1);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                query = newText;
                URL_search = URL_search_i + query;

                final StringRequest request2 = new StringRequest(URL_search, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        SearchInfo users = gson.fromJson(response, SearchInfo.class);
                        // Stopping Shimmer Effect's animation after data is loaded to ListView
                        data1 = users.getResults();
                        search_result.setAdapter(new search_adapter(SearchActivity.this, data1));

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SearchActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(request2);


                return false;
            }
        });



    }
}
