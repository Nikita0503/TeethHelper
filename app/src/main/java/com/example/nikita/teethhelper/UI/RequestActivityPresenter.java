package com.example.nikita.teethhelper.UI;

import android.app.Fragment;
import android.util.Log;

import com.example.nikita.teethhelper.R;
import com.example.nikita.teethhelper.UI.Fragments.DoctorFragment;
import com.example.nikita.teethhelper.UI.Fragments.PatientFragment;
import com.example.nikita.teethhelper.UI.Fragments.RenderFragment;
import com.example.nikita.teethhelper.UI.Fragments.ServiceFragment;
import com.example.nikita.teethhelper.UI.Fragments.VisitFragment;
import com.example.nikita.teethhelper.data.Doctor;
import com.example.nikita.teethhelper.data.Patient;
import com.example.nikita.teethhelper.data.Render;
import com.example.nikita.teethhelper.data.Service;
import com.example.nikita.teethhelper.data.Visit;

/**
 * Created by Nikita on 19.05.2018.
 */

public class RequestActivityPresenter {
    private RequestActivity requestActivity;
    public RequestActivityPresenter(RequestActivity requestActivity){
        this.requestActivity = requestActivity;
    }

    public String getRequestText(String tableName, String typeOfRequest, Fragment fragment){
        String request = "";
        RequestMaker requestMaker = new RequestMaker();
        switch (typeOfRequest){
            case "SELECT":
                request = typeOfRequest + " * FROM " + tableName;
                switch (tableName){
                    case "renders":
                        RenderFragment renderFragment = (RenderFragment) fragment;
                        Render render = renderFragment.getRender();
                        request += requestMaker.getRequestTextByRender(render);
                        break;
                    case "patients":
                        PatientFragment patientFragment = (PatientFragment) fragment;
                        Patient patient = patientFragment.getPatient();
                        request += requestMaker.getRequestTextByPatient(patient);
                        break;
                    case "doctors":
                        DoctorFragment doctorFragment = (DoctorFragment) fragment;
                        Doctor doctor = doctorFragment.getDoctor();
                        request += requestMaker.getRequestTextByDoctor(doctor);
                        break;
                    case "service":
                        ServiceFragment serviceFragment = (ServiceFragment) fragment;
                        Service service = serviceFragment.getService();
                        request += requestMaker.getRequestTextByService(service);
                        break;
                    case "visits":
                        VisitFragment visitFragment = (VisitFragment) fragment;
                        Visit visit = visitFragment.getVisit();
                        request += requestMaker.getRequestTextByVisit(visit);
                        break;
                }
                break;
            case "INSERT":
                request = typeOfRequest + " INTO " + tableName;
                switch (tableName){
                    case "renders":
                        RenderFragment renderFragment = (RenderFragment) fragment;
                        Render render = renderFragment.getRender();
                        request += requestMaker.getInsertRequestTextByRender(render);
                        break;
                    case "patients":
                        PatientFragment patientFragment = (PatientFragment) fragment;
                        Patient patient = patientFragment.getPatient();
                        request += requestMaker.getInsertRequestTextByPatient(patient);
                        break;
                    case "doctors":
                        DoctorFragment doctorFragment = (DoctorFragment) fragment;
                        Doctor doctor = doctorFragment.getDoctor();
                        request += requestMaker.getInsertRequestTextByDoctor(doctor);
                        break;
                    case "service":
                        ServiceFragment serviceFragment = (ServiceFragment) fragment;
                        Service service = serviceFragment.getService();
                        request += requestMaker.getInsertRequestTextByService(service);
                        break;
                    case "visits":
                        VisitFragment visitFragment = (VisitFragment) fragment;
                        Visit visit = visitFragment.getVisit();
                        request += requestMaker.getInsertRequestTextByVisit(visit);
                        break;
                }
                break;
            case "DELETE":
                request = typeOfRequest + " FROM " + tableName;
                switch (tableName) {
                    case "renders":
                        RenderFragment renderFragment = (RenderFragment) fragment;
                        Render render = renderFragment.getRender();
                        request += requestMaker.getRequestTextByRender(render);
                        break;
                    case "patients":
                        PatientFragment patientFragment = (PatientFragment) fragment;
                        Patient patient = patientFragment.getPatient();
                        request += requestMaker.getRequestTextByPatient(patient);
                        break;
                    case "doctors":
                        DoctorFragment doctorFragment = (DoctorFragment) fragment;
                        Doctor doctor = doctorFragment.getDoctor();
                        request += requestMaker.getRequestTextByDoctor(doctor);
                        break;
                    case "service":
                        ServiceFragment serviceFragment = (ServiceFragment) fragment;
                        Service service = serviceFragment.getService();
                        request += requestMaker.getRequestTextByService(service);
                        break;
                    case "visits":
                        VisitFragment visitFragment = (VisitFragment) fragment;
                        Visit visit = visitFragment.getVisit();
                        request += requestMaker.getRequestTextByVisit(visit);
                        break;
                }
                break;
            case "UPDATE":
                request = typeOfRequest + " " + tableName + " SET ";
                switch (tableName){
                    case "renders":
                        RenderFragment renderFragment = (RenderFragment) fragment;
                        Render render = renderFragment.getRender();
                        request += requestMaker.getUpdateRequestTextByRender(render);
                        break;
                    case "patients":
                        PatientFragment patientFragment = (PatientFragment) fragment;
                        Patient patient = patientFragment.getPatient();
                        request += requestMaker.getUpdateRequestTextByPatient(patient);
                        break;
                    case "doctors":
                        DoctorFragment doctorFragment = (DoctorFragment) fragment;
                        Doctor doctor = doctorFragment.getDoctor();
                        request += requestMaker.getUpdateRequestTextByDoctor(doctor);
                        break;
                    case "service":
                        ServiceFragment serviceFragment = (ServiceFragment) fragment;
                        Service service = serviceFragment.getService();
                        request += requestMaker.getUpdateRequestTextByService(service);
                        break;
                    case "visits":
                        VisitFragment visitFragment = (VisitFragment) fragment;
                        Visit visit = visitFragment.getVisit();
                        request += requestMaker.getUpdateRequestTextByVisit(visit);
                        break;
                }
        }
        return request;
    }



    public void fetchData(){
        String[] typesOfRequest = requestActivity.getResources().getStringArray(R.array.typesOfRequest);
        String[] tableNames = requestActivity.getResources().getStringArray(R.array.tableNames);
        requestActivity.setAdapters(typesOfRequest, tableNames);
    }

    public Fragment getFragmentByTableId(int tableId){
        Fragment fragment;
        switch (tableId){
            case 0:
                fragment = new RenderFragment();
                break;
            case 1:
                fragment = new PatientFragment();
                break;
            case 2:
                fragment = new DoctorFragment();
                break;
            case 3:
                fragment = new ServiceFragment();
                break;
            case 4:
                fragment = new VisitFragment();
                break;
            default:
                fragment = null;
        }
        return fragment;
    }


}