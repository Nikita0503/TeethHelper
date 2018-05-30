package com.example.nikita.teethhelper.presenters;

import android.content.Intent;
import android.util.Log;
import android.widget.SimpleExpandableListAdapter;

import com.example.nikita.teethhelper.Contract;
import com.example.nikita.teethhelper.R;
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
import com.example.nikita.teethhelper.tableHelpers.defaultTable;
import com.example.nikita.teethhelper.tableHelpers.DoctorsTable;
import com.example.nikita.teethhelper.tableHelpers.PatientsTable;
import com.example.nikita.teethhelper.tableHelpers.RendersTable;
import com.example.nikita.teethhelper.tableHelpers.ServicesTable;
import com.example.nikita.teethhelper.tableHelpers.VisitsTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Nikita on 15.04.2018.
 */

public class ListPresenter implements Contract.Presenter{
    public static final int RENDERS_TABLE_ID = 0;
    public static final int DOCTORS_TABLE_ID = 1;
    public static final int PATIENTS_TABLE_ID = 2;
    public static final int SERVICES_TABLE_ID = 3;
    public static final int VISITS_TABLE_ID = 4;
    public CompositeDisposable disposables;
    private ListActivity mListActivity;

    public ListPresenter(ListActivity listActivity){
        this.mListActivity = listActivity;
    }

    @Override
    public void onStart(){
        disposables = new CompositeDisposable();
    }

    public void fetchData(int tableId){
        switch (tableId){
            case RENDERS_TABLE_ID:
                RendersTable rendersTable = new RendersTable(mListActivity, this);
                rendersTable.fetchData();
                mListActivity.setColors(mListActivity.getResources().getColor(R.color.colorDarkAqua), mListActivity.getResources().getColor(R.color.colorAqua), mListActivity.getResources().getString(R.string.renders));
                break;
            case DOCTORS_TABLE_ID:
                DoctorsTable doctorsTable = new DoctorsTable(mListActivity, this);
                doctorsTable.fetchData();
                mListActivity.setColors(mListActivity.getResources().getColor(R.color.colorDarkRed), mListActivity.getResources().getColor(R.color.colorRed), mListActivity.getResources().getString(R.string.doctors));
                break;
            case PATIENTS_TABLE_ID:
                PatientsTable patientsTable = new PatientsTable(mListActivity, this);
                patientsTable.fetchData();
                mListActivity.setColors(mListActivity.getResources().getColor(R.color.colorDarkPurple), mListActivity.getResources().getColor(R.color.colorPurple), mListActivity.getResources().getString(R.string.patients));
                break;
            case SERVICES_TABLE_ID:
                ServicesTable servicesTable = new ServicesTable(mListActivity, this);
                servicesTable.fetchData();
                mListActivity.setColors(mListActivity.getResources().getColor(R.color.colorDarkOrange), mListActivity.getResources().getColor(R.color.colorOrange), mListActivity.getResources().getString(R.string.services));
                break;
            case VISITS_TABLE_ID:
                VisitsTable visitsTable = new VisitsTable(mListActivity, this);
                visitsTable.fetchData();
                mListActivity.setColors(mListActivity.getResources().getColor(R.color.colorDarkGreen), mListActivity.getResources().getColor(R.color.colorGreen), mListActivity.getResources().getString(R.string.visits));
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
        final defaultObject object;
        defaultTable table = null;
        switch (tableId) {
            case RENDERS_TABLE_ID:
                object = getRenderByObjectData(objectData);
                table = new RendersTable(mListActivity.getApplicationContext(), this);
                break;
            case DOCTORS_TABLE_ID:
                object = getDoctorByObjectData(objectData);
                table = new DoctorsTable(mListActivity.getApplicationContext(), this);
                break;
            case PATIENTS_TABLE_ID:
                object = getPatientByObjectData(objectData);
                table = new PatientsTable(mListActivity.getApplicationContext(), this);
                break;
            case SERVICES_TABLE_ID:
                object = getServiceByObjectData(objectData);
                table = new ServicesTable(mListActivity.getApplicationContext(), this);
                break;
            case VISITS_TABLE_ID:
                object = getVisitByObjectData(objectData);
                table = new VisitsTable(mListActivity.getApplicationContext(), this);
                break;
            default:
                object = null;
        }
        Observable<defaultObject> observable = Observable.create(new ObservableOnSubscribe<defaultObject>() {
            @Override
            public void subscribe(ObservableEmitter<defaultObject> e) throws Exception {
                e.onNext(object);
                e.onComplete();
            }
        });
        disposables.add(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(table.addRow()));
    }

    public void prepareDataForDelete(ArrayList<String> objectData, int tableId){
        final defaultObject object;
        defaultTable table = null;
        switch (tableId){
            case RENDERS_TABLE_ID:
                object = getRenderByObjectData(objectData);
                table = new RendersTable(mListActivity.getApplicationContext(), this);
                break;
            case DOCTORS_TABLE_ID:
                object = getDoctorByObjectData(objectData);
                table = new DoctorsTable(mListActivity.getApplicationContext(), this);
                break;
            case PATIENTS_TABLE_ID:
                object = getPatientByObjectData(objectData);
                table = new PatientsTable(mListActivity.getApplicationContext(), this);
                break;
            case SERVICES_TABLE_ID:
                object = getServiceByObjectData(objectData);
                table = new ServicesTable(mListActivity.getApplicationContext(), this);
                break;
            case VISITS_TABLE_ID:
                object = getVisitByObjectData(objectData);
                table = new VisitsTable(mListActivity.getApplicationContext(), this);
                break;
            default:
                object = null;
        }
        Observable<defaultObject> observable = Observable.create(new ObservableOnSubscribe<defaultObject>() {
            @Override
            public void subscribe(ObservableEmitter<defaultObject> e) throws Exception {
                e.onNext(object);
                e.onComplete();
            }
        });
        disposables.add(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(table.deleteRow()));
    }

    public void prepareDataForUpdate(ArrayList<String> oldObjectData, int tableId){// изменить название метода на более логичное
        Intent data;
        switch (tableId){
            case RENDERS_TABLE_ID:
                Render render = getRenderByObjectData(oldObjectData);
                Log.d("123", render.service + " " + render.patient + " " + render.doctor + " " + render.sum + " " + render.doctor);
                data = new Intent(mListActivity, RenderDataActivity.class);
                data.putExtra("oldService", render.service);
                data.putExtra("oldPatient", render.patient);
                data.putExtra("oldDoctor", render.doctor);
                data.putExtra("oldSum", render.sum);
                data.putExtra("oldDate", render.date);
                mListActivity.startActivityForResult(data, 2);
                break;
            case DOCTORS_TABLE_ID:
                Doctor doctor = getDoctorByObjectData(oldObjectData);
                Log.d("123", doctor.code + " " + doctor.name + " " + doctor.passport + " " + doctor.address + " " + doctor.specialization + " " + doctor.experience + " " + doctor.berth);
                data = new Intent(mListActivity, DoctorDataActivity.class);
                data.putExtra("code", doctor.code);
                data.putExtra("oldName", doctor.name);
                data.putExtra("oldPassport", doctor.passport);
                data.putExtra("oldAddress", doctor.address);
                data.putExtra("oldSpecialization", doctor.specialization);
                data.putExtra("oldExperience", doctor.experience);
                data.putExtra("oldBerth", doctor.berth);
                mListActivity.startActivityForResult(data, 2);
                break;
            case PATIENTS_TABLE_ID:
                Patient patient = getPatientByObjectData(oldObjectData);
                Log.d("123", patient.code + " " + patient.name + " " + patient.passport + " " + patient.address + " " + patient.disease);
                data = new Intent(mListActivity, PatientDataActivity.class);
                data.putExtra("code", patient.code);
                data.putExtra("oldName", patient.name);
                data.putExtra("oldPassport", patient.passport);
                data.putExtra("oldAddress", patient.address);
                data.putExtra("oldDisease", patient.disease);
                mListActivity.startActivityForResult(data, 2);
                break;
            case SERVICES_TABLE_ID:
                Service service = getServiceByObjectData(oldObjectData);
                Log.d("OLDPREPARE", service.patient + " " + service.doctor + " " + service.date + " " + service.cost + " " + service.manipulation);
                data = new Intent(mListActivity, ServiceDataActivity.class);
                data.putExtra("oldManipulation", service.manipulation);
                data.putExtra("oldPatient", service.patient);
                data.putExtra("oldDoctor", service.doctor);
                data.putExtra("oldCost", service.cost);
                data.putExtra("oldDate", service.date);
                mListActivity.startActivityForResult(data, 2);
                break;
            case VISITS_TABLE_ID:
                Visit visit = getVisitByObjectData(oldObjectData);
                Log.d("123", visit.patient + " " + visit.date + " " + visit.service);
                data = new Intent(mListActivity, VisitsDataActivity.class);
                data.putExtra("oldPatient", visit.patient);
                data.putExtra("oldDate", visit.date);
                data.putExtra("oldService", visit.service);
                mListActivity.startActivityForResult(data, 2);
                break;
        }
    }

    public void prepareDataForUpdate(ArrayList<String> oldObjectData, ArrayList<String> newObjectData, int tableId){
        final defaultObject oldObject;
        final defaultObject newObject;
        defaultTable table = null;
        switch (tableId){
            case RENDERS_TABLE_ID:
                oldObject = getRenderByObjectData(oldObjectData);
                newObject = getRenderByObjectData(newObjectData);
                table = new RendersTable(mListActivity.getApplicationContext(), this);
                break;
            case DOCTORS_TABLE_ID:
                oldObject = getDoctorByObjectData(oldObjectData);
                newObject = getDoctorByObjectData(newObjectData);
                table = new DoctorsTable(mListActivity.getApplicationContext(), this);
                break;
            case PATIENTS_TABLE_ID:
                oldObject = getPatientByObjectData(oldObjectData);
                newObject = getPatientByObjectData(newObjectData);
                table = new PatientsTable(mListActivity.getApplicationContext(), this);
                break;
            case SERVICES_TABLE_ID:
                oldObject = getServiceByObjectData(oldObjectData);
                newObject = getServiceByObjectData(newObjectData);
                table = new ServicesTable(mListActivity.getApplicationContext(), this);
                break;
            case VISITS_TABLE_ID:
                oldObject = getVisitByObjectData(oldObjectData);
                newObject = getVisitByObjectData(newObjectData);
                table = new VisitsTable(mListActivity.getApplicationContext(), this);
                break;
            default:
                oldObject = null;
                newObject = null;
        }
        Observable<defaultObject[]> observable = Observable.create(new ObservableOnSubscribe<defaultObject[]>() {
            @Override
            public void subscribe(ObservableEmitter<defaultObject[]> e) throws Exception {
                e.onNext(new defaultObject[]{oldObject, newObject});
                e.onComplete();
            }
        });
        disposables.add(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(table.updateRow()));
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
                mListActivity,
                groupData,
                R.layout.list_item_group,
                groupFrom,
                groupTo,
                childData,
                R.layout.list_item_child,
                childFrom,
                childTo);
        mListActivity.setListAdapter(adapter);
    }

    public void updateDataByTableId(int tableId){
        defaultTable presenter;
        switch (tableId){
            case RENDERS_TABLE_ID:
                presenter = new RendersTable(mListActivity.getApplicationContext(), this);
                break;
            case DOCTORS_TABLE_ID:
                presenter = new DoctorsTable(mListActivity.getApplicationContext(), this);
                break;
            case PATIENTS_TABLE_ID:
                presenter = new PatientsTable(mListActivity.getApplicationContext(), this);
                break;
            case SERVICES_TABLE_ID:
                presenter = new ServicesTable(mListActivity.getApplicationContext(), this);
                break;
            case VISITS_TABLE_ID:
                presenter = new VisitsTable(mListActivity.getApplicationContext(), this);
                break;
            default:
                presenter = null;
        }
        presenter.fetchData();
    }

    public void sendMessage(String result){
        mListActivity.showMessage(result);
    }

    @Override
    public void onStop(){
        disposables.clear();
    }
}
