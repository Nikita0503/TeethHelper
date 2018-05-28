package com.example.nikita.teethhelper.data;

/**
 * Created by Nikita on 08.04.2018.
 */

public class Service implements defaultObject{
    public float cost;
    public String manipulation;
    public String patient;
    public String doctor;
    public String date;

    public Service(String manipulation, String patient, String doctor, float cost, String date) {
        this.manipulation = manipulation;
        this.patient = patient;
        this.doctor = doctor;
        this.cost = cost;
        this.date = date;
    }
}
