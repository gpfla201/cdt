package com.hufs.cdt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class option extends AppCompatActivity {
    EditText hopeaddress, hopeprice, option, extrafee;
    String address,price,opt,fee, ipju, roomkind;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option);

        hopeaddress=(EditText)findViewById(R.id.hopeaddress);
        hopeprice=(EditText)findViewById(R.id.hopeprice);
        option=(EditText)findViewById(R.id.realoption);
        extrafee=(EditText)findViewById(R.id.extrafee);


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
        address= hopeaddress.getText().toString();
        price=hopeprice.getText().toString();
        opt=option.getText().toString();
        fee=extrafee.getText().toString();
        ipju=ipjuChecked();
        roomkind=roomkindChecked();


    }



}