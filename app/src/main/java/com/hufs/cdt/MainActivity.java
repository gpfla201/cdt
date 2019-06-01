package com.hufs.cdt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mylistview = null;


    ArrayList<ItemData> arrayData = new ArrayList<>();
    static boolean calledAlready = false;
    ListAdapter oAdapter;
    ItemData myItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //firebase를 부르기
        if (!calledAlready)
        {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true); // 다른 인스턴스보다 먼저 실행되어야 한다.
            calledAlready = true;
        }

        mylistview = (ListView)findViewById(R.id.main_list_view);

        Button post_btn2 = (Button)findViewById(R.id.post_btn1);
        Button mypage_btn = (Button)findViewById(R.id.mypage_btn);
        Button search_tv = (Button)findViewById(R.id.search_tv);
        Button msg_btn = (Button)findViewById(R.id.msg_btn);



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = database.getReference("postings");





        //postings의 데이터들에 대해서
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // 클래스 모델이 필요?
                for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {

                    String myaddress = fileSnapshot.child("address").getValue(String.class);
                    String price = fileSnapshot.child("price").getValue(String.class);
                    //받아온 주소랑 가격을 받아옴
                    myItem=new ItemData();
                    myItem.strTitle = myaddress;
                    myItem.strAddress =price;
                    Log.i("주소",myItem.strTitle);
                    Log.d("가격",myItem.strAddress);
                    arrayData.add(myItem);
                }
                oAdapter = new ListAdapter(arrayData);
                mylistview.setAdapter(oAdapter);
                oAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("TAG: ", "Failed to read value", databaseError.toException());
            }
        });












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












/*
        //밑은 ArrayList임
        ArrayList<ItemData> arrayData = new ArrayList<>();

        //myItem에서 아이템을 넣고 확인
        ItemData myItem = new ItemData();

        myItem.strTitle = "아이템 평수";
        myItem.strAddress = "주소";
        arrayData.add(myItem);
        //이걸 DB에 넣고 진행할겁니다.
*/

        //raw한 데이터를 넣는 방법은 밑과 같다.
        //ListAdapter oAdapter = new ListAdapter(arrayData);
        //mylistview.setAdapter(oAdapter);

    }

    //onCreate
}
