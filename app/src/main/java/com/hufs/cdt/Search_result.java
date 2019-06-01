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

public class Search_result extends AppCompatActivity implements OnMapReadyCallback{

    final static int myrequestCode=11;
    private ProgressDialog progressDialog;
    public static final int LOAD_SUCCESS = 101;

    private boolean positionFlag;

    LatLng w;

    public static double xandyx=0;

    public static double xandyy=0;
    private ProgressDialog pDialog;
    private TextView JSONText;
    public String addr;
    private TextView query;
    int a;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.search_completion);
        JSONText = (TextView)findViewById(R.id.textview_main_jsontext);
        query=(TextView)findViewById(R.id.query);
        query.setMovementMethod(new ScrollingMovementMethod());


        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map);



        if (mapFragment == null) {

            mapFragment = MapFragment.newInstance();

            getSupportFragmentManager().beginTransaction().add(R.id.map, mapFragment).commit();

        }





        mapFragment.getMapAsync(this);



    }



    public void thrun(){





        Thread thread= new Thread(new Runnable() {

            private boolean positionFlag;

            private void getPoint(String... addr) {

                geo geo = new geo(Search_result.this, listener);

                geo.execute(addr);

            }



            private geo.OnGeoPointListener listener = new geo.OnGeoPointListener() {

                @Override

                public void onPoint(geo.Point[] p) {

                    int sCnt = 0;

                    for (geo.Point point : p) {



                        if (point.havePoint) sCnt++;



                        double k=  point.x;

                        double d=  point.y;



                        xandyx=point.x;

                        xandyy=point.y;


                    }



                }



                @Override

                public void onProgress(int progress, int max) {



                }

            };

            @Override

            public void run() {

                final EditText editText=(EditText)findViewById(R.id.search_ettx);

                final String c=editText.getText().toString();



                getPoint(

                        c



                );
                addr=c;


            }

        });
        thread.start();

    }

    @UiThread

    @Override

    public void onMapReady ( @NonNull final NaverMap naverMap){


        final  Marker marker1 = new Marker();

        Button button =(Button)findViewById(R.id.search_btn3);



        button.setOnClickListener(new View.OnClickListener() {



            @Override

            public void onClick(View v) {

                thrun();
                getJSON();

                LatLng k = new LatLng((double) xandyy, (double) xandyx);

                marker1.setPosition(k);

                marker1.setMap(naverMap);

                naverMap.moveCamera(CameraUpdate.scrollTo(k));
            }

        });




    }




    private final MyHandler mHandler = new MyHandler(this);


    private static class MyHandler extends Handler {
        private final WeakReference<Search_result> weakReference;

        public MyHandler(Search_result Search_result) {
            weakReference = new WeakReference<Search_result>(Search_result);
        }

        @Override
        public void handleMessage(Message msg) {

            Search_result Search_result= weakReference.get();

            if (Search_result!= null) {
                switch (msg.what) {

                    case LOAD_SUCCESS:


                        String jsonString = (String)msg.obj;

                        Search_result.query.setText(jsonString);



                        break;
                }
            }
        }
    }




    public void  getJSON() {

        Thread thread = new Thread(new Runnable() {

            public void run() {

                String result;
                String clientId = "u9zcc3txnl";// 애플리케이션 클라이언트 아이디값";

                String clientSecret = "u4Mi9iOJWcFa2qlUhAo15AsZ6iTwHTxuolzTLGcW";// 애플리케이션 클라이언트 시크릿값";
                try {

                    addr = URLEncoder.encode(addr, "UTF-8");

                    String apiURL = "https://naveropenapi.apigw.ntruss.com/map-place/v1/search?query="+addr+"&coordinate="+xandyx+","+xandyy; // json

                    // String apiURL =

                    // "https://openapi.naver.com/v1/map/geocode.xml?query=" + addr; //

                    // xml

                    URL url = new URL(apiURL);

                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("GET");

                    con.setRequestProperty("X-NCP-APIGW-API-KEY-ID",clientId );

                    con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);

                    int responseCode = con.getResponseCode();
                    InputStream inputStream;
                    if (responseCode == HttpURLConnection.HTTP_OK) {

                        inputStream = con.getInputStream();
                    } else {
                        inputStream = con.getErrorStream();

                    }


                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    StringBuilder sb = new StringBuilder();
                    String line;


                    while ((line = bufferedReader.readLine()) != null) {
                        sb.append(line);
                    }

                    bufferedReader.close();
                    con.disconnect();

                    result = sb.toString().trim();




                } catch (Exception e) {
                    result = e.toString();
                }

                String json=result;
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    String predictions = jsonObject.getString("places");
                    JSONArray jsonArray = new JSONArray(predictions);

                    JSONObject subJsonObject = jsonArray.getJSONObject(0);
                    String jibun = subJsonObject.getString("jibun_address");
                    String road = subJsonObject.getString("road_address");
                    double x=subJsonObject.getDouble("x");
                    double y=subJsonObject.getDouble("y");
                    String k= String.valueOf(x);
                    String w= String.valueOf(y);
                    String aqw=jibun+"\n"+road+"\n"+k+"\n"+w;
                    Message message = mHandler.obtainMessage(LOAD_SUCCESS,aqw);
                    mHandler.sendMessage(message);



                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }

        });
        thread.start();

    }


}