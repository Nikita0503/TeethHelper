package com.example.nikita.teethhelper;

import com.example.nikita.teethhelper.data.Doctor;
import com.example.nikita.teethhelper.data.Patient;
import com.example.nikita.teethhelper.data.Render;
import com.example.nikita.teethhelper.data.Visit;
import com.example.nikita.teethhelper.tables.DoctorsTable;
import com.example.nikita.teethhelper.tables.PatientsTable;
import com.example.nikita.teethhelper.tables.RendersTable;
import com.example.nikita.teethhelper.tables.VisitsTable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.StringTokenizer;

/**
 * Created by Nikita on 21.05.2018.
 */

public class ReportActivityPresenter {
    ReportActivity reportActivity;

    public ReportActivityPresenter(ReportActivity reportActivity){
        this.reportActivity = reportActivity;
    }

    public void writeToFile(String typeOFOrder){
        PDFWriter pdfWriter = new PDFWriter(reportActivity.getApplicationContext());
        switch (typeOFOrder){
            case "doctors":
                DoctorsTable doctorsTable = new DoctorsTable(reportActivity.getApplicationContext());
                ArrayList<Doctor> doctors = doctorsTable.getDoctors();
                pdfWriter.writeDoctors(getArrayListByDoctors(doctors));
                break;
            case "patients":
                PatientsTable patientsTable = new PatientsTable(reportActivity.getApplicationContext());
                ArrayList<Patient> patients = patientsTable.getPatients();
                pdfWriter.writePatients(getArrayListByPatients(patients));
                break;
            case "renders":
                RendersTable rendersTable = new RendersTable(reportActivity.getApplicationContext());
                ArrayList<Render> renders = rendersTable.getRenders();
                pdfWriter.writeRenders(getArrayListByRenders(renders));
                break;
            case "visits for period":
                VisitsTable visitsTable = new VisitsTable(reportActivity.getApplicationContext());
                ArrayList<Visit> visitsBeforeSelection = visitsTable.getVisits();
                ArrayList<Visit> visitsAfterSelection = getVisitsSelectionByDates(visitsBeforeSelection , reportActivity.date.getStringExtra("dateAfter"), reportActivity.date.getStringExtra("dateBefore"));
                pdfWriter.writeVisits(getArrayListByVisits(visitsAfterSelection));
                break;
            case "visits statistic for period":
                VisitsTable visitsTable2 = new VisitsTable(reportActivity.getApplicationContext());
                ArrayList<Visit> visitsBeforeSelection2 = visitsTable2.getVisits();
                ArrayList<Visit> visitsAfterSelection2 = getVisitsSelectionByDates(visitsBeforeSelection2 , reportActivity.date.getStringExtra("dateAfter"), reportActivity.date.getStringExtra("dateBefore"));
                pdfWriter.writeVisitsStatistic(getVisitsStatistic(visitsAfterSelection2));
                break;
        }
    }

    private ArrayList<String[]> getArrayListByDoctors(ArrayList<Doctor> doctors){
        ArrayList<String[]> arrayList = new ArrayList<String[]>();
        String[] tagNames = reportActivity.getResources().getStringArray(R.array.doctorTagNames);
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
        String[] tagNames = reportActivity.getResources().getStringArray(R.array.patientTagNames);
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

    private ArrayList<String[]> getArrayListByRenders(ArrayList<Render> renders){
        ArrayList<String[]> arrayList = new ArrayList<String[]>();
        String[] tagNames = reportActivity.getResources().getStringArray(R.array.renderTagNames);
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

    private ArrayList<String[]> getArrayListByVisits(ArrayList<Visit> visits){
        ArrayList<String[]> arrayList = new ArrayList<String[]>();;
        String[] tagNames = reportActivity.getResources().getStringArray(R.array.visitTagNames);
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


}
