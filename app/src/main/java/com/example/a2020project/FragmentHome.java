package com.example.a2020project;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.a2020project.Json.CategoryJson;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.util.MarkerIcons;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Vector;


public class FragmentHome extends Fragment implements OnMapReadyCallback {

    ImageButton gpsBtn;
    Button rb;
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
    Context context;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_home, container, false);
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        //context 받아오기
        context = getContext();

        rb = (Button) v.findViewById(R.id.seeRecommending);
        rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RecommendationScreen.class);
                startActivity(intent);
            }
        });


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
        String category;
        //마커 선언
        markersData = new Vector<RestaurantData>();
        //DB에서 restaurant 하나씩 받아 옴
        try {
            //서버로 한국 음식 카테고리 정보 보내기.
            CategoryJson loginTask = new CategoryJson();
            ArrayList<JSONObject> resultInJson = loginTask.execute("http://khprince.com/restaurantApp/allRest.php", "nothing").get();
            int i = 0;
            RestaurantData restaurantData;
            while(i < resultInJson.size()){
                restaurantData = new RestaurantData();

                String restaurantName = (String)resultInJson.get(i).get("restaurantName");
                String ownerName = (String)resultInJson.get(i).get("ownerName");
                category = (String)resultInJson.get(i).get("category");
                String restaurantLongitude = (String)resultInJson.get(i).get("restaurantLongitude");
                String restaurantLatitude = (String)resultInJson.get(i).get("restaurantLatitude");
                String reservedSeat = (String)resultInJson.get(i).get("reservedSeat");
                String availableSeat = (String)resultInJson.get(i).get("availableSeat");
                i++;
                //클래스를 만들어서 해당 정보들을 저장하여 넘겨아 할 듯 ㅇㅇ
                //Log.e("All rest","i: " + i + " " +restaurantName + " " + " " + category + " " + restaurantLongitude + " " + restaurantLatitude);

                restaurantData.add(restaurantName, ownerName, category, Double.parseDouble(restaurantLatitude), Double.parseDouble(restaurantLongitude), Integer.parseInt(reservedSeat),Integer.parseInt(availableSeat));
                markersData.add(restaurantData);
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("tag_marker","fail to send category name to server");
        }
        //new LatLng(Double.parseDouble(restaurantLatitude), Double.parseDouble(restaurantLongitude))
        // 카메라 이동 되면 호출 되는 이벤트
        naverMap.addOnCameraChangeListener(new NaverMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(int reason, boolean animated) {
                freeActiveMarkers();
                // 정의된 마커위치들중 가시거리 내에있는것들만 마커 생성
                LatLng currentPosition = getCurrentPosition(naverMap);
                //마커 정보 선언
                LatLng markersPosition;
                double restaurantLatitude;
                double restaurantLongitude;
                //vector에 저장된 marker class를 하나씩 가져온다.
                for (RestaurantData markerPosition : markersData) {
                    restaurantLatitude = markerPosition.restaurantLatitude;
                    restaurantLongitude = markerPosition.restaurantLongitude;
                    markersPosition = new LatLng(restaurantLatitude, restaurantLongitude);
                    if (!withinSightMarker(currentPosition, markersPosition))
                        continue;
                    Marker marker = new Marker();
                    marker.setPosition(markersPosition);
                    marker.setMap(naverMap);
                    //마커 크기 조절
                    marker.setWidth(50);
                    marker.setHeight(80);
                    //마커 겹치면 지우기
                    marker.setHideCollidedMarkers(true);
                    //마커 색 변경
                    switch(markerPosition.category){
                        case "koreanfood":
                            marker.setIcon(MarkerIcons.BLACK);
                            marker.setIconTintColor(Color.RED);
                            break;
                        case "chinesefood":
                            marker.setIcon(MarkerIcons.BLACK);
                            marker.setIconTintColor(Color.BLACK);
                            break;
                        case "japenesefood":
                            marker.setIcon(MarkerIcons.BLACK);
                            marker.setIconTintColor(Color.GREEN);
                            break;
                        case "yangsikfood":
                            marker.setIcon(MarkerIcons.BLACK);
                            marker.setIconTintColor(Color.BLUE);
                            break;
                        case "bunsikfood":
                            marker.setIcon(MarkerIcons.BLACK);
                            marker.setIconTintColor(0x8b00ff);
                            break;
                        case "cafefood":
                            marker.setIcon(MarkerIcons.BLACK);
                            marker.setIconTintColor(Color.GRAY);
                            break;
                    }
                    InfoWindow infoWindow = new InfoWindow();
                    infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(context) {
                        @NonNull
                        @Override
                        public CharSequence getText(@NonNull InfoWindow infoWindow) {
                            //정보창에 띄우는 글
                            return convertHE(markerPosition.category) + ": " + markerPosition.restaurantName;
                        }
                    });
                    //정보 창 onClickListener
                    infoWindow.setOnClickListener(overlay -> {
                        Intent intent = new Intent(context, StorepageActivity.class);
                        intent.putExtra("restaurantName", markerPosition.restaurantName);
                        context.startActivity(intent);
                        return false;
                    });
                    // 지도를 클릭하면 정보 창을 닫음
                    naverMap.setOnMapClickListener((coord, point) -> {
                        infoWindow.close();
                    });
                    // 마커를 클릭하면:
                    Overlay.OnClickListener listener = overlay -> {
                        Marker markerAA = (Marker)overlay;
                        if (markerAA.getInfoWindow() == null) {
                            // 현재 마커에 정보 창이 열려있지 않을 경우 엶
                            infoWindow.open(markerAA);
                        } else {
                            // 이미 현재 마커에 정보 창이 열려있을 경우 닫음
                            infoWindow.close();
                        }
                        return true;
                    };
                    marker.setOnClickListener(listener);
                    activeMarkers.add(marker);
                }
            }
        });
    }

    // 마커 정보 저장시킬 변수들 선언
    private Vector<RestaurantData> markersData;
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
    //식당이름 한영 전환
    private String convertHE(String english){
        switch (english){
            case "koreanfood":
                return "한식";
            case "chinesefood":
                return "중식";
            case "japenesefood":
                return "일식";
            case "yangsikfood":
                return "양식";
            case "bunsikfood":
                return "분식";
            case "cafefood":
                return "카페";
        }
        return "해당하는 카테고리는 없습니다";
    }
    //식당 정보가 들어있는 데이터 클래스
    static class RestaurantData{
        String restaurantName, ownerName, category;
        double restaurantLatitude, restaurantLongitude;
        int reservedSeat, availableSeat;
        void add(String restaurantName, String ownerName, String category, double restaurantLatitude, double restaurantLongitude, int reservedSeat, int availableSeat)
        {
            this.restaurantName = restaurantName;
            this.ownerName = ownerName;
            this.category = category;
            this.restaurantLongitude = restaurantLongitude;
            this.restaurantLatitude = restaurantLatitude;
            this.reservedSeat = reservedSeat;
            this.availableSeat = availableSeat;
        }
    }
}
