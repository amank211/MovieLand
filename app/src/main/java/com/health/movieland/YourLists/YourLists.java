package com.health.movieland.YourLists;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.health.movieland.Discover.DiscoverMovie;
import com.health.movieland.Discover.DiscoverTv;
import com.health.movieland.Discover.GenreList;
import com.health.movieland.Discover.Genre_ListAdapter;
import com.health.movieland.Discover.Result_movie;
import com.health.movieland.Discover.Result_tv;
import com.health.movieland.Discover.discover_movie_Adapter;
import com.health.movieland.Discover.discover_tv_Adapter;
import com.health.movieland.Login.Session;
import com.health.movieland.MainActivity;
import com.health.movieland.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class YourLists extends Fragment {

    private static String URL_lists = "https://api.themoviedb.org/3/account/{account_id}/lists?api_key=223444c9a68bd2763fbf89598bb95134&language=en-US&page=1&session_id=";
    public static String session = "session_id";
    private List<Result> data;
    private UserListsAdapter adapter;
    private Button create_list;
    private String lang = "en";
    private String name,descr = "";
    private Button create, cancel;
    private EditText name_text, descr_text;
    private static String URL_create = "https://api.themoviedb.org/3/list?api_key=223444c9a68bd2763fbf89598bb95134&session_id=";

    public YourLists(){}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.your_lists, container, false);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        String session_id = prefs.getString(session, "NA");
        URL_lists += session_id;
        URL_create += session_id;
        create_list = v.findViewById(R.id.create_list);

        final RequestQueue queue = Volley.newRequestQueue(getContext());


        final RecyclerView lists = v.findViewById(R.id.lists_rec);
        lists.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        final StringRequest request2 = new StringRequest(URL_lists, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                UserLists list = gson.fromJson(response, UserLists.class);
                data = list.getResults();
                adapter = new UserListsAdapter(getContext(), data);
                lists.setAdapter(adapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request2);

        LayoutInflater inflater_pop = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater_pop.inflate(R.layout.createlist_popup, null);
        int widthl = LinearLayout.LayoutParams.WRAP_CONTENT;
        int heightl = LinearLayout.LayoutParams.WRAP_CONTENT;
        cancel = popupView.findViewById(R.id.cancel);
        create = popupView.findViewById(R.id.create);
        name_text = popupView.findViewById(R.id.name);
        descr_text = popupView.findViewById(R.id.descr);



        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, widthl, heightl, focusable);

        create_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                create.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        name = name_text.getText().toString();
                        if(Objects.equals(name,"")){
                            Toast.makeText(getContext(), "Please fill the name block", Toast.LENGTH_SHORT).show();
                        } else {
                            pushnewitem(name, descr, lang, queue);
                            popupWindow.dismiss();
                        }
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
            }
        });



        return v;
    }
    public void pushnewitem(String name, String descr, String lang, RequestQueue queue){
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("name", name);
            jsonBody.put("description", descr);
            jsonBody.put("language", lang);
            final String mRequestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_create, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    Response_newList session = gson.fromJson(response, Response_newList.class);
                    Boolean success = session.getSuccess();
                    String message = session.getStatusMessage();
                    if(success){
                        Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
                    } else{
                        Toast.makeText(getContext(), "Unsuccessful", Toast.LENGTH_SHORT).show();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_RESPONSE", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    try{
                        if (response != null) {

                            responseString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                        }
                    } catch (Exception e){
                        return Response.error(new ParseError(e));
                    }

                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            queue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
