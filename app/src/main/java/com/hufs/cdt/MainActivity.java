package com.hufs.cdt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView mylistview = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button post_btn2 = (Button)findViewById(R.id.post_btn1);
        Button mypage_btn = (Button)findViewById(R.id.mypage_btn);
        Button search_tv = (Button)findViewById(R.id.search_tv);
        Button msg_btn = (Button)findViewById(R.id.msg_btn);



        post_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(),
                        Post.class);
                startActivity(intent);
            }
        });

        mypage_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(),
                        Mypage.class);
                startActivity(intent);
            }
        });

        search_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(),
                        Search_result.class);
                startActivity(intent);
            }
        });

        msg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(),
                        StartActivity.class);
                startActivity(intent);
            }
        });

        //밑은 ArrayList임
        ArrayList<ItemData> arrayData = new ArrayList<>();

        //myItem에서 아이템을 넣고 확인
        ItemData myItem = new ItemData();

        myItem.strTitle = "아이템 평수";
        myItem.strAddress = "주소";
        arrayData.add(myItem);
        //이걸 DB에 넣고 진행할겁니다.


        mylistview = (ListView)findViewById(R.id.main_list_view);
        ListAdapter oAdapter = new ListAdapter(arrayData);
        mylistview.setAdapter(oAdapter);

    }

    //onCreate
}
