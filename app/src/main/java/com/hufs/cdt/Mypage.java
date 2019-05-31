package com.hufs.cdt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Mypage extends AppCompatActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);
        Intent intent= getIntent();
        String strEmail = intent.getStringExtra("email");
        TextView textView=(TextView)findViewById(R.id.login_id);
        textView.setText(strEmail);


        Button post_btn1 = (Button)findViewById(R.id.post_btn1);
        Button login_btn = (Button)findViewById(R.id.login_btn);



        post_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(),
                        FindView.class);
                startActivity(intent);
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
    }
}
