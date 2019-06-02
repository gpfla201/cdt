package com.hufs.cdt;



import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;



import android.content.Intent;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.text.method.ScrollingMovementMethod;
import android.view.View;

import android.widget.Button;



import android.annotation.SuppressLint;

import android.content.Context;

import android.content.SharedPreferences;

import android.os.AsyncTask;

import android.os.Handler;

import android.os.Message;

import android.support.annotation.NonNull;

import android.support.annotation.UiThread;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;

import android.widget.Button;

import android.widget.EditText;

import android.widget.TextView;

import android.widget.Toast;



import com.naver.maps.geometry.LatLng;

import com.naver.maps.map.CameraUpdate;

import com.naver.maps.map.MapFragment;

import com.naver.maps.map.NaverMap;

import com.naver.maps.map.OnMapReadyCallback;

import com.naver.maps.map.overlay.Marker;



import android.app.ProgressDialog;



import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;

import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;

import java.net.MalformedURLException;

import java.net.URL;

import java.net.URLEncoder;




import javax.net.ssl.HttpsURLConnection;

public class Search_result extends AppCompatActivity{

    String inputtext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_completion);



        Button search=(Button)findViewById(R.id.search_btn3) ;

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText text = (EditText)findViewById(R.id.search_ettx);
                inputtext=(String)text.getText().toString();
                Intent intent =new Intent(getApplicationContext(),search_result_list.class);
                Log.e("inputaddress",inputtext);
                intent.putExtra("inputaddress",inputtext);
                startActivityForResult(intent,2);
            }
        });

    }




}