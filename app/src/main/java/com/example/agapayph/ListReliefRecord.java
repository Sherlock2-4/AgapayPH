package com.example.agapayph;

public class ListReliefRecord {
    public int relief_id;
    public String beneficiary_name;
    public String barangay;
    public String relief_type;
    public int quantity;
    public String distribution_date;
    public int volunteer_id;

    public ListReliefRecord(int relief_id, String beneficiary_name, String barangay,
                            String relief_type, int quantity, String distribution_date,
                            int volunteer_id){
        this.relief_id = relief_id;
        this.beneficiary_name = beneficiary_name;
        this.barangay = barangay;
        this.relief_type = relief_type;
        this.quantity = quantity;
        this.distribution_date = distribution_date;
        this.volunteer_id = volunteer_id;
    }
}
