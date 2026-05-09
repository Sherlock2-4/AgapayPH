package com.example.agapayph;

public class ListMissingPerson {
    public int missing_person_id;
    public String missing_person_name;
    public int age;
    public String description;
    public String last_location;
    public String date_missing;
    public String status;

    public ListMissingPerson(int missing_person_id, String missing_person_name, int age,
                             String description, String last_location, String date_missing,
                             String status){
        this.missing_person_id = missing_person_id;
        this.missing_person_name = missing_person_name;
        this.age = age;
        this.description = description;
        this.last_location = last_location;
        this.date_missing = date_missing;
        this.status = status;
    }
}
