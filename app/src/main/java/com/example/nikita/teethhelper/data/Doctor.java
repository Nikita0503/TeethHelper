package com.example.nikita.teethhelper.data;

/**
 * Created by Nikita on 08.04.2018.
 */

public class Doctor implements defaultObject{
    public int code;
    public int experience;
    public String name;
    public String passport;
    public String address;
    public String specialization;
    public String berth;

    public Doctor(int code, String name, String passport, String address, String specialization, int experience, String berth) {
        this.code = code;
        this.name = name;
        this.passport = passport;
        this.address = address;
        this.specialization = specialization;
        this.experience = experience;
        this.berth = berth;
    }

    public Doctor(String name, String passport, String address, String specialization, int experience, String berth) {
        this.name = name;
        this.passport = passport;
        this.address = address;
        this.specialization = specialization;
        this.experience = experience;
        this.berth = berth;
    }
}
