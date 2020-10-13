package com.health.movieland;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.media.Image;
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
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.health.movieland.Cast.Cast;
import com.health.movieland.Cast.cast_adapter;
import com.health.movieland.Celebs.Celebs;
import com.health.movieland.Discover.Discover;
import com.health.movieland.Home.Home;
import com.health.movieland.Media_Activity.Genre;
import com.health.movieland.Media_Activity.Media_cast.Media_cast;
import com.health.movieland.Media_Activity.MovieInfo;
import com.health.movieland.Media_Activity.Overview.Overview;
import com.health.movieland.Media_Activity.ProductionCompany;
import com.health.movieland.Media_Activity.SimilarMovies.SimilarMovies;
import com.health.movieland.Media_Activity.genre_adapter;
import com.health.movieland.Media_Activity.tv.Genre_tv;
import com.health.movieland.Media_Activity.tv.TvInfo;
import com.health.movieland.Media_Activity.tv.genretv_adapter;
import com.health.movieland.Movies.Movies;
import com.health.movieland.TV.TV;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MediaActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private AdView mAdView;

    public String movie_id;
    public String media_type;
    public String movie_title;
    public String movie_year;
    public List<String> genre_string = new ArrayList<>();
    public String duration;
    private String overview;
    private String original_title;
    private String original_lang;
    private String director;
    private String budget;
    private String revenue;
    private String homepage;
    private String productions;
    private String releases;

    public List<ProductionCompany> prods = new ArrayList<>();
    public Genre[] genre;
    public List<Genre_tv> genre_tv = new ArrayList<>();
    private String URL_movieinfo1 = "https://api.themoviedb.org/3/movie/";
    private String URL_movieinfo2 = "?api_key=223444c9a68bd2763fbf89598bb95134&language=en-US";
    private String URL_movieinfo;
    private String URL_tvinfo1 = "https://api.themoviedb.org/3/tv/";
    private String URL_tvinfo2 = "?api_key=223444c9a68bd2763fbf89598bb95134&language=en-US";
    private String URL_tvinfo;


    public void addtabs(ViewPager v){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new Overview(), "Overview");
        adapter.addFrag(new Media_cast(), "Cast");
        adapter.addFrag(new SimilarMovies(), "Similar Movies");


        v.setAdapter(adapter);
    }

    private void setoverview(String s){
        overview = s;
    }
    private void setOriginal_title(String s){
        original_title = s;
    }
    private void setOriginal_lang(String s){
        original_lang = s;
    }
    private void setDirector(String s){
        director = s;
    }
    private void setBudget(String s){
        budget = s;
    }
    private void setRevenue(String s){
        revenue = s;
    }
    private void setHomepage(String s){
        homepage = s;
    }
    private void setProductions(String s){
        productions = s;
    }
    private void setReleases(String s){
        releases = s;
    }

    public String getoverview(){
        return overview;
    }

    public String getOriginal_title(){
        return original_title;
    }
    public String getOriginal_lang(){
        return original_lang;
    }
    public String getDirector(){
        return  director;
    }
    public String getBudget(){
        return  budget;
    }
    public String getRevenue(){
        return revenue ;
    }
    public String getHomepage(){
        return homepage ;
    }
    public String getProductions(){
        return productions;
    }
    public String getReleases(){
       return releases;
    }
    public String getmovie_id(){return movie_id;}
    public String getmedia_type(){return media_type;}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);


        Intent intent = getIntent();
        movie_id = intent.getStringExtra("movie_id");
        media_type = intent.getStringExtra("media_type");

        Log.d("iD" , movie_id);


        RequestQueue queue = Volley.newRequestQueue(this);
        final ImageView banner = findViewById(R.id.banner);

        switch (media_type){
            case "movie" :
                URL_movieinfo = URL_movieinfo1 + movie_id + URL_movieinfo2;

                StringRequest request1 = new StringRequest(URL_movieinfo, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        MovieInfo users = gson.fromJson(response, MovieInfo.class);
                        Toolbar toolbar = findViewById(R.id.media_toolbar);
                        toolbar.setTitle(users.getTitle());
                        genre = users.getGenres();
                        for(int i =0;i<genre.length; i++) {
                            genre_string.add(genre[i].getName());
                        }
                        if(genre_string.size() > 0) {
                            if(genre_string.size() > 1) {
                                toolbar.setSubtitle(users.getReleaseDate().substring(0,4) + " - " + genre_string.get(0) + " / " +genre_string.get(1) + " - " +users.getRuntime());
                            } else{
                                toolbar.setSubtitle(users.getReleaseDate().substring(0,4) + " - " + genre_string.get(0) + " - " +users.getRuntime());
                            }
                        } else{
                            toolbar.setSubtitle(users.getReleaseDate().substring(0,4) + " - " + users.getRuntime());
                        }
                        setSupportActionBar(toolbar);
                        Picasso.with(MediaActivity.this).load(users.getBackdropPath()).placeholder(R.drawable.movie_placeholder2).into(banner);
                        setoverview(users.getOverview());
                        setBudget(String.valueOf(users.getBudget()));
                        setHomepage(users.getHomepage());
                        setOriginal_lang(users.getOriginalLanguage());
                        prods = users.getProductionCompanies();
                        String temp = "";
                        for(int i = 0; i < prods.size();i++){
                            temp += prods.get(i).getName();
                            if(i != prods.size()-1){
                                temp += ", ";
                            }
                        }
                        setProductions(temp);
                        setReleases(users.getReleaseDate());
                        setOriginal_title(users.getOriginalTitle());
                        setRevenue(String.valueOf(users.getRevenue()));
                        genre = users.getGenres();
                        movie_title = users.getTitle();
                        movie_year = users.getReleaseDate();
                        duration = users.getRuntime();


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MediaActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });



                queue.add(request1);

                break;

            case "tv" :

                URL_tvinfo = URL_tvinfo1 + movie_id + URL_tvinfo2;


                StringRequest request3 = new StringRequest(URL_tvinfo, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        TvInfo users = gson.fromJson(response, TvInfo.class);
                        Toolbar toolbar = findViewById(R.id.media_toolbar);
                        toolbar.setTitle(users.getName());
                        genre_tv = users.getGenres();
                        for(int i =0;i<genre_tv.size(); i++) {
                            genre_string.add(genre_tv.get(i).getName());
                        }
                        if(genre_string.size() > 0) {
                            if(genre_string.size() > 1) {
                                toolbar.setSubtitle(users.getFirstAirDate().substring(0,4) + " - " + genre_string.get(0) + " / " +genre_string.get(1) + " - " +users.getNumberOfSeasons());
                            } else{
                                toolbar.setSubtitle(users.getFirstAirDate().substring(0,4) + " - " + genre_string.get(0) + " - " +users.getNumberOfSeasons());
                            }
                        } else{
                            toolbar.setSubtitle(users.getFirstAirDate().substring(0,4) + " - " + users.getNumberOfSeasons());
                        }
                        setSupportActionBar(toolbar);
                        Picasso.with(MediaActivity.this).load(users.getBackdropPath()).placeholder(R.drawable.movie_placeholder2).into(banner);

                        setoverview(users.getOverview());
                        setHomepage(users.getHomepage());
                        setOriginal_lang(users.getOriginalLanguage());
                        prods = users.getProductionCompanies();
                        String temp = "";
                        for(int i = 0; i < prods.size();i++){
                            temp += prods.get(i).getName();
                            if(i != prods.size()-1){
                                temp += ", ";
                            }
                        }
                        setProductions(temp);
                        setReleases(users.getFirstAirDate());
                        setOriginal_title(users.getOriginalName());


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MediaActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });



                queue.add(request3);

                break;
        }


        viewPager = findViewById(R.id.media_viewpager);
        addtabs(viewPager);

        tabLayout = findViewById(R.id.media_tabs);
        tabLayout.setupWithViewPager(viewPager);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


    }
}
