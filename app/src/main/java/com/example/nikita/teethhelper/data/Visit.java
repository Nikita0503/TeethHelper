package com.example.nikita.teethhelper.data;

/**
 * Created by Nikita on 08.04.2018.
 */

public class Visit implements defaultObject{

    public String patient;
    public String date;
    public String service;

    public Visit(String patient, String date, String service) {
        this.patient = patient;
        this.date = date;
        this.service = service;
    }
}
