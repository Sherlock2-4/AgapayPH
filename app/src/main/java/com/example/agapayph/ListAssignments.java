package com.example.agapayph;

public class ListAssignments {
    public int assignment_id;
    public int volunteer_id;
    public String volunteer_name;
    public String assignment_title;
    public String completion_status;
    public String isAccepted;

    public ListAssignments(int assignment_id, int volunteer_id, String volunteer_name,
                           String assignment_title, String completion_status, String isAccepted){
        this.assignment_id = assignment_id;
        this.volunteer_id = volunteer_id;
        this.volunteer_name = volunteer_name;
        this.assignment_title = assignment_title;
        this.completion_status = completion_status;
        this.isAccepted = isAccepted;
    }
}
