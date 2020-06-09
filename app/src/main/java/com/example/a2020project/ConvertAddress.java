package com.example.a2020project;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import java.io.IOException;
import java.util.List;

public class ConvertAddress extends Activity {
    private String address;
    private Context context;
    private double lat;
    private double lon;
    private final Geocoder geocoder;
    private List<Address> list = null;


    ConvertAddress(String Address, Context context) throws IOException {
        this.address = Address;
        this.context = context;
        geocoder = new Geocoder(context);
        transAddress();
    }
    private void transAddress(){
        // 지역 이름
        try{
            list = geocoder.getFromLocationName(address,10); // 읽을 개수
        } catch( IOException e){
            e.printStackTrace();
            Log.e("address fault", "입출력 오류 - 서버에서 주소변환시 에러발생");
        }
        if(list !=null)
        {
            if (list.size() == 0) {
                Log.e("address fault", "해당되는 주소 정보는 없습니다");
            } else {
                // 해당되는 주소로 인텐트 날리기
                Address addr = list.get(0);
                lat = addr.getLatitude();
                lon = addr.getLongitude();
                Log.d("address result","lat: " + lat+ "lon: " + lon);
            }
        }
    }
    double getlat(){
        return lat;
    }
    double getlon(){
        return lon;
    }



}
