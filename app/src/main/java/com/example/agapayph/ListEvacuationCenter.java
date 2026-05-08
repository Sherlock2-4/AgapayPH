package com.example.agapayph;

public class ListEvacuationCenter {

    public String evaucation_name;
    public int capacity;
    public int current_occupancy;
    public String address;
    public int food_packs;
    public int water;
    public int medicine_kit;

    public ListEvacuationCenter(String evaucation_name, int capacity, int current_occupancy, String address, int food_packs, int water, int medicine_kit) {
        this.evaucation_name = evaucation_name;
        this.capacity = capacity;
        this.current_occupancy = current_occupancy;
        this.address = address;
        this.food_packs = food_packs;
        this.water = water;
        this.medicine_kit = medicine_kit;
    }
}
