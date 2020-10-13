package com.health.movieland;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.health.movieland.Person_Activity.ImagesPerson;
import com.health.movieland.Person_Activity.PersonInfo;
import com.health.movieland.Person_Activity.Person_movie.PersonMovieInfo;
import com.health.movieland.Person_Activity.Person_tv.PersonTvInfo;
import com.health.movieland.Person_Activity.Result_images;
import com.health.movieland.Person_Activity.person_movieAdapter;
import com.health.movieland.Person_Activity.person_tvAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PersonActivity extends AppCompatActivity {
    private TextView person_id;
    private String person_id_string;

    private static String URL_per_info_1 = "https://api.themoviedb.org/3/person/";
    private static String URL_per_info_2 = "?api_key=223444c9a68bd2763fbf89598bb95134&language=en-US";
    private static String URL_per_info;
    private static String URL_per_movie_1 = "https://api.themoviedb.org/3/person/";
    private static String URL_per_movie_2 = "/movie_credits?api_key=223444c9a68bd2763fbf89598bb95134&language=en-US";
    private static String URL_per_movie;
    private static String URL_per_tv_1 = "https://api.themoviedb.org/3/person/";
    private static String URL_per_tv_2 = "/tv_credits?api_key=223444c9a68bd2763fbf89598bb95134&language=en-US";
    private static String URL_per_tv;
    private static String URL_per_images1 = "https://api.themoviedb.org/3/person/";
    private static String URL_per_images2 = "/tagged_images?api_key=223444c9a68bd2763fbf89598bb95134&language=en-US&page=1";
    private static String URL_per_images;
    List<Result_images> data = new ArrayList<>();
    List<com.health.movieland.Person_Activity.Person_movie.Cast> data2 = new ArrayList<>();

    private List<String> aka_string = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        final TextView born = findViewById(R.id.born);
        final TextView birthplace = findViewById(R.id.birthplace);
        final TextView bio = findViewById(R.id.overview);
        final TextView homepage = findViewById(R.id.homepage);
        final TextView aka = findViewById(R.id.AKA);
        final ImageView banner = findViewById(R.id.banner);



        Intent intent = getIntent();
        person_id_string = intent.getStringExtra("person_id");

        URL_per_info = URL_per_info_1 + person_id_string + URL_per_info_2;

        URL_per_movie = URL_per_movie_1 + person_id_string + URL_per_movie_2;

        URL_per_tv = URL_per_tv_1 + person_id_string + URL_per_tv_2;

        URL_per_images = URL_per_images1 + person_id_string + URL_per_images2;

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request1 = new StringRequest(URL_per_info, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                PersonInfo users = gson.fromJson(response, PersonInfo.class);
                Toolbar toolbar = findViewById(R.id.person_toolbar);
                toolbar.setTitle(users.getName());
                toolbar.setSubtitle(users.getKnownForDepartment());
                setSupportActionBar(toolbar);

                born.setText(users.getBirthday());
                birthplace.setText(users.getPlaceOfBirth());
                homepage.setText(users.getHomepage());
                bio.setText(users.getBiography());
                Log.d("ID", String.valueOf(users.getId()));
                String temp = "";
                aka_string = users.getAlsoKnownAs();
                for (int i =0; i<aka_string.size(); i++){
                    temp += aka_string.get(i);
                    if(i != aka_string.size()-1){
                        temp += ", ";
                    }
                }
                aka.setText(temp);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PersonActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

        final RecyclerView movies = findViewById(R.id.movies_rec);
        movies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        StringRequest request2 = new StringRequest(URL_per_movie, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                PersonMovieInfo users = gson.fromJson(response, PersonMovieInfo.class);
                data2 = users.getCast();
                if(data2.size()>0) {
                    movies.setAdapter(new person_movieAdapter(PersonActivity.this, users.getCast()));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PersonActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

        final RecyclerView tv = findViewById(R.id.tv_rec);
        tv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));



        StringRequest request3 = new StringRequest(URL_per_tv, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                PersonTvInfo users = gson.fromJson(response, PersonTvInfo.class);
                tv.setAdapter(new person_tvAdapter(PersonActivity.this, users.getCast()));


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PersonActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
            }
        });


        StringRequest request4 = new StringRequest(URL_per_images, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                ImagesPerson users = gson.fromJson(response, ImagesPerson.class);
                data = users.getResults();
                String path = "https://image.tmdb.org/t/p/w500";

                Double vote = 0.0;

                for(int i = 0; i < data.size();i++){
                    if(data.get(i).getVoteAverage() > vote && data.get(i).getAspectRatio() > 1.5){
                        vote = data.get(i).getVoteAverage();
                        path = data.get(i).getFilePath();
                    }
                }

                Picasso.with(PersonActivity.this).load(path).placeholder(R.drawable.movie_placeholder2).into(banner);





            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PersonActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
            }
        });



        queue.add(request1);
        queue.add(request2);
        queue.add(request3);
        queue.add(request4);

    }
}
