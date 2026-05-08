package com.example.agapayph;

public class ListEvacuationCenter {

    public String evaucation_name;
    public String capacity;
    public String current_occupancy;
    public String address;

    public ListEvacuationCenter(String evaucation_name, String capacity, String current_occupancy, String address) {
        this.evaucation_name = evaucation_name;
        this.capacity = capacity;
        this.current_occupancy = current_occupancy;
        this.address = address;
    }
}
