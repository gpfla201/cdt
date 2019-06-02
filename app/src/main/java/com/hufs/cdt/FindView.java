package com.hufs.cdt;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FindView extends AppCompatActivity {

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
    TextView aid ,ajibunaddr, aspec, aprice, afloor , aroom, aoption, aguan, aparking, adate, aipju, aroomkind, aseol;
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


        //firebase에서 게시글을 받아올 것입니다.
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = database.getReference("postings");

        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot=dataSnapshot.child(myaddresss);
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
                putThing(room, jibun, specefic, price, floor, option, guan, parking, date, ipju, roomkind, seol, roomid);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        review_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(),
                        Review.class);

                startActivity(intent);

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
                String id = test.getText().toString();
                String name = "혜선";

                Intent intent = new Intent(FindView.this, StartActivity.class);
                intent.putExtra("chatName", id);
                intent.putExtra("userName", name);
                startActivity(intent);
            }
        });





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