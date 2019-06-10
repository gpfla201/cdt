package com.hufs.cdt;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView mylistview = null;


    ArrayList<ItemData> arrayData = new ArrayList<>();

    ListAdapter oAdapter;
    ItemData myItem;
    static String  inputaddress,inputoption,inputprice,inputfee,inputipju,inputroomkind;
    public static final String inputemail=option.uid;//이메일 주소
    public static final String inputid=option.uids;//내 아이디

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mylistview = (ListView)findViewById(R.id.main_list_view);

        Button post_btn2 = (Button)findViewById(R.id.post_btn1);
        Button mypage_btn = (Button)findViewById(R.id.mypage_btn);
        Button search_tv = (Button)findViewById(R.id.search_tv);
        Button search_tv1 = (Button)findViewById(R.id.search_tv1);



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = database.getReference("postings");

        DatabaseReference optRef=database.getReference("options"+"/"+inputid);

        optRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    //옵션값을 받아옴.
                    inputaddress=dataSnapshot.child("address").getValue(String.class);
                    Log.d("inputaddress 주소 잘 받아왔냐",inputaddress);
                    inputoption=dataSnapshot.child("option").getValue(String.class);
                    inputprice=dataSnapshot.child("price").getValue(String.class);
                    inputfee=dataSnapshot.child("guan").getValue(String.class);
                    inputipju=dataSnapshot.child("ipju").getValue(String.class);
                    inputroomkind=dataSnapshot.child("roomkind").getValue(String.class);


                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        //postings의 데이터들에 대해서
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
                    String fee=fileSnapshot.child("guan").getValue(String.class);
                    String ipju = fileSnapshot.child("ipju").getValue(String.class);
                    String room =fileSnapshot.child("roomkind").getValue(String.class);
                    String option=fileSnapshot.child("option").getValue(String.class);


                    //옵션 값


                    if (myaddress.contains(inputaddress)&&price.contains(inputprice)&&option.contains(inputoption)&&fee.contains(inputfee)&&ipju.contains(inputipju)&&room.contains(inputroomkind)) {

                        myItem = new ItemData();
                        myItem.strTitle = myaddress;
                        myItem.strAddress = price;
                        Log.i("주소", myItem.strTitle);
                        Log.d("가격", myItem.strAddress);
                        Log.d("판별", String.valueOf(myaddress.contains(inputaddress)));
                        Log.d("인풋", inputaddress);
                        Log.d("지번주소", myaddress);
                        Log.d("도로명 주소", roadaddress);
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
                Intent intent= new Intent(MainActivity.this,FindView.class);
                intent.putExtra("addressname",s);
                Log.e("넘어가는 주소는",s);
                startActivityForResult(intent,0);
            }
        });




        post_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Mypage.strEmail!=null||inputid!=null){
                Intent intent = new Intent(
                        getApplicationContext(),
                        Post.class);
                intent.putExtra("email",inputid);
                intent.putExtra("useremail",inputemail);
                startActivity(intent);
            }
            else{
                Toast.makeText(getApplicationContext(), "로그인이 필요합니다.", Toast.LENGTH_SHORT).show(); //로그아웃 Toast 메세지
                }
            }
        });

        mypage_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(),
                        Mypage.class);
                        intent.putExtra("email",inputid);
                        intent.putExtra("useremail",inputemail);
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

        search_tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(),
                        Search_result.class);
                startActivity(intent);

            }
        });



    }
    //onCreate

}
