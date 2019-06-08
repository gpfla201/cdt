package com.hufs.cdt;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;

import java.util.ArrayList;

public class FindView extends AppCompatActivity implements OnMapReadyCallback {

    private EditText test;


    String myaddresss;
    String addr;//지번주소
    String jibun;//지번 주소
    String specefic;//상세 내용
    String price;//가격
    String floor;//층수
    String option;//옵션
    String guan;//관리비
    String parking;//주차비
    String date;//날짜
    String ipju;
    String roomkind;//방 종류
    String seol;//설명
    String roomid;//작성자 Id
    String room;
    String x;
    String y;
    String nadd;
    TextView aid ,ajibunaddr, aspec, aprice, afloor , aroom, aoption, aguan, aparking, adate, aipju, aroomkind, aseol;

    //Firebase Storage
    FirebaseStorage storage = FirebaseStorage.getInstance();
    // Create a storage reference from our app
    StorageReference storageRef = storage.getReference();
    // Create a reference with an initial file path and name
    StorageReference pathReference;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_view);


        test=(EditText)findViewById(R.id.test);
        TextView myaddress=(TextView)findViewById(R.id.editText);
        Button review_btn = (Button)findViewById(R.id.login_btn);
        Button home_btn = (Button)findViewById(R.id.home_btn);
        Button call_btn = (Button) findViewById(R.id.call_btn);

        aid=(TextView)findViewById(R.id.search_key);
        ajibunaddr = (TextView)findViewById(R.id.textView11);
        aspec= (TextView)findViewById(R.id.specefic);
        aprice =(TextView)findViewById(R.id.editText6);
        afloor=(TextView)findViewById(R.id.editText2);
        aroom=(TextView)findViewById(R.id.editText3);
        aoption=(TextView)findViewById(R.id.editText4);
        aguan=(TextView)findViewById(R.id.editText5);
        aparking=(TextView)findViewById(R.id.editText1);
        adate=(TextView)findViewById(R.id.editText8);
        aipju=(TextView)findViewById(R.id.textView10);
        aroomkind=(TextView)findViewById(R.id.textView12);
        aseol=(TextView)findViewById(R.id.editText7);


        Intent intent=getIntent();
        myaddresss=intent.getStringExtra("addressname");
        myaddress.setText(myaddresss);
        addr=myaddresss;

        //이미지 받아오기, 글 제목을 기준으로 ID를 받아옵니다.


        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        // ImageView in your Activity

        // Download directly from StorageReference using Glide

        // (See MyAppGlideModule for Loader registration)




        if (mapFragment == null) {

            mapFragment = MapFragment.newInstance();

            getSupportFragmentManager().beginTransaction().add(R.id.map, mapFragment).commit();

        }



        mapFragment.getMapAsync(this);



        //firebase에서 게시글을 받아올 것입니다.
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = database.getReference("postings");

        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot=dataSnapshot.child(myaddresss);
                pathReference = storageRef.child(myaddresss);

                storageRef.child(myaddresss).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Got the download URL for 'users/me/profile.png'
                        ImageView imageView = findViewById(R.id.imageView);

                        Log.d("이미지 URL",pathReference.getDownloadUrl().toString());

                        GlideApp.with(getApplicationContext())
                                .load(uri).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                .error(R.drawable.logo)
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        return false;
                                    }
                                })
                                .into(imageView);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });






                Log.d("이미지 이름은?",myaddresss);
                jibun=dataSnapshot.child("jibun_address").getValue(String.class);
                specefic=dataSnapshot.child("specefic").getValue(String.class);
                room=dataSnapshot.child("room").getValue(String.class);
                addr = dataSnapshot.child("address").getValue(String.class);
                price = dataSnapshot.child("price").getValue(String.class);
                floor = dataSnapshot.child("floor").getValue(String.class);
                option = dataSnapshot.child("option").getValue(String.class);//옵션
                guan = dataSnapshot.child("guan").getValue(String.class);//관리비
                parking = dataSnapshot.child("parking").getValue(String.class);//주차비
                date = dataSnapshot.child("date").getValue(String.class);//날짜
                roomkind = dataSnapshot.child("roomkind").getValue(String.class);//방 종류
                seol = dataSnapshot.child("seol").getValue(String.class);
                ipju=dataSnapshot.child("ipju").getValue(String.class);
                roomid=dataSnapshot.child("uid").getValue(String.class);
                x=dataSnapshot.child("x").getValue(String.class);
                y=dataSnapshot.child("y").getValue(String.class);
                nadd=addr+" " +specefic;
                putThing(room, jibun, specefic, price, floor, option, guan, parking, date, ipju, roomkind, seol, roomid);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(),
                        MainActivity.class);

                startActivity(intent);
            }
        });

        call_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String id = nadd;
                //String name = "혜선";
                Log.d("넘어가는 아이디",nadd);
                Log.d("넘어가는 아이디 그냥 아이디",id);
                Intent intent = new Intent(FindView.this, StartActivity.class);
                intent.putExtra("chatName", id);
                //intent.putExtra("userName", name);
                startActivity(intent);
            }
        });





    }
    @UiThread

    @Override

    public void onMapReady ( @NonNull final NaverMap naverMap){
        double xandyx=Double.parseDouble(x);
        double xandyy=Double.parseDouble(y);

        final Marker marker1 = new Marker();
        LatLng k = new LatLng((double) xandyy, (double) xandyx);

        marker1.setPosition(k);

        marker1.setMap(naverMap);

        naverMap.moveCamera(CameraUpdate.scrollTo(k));




    }


    //글들을 폼에 넣는 것입니다.
    public void putThing(String room, String jibun,String specefic, String price, String floor, String option, String guan, String parking, String date, String ipju, String roomkind, String seol, String roomid){
        aid.setText(roomid);
        ajibunaddr.setText(jibun);
        aspec.setText(specefic);
        aprice.setText(price);
        afloor.setText(floor);
        aroom.setText(room);
        aoption.setText(option);
        aguan.setText(guan);
        aparking.setText(parking);
        adate.setText(date);
        aipju.setText(ipju);
        aroomkind.setText(roomkind);
        aseol.setText(seol);


    }

}