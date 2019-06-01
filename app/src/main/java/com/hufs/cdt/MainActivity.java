package com.hufs.cdt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
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




        mylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {

                //클릭한 아이템의 문자열을 가져옴
                String selected_item = (String)adapterView.getItemAtPosition(position);

                TextView address=(TextView)findViewById(R.id.textTitle) ;
                String s= address.getText().toString();
                //텍스트뷰에 출력
                Intent intent= new Intent(MainActivity.this,FindView.class);
                intent.putExtra("addressname",s);
                startActivityForResult(intent,0);
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


    }
    //onCreate

}
