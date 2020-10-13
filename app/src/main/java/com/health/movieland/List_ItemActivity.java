package com.health.movieland;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.health.movieland.List_Details.List_Details;
import com.health.movieland.List_Details.listDetailAdapter;
import com.health.movieland.YourLists.UserLists;
import com.health.movieland.YourLists.UserListsAdapter;

import java.net.URLConnection;

public class List_ItemActivity extends AppCompatActivity {

    private static String URL_lists1 = "https://api.themoviedb.org/3/list/";
    private static String URL_lists2 = "?api_key=223444c9a68bd2763fbf89598bb95134&language=en-US";
    private static String URL_lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list__item);

        Intent intent = getIntent();
        String id  = intent.getStringExtra("list_id");

        URL_lists = URL_lists1 + id + URL_lists2;

        final RequestQueue queue = Volley.newRequestQueue(this);

        final RecyclerView lists = findViewById(R.id.items_rec);
        lists.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        final StringRequest request2 = new StringRequest(URL_lists, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                List_Details list = gson.fromJson(response, List_Details.class);
                lists.setAdapter(new listDetailAdapter(List_ItemActivity.this, list.getItems()));


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(List_ItemActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request2);
    }
}
