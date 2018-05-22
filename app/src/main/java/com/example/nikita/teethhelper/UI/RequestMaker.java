package com.example.nikita.teethhelper.UI;

import com.example.nikita.teethhelper.data.Doctor;
import com.example.nikita.teethhelper.data.Patient;
import com.example.nikita.teethhelper.data.Render;
import com.example.nikita.teethhelper.data.Service;
import com.example.nikita.teethhelper.data.Visit;

/**
 * Created by Nikita on 21.05.2018.
 */

public class RequestMaker {

    public String getRequestTextByRender(Render render){
        String request = "";
        int counter = 0;
        if(render.service.trim().length() > 0){
            if(counter>0) {
                request += " AND service = '" + render.service + "'";
            }else{
                request += " WHERE service = '" + render.service + "'";
                counter++;
            }
        }
        if(render.patient.trim().length() > 0){
            if(counter>0) {
                request += " AND patient = '" + render.patient + "'";
            }else{
                request += " WHERE patient = '" + render.patient + "'";
                counter++;
            }
        }
        if(render.doctor.trim().length() > 0){
            if(counter>0) {
                request += " AND doctor = '" + render.doctor + "'";
            }else{
                request += " WHERE doctor = '" + render.doctor + "'";
                counter++;
            }
        }
        if(render.sum > 0){
            if(counter>0) {
                request += " AND sum = " + render.sum;
            }else{
                request += " WHERE sum = " + render.sum;
                counter++;
            }
        }
        if(render.date.trim().length() > 0){
            if(counter>0) {
                request += " AND date = '" + render.date + "'";
            }else{
                request += " WHERE date = '" + render.date + "'";
                counter++;
            }
        }
        return request;
    }

    public String getRequestTextByDoctor(Doctor doctor){
        String request = "";
        int counter = 0;
        if (doctor.name.trim().length() > 0){
            if(counter>0) {
                request += " AND doctor = '" + doctor.name + "'";
            }else{
                request += " WHERE doctor = '" + doctor.name + "'";
                counter++;
            }
        }
        if (doctor.passport.trim().length() > 0){
            if(counter>0) {
                request += " AND passport = '" + doctor.passport + "'";
            }else{
                request += " WHERE passport = '" + doctor.passport + "'";
                counter++;
            }
        }
        if (doctor.address.trim().length() > 0){
            if(counter>0) {
                request += " AND address = '" + doctor.address + "'";
            }else{
                request += " WHERE address = '" + doctor.address + "'";
                counter++;
            }
        }
        if (doctor.specialization.trim().length() > 0){
            if(counter>0) {
                request += " AND specialization = '" + doctor.specialization + "'";
            }else{
                request += " WHERE specialization = '" + doctor.specialization + "'";
                counter++;
            }
        }
        if (doctor.experience > 0){
            if(counter>0) {
                request += " AND experience = " + doctor.experience;
            }else{
                request += " WHERE experience = " + doctor.experience;
                counter++;
            }
        }
        if (doctor.berth.trim().length() > 0){
            if(counter>0) {
                request += " AND berth = '" + doctor.berth + "'";
            }else{
                request += " WHERE berth = '" + doctor.berth + "'";
                counter++;
            }
        }
        return request;
    }

    public String getRequestTextByPatient(Patient patient){
        String request = "";
        int counter = 0;
        if (patient.name.trim().length() > 0){
            if(counter>0) {
                request += " AND name = '" + patient.name + "'";
            }else{
                request += " WHERE name = '" + patient.name + "'";
                counter++;
            }
        }
        if (patient.passport.trim().length() > 0){
            if(counter>0) {
                request += " AND passport = '" + patient.passport + "'";
            }else{
                request += " WHERE passport = '" + patient.passport + "'";
                counter++;
            }
        }
        if (patient.address.trim().length() > 0){
            if(counter>0) {
                request += " AND address = '" + patient.address + "'";
            }else{
                request += " WHERE address = '" + patient.address + "'";
                counter++;
            }
        }
        if (patient.disease.trim().length() > 0){
            if(counter>0) {
                request += " AND disease = '" + patient.disease + "'";
            }else{
                request += " WHERE disease = '" + patient.disease + "'";
                counter++;
            }
        }
        return request;
    }

    public String getRequestTextByService(Service service){
        String request = "";
        int counter = 0;
        if (service.patient.trim().length() > 0){
            if(counter>0) {
                request += " AND patient = '" + service.patient + "'";
            }else{
                request += " WHERE patient = '" + service.patient + "'";
                counter++;
            }
        }
        if (service.doctor.trim().length() > 0){
            if(counter>0) {
                request += " AND doctor = '" + service.doctor + "'";
            }else{
                request += " WHERE doctor = '" + service.doctor + "'";
                counter++;
            }
        }
        if (service.manipulation.trim().length() > 0){
            if(counter>0) {
                request += " AND manipulation = '" + service.manipulation + "'";
            }else{
                request += " WHERE manipulation = '" + service.manipulation + "'";
                counter++;
            }
        }
        if (service.cost > 0){
            if(counter>0) {
                request += " AND cost = " + service.cost;
            }else{
                request += " WHERE cost = " + service.cost;
                counter++;
            }
        }
        if (service.date.trim().length() > 0) {
            if(counter>0) {
                request += " AND date = '" + service.date + "'";
            }else{
                request += " WHERE date = '" + service.date + "'";
                counter++;
            }
        }
        return request;
    }

    public String getRequestTextByVisit(Visit visit){
        String request = "";
        int counter = 0;
        if (visit.patient.trim().length() > 0){
            if(counter>0) {
                request += " AND patient = '" + visit.patient+"'";
            }else{
                request += " WHERE patient = '" + visit.patient+"'";
                counter++;
            }
        }
        if (visit.service.trim().length() > 0){
            if(counter>0) {
                request += " AND service = '" + visit.service+"'";
            }else{
                request += " WHERE service = '" + visit.service+"'";
                counter++;
            }
        }
        if (visit.date.trim().length() > 0){
            if(counter>0) {
                request += "AND date = '" + visit.date + "'";
            }else{
                request += " WHERE date '= " + visit.date + "'";
                counter++;
            }
        }
        return request;
    }

    public String getInsertRequestTextByRender(Render render){
        String request = "[{service, patient, doctor, sum, date}] VALUES (";
        if(render.service.trim().length() > 0){
            request += render.service + ", ";
        }else{
            request += "someService, ";
        }
        if(render.patient.trim().length() > 0){
            request += render.patient + ", ";
        }else{
            request += "somePatient, ";
        }
        if(render.doctor.trim().length() > 0){
            request += render.doctor + ", ";
        }else{
            request += "someDoctor, ";
        }
        if(render.sum > 0){
            request += render.sum + ", ";
        }else{
            request += "0, ";
        }
        if(render.date.trim().length() > 0){
            request += render.date + ")";
        }else{
            request += "5.3.1998)";
        }
        return request;
    }

    public String getInsertRequestTextByDoctor(Doctor doctor){
        String request = "[{name, passport, address, specialization, experience, berth}] VALUES (";
        if(doctor.name.trim().length() > 0){
            request += doctor.name + ", ";
        }else{
            request += "someName, ";
        }
        if(doctor.passport.trim().length() > 0){
            request += doctor.passport;
        }else{
            request += "somePassport, ";
        }
        if(doctor.address.trim().length() > 0){
            request += doctor.address + ", ";
        }else{
            request += "someAddress, ";
        }
        if(doctor.specialization.trim().length() > 0){
            request += doctor.specialization + ", ";
        }else{
            request += "someSpecialization, ";
        }
        if(doctor.experience > 0){
            request += doctor.experience + ", ";
        }else{
            request += "0, ";
        }
        if(doctor.berth.trim().length() > 0){
            request += doctor.berth + ")";
        }else{
            request += "someBerth)";
        }
        return request;
    }

    public String getInsertRequestTextByPatient(Patient patient){
        String request = "[{name, passport, address, disease}] VALUES (";
        if(patient.name.trim().length() > 0){
            request += patient.name + ", ";
        }else{
            request += "someName, ";
        }
        if(patient.passport.trim().length() > 0){
            request += patient.passport + ", ";
        }else{
            request += "somePassport, ";
        }
        if(patient.address.trim().length() > 0){
            request += patient.address + ", ";
        }else{
            request += "someAddress, ";
        }
        if(patient.disease.trim().length() > 0){
            request += patient.disease + ")";
        }else{
            request += "someDisease)";
        }
        return request;
    }

    public String getInsertRequestTextByService(Service service){
        String request = "[{patient, doctor, manipulation, cost, date}] VALUES (";
        if(service.patient.trim().length() > 0){
            request += service.patient + ", ";
        }else{
            request += "somePatient, ";
        }
        if(service.doctor.trim().length() > 0){
            request  += service.doctor + ", ";
        }else{
            request += "someDoctor, ";
        }
        if(service.manipulation.trim().length() > 0){
            request += service.manipulation + ", ";
        }else{
            request += "someManipulation, ";
        }
        if(service.cost > 0){
            request += service.cost + ", ";
        }else{
            request += "0, ";
        }
        if(service.date.trim().length() > 0){
            request += service.date + ")";
        }else{
            request += "5.3.1998)";
        }
        return request;
    }

    public String getInsertRequestTextByVisit(Visit visit){
        String request = "[{patient, date, service, date}] VALUES (";
        if (visit.patient.trim().length() > 0){
            request += visit.patient + ", ";
        }else{
            request += "somePatient, ";
        }
        if (visit.date.trim().length() > 0){
            request += visit.date + ", ";
        }else{
            request += "5.3.1998, ";
        }
        if (visit.service.trim().length() > 0){
            request += visit.service + ", ";
        }else{
            request += "someService, ";
        }
        if (visit.date.trim().length() > 0){
            request += visit.date + ")";
        }else{
            request += "5.3.1998)";
        }
        return request;
    }

    public String getUpdateRequestTextByRender(Render render){
        String request = "";
        String valuesRequestPart = "";
        String whereRequestPart = "WHERE ";
        int counter = 0;
        if(render.service.trim().length() > 0){
            if(counter>0){
                valuesRequestPart += ", service = 'someService'";
                whereRequestPart += " AND service = '" + render.service + "'";
            }else{
                valuesRequestPart += " service = 'someService'";
                whereRequestPart += " AND service = '" + render.service + "'";
                counter++;
            }
        }
        if(render.patient.trim().length() > 0){
            if(counter>0){
                valuesRequestPart += ", patient = 'somePatient'";
                whereRequestPart += " AND patient = '" + render.patient + "'";
            }else{
                valuesRequestPart += " patient = 'somePatient'";
                whereRequestPart += " patient = '" + render.patient + "'";
                counter++;
            }
        }
        if(render.doctor.trim().length() > 0){
            if(counter>0){
                valuesRequestPart += ", doctor = 'someDoctor'";
                whereRequestPart += " AND doctor = '" + render.doctor + "'";
            }else{
                valuesRequestPart += " doctor = 'someDoctor'";
                whereRequestPart += " doctor = '" + render.doctor + "'";
                counter++;
            }
        }
        if(render.sum > 0){
            if(counter>0){
                valuesRequestPart += ", sum = 0";
                whereRequestPart += " AND sum = " + render.sum;
            }else{
                valuesRequestPart += " sum = 0";
                whereRequestPart += " sum = " + render.sum;
                counter++;
            }
        }
        if(render.date.trim().length() > 0){
            if(counter>0){
                valuesRequestPart += ", date = 'someDate'";
                whereRequestPart += " AND date = '" + render.date + "'";
            }else{
                valuesRequestPart += " date = 'someDate'";
                whereRequestPart += " date = '" + render.date + "'";
                counter++;
            }
        }
        request = valuesRequestPart + " " +  whereRequestPart;
        return request;
    }

    public String getUpdateRequestTextByDoctor(Doctor doctor){
        String request = "";
        String valuesRequestPart = "";
        String whereRequestPart = "WHERE ";
        int counter = 0;
        if(doctor.name.trim().length() > 0){
            if(counter>0){
                valuesRequestPart += ", name = 'someName'";
                whereRequestPart += " AND name = '" + doctor.name + "'";
            }else{
                valuesRequestPart += " name = 'someName'";
                whereRequestPart += " name = '" + doctor.name + "'";
                counter++;
            }
        }
        if(doctor.passport.trim().length() > 0){
            if(counter>0){
                valuesRequestPart += ", passport = 'somePassport'";
                whereRequestPart += " AND passport = '" + doctor.passport + "'";
            }else{
                valuesRequestPart += " passport = 'somePassport'";
                whereRequestPart += " passport = '" + doctor.passport + "'";
                counter++;
            }
        }
        if(doctor.address.trim().length() > 0){
            if(counter>0){
                valuesRequestPart += ", address = 'someAddress'";
                whereRequestPart += " AND address = '" + doctor.address + "'";
            }else{
                valuesRequestPart += " address = 'someAddress'";
                whereRequestPart += " address = '" + doctor.address + "'";
                counter++;
            }
        }
        if(doctor.specialization.trim().length() > 0){
            if(counter>0){
                valuesRequestPart += ", specialization = 'someSpecialization'";
                whereRequestPart += " AND specialization = '" + doctor.specialization + "'";
            }else{
                valuesRequestPart += " specialization = 'someSpecialization'";
                whereRequestPart += " specialization = '" + doctor.specialization + "'";
                counter++;
            }
        }
        if(doctor.experience > 0){
            if(counter>0){
                valuesRequestPart += ", experience = 0";
                whereRequestPart += " AND experience = " + doctor.experience;
            }else{
                valuesRequestPart += " experience = 0";
                whereRequestPart += " experience = " + doctor.experience;
                counter++;
            }
        }
        if(doctor.berth.trim().length() > 0){
            if(counter>0){
                valuesRequestPart += ", berth = 'someBerth'";
                whereRequestPart += " AND berth = '" + doctor.berth + "'";
            }else{
                valuesRequestPart += " berth = 'someBerth'";
                whereRequestPart += " berth = '" + doctor.berth + "'";
                counter++;
            }
        }
        request += valuesRequestPart + " " + whereRequestPart;
        return request;
    }

    public String getUpdateRequestTextByPatient(Patient patient){
        String request = "";
        String valuesRequestPart = "";
        String whereRequestPart = "WHERE ";
        int counter = 0;
        if(patient.name.trim().length() > 0){
            if(counter>0){
                valuesRequestPart += ", name = 'someName'";
                whereRequestPart += " AND name = '" + patient.name + "'";
            }else{
                valuesRequestPart += " name = 'someName'";
                whereRequestPart += " name = '" + patient.name + "'";
                counter++;
            }
        }
        if(patient.passport.trim().length() > 0){
            if(counter>0){
                valuesRequestPart += ", passport = 'somePassport'";
                whereRequestPart += " AND passport = '" + patient.passport + "'";
            }else{
                valuesRequestPart += " passport = 'somePassport'";
                whereRequestPart += " passport = '" + patient.passport + "'";
                counter++;
            }
        }
        if(patient.address.trim().length() > 0){
            if(counter>0){
                valuesRequestPart += ", address = 'someAddress'";
                whereRequestPart += " AND address = '" + patient.address + "'";
            }else{
                valuesRequestPart += " address = 'someAddress'";
                whereRequestPart += " address = '" + patient.address + "'";
                counter++;
            }
        }
        if(patient.disease.trim().length() > 0){
            if(counter>0){
                valuesRequestPart += ", disease = 'someDisease'";
                whereRequestPart += " AND disease = '" + patient.disease + "'";
            }else{
                valuesRequestPart += " disease = 'someDisease'";
                whereRequestPart += " disease = '" + patient.disease + "'";
                counter++;
            }
        }
        request = valuesRequestPart + " " +  whereRequestPart;
        return request;
    }

    public String getUpdateRequestTextByService(Service service){
        String request = "";
        String valuesRequestPart = "";
        String whereRequestPart = "WHERE ";
        int counter = 0;
        if(service.patient.trim().length() > 0){
            if(counter>0){
                valuesRequestPart += ", patient = 'somePatient'";
                whereRequestPart += " AND patient = '" + service.patient + "'";
            }else{
                valuesRequestPart += " patient = 'somePatient'";
                whereRequestPart += " patient = '" + service.patient + "'";
                counter++;
            }
        }
        if(service.doctor.trim().length() > 0){
            if(counter>0){
                valuesRequestPart += ", doctor = 'someDoctor'";
                whereRequestPart += " AND doctor = '" + service.doctor + "'";
            }else{
                valuesRequestPart += " doctor = 'someDoctor'";
                whereRequestPart += " doctor = '" + service.doctor + "'";
                counter++;
            }
        }
        if(service.date.trim().length() > 0){
            if(counter>0){
                valuesRequestPart += ", date = 'someDate'";
                whereRequestPart += " AND date = '" + service.date + "'";
            }else{
                valuesRequestPart += " date = 'someDate'";
                whereRequestPart += " date = '" + service.date + "'";
                counter++;
            }
        }
        if(service.cost > 0){
            if(counter>0){
                valuesRequestPart += ", cost = 0";
                whereRequestPart += " AND cost = " + service.cost;
            }else{
                valuesRequestPart += " cost = 0";
                whereRequestPart += " cost = " + service.cost;
                counter++;
            }
        }
        if(service.manipulation.trim().length() > 0){
            if(counter>0){
                valuesRequestPart += ", manipulation = 'someManipulation'";
                whereRequestPart += " AND manipulation = '" + service.manipulation + "'";
            }else{
                valuesRequestPart += " manipulation = 'someManipulation'";
                whereRequestPart += " manipulation = '" + service.manipulation + "'";
                counter++;
            }
        }
        request = valuesRequestPart + " " +  whereRequestPart;
        return request;
    }

    public String getUpdateRequestTextByVisit(Visit visit){
        String request = "";
        String valuesRequestPart = "";
        String whereRequestPart = "WHERE ";
        int counter = 0;
        if(visit.patient.trim().length() > 0){
            if(counter>0){
                valuesRequestPart += ", patient = 'somePatient";
                whereRequestPart += " AND patient = '" + visit.patient + "'";
            }else{
                valuesRequestPart += " patient = 'somePatient'";
                whereRequestPart += " patient = '" + visit.patient + "'";
                counter++;
            }
        }
        if(visit.date.trim().length() > 0){
            if(counter>0){
                valuesRequestPart += ", date = '5.3.1998'";
                whereRequestPart += " AND date = '" + visit.date + "'";
            }else{
                valuesRequestPart += " date = '5.3.1998'";
                whereRequestPart += " date = '" + visit.date + "'";
                counter++;
            }
        }
        if(visit.service.trim().length() > 0){
            if(counter>0){
                valuesRequestPart += ", service = 'someService'";
                whereRequestPart += " AND service = '" + visit.service + "'";
            }else{
                valuesRequestPart += " service = 'someService'";
                whereRequestPart += " AND service = '" + visit.service + "'";
                counter++;
            }
        }
        request += valuesRequestPart + " " + whereRequestPart;
        return request;
    }
}
