package com.example.a2020project;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.chaquo.python.PyObject;
import com.example.a2020project.Json.CategoryJson;
import com.example.a2020project.Recycler.CategoryAdapter;
import com.example.a2020project.Recycler.CategoryRow;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;

import java.util.ArrayList;
import java.util.Vector;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class FragmentHome extends Fragment implements OnMapReadyCallback {

    ImageButton gpsBtn;
    private MapView mapView;
    private final int PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private final int PERMISSIONS_ACCESS_COARSE_LOCATION = 1001;
    private boolean isAccessFineLocation = false;
    private boolean isAccessCoarseLocation = false;
    private boolean isPermission = false;
    private FusedLocationSource locationSource;
    private NaverMap naverMap;
    private GpsInfo gps;
    private Marker marker = new Marker();
    private Marker restMaker = new Marker();

    ;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_home, container, false);
        View v = inflater.inflate(R.layout.fragment_home, container, false);


        //ID of user
        String userKID = "sadasp";//(kimMunSeok)

        RecommedationSystemMachineLearning recommedationSystemMachineLearning = new RecommedationSystemMachineLearning();// Class to handle connection between python and android studio
        recommedationSystemMachineLearning.initiallizePython(this.getContext()); //We need to initiallize it
        JSONObject recommendedResults = recommedationSystemMachineLearning.runModel(userKID); //The recommended results are in the form of JSON


        ArrayList<CategoryRow> categoryInfoArrayList = new ArrayList<>(); // To store all the results



        try {
            JSONObject restaurantName = (JSONObject) recommendedResults.get("restaurantName");
            JSONObject ownerName = (JSONObject) recommendedResults.get("ownerName");
            JSONObject category = (JSONObject) recommendedResults.get("category");
            JSONObject restaurantLongitude = (JSONObject) recommendedResults.get("restaurantLongitude");
            JSONObject restaurantLatitude = (JSONObject) recommendedResults.get("restaurantLatitude");
            JSONObject reservedSeat = (JSONObject) recommendedResults.get("reservedSeat");
            JSONObject availableSeat = (JSONObject) recommendedResults.get("availableSeat");

            JSONArray keys = restaurantName.names();

            //Log.d("Python Test:", temp.toString() );

            int i = 0;
            while(i < keys.length()) {
                String rName = (String) restaurantName.getString(keys.get(i).toString());
                String oName = (String) ownerName.getString(keys.get(i).toString());
                String cat = (String) category.getString(keys.get(i).toString());
                String restLongitude = (String) restaurantLongitude.getString(keys.get(i).toString());
                String restLatitude = (String) restaurantLatitude.getString(keys.get(i).toString());
                String rSeat = (String) reservedSeat.getString(keys.get(i).toString());
                String aSeat = (String) availableSeat.getString(keys.get(i).toString());
                i++;
                Log.e("Python recommendation","i: " + i + " " +rName + " " + " " + cat + " " + restLongitude + " " + restLatitude);
                categoryInfoArrayList.add(new CategoryRow(rName,oName,cat,restLongitude,restLatitude,rSeat,aSeat));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // We need to put this categoryInfoArrayList to adapter


        gpsBtn = (ImageButton) v.findViewById(R.id.GPSButton);
        gpsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getActivity(), GPSActivity.class);
                LatLng coord = new LatLng(34.5670135, 126.9783740);
                if (!isPermission) {
                    callPermission();
                    Log.d("GPS TEST:", "check2");
                    return;
                }

                gps = new GpsInfo(getActivity());
                // GPS 사용유무 가져오기

                if (gps.isGetLocation()) {
                    Log.d("GPS TEST:", "check3");
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

        //테스트2
        LatLng tempCoord = new LatLng(35.23182, 129.0844262);
        restMaker.setPosition(tempCoord);
        marker.setMap(naverMap);

        mapView = (MapView) v.findViewById(R.id.naver_map);
        mapView.getMapAsync(this);
        //
        callPermission();

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_ACCESS_FINE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            isAccessFineLocation = true;

        } else if (requestCode == PERMISSIONS_ACCESS_COARSE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

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
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_ACCESS_COARSE_LOCATION);
        } else {
            isPermission = true;
        }
    }

    //여기서 부터 마커를 추가하고 이동할 때 마다 보이게 한다.
    public void onMapReady(@NonNull final NaverMap naverMap) {
        this.naverMap = naverMap;
        naverMap.setLocationSource(locationSource);

        //마커 선언
        markersPosition = new Vector<LatLng>();
        //DB에서 restaurant 하나씩 받아 옴
        try {
            //서버로 한국 음식 카테고리 정보 보내기.
            CategoryJson loginTask = new CategoryJson();
            ArrayList<JSONObject> resultInJson = loginTask.execute("http://khprince.com/restaurantApp/allRest.php", "nothing").get();
            int i = 0;
            while(i < resultInJson.size()){
                String restaurantName = (String)resultInJson.get(i).get("restaurantName");
                String ownerName = (String)resultInJson.get(i).get("ownerName");
                String category = (String)resultInJson.get(i).get("category");
                String restaurantLongitude = (String)resultInJson.get(i).get("restaurantLongitude");
                String restaurantLatitude = (String)resultInJson.get(i).get("restaurantLatitude");
                String reservedSeat = (String)resultInJson.get(i).get("reservedSeat");
                String availableSeat = (String)resultInJson.get(i).get("availableSeat");
                i++;

                Log.e("All rest","i: " + i + " " +restaurantName + " " + " " + category + " " + restaurantLongitude + " " + restaurantLatitude);
                markersPosition.add(new LatLng(Double.parseDouble(restaurantLatitude), Double.parseDouble(restaurantLongitude)));
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("tag","fail to send category name to server");
        }

        // 카메라 이동 되면 호출 되는 이벤트
        naverMap.addOnCameraChangeListener(new NaverMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(int reason, boolean animated) {
                freeActiveMarkers();
                // 정의된 마커위치들중 가시거리 내에있는것들만 마커 생성
                LatLng currentPosition = getCurrentPosition(naverMap);
                for (LatLng markerPosition : markersPosition) {
                    if (!withinSightMarker(currentPosition, markerPosition))
                        continue;
                    Marker marker = new Marker();
                    marker.setPosition(markerPosition);
                    marker.setMap(naverMap);
                    //마커 크기 조절
                    marker.setWidth(50);
                    marker.setHeight(80);
                    //마커 겹치면 지우기 
                    marker.setHideCollidedMarkers(true);
                    activeMarkers.add(marker);
                }
            }
        });
    }

    // 마커 정보 저장시킬 변수들 선언
    private Vector<LatLng> markersPosition;
    private Vector<Marker> activeMarkers;

    // 현재 카메라가 보고있는 위치
    public LatLng getCurrentPosition(NaverMap naverMap) {
        CameraPosition cameraPosition = naverMap.getCameraPosition();
        return new LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude);
    }

    // 선택한 마커의 위치가 가시거리(카메라가 보고있는 위치 반경 3km 내)에 있는지 확인
    public final static double REFERANCE_LAT = 1 / 109.958489129649955;
    public final static double REFERANCE_LNG = 1 / 88.74;
    public final static double REFERANCE_LAT_X3 = 3 / 109.958489129649955;
    public final static double REFERANCE_LNG_X3 = 3 / 88.74;

    public boolean withinSightMarker(LatLng currentPosition, LatLng markerPosition) {
        boolean withinSightMarkerLat = Math.abs(currentPosition.latitude - markerPosition.latitude) <= REFERANCE_LAT_X3;
        boolean withinSightMarkerLng = Math.abs(currentPosition.longitude - markerPosition.longitude) <= REFERANCE_LNG_X3;
        return withinSightMarkerLat && withinSightMarkerLng;
    }

    // 지도상에 표시되고있는 마커들 지도에서 삭제
    private void freeActiveMarkers() {
        if (activeMarkers == null) {
            activeMarkers = new Vector<Marker>();
            return;
        }
        for (Marker activeMarker : activeMarkers) {
            activeMarker.setMap(null);
        }
        activeMarkers = new Vector<Marker>();
    }


}
