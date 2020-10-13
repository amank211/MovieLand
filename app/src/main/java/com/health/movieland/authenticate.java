package com.health.movieland;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

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
import com.health.movieland.Login.Session;
import com.health.movieland.Login.Token;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Objects;

public class authenticate extends AppCompatActivity {
    private TextView url;
    private String URL_token = "https://api.themoviedb.org/3/authentication/token/new?api_key=223444c9a68bd2763fbf89598bb95134";
    private String request_token;
    private Boolean token_success;
    private String perm;
    private int last_index;
    private String URL_session = "https://api.themoviedb.org/3/authentication/session/new?api_key=223444c9a68bd2763fbf89598bb95134";
    private Intent data = new Intent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate);
        final WebView mywebview = (WebView) findViewById(R.id.webView);
        url = findViewById(R.id.url);
        url.setText(mywebview.getUrl());
        mywebview.getSettings().setJavaScriptEnabled(true);
        final RequestQueue queue = Volley.newRequestQueue(this);


        mywebview.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String urlto) {

                try {
                    last_index = urlto.lastIndexOf('/');
                    perm = urlto.substring(last_index);
                    url.setText(urlto);
                    if(Objects.equals(perm,"/allow")){
                        try {
                            JSONObject jsonBody = new JSONObject();
                            jsonBody.put("request_token", request_token);
                            final String mRequestBody = jsonBody.toString();

                            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_session, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    GsonBuilder gsonBuilder = new GsonBuilder();
                                    Gson gson = gsonBuilder.create();
                                    Session session = gson.fromJson(response, Session.class);
                                    String session_id = session.getSessionId();
                                    String text = session_id;
                                    data.setData(Uri.parse(text));
                                    setResult(RESULT_OK, data);
                                    finish();
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
                    if(Objects.equals(perm,"/deny")){
                        setResult(RESULT_CANCELED, data);
                        finish();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }

        });



        StringRequest request1 = new StringRequest(URL_token, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("token_response", response);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                Token token = gson.fromJson(response, Token.class);
                token_success = (Boolean) token.getSuccess();
                if(token_success){
                    request_token = token.getRequestToken();
                    mywebview.loadUrl("https://www.themoviedb.org/authenticate/" + request_token);
                } else{
                    Toast.makeText(authenticate.this, "Token not generated please refresh the page", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(authenticate.this, "something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request1);









    }
}
