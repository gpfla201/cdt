package com.hufs.cdt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class search_result_list extends AppCompatActivity {

    static String inputaddress;
    private ListView mylistview = null;


    ArrayList<ItemData> arrayData = new ArrayList<>();
    ListAdapter oAdapter;
    ItemData myItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_list);

        Intent intent=getIntent();
        inputaddress=intent.getStringExtra("inputaddress");

        mylistview=(ListView)findViewById(R.id.search_list_view);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = database.getReference("postings");

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // 클래스 모델이 필요?
                for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {

                    String myaddress = fileSnapshot.child("address").getValue(String.class);
                    String spece= fileSnapshot.child("specefic").getValue(String.class);
                    myaddress=myaddress+" "+spece;
                    String roadaddress = fileSnapshot.child("jibun_address").getValue(String.class);
                    String price = fileSnapshot.child("price").getValue(String.class);
                    //받아온 주소랑 가격을 받아옴

                    if(myaddress.contains(inputaddress)||roadaddress.contains(inputaddress)) {
                    myItem=new ItemData();
                    myItem.strTitle = myaddress;
                    myItem.strAddress =price;
                    Log.i("주소",myItem.strTitle);
                    Log.d("가격",myItem.strAddress);
                    Log.d("판별", String.valueOf(myaddress.contains(inputaddress)));
                    Log.d("인풋",inputaddress);
                    Log.d("지번주소",myaddress);
                    Log.d("도로명 주소",roadaddress);
                        arrayData.add(myItem);
                    }
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
                //String selected_item = (String)adapterView.getItemAtPosition(position);


                TextView address=(TextView)view.findViewById(R.id.textTitle);
                String s= address.getText().toString();

                //텍스트뷰에 출력
                Intent intent= new Intent(search_result_list.this,FindView.class);
                intent.putExtra("addressname",s);
                Log.e("넘어가는 주소는",s);
                startActivityForResult(intent,0);
            }
        });

    }



}
