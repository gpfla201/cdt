package com.hufs.cdt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FindView extends AppCompatActivity {

    private EditText test;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_view);

        test=(EditText)findViewById(R.id.test);

        Button review_btn = (Button)findViewById(R.id.login_btn);
        Button home_btn = (Button)findViewById(R.id.home_btn);
        Button call_btn = (Button) findViewById(R.id.call_btn);

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
}