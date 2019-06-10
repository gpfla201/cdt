package com.hufs.cdt;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class makeoption {

    public String id;
    public String address;
    public String price;
    public String option;
    public String guan;
    public String ipju;
    public String roomkind;

    public makeoption(){
    }

    public  makeoption(String id, String address,String price, String option, String guan,String ipju,String roomkind){
        this.id=id;
        this.address=address;
        this.price=price;
        this.option=option;
        this.guan=guan;
        this.ipju=ipju;
        this.roomkind=roomkind;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("uid", id);
        result.put("address", address);
        result.put("price", price);
        result.put("option", option);
        result.put("guan",guan);
        result.put("ipju",ipju);
        result.put("roomkind",roomkind);

        return result;
    }
}
