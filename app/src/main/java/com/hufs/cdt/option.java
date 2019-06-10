package com.hufs.cdt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class option extends AppCompatActivity {

    static boolean from_option=false;// 옵션에서 인텐트가 되는 값인가?

    EditText hopeaddress, hopeprice, option, extrafee;
    static String uidd, uids,uid,address,price,opt,fee, ipju, roomkind;
    Button search;
    TextView myid;
    public static DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    public DatabaseReference conditionRef= mRootRef.child("options").push();;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option);
        Intent intent=getIntent();
        //옵션으로부터 이메일을 가져옴
        uid=intent.getStringExtra("email");
        String[] parts = uid.split("@");
        uids = parts[0]; // 아이디부분

        myid=(TextView)findViewById(R.id.myid);
        hopeaddress=(EditText)findViewById(R.id.hopeaddress);
        hopeprice=(EditText)findViewById(R.id.hopeprice);
        option=(EditText)findViewById(R.id.realoption);
        extrafee=(EditText)findViewById(R.id.extrafee);

        myid.setText(uid);
        findViewById(R.id.rightnow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ipjuChecked();
            }
        });
        findViewById(R.id.nego).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ipjuChecked();
            }
        });
        findViewById(R.id.oneroom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomkindChecked();
            }
        });
        findViewById(R.id.tworoom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomkindChecked();
            }
        });
        findViewById(R.id.etcroom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomkindChecked();
            }
        });

        search=(Button)findViewById(R.id.search_match);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchmatch();
            }
        });




    }



    public String ipjuChecked(){
        CheckBox checkBox=(CheckBox)findViewById(R.id.rightnow);
        CheckBox checkBox2=(CheckBox)findViewById(R.id.nego);


        String resultText = "";

        if(checkBox.isChecked()){
            resultText=checkBox.getText().toString();
        }
        if(checkBox2.isChecked()){
            resultText=checkBox2.getText().toString();
        }

        return resultText;
    }

    public String roomkindChecked(){
        CheckBox checkBox=(CheckBox)findViewById(R.id.oneroom);
        CheckBox checkBox2=(CheckBox)findViewById(R.id.tworoom);
        CheckBox checkBox3=(CheckBox)findViewById(R.id.etcroom);


        String resultText = "";

        if(checkBox.isChecked()){
            resultText=checkBox.getText().toString();
        }
        if(checkBox2.isChecked()){
            resultText=checkBox2.getText().toString();
        }
        if(checkBox3.isChecked()){
            resultText=checkBox3.getText().toString();
        }

        return resultText;
    }




//검색하기 시작
    public void searchmatch(){
        //uid도 넣어야함
        uidd=myid.getText().toString();
        address= hopeaddress.getText().toString();
        price=hopeprice.getText().toString();
        opt=option.getText().toString();
        fee=extrafee.getText().toString();
        ipju=ipjuChecked();
        roomkind=roomkindChecked();

        // 옵션 정보들을 DB에 올려버림
        makeoption myop= new makeoption(uidd, address, price, opt, fee, ipju, roomkind);

        Map<String, Object> postValues = myop.toMap();
        Map<String, Object> childUpdates = new HashMap<>();

        childUpdates.put("options"+"/"+uids,postValues);
        //DB에 올라감


        //앱에 내용 추가하는거임
        mRootRef.updateChildren(childUpdates);


        Intent intent =new Intent(getApplicationContext(),MainActivity.class);
        from_option=true;
        intent.putExtra("uidemail",uids);
        intent.putExtra("uemail",uid);
        intent.putExtra("inputaddress",address);
        intent.putExtra("inputprice",price);
        intent.putExtra("inputoption",opt);
        intent.putExtra("inputfee",fee);
        intent.putExtra("inputipju",ipju);
        intent.putExtra("inputroomkind",roomkind);
        startActivityForResult(intent,15);

    }



}