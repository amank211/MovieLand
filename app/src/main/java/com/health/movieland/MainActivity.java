package com.health.movieland;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
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
import com.health.movieland.Celebs.Celebs;
import com.health.movieland.Discover.Discover;
import com.health.movieland.Home.Home;
import com.health.movieland.Login.Token;
import com.health.movieland.Movies.Movies;
import com.health.movieland.TV.TV;
import com.health.movieland.User_info.UserInfo;
import com.health.movieland.YourLists.YourLists;

import java.util.List;

import static com.google.android.gms.ads.MobileAds.*;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private AdView mAdView;

    public static String logged_in = "Logged_in";
    public static String session = "session_id";
    public static String id = "user_id";
    public static String name = "user_name";
    private TextView user;

    private static String URL_user = "https://api.themoviedb.org/3/account?api_key=223444c9a68bd2763fbf89598bb95134&session_id=";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String session_id = prefs.getString(session, "NA");
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("MOVIE LAND");
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.viewpager);
        addtabs(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        ImageView search = findViewById(R.id.search);
        user = findViewById(R.id.user);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, authenticate.class);
                startActivity(intent);
            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,requestCode,data);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        if (requestCode == 311) {
            if (resultCode == RESULT_OK) {
                String session_id = data.getData().toString();
                Log.d("session_id", session_id);
                SharedPreferences.Editor edit = prefs.edit();
                edit.putString(session, session_id);
                edit.putBoolean(logged_in, Boolean.TRUE);
                edit.commit();
                URL_user += session_id;
                user_info();
            } else{
                Log.d("session_id", "canceled");
            }
        }
    }

    public void user_info(){
        final RequestQueue queue = Volley.newRequestQueue(this);
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        StringRequest request1 = new StringRequest(URL_user, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("token_response", response);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                UserInfo userinfo = gson.fromJson(response, UserInfo.class);
                SharedPreferences.Editor edit = prefs.edit();
                edit.putInt(id, userinfo.getId());
                edit.putString(name, userinfo.getUsername());
                edit.commit();
                user.setText(userinfo.getUsername());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request1);
    }



    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean previouslyStarted = prefs.getBoolean(logged_in, false);
        String username = prefs.getString(name, "NA");
        int user_id = prefs.getInt(id, 0);
        String session_id = prefs.getString(session, "NA");
        if (!previouslyStarted) {
            Log.d("activity_time", "1");
            Intent intent = new Intent(MainActivity.this, authenticate.class);
            startActivityForResult(intent,311);
        }
        if(username != "NA"){
            user.setText(username);
        }
    }


    private void addtabs(ViewPager v){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new Home(), "Movies");
        adapter.addFrag(new TV(), "TV");
        adapter.addFrag(new Discover(), "Discover");
        adapter.addFrag(new Celebs(), "Celebs");
        adapter.addFrag(new YourLists(), "List");


        v.setAdapter(adapter);
    }
}
