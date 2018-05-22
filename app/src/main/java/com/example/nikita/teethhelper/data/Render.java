package com.example.nikita.teethhelper.data;

/**
 * Created by Nikita on 08.04.2018.
 */

public class Render implements defaultObject{

    public String service;
    public String patient;
    public String doctor;
    public float sum;
    public String date;


    public Render(String service, String patient, String doctor, float sum, String date) {
        this.service = service;
        this.patient = patient;
        this.doctor = doctor;
        this.sum = sum;
        this.date = date;
    }
}
