package com.example.a2020project.Recycler;

public class CategoryRow {
    //하나의 행에 대한 정보를 담을 클래스입니다.
    public String restaurantName;
    public String ownerName;
    public String category;
    public String restaurantLongitude;
    public String restaurantLatitude;
    public String reservedSeat;
    public String availableSeat;

    public CategoryRow(String restaurantName, String ownerName, String category, String restaurantLongitude, String restaurantLatitude, String reservedSeat , String availableSeat){
        this.restaurantName = restaurantName;
        this.ownerName = ownerName;
        this.category = category;
        this.restaurantLongitude = restaurantLongitude;
        this.restaurantLatitude = restaurantLatitude;
        this.reservedSeat = reservedSeat;
        this.availableSeat = availableSeat;
    }
}
