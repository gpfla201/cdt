package com.hufs.cdt;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

//firebase DB에서 글을 받아오는 부분입니다.
public class searchfirebase {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mydbcall = database.getReference("https://cdtproject-223cc.firebaseio.com/cdtproject-223cc/postings");

    ArrayList returnvalue= new ArrayList();

    String searchKey="";

    //생성자
    public searchfirebase(String skey){
        this.searchKey= skey;
    }

    //검색기능 주소를 바탕으로 검색해서 결과값을 보여줘야함.

    public ArrayList<String> getstuff(){

        return returnvalue;
    }
}
