package com.example.a2020project;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;

import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

public class FragmentHome extends Fragment implements OnMapReadyCallback{

    ImageButton gpsBtn;
    MapView mapView;
    private final int PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private final int PERMISSIONS_ACCESS_COARSE_LOCATION = 1001;
    private boolean isAccessFineLocation = false;
    private boolean isAccessCoarseLocation = false;
    private boolean isPermission = false;
    private FusedLocationSource locationSource;
    private NaverMap naverMap;
    private GpsInfo gps;
    Marker marker = new Marker();

    LatLng coord = new LatLng(34.5670135, 126.9783740);

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        //return inflater.inflate(R.layout.fragment_home, container, false);
        View v = inflater.inflate(R.layout.fragment_home, container, false);


        gpsBtn = (ImageButton)v.findViewById(R.id.GPSButton);
        gpsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getActivity(), GPSActivity.class);
                LatLng coord = new LatLng(34.5670135, 126.9783740);
                if (!isPermission) {
                    callPermission();
                    Log.d("GPS TEST:","check2");
                    return;
                }

                gps = new GpsInfo(getActivity());
                // GPS 사용유무 가져오기

                if (gps.isGetLocation()) {
                    Log.d("GPS TEST:","check3");
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    LatLng coord2 = new LatLng(latitude, longitude);
                    coord = coord2;
                } else {
                    // GPS 를 사용할수 없으므로
                    gps.showSettingsAlert();
                }
                marker.setPosition(coord);
                marker.setMap(naverMap);
                naverMap.moveCamera(CameraUpdate.scrollTo(coord));
                //startActivity(intent);
            }
        });
        mapView = (MapView)v.findViewById(R.id.naver_map);
        mapView.getMapAsync(this);
        //
        callPermission();
        return v;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_ACCESS_FINE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            isAccessFineLocation = true;

        } else if (requestCode == PERMISSIONS_ACCESS_COARSE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            isAccessCoarseLocation = true;
        }

        if (isAccessFineLocation && isAccessCoarseLocation) {
            isPermission = true;
        }
    }

    private void callPermission() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_ACCESS_FINE_LOCATION);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_ACCESS_COARSE_LOCATION);
        } else {
            isPermission = true;
        }
    }



    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;
        naverMap.setLocationSource(locationSource);
    }

}
