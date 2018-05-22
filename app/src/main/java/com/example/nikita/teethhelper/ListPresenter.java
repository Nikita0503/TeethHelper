package com.example.nikita.teethhelper;

import android.content.Intent;
import android.util.Log;
import android.widget.SimpleExpandableListAdapter;

import com.example.nikita.teethhelper.UI.ListActivity;
import com.example.nikita.teethhelper.UI.RecordActivities.DoctorDataActivity;
import com.example.nikita.teethhelper.UI.RecordActivities.PatientDataActivity;
import com.example.nikita.teethhelper.UI.RecordActivities.RenderDataActivity;
import com.example.nikita.teethhelper.UI.RecordActivities.ServiceDataActivity;
import com.example.nikita.teethhelper.UI.RecordActivities.VisitsDataActivity;
import com.example.nikita.teethhelper.data.Doctor;
import com.example.nikita.teethhelper.data.Patient;
import com.example.nikita.teethhelper.data.Render;
import com.example.nikita.teethhelper.data.Service;
import com.example.nikita.teethhelper.data.Visit;
import com.example.nikita.teethhelper.data.defaultObject;
import com.example.nikita.teethhelper.tables.DoctorsTable;
import com.example.nikita.teethhelper.tables.PatientsTable;
import com.example.nikita.teethhelper.tables.RendersTable;
import com.example.nikita.teethhelper.tables.ServicesTable;
import com.example.nikita.teethhelper.tables.VisitsTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nikita on 15.04.2018.
 */

public class ListPresenter {

    ListActivity listActivity;

    public ListPresenter(ListActivity listActivity){
        this.listActivity = listActivity;
    }

    public void fetchData(int tableId){
        switch (tableId){
            case 0:
                RendersTable rendersTable = new RendersTable(listActivity, this);
                rendersTable.fetchData();
                listActivity.setColors(listActivity.getResources().getColor(R.color.colorDarkAqua), listActivity.getResources().getColor(R.color.colorAqua), "Renders");
                break;
            case 1:
                DoctorsTable doctorsTable = new DoctorsTable(listActivity, this);
                doctorsTable.fetchData();
                listActivity.setColors(listActivity.getResources().getColor(R.color.colorDarkRed), listActivity.getResources().getColor(R.color.colorRed), "Doctors");
                break;
            case 2:
                PatientsTable patientsTable = new PatientsTable(listActivity, this);
                patientsTable.fetchData();
                listActivity.setColors(listActivity.getResources().getColor(R.color.colorDarkPurple), listActivity.getResources().getColor(R.color.colorPurple), "Patients");
                break;
            case 3:
                ServicesTable servicesTable = new ServicesTable(listActivity, this);
                servicesTable.fetchData();
                listActivity.setColors(listActivity.getResources().getColor(R.color.colorDarkOrange), listActivity.getResources().getColor(R.color.colorOrange), "Services");
                break;
            case 4:
                VisitsTable visitsTable = new VisitsTable(listActivity, this);
                visitsTable.fetchData();
                listActivity.setColors(listActivity.getResources().getColor(R.color.colorDarkGreen), listActivity.getResources().getColor(R.color.colorGreen), "Visits");
                break;
        }

    }

    public void prepareDataByRenders(ArrayList<Render> renders, String[] tagNames) {
        Map<String, String> m;
        ArrayList<Map<String, String>> groupData;
        ArrayList<Map<String, String>> childDataItem;
        ArrayList<ArrayList<Map<String, String>>> childData;

        groupData = new ArrayList<Map<String, String>>();
        for (Render render : renders) {
            m = new HashMap<String, String>();
            m.put("groupName", render.patient);
            groupData.add(m);
        }

        childData = new ArrayList<ArrayList<Map<String, String>>>();
        for(Render render : renders) {
            childDataItem = new ArrayList<Map<String, String>>();
            m = new HashMap<String, String>();
            m.put("data", String.valueOf(render.service));
            m.put("tagName", tagNames[0]);
            childDataItem.add(m);
            m = new HashMap<String, String>();
            m.put("data", String.valueOf(render.patient));
            m.put("tagName", tagNames[1]);
            childDataItem.add(m);
            m = new HashMap<String, String>();
            m.put("data", String.valueOf(render.doctor));
            m.put("tagName", tagNames[2]);
            childDataItem.add(m);
            m = new HashMap<String, String>();
            m.put("data", String.valueOf(render.sum));
            m.put("tagName", tagNames[3]);
            childDataItem.add(m);
            m = new HashMap<String, String>();
            m.put("data", String.valueOf(render.date));
            m.put("tagName", tagNames[4]);
            childDataItem.add(m);
            childData.add(childDataItem);
        }

        makeListAdapter(groupData, childData);
    }

    public void prepareDataByPatients(ArrayList<Patient> patients, String[] tagNames) {
        Map<String, String> m;
        ArrayList<Map<String, String>> groupData;
        ArrayList<Map<String, String>> childDataItem;
        ArrayList<ArrayList<Map<String, String>>> childData;

        groupData = new ArrayList<Map<String, String>>();
        for (Patient patient : patients) {
            m = new HashMap<String, String>();
            m.put("groupName", patient.name);
            groupData.add(m);
        }
        childData = new ArrayList<ArrayList<Map<String, String>>>();
        for(Patient patient : patients) {
            childDataItem = new ArrayList<Map<String, String>>();
            m = new HashMap<String, String>();
            m.put("data", String.valueOf(patient.code));
            m.put("tagName", tagNames[0]);
            childDataItem.add(m);
            m = new HashMap<String, String>();
            m.put("data", String.valueOf(patient.name));
            m.put("tagName", tagNames[1]);
            childDataItem.add(m);
            m = new HashMap<String, String>();
            m.put("data", String.valueOf(patient.passport));
            m.put("tagName", tagNames[2]);
            childDataItem.add(m);
            m = new HashMap<String, String>();
            m.put("data", String.valueOf(patient.address));
            m.put("tagName", tagNames[3]);
            childDataItem.add(m);
            m = new HashMap<String, String>();
            m.put("data", String.valueOf(patient.disease));
            m.put("tagName", tagNames[4]);
            childDataItem.add(m);
            childData.add(childDataItem);
        }
        makeListAdapter(groupData, childData);
    }

    public void prepareDataByDoctors(ArrayList<Doctor> doctors, String[] tagNames) {
        Map<String, String> m;
        ArrayList<Map<String, String>> groupData;
        ArrayList<Map<String, String>> childDataItem;
        ArrayList<ArrayList<Map<String, String>>> childData;

        groupData = new ArrayList<Map<String, String>>();
        for (Doctor doctor : doctors) {
            m = new HashMap<String, String>();
            m.put("groupName", doctor.name);
            groupData.add(m);
        }

        childData = new ArrayList<ArrayList<Map<String, String>>>();
        for(Doctor doctor : doctors) {
            childDataItem = new ArrayList<Map<String, String>>();
            m = new HashMap<String, String>();
            m.put("data", String.valueOf(doctor.code));
            m.put("tagName", tagNames[0]);
            childDataItem.add(m);
            m = new HashMap<String, String>();
            m.put("data", String.valueOf(doctor.name));
            m.put("tagName", tagNames[1]);
            childDataItem.add(m);
            m = new HashMap<String, String>();
            m.put("data", String.valueOf(doctor.passport));
            m.put("tagName", tagNames[2]);
            childDataItem.add(m);
            m = new HashMap<String, String>();
            m.put("data", String.valueOf(doctor.address));
            m.put("tagName", tagNames[3]);
            childDataItem.add(m);
            m = new HashMap<String, String>();
            m.put("data", String.valueOf(doctor.specialization));
            m.put("tagName", tagNames[4]);
            childDataItem.add(m);
            m = new HashMap<String, String>();
            m.put("data", String.valueOf(doctor.experience));
            m.put("tagName", tagNames[5]);
            childDataItem.add(m);
            m = new HashMap<String, String>();
            m.put("data", String.valueOf(doctor.berth));
            m.put("tagName", tagNames[6]);
            childDataItem.add(m);
            childData.add(childDataItem);
        }

        makeListAdapter(groupData, childData);
    }

    public void prepareDataByServices(ArrayList<Service> services, String[] tagNames) {
        Map<String, String> m;
        ArrayList<Map<String, String>> groupData;
        ArrayList<Map<String, String>> childDataItem;
        ArrayList<ArrayList<Map<String, String>>> childData;

        groupData = new ArrayList<Map<String, String>>();
        for (Service service : services) {
            m = new HashMap<String, String>();
            m.put("groupName", service.manipulation);
            groupData.add(m);
        }

        childData = new ArrayList<ArrayList<Map<String, String>>>();
        for(Service service : services) {
            childDataItem = new ArrayList<Map<String, String>>();
            m = new HashMap<String, String>();
            m.put("data", String.valueOf(service.manipulation));
            m.put("tagName", tagNames[0]);
            childDataItem.add(m);
            m = new HashMap<String, String>();
            m.put("data", String.valueOf(service.patient));
            m.put("tagName", tagNames[1]);
            childDataItem.add(m);
            m = new HashMap<String, String>();
            m.put("data", String.valueOf(service.doctor));
            m.put("tagName", tagNames[2]);
            childDataItem.add(m);
            m = new HashMap<String, String>();
            m.put("data", String.valueOf(service.cost));
            m.put("tagName", tagNames[3]);
            childDataItem.add(m);
            m = new HashMap<String, String>();
            m.put("data", String.valueOf(service.date));
            m.put("tagName", tagNames[4]);
            childDataItem.add(m);
            childData.add(childDataItem);
        }

        makeListAdapter(groupData, childData);
    }

    public void prepareDataByVisits(ArrayList<Visit> visits, String[] tagNames) {
        Map<String, String> m;
        ArrayList<Map<String, String>> groupData;
        ArrayList<Map<String, String>> childDataItem;
        ArrayList<ArrayList<Map<String, String>>> childData;

        groupData = new ArrayList<Map<String, String>>();
        for (Visit visit : visits) {
            m = new HashMap<String, String>();
            m.put("groupName", visit.patient);
            groupData.add(m);
        }

        childData = new ArrayList<ArrayList<Map<String, String>>>();
        for(Visit visit : visits) {
            childDataItem = new ArrayList<Map<String, String>>();
            m = new HashMap<String, String>();
            m.put("data", String.valueOf(visit.patient));
            m.put("tagName", tagNames[0]);
            childDataItem.add(m);
            m = new HashMap<String, String>();
            m.put("data", String.valueOf(visit.date));
            m.put("tagName", tagNames[1]);
            childDataItem.add(m);
            m = new HashMap<String, String>();
            m.put("data", String.valueOf(visit.service));
            m.put("tagName", tagNames[2]);
            childDataItem.add(m);
            childData.add(childDataItem);
        }

        makeListAdapter(groupData, childData);
    }

    public void prepareDataForAddition(ArrayList<String> objectData, int tableId){
        defaultObject object;
        switch (tableId) {
            case 0:
                object = getRenderByObjectData(objectData);
                RendersTable rendersTable = new RendersTable(listActivity, this);
                rendersTable.addRow(object);
                break;
            case 1:
                object = getDoctorByObjectData(objectData);
                DoctorsTable doctorsTable = new DoctorsTable(listActivity, this);
                doctorsTable.addRow(object);
                break;
            case 2:
                object = getPatientByObjectData(objectData);
                PatientsTable patientsTable = new PatientsTable(listActivity, this);
                patientsTable.addRow(object);
                break;
            case 3:
                object = getServiceByObjectData(objectData);
                Service service = (Service) object;
                Log.d("NEWSERVICE", service.manipulation + service.patient + service.doctor + service.cost + service.date);
                ServicesTable servicesTable = new ServicesTable(listActivity, this);
                servicesTable.addRow(object);
                break;
            case 4:
                object = getVisitByObjectData(objectData);
                VisitsTable visitsTable = new VisitsTable(listActivity, this);
                visitsTable.addRow(object);
                break;
        }
    }

    public void prepareDataForDelete(ArrayList<String> objectData, int tableId){
        defaultObject object;
        switch (tableId){
            case 0:
                object = getRenderByObjectData(objectData);
                RendersTable rendersTable = new RendersTable(listActivity, this);
                rendersTable.deleteRow(object);
                break;
            case 1:
                object = getDoctorByObjectData(objectData);
                DoctorsTable doctorsTable = new DoctorsTable(listActivity, this);
                doctorsTable.deleteRow(object);
                break;
            case 2:
                object = getPatientByObjectData(objectData);
                PatientsTable patientsTable = new PatientsTable(listActivity, this);
                patientsTable.deleteRow(object);
                break;
            case 3:
                object = getServiceByObjectData(objectData);
                ServicesTable servicesTable = new ServicesTable(listActivity, this);
                servicesTable.deleteRow(object);
                break;
            case 4:
                object = getVisitByObjectData(objectData);
                VisitsTable visitsTable = new VisitsTable(listActivity, this);
                visitsTable.deleteRow(object);
                break;
            default:
                object = null;
        }
    }

    public void prepareDataForUpdate(ArrayList<String> oldObjectData, int tableId){// изменить название метода на более логичное
        Intent data;
        switch (tableId){
            case 0:
                Render render = getRenderByObjectData(oldObjectData);
                Log.d("123", render.service + " " + render.patient + " " + render.doctor + " " + render.sum + " " + render.doctor);
                data = new Intent(listActivity, RenderDataActivity.class);
                data.putExtra("oldService", render.service);
                data.putExtra("oldPatient", render.patient);
                data.putExtra("oldDoctor", render.doctor);
                data.putExtra("oldSum", render.sum);
                data.putExtra("oldDate", render.date);
                listActivity.startActivityForResult(data, 2);
                break;
            case 1:
                Doctor doctor = getDoctorByObjectData(oldObjectData);
                Log.d("123", doctor.code + " " + doctor.name + " " + doctor.passport + " " + doctor.address + " " + doctor.specialization + " " + doctor.experience + " " + doctor.berth);
                data = new Intent(listActivity, DoctorDataActivity.class);
                data.putExtra("code", doctor.code);
                data.putExtra("oldName", doctor.name);
                data.putExtra("oldPassport", doctor.passport);
                data.putExtra("oldAddress", doctor.address);
                data.putExtra("oldSpecialization", doctor.specialization);
                data.putExtra("oldExperience", doctor.experience);
                data.putExtra("oldBerth", doctor.berth);
                listActivity.startActivityForResult(data, 2);
                break;
            case 2:
                Patient patient = getPatientByObjectData(oldObjectData);
                Log.d("123", patient.code + " " + patient.name + " " + patient.passport + " " + patient.address + " " + patient.disease);
                data = new Intent(listActivity, PatientDataActivity.class);
                data.putExtra("code", patient.code);
                data.putExtra("oldName", patient.name);
                data.putExtra("oldPassport", patient.passport);
                data.putExtra("oldAddress", patient.address);
                data.putExtra("oldDisease", patient.disease);
                listActivity.startActivityForResult(data, 2);
                break;
            case 3:
                Service service = getServiceByObjectData(oldObjectData);
                Log.d("OLDPREPARE", service.patient + " " + service.doctor + " " + service.date + " " + service.cost + " " + service.manipulation);
                data = new Intent(listActivity, ServiceDataActivity.class);
                data.putExtra("oldManipulation", service.manipulation);
                data.putExtra("oldPatient", service.patient);
                data.putExtra("oldDoctor", service.doctor);
                data.putExtra("oldCost", service.cost);
                data.putExtra("oldDate", service.date);
                listActivity.startActivityForResult(data, 2);
                break;
            case 4:
                Visit visit = getVisitByObjectData(oldObjectData);
                Log.d("123", visit.patient + " " + visit.date + " " + visit.service);
                data = new Intent(listActivity, VisitsDataActivity.class);
                data.putExtra("oldPatient", visit.patient);
                data.putExtra("oldDate", visit.date);
                data.putExtra("oldService", visit.service);
                listActivity.startActivityForResult(data, 2);
                break;
        }
    }

    public void prepareDataForUpdate(ArrayList<String> oldObjectData, ArrayList<String> newObjectData, int tableId){
        defaultObject oldObject;
        defaultObject newObject;
        switch (tableId){
            case 0:
                oldObject = getRenderByObjectData(oldObjectData);
                newObject = getRenderByObjectData(newObjectData);
                RendersTable rendersTable = new RendersTable(listActivity, this);
                rendersTable.updateRow(oldObject, newObject);
                break;
            case 1:
                oldObject = getDoctorByObjectData(oldObjectData);
                newObject = getDoctorByObjectData(newObjectData);
                DoctorsTable doctorsTable = new DoctorsTable(listActivity, this);
                doctorsTable.updateRow(oldObject, newObject);
                break;
            case 2:
                oldObject = getPatientByObjectData(oldObjectData);
                newObject = getPatientByObjectData(newObjectData);
                PatientsTable patientsTable = new PatientsTable(listActivity, this);
                patientsTable.updateRow(oldObject, newObject);
                break;
            case 3:
                oldObject = getServiceByObjectData(oldObjectData);
                newObject = getServiceByObjectData(newObjectData);
                ServicesTable servicesTable = new ServicesTable(listActivity, this);
                servicesTable.updateRow(oldObject, newObject);
                break;
            case 4:
                oldObject = getVisitByObjectData(oldObjectData);
                newObject = getVisitByObjectData(newObjectData);
                VisitsTable visitsTable = new VisitsTable(listActivity, this);
                visitsTable.updateRow(oldObject, newObject);
                break;
        }
    }

    private Render getRenderByObjectData(ArrayList<String> objectData){
        Render render;
        render = new Render(objectData.get(0),
                objectData.get(1),
                objectData.get(2),
                Float.parseFloat(objectData.get(3)),
                objectData.get(4));
        return render;
    }

    private Patient getPatientByObjectData(ArrayList<String> objectData){
        Patient patient;
        if(objectData.size()==5){
            patient = new Patient(Integer.parseInt(objectData.get(0)),
                    objectData.get(1),
                    objectData.get(2),
                    objectData.get(3),
                    objectData.get(4));
            return patient;
        }else{
            patient = new Patient(objectData.get(0),
                    objectData.get(1),
                    objectData.get(2),
                    objectData.get(3));
            return patient;
        }
    }

    private Doctor getDoctorByObjectData(ArrayList<String> objectData){
        Doctor doctor;
        if(objectData.size()==7) {
            doctor = new Doctor(Integer.parseInt(objectData.get(0)),
                    objectData.get(1),
                    objectData.get(2),
                    objectData.get(3),
                    objectData.get(4),
                    Integer.parseInt(objectData.get(5)),
                    objectData.get(6));
            return doctor;
        }
        else{
            doctor = new Doctor(objectData.get(0),
                    objectData.get(1),
                    objectData.get(2),
                    objectData.get(3),
                    Integer.parseInt(objectData.get(4)),
                    objectData.get(5));
            return doctor;
        }
    }

    private Service getServiceByObjectData(ArrayList<String> objectData){
        Service service = new Service(objectData.get(0),
                    objectData.get(1),
                    objectData.get(2),
                    Float.parseFloat(objectData.get(3)),
                    objectData.get(4));
        return service;
    }

    private Visit getVisitByObjectData(ArrayList<String> objectData){
        Visit visit = new Visit(objectData.get(0),
                    objectData.get(1),
                    objectData.get(2));
        return visit;
    }

    public void makeListAdapter(ArrayList<Map<String, String>> groupData, ArrayList<ArrayList<Map<String, String>>> childData){
        String groupFrom[] = new String[] {"groupName"};
        int groupTo[] = new int[] {R.id.list_item_group};
        String childFrom[] = new String[] {"data", "tagName"};
        int childTo[] = new int[] {R.id.list_item_child, R.id.list_item_child_tag};
        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
                listActivity,
                groupData,
                R.layout.list_item_group,
                groupFrom,
                groupTo,
                childData,
                R.layout.list_item_child,
                childFrom,
                childTo);
        listActivity.setListAdapter(adapter);
    }

    public void updateDataByTableId(int tableId){
        defaultTable presenter;
        switch (tableId){
            case 0:
                presenter = new RendersTable(listActivity, this);
                break;
            case 1:
                presenter = new DoctorsTable(listActivity, this);
                break;
            case 2:
                presenter = new PatientsTable(listActivity, this);
                break;
            case 3:
                presenter = new ServicesTable(listActivity, this);
                break;
            case 4:
                presenter = new VisitsTable(listActivity, this);
                break;
            default:
                presenter = null;
        }
        presenter.fetchData();
    }

    public void sendMessage(String result){
        listActivity.showMessage(result);
    }
}
