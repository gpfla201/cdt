package com.hufs.cdt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Mypage extends AppCompatActivity{

    public static String strEmail;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);

        Intent intent= getIntent();
        strEmail = intent.getStringExtra("email");

        TextView textView=(TextView)findViewById(R.id.login_id);
        textView.setText(getmyid());


        Button post_btn1 = (Button)findViewById(R.id.post_btn1);
        Button login_btn = (Button)findViewById(R.id.login_btn);
        Button home = (Button)findViewById(R.id.action_btn);


        post_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =new Intent(getApplicationContext(),search_result_list.class);
                Log.e("inputaddress",strEmail);
                intent.putExtra("inputaddress",strEmail);
                startActivityForResult(intent,3);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(),
                        Login.class);
                startActivity(intent);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(),
                        MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public static final String getmyid(){
        return strEmail;
    }
}
