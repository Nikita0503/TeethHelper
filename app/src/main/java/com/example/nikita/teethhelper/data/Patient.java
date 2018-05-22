package com.example.nikita.teethhelper.data;

/**
 * Created by Nikita on 08.04.2018.
 */

public class Patient implements defaultObject {

    public int code;
    public String name;
    public String passport;
    public String address;
    public String disease;

    public Patient(int code, String name, String passport, String address, String disease) {
        this.code = code;
        this.name = name;
        this.passport = passport;
        this.address = address;
        this.disease = disease;
    }

    public Patient(String name, String passport, String address, String disease) {
        this.name = name;
        this.passport = passport;
        this.address = address;
        this.disease = disease;
    }
}
