package com.example.nikita.teethhelper.presenters;

import com.example.nikita.teethhelper.Contract;
import com.example.nikita.teethhelper.PDFWriter;
import com.example.nikita.teethhelper.R;
import com.example.nikita.teethhelper.UI.ReportActivity;
import com.example.nikita.teethhelper.data.Doctor;
import com.example.nikita.teethhelper.data.Patient;
import com.example.nikita.teethhelper.data.Render;
import com.example.nikita.teethhelper.data.Visit;
import com.example.nikita.teethhelper.data.defaultObject;
import com.example.nikita.teethhelper.tableHelpers.DoctorsTable;
import com.example.nikita.teethhelper.tableHelpers.PatientsTable;
import com.example.nikita.teethhelper.tableHelpers.RendersTable;
import com.example.nikita.teethhelper.tableHelpers.VisitsTable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.StringTokenizer;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Nikita on 21.05.2018.
 */

public class ReportActivityPresenter implements Contract.Presenter{
    private ReportActivity mReportActivity;
    private CompositeDisposable mDisposables;

    public ReportActivityPresenter(ReportActivity reportActivity){
        this.mReportActivity = reportActivity;
    }

    @Override
    public void onStart(){
        mDisposables = new CompositeDisposable();
    }

    public void writeToFile(String typeOFOrder){
        final PDFWriter pdfWriter = new PDFWriter(mReportActivity.getApplicationContext());
        switch (typeOFOrder){
            case "doctors":
                DoctorsTable doctorsTable = new DoctorsTable(mReportActivity.getApplicationContext());
                Disposable doctorsDisposable =  doctorsTable.getDoctors.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<ArrayList<Doctor>>() {
                            @Override
                            public void onSuccess(final ArrayList<Doctor> doctors) {
                                Observable<ArrayList<String[]>> observable = Observable.create(new ObservableOnSubscribe<ArrayList<String[]>>() {
                                    @Override
                                    public void subscribe(ObservableEmitter<ArrayList<String[]>> e) throws Exception {
                                        e.onNext(getArrayListByDoctors(doctors));
                                        e.onComplete();
                                    }
                                });
                                observable
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread());
                                mDisposables.add(observable.subscribeWith(pdfWriter.writeDoctors()));
                            }
                            @Override
                            public void onError(Throwable e) {
                                mReportActivity.showMessage(mReportActivity.getResources().getString(R.string.error));
                            }
                        });
                mDisposables.add(doctorsDisposable);
                break;

            case "patients":
                PatientsTable patientsTable = new PatientsTable(mReportActivity.getApplicationContext());
                Disposable patientDisposable = patientsTable.getPatients.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<ArrayList<Patient>>() {
                            @Override
                            public void onSuccess(final ArrayList<Patient> patients) {
                                Observable<ArrayList<String[]>> observable = Observable.create(new ObservableOnSubscribe<ArrayList<String[]>>() {
                                    @Override
                                    public void subscribe(ObservableEmitter<ArrayList<String[]>> e) throws Exception {
                                        e.onNext(getArrayListByPatients(patients));
                                        e.onComplete();
                                    }
                                });
                                observable
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread());
                                mDisposables.add(observable.subscribeWith(pdfWriter.writePatients()));
                            }
                            @Override
                            public void onError(Throwable e) {
                                mReportActivity.showMessage(mReportActivity.getResources().getString(R.string.error));
                            }
                        });
                mDisposables.add(patientDisposable);
                break;

            case "renders":
                RendersTable rendersTable = new RendersTable(mReportActivity.getApplicationContext());
                Disposable rendersDisposable = rendersTable.getRenders.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<ArrayList<Render>>() {
                            @Override
                            public void onSuccess(final ArrayList<Render> renders) {
                                Observable<ArrayList<String[]>> observable = Observable.create(new ObservableOnSubscribe<ArrayList<String[]>>() {
                                    @Override
                                    public void subscribe(ObservableEmitter<ArrayList<String[]>> e) throws Exception {
                                        e.onNext(getArrayListByRenders(renders));
                                        e.onComplete();
                                    }
                                });
                                observable
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread());
                                mDisposables.add(observable.subscribeWith(pdfWriter.writeRenders()));
                            }
                            @Override
                            public void onError(Throwable e) {
                                mReportActivity.showMessage(mReportActivity.getResources().getString(R.string.error));
                            }
                        });
                mDisposables.add(rendersDisposable);
                break;
            case "visits for period":
                VisitsTable visitsTable = new VisitsTable(mReportActivity.getApplicationContext());
                Disposable visitsDisposable = visitsTable.getVisits.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<ArrayList<Visit>>() {
                            @Override
                            public void onSuccess(ArrayList<Visit> visitsBeforeSelection) {
                                final ArrayList<Visit> visitsAfterSelection = getVisitsSelectionByDates(visitsBeforeSelection , mReportActivity.date.getStringExtra("dateAfter"), mReportActivity.date.getStringExtra("dateBefore"));
                                Observable<ArrayList<String[]>> observable = Observable.create(new ObservableOnSubscribe<ArrayList<String[]>>() {
                                    @Override
                                    public void subscribe(ObservableEmitter<ArrayList<String[]>> e) throws Exception {
                                        e.onNext(getArrayListByVisits(visitsAfterSelection));
                                        e.onComplete();
                                    }
                                });
                                observable
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread());
                                mDisposables.add(observable.subscribeWith(pdfWriter.writeVisits()));
                            }
                            @Override
                            public void onError(Throwable e) {
                                mReportActivity.showMessage(mReportActivity.getResources().getString(R.string.error));
                            }
                        });
                mDisposables.add(visitsDisposable);
                break;

            case "visits statistic for period":
                VisitsTable visitsTable2 = new VisitsTable(mReportActivity.getApplicationContext());
                Disposable visitsStatisticDisposable = visitsTable2.getVisits.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<ArrayList<Visit>>() {
                            @Override
                            public void onSuccess(ArrayList<Visit> visitsBeforeSelection) {
                                final ArrayList<Visit> visitsAfterSelection2 = getVisitsSelectionByDates(visitsBeforeSelection , mReportActivity.date.getStringExtra("dateAfter"), mReportActivity.date.getStringExtra("dateBefore"));
                                Observable<ArrayList<String>> observable = Observable.create(new ObservableOnSubscribe<ArrayList<String>>() {
                                    @Override
                                    public void subscribe(ObservableEmitter<ArrayList<String>> e) throws Exception {
                                        e.onNext(getVisitsStatistic(visitsAfterSelection2));
                                        e.onComplete();
                                    }
                                });
                                observable
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread());
                                mDisposables.add(observable.subscribeWith(pdfWriter.writeVisitsStatistic()));
                            }
                            @Override
                            public void onError(Throwable e) {
                                mReportActivity.showMessage(mReportActivity.getResources().getString(R.string.error));
                            }
                        });
                mDisposables.add(visitsStatisticDisposable);
                break;
        }
    }

    private Date getDateByStringTokenizer(StringTokenizer stringDate){
        Date date = null;
        while(stringDate.hasMoreTokens()) {
            int day = Integer.parseInt(stringDate.nextToken());
            int month = Integer.parseInt(stringDate.nextToken());
            int year = Integer.parseInt(stringDate.nextToken());
            date = new Date(year, month, day);
        }
        return date;
    }

    private ArrayList<String[]> getArrayListByRenders(ArrayList<Render> renders){
        ArrayList<String[]> arrayList = new ArrayList<String[]>();
        String[] tagNames = mReportActivity.getResources().getStringArray(R.array.renderTagNames);
        String row[];
        for(int i = 0; i < renders.size(); i++){
            row = new String[5];
            row[0] = tagNames[0] + " : " + renders.get(i).service;
            row[1] = tagNames[1] + " : " + renders.get(i).patient;
            row[2] = tagNames[2] + " : " + renders.get(i).doctor;
            row[3] = tagNames[3] + " : " + renders.get(i).sum;
            row[4] = tagNames[4] + " : " + renders.get(i).date;
            arrayList.add(row);
        }
        return arrayList;
    }

    private ArrayList<String[]> getArrayListByDoctors(ArrayList<Doctor> doctors){
        ArrayList<String[]> arrayList = new ArrayList<String[]>();
        String[] tagNames = mReportActivity.getResources().getStringArray(R.array.doctorTagNames);
        String row[];
        for(int i = 0; i < doctors.size(); i++){
            row = new String[6];
            row[0] = tagNames[1] + " : " + doctors.get(i).name;
            row[1] = tagNames[2] + " : " + doctors.get(i).passport;
            row[2] = tagNames[3] + " : " + doctors.get(i).address;
            row[3] = tagNames[4] + " : " + doctors.get(i).specialization;
            row[4] = tagNames[5] + " : " + doctors.get(i).experience;
            row[5] = tagNames[6] + " : " + doctors.get(i).berth;
            arrayList.add(row);
        }
        return arrayList;
    }

    private ArrayList<String[]> getArrayListByPatients(ArrayList<Patient> patients){
        ArrayList<String[]> arrayList = new ArrayList<String[]>();
        String[] tagNames = mReportActivity.getResources().getStringArray(R.array.patientTagNames);
        String row[];
        for(int i = 0; i < patients.size(); i++){
            row = new String[4];
            row[0] = tagNames[1] + " : " + patients.get(i).name;
            row[1] = tagNames[2] + " : " + patients.get(i).passport;
            row[2] = tagNames[3] + " : " + patients.get(i).address;
            row[3] = tagNames[4] + " : " + patients.get(i).disease;
            arrayList.add(row);
        }
        return arrayList;
    }

    private ArrayList<String[]> getArrayListByVisits(ArrayList<Visit> visits){
        ArrayList<String[]> arrayList = new ArrayList<String[]>();;
        String[] tagNames = mReportActivity.getResources().getStringArray(R.array.visitTagNames);
        String row[];
        for(int i = 0; i < visits.size(); i++){
            row = new String[3];
            row[0] = tagNames[0] + " : " + visits.get(i).patient;
            row[1] = tagNames[1] + " : " + visits.get(i).date;
            row[2] = tagNames[2] + " : " + visits.get(i).service;
            arrayList.add(row);
        }
        return arrayList;
    }

    private ArrayList<Visit> getVisitsSelectionByDates(ArrayList<Visit> visits, String startDateString, String endDateString){
        ArrayList<Visit> visitsAfterSelection = new ArrayList<Visit>();
        Date startDate = getDateByStringTokenizer(new StringTokenizer(startDateString, "."));
        Date endDate =  getDateByStringTokenizer(new StringTokenizer(endDateString, "."));
        for(int i = 0; i < visits.size(); i++){
            Date date =  getDateByStringTokenizer(new StringTokenizer(visits.get(i).date, "."));
            if(date.after(startDate) && date.before(endDate)){
                visitsAfterSelection.add(visits.get(i));
            }
        }
        return visitsAfterSelection;
    }

    private ArrayList<String> getVisitsStatistic(ArrayList<Visit> visits){
        ArrayList<String> statistic = new ArrayList<String>();
        HashSet<String> patientsSet = new HashSet<String>();
        int counter;
        for(int i = 0; i < visits.size(); i++){
            patientsSet.add(visits.get(i).patient);
        }
        ArrayList<String> patients = new ArrayList<String>();
        patients.addAll(patientsSet);
        for(int i = 0; i < patients.size(); i++){
            counter = 0;
            for(int j = 0; j < visits.size(); j++){
                if(patients.get(i).equals(visits.get(j).patient)){
                    counter++;
                }
            }
            statistic.add(patients.get(i) + " : " + counter);
            counter = 0;
        }
        return statistic;
    }

@   Override
    public void onStop(){
        mDisposables.clear();
    }
}
