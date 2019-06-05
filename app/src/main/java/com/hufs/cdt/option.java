package com.hufs.cdt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

public class option extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option);
        EditText hopeaddress=(EditText)findViewById(R.id.hopeaddress);
        EditText hopeprice=(EditText)findViewById(R.id.hopeprice);
        EditText option=(EditText)findViewById(R.id.realoption);
        EditText extrafee=(EditText)findViewById(R.id.extrafee);
        EditText period=(EditText)findViewById(R.id.period);
        Button yesparking=(Button)findViewById(R.id.parkingyes);
        Button noparking=(Button)findViewById(R.id.parkingno);
        Button button1= (Button)findViewById(R.id.oneroom);
        Button button2=(Button)findViewById(R.id.tworoom);
        Button buttonetc=(Button)findViewById(R.id.etcroom);

    }

    }
