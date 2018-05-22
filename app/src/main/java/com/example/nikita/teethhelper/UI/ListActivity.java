package com.example.nikita.teethhelper.UI;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;

import com.example.nikita.teethhelper.ListPresenter;
import com.example.nikita.teethhelper.R;
import com.example.nikita.teethhelper.UI.RecordActivities.DoctorDataActivity;
import com.example.nikita.teethhelper.UI.RecordActivities.PatientDataActivity;
import com.example.nikita.teethhelper.UI.RecordActivities.RenderDataActivity;
import com.example.nikita.teethhelper.UI.RecordActivities.ServiceDataActivity;
import com.example.nikita.teethhelper.UI.RecordActivities.VisitsDataActivity;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class ListActivity extends AppCompatActivity {

    int tableId;
    @BindView(R.id.elvMain)
    ExpandableListView elvMain;
    //String[] tagNames = new String[] {"Code", "Name", "Passport", "Address", "Disease"};
    /** Called when the activity is first created. */



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        ExpandableListView.ExpandableListContextMenuInfo info = (ExpandableListView.ExpandableListContextMenuInfo) item
                .getMenuInfo();

        Integer position = (int) info.id;

        //AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        ArrayList<String> objectData = new ArrayList<String>();
        for(int i = 0; i < elvMain.getExpandableListAdapter().getChildrenCount(position); i++) {
            Map<String, String> m = (Map<String, String>) elvMain.getExpandableListAdapter().getChild(position, i);
            objectData.add(m.get("data"));
            Log.d("123", m.get("data"));
        }
        ListPresenter listPresenter = new ListPresenter(ListActivity.this);
        switch (item.getItemId()) {
            case R.id.edit:
                Log.d("123", "EDIT "+position); // метод, выполняющий действие при редактировании пункта меню
                Log.d("123", "TABLE "+tableId); // метод, выполняющий действие при редактировании пункта меню
                listPresenter.prepareDataForUpdate(objectData, tableId);
                return true;
            case R.id.delete:
                Log.d("123", "DELETE "+position); //метод, выполняющий действие при удалении пункта меню
                Log.d("123", "TABLE "+tableId); //метод, выполняющий действие при удалении пункта меню
                listPresenter.prepareDataForDelete(objectData, tableId);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void setColors(int titleBarColor, int statusBarColor, String tableName){
        /*View view = this.getWindow().getDecorView();
        view.setBackgroundColor(backgroundColor);*/
       //    elvMain.setBackgroundColor(titleBarColor);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(statusBarColor));
        getSupportActionBar().setTitle(tableName);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.setStatusBarColor(titleBarColor);
        }
       // getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#3B3B3B\">" + s + "</font>"));//переделать эту хуйню в styles
        //this.setTheme(R.style.RedTheme);

    }

    public void showMessage(String message){
        Toasty.success(getApplicationContext(), message, Toast.LENGTH_SHORT, true).show();
    }


    public void setListAdapter(SimpleExpandableListAdapter adapter){
        elvMain.setAdapter(adapter);
    }

    @Override
    public void onResume(){
        super.onResume();
        ListPresenter presenter = new ListPresenter(this);
        presenter.fetchData(tableId);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        tableId = intent.getIntExtra("tableId", -1);
        registerForContextMenu(elvMain);
        /*elvMain.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<String> objectData = new ArrayList<String>();
                for(int i = 0; i < elvMain.getExpandableListAdapter().getChildrenCount(position); i++) {
                        Map<String, String> m = (Map<String, String>) elvMain.getExpandableListAdapter().getChild(position, i);
                        objectData.add(m.get("data"));
                        Log.d("123", m.get("data"));
                    }
                    ListPresenter listPresenter = new ListPresenter(ListActivity.this);
                    listPresenter.prepareDataForUpdate(objectData, tableId);
                    //listPresenter.prepareDataForDelete(objectData, tableId);
                    //String selected =((TextView)view.findViewById(R.id.list_item_group)).getText().toString();
                    //Log.d("123", parent.getItemAtPosition(position)+"");
                    //int groupPosition = elvMain.getPackedPositionGroup(id);
                    //int childPosition = elvMain.getPackedPositionChild(id);
                    //Log.d("123", groupPosition + " " + childPosition);
                return true;

            }
        });*/
        //ListPresenter presenter = new ListPresenter(this);
        //presenter.fetchData(tableId);

        

        /*DBHepler dbHepler = new DBHepler(this);
        SQLiteDatabase db = dbHepler.getWritableDatabase();
//ИМЕНА ГРУПП
        Cursor names = db.query("patients", null, null, null, null, null, null);
        groupData = new ArrayList<Map<String, String>>();
        if (names.moveToFirst()) {
            // определяем номера столбцов по имени в выборке
            int nameColIndex = names.getColumnIndex("name");
            do {
                m = new HashMap<String, String>();
                m.put("groupName", names.getString(nameColIndex)); // имя компании
                groupData.add(m);
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false -
                // выходим из цикла
            } while (names.moveToNext());
        } else
            Log.d("LOG", "0 rows");
        names.close();
        // список атрибутов групп для чтения
        String groupFrom[] = new String[] {"groupName"};
        // список ID view-элементов, в которые будет помещены атрибуты групп
        int groupTo[] = new int[] {R.id.list_item_group};
//ИМЕНА ГРУПП
        Cursor c = db.query("patients", null, null, null, null, null, null);
        // создаем коллекцию для коллекций элементов
        childData = new ArrayList<ArrayList<Map<String, String>>>();
//СОДЕРЖИМОЕ ГРУПП
        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("code");
            int nameColIndex = c.getColumnIndex("name");
            int passportColIndex = c.getColumnIndex("passport");
            int addressColIndex = c.getColumnIndex("address");
            int diseaseColIndex = c.getColumnIndex("disease");
            do {

                childDataItem = new ArrayList<Map<String, String>>();
                m = new HashMap<String, String>();
                m.put("phoneName", String.valueOf(c.getInt(idColIndex))); // название телефона
                m.put("tagName", tagNames[0]); // имя компании
                childDataItem.add(m);

                m = new HashMap<String, String>();
                m.put("phoneName", c.getString(nameColIndex)); // название телефона
                m.put("tagName", tagNames[1]); // имя компании
                childDataItem.add(m);

                m = new HashMap<String, String>();
                m.put("phoneName", c.getString(passportColIndex)); // название телефона
                m.put("tagName", tagNames[2]); // имя компании
                childDataItem.add(m);

                m = new HashMap<String, String>();
                m.put("phoneName", c.getString(addressColIndex)); // название телефона
                m.put("tagName", tagNames[3]); // имя компании
                childDataItem.add(m);

                m = new HashMap<String, String>();
                m.put("phoneName", c.getString(diseaseColIndex)); // название телефона
                m.put("tagName", tagNames[4]); // имя компании
                childDataItem.add(m);

                childData.add(childDataItem);
                // получаем значения по номерам столбцов и пишем все в лог
                Log.d("sdf",
                        "ID = " + c.getInt(idColIndex) + ", name = "
                                + c.getString(nameColIndex) + ", passport = "
                                + c.getString(passportColIndex) + ", address = "
                                + c.getString(addressColIndex) + ", disease = "
                                + c.getString(diseaseColIndex));
            } while (c.moveToNext());
        } else
            Log.d("LOG", "0 rows");
        c.close();
        // список атрибутов элементов для чтения
        String childFrom[] = new String[] {"phoneName", "tagName"};
        // список ID view-элементов, в которые будет помещены атрибуты элементов
        int childTo[] = new int[] {R.id.list_item_child, R.id.list_item_child_tag};

        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
                this,
                groupData,
                R.layout.list_item_group,
                groupFrom,
                groupTo,
                childData,
                R.layout.list_item_child,
                childFrom,
                childTo);
        elvMain = (ExpandableListView) findViewById(R.id.elvMain);
        elvMain.setAdapter(adapter);*/
    }



    /*public void fillListView(ArrayList<Patient> patients, String[] tagNames){
        String groupFrom[] = new String[] {"groupName"};
        int groupTo[] = new int[] {R.id.list_item_group};
        String childFrom[] = new String[] {"data", "tagName"};
        int childTo[] = new int[] {R.id.list_item_child, R.id.list_item_child_tag};

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

        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
                this,
                groupData,
                R.layout.list_item_group,
                groupFrom,
                groupTo,
                childData,
                R.layout.list_item_child,
                childFrom,
                childTo);
        elvMain = (ExpandableListView) findViewById(R.id.elvMain);
        elvMain.setAdapter(adapter);
    }*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (tableId){
            case 0:
                intent = new Intent(ListActivity.this, RenderDataActivity.class);
                startActivityForResult(intent, 1);
                break;
            case 1:
                intent = new Intent(ListActivity.this, DoctorDataActivity.class);
                startActivityForResult(intent, 1);
                break;
            case 2:
                intent = new Intent(ListActivity.this, PatientDataActivity.class);
                startActivityForResult(intent, 1);
                break;
            case 3:
                intent = new Intent(ListActivity.this, ServiceDataActivity.class);
                startActivityForResult(intent, 1);
                break;
            case 4:
                intent = new Intent(ListActivity.this, VisitsDataActivity.class);
                startActivityForResult(intent, 1);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showResult(String result){
        Toasty.success(getApplicationContext(), result, Toast.LENGTH_SHORT, true).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (intent == null) {return;}
        ListPresenter listPresenter = new ListPresenter(this);
        switch (requestCode){
            case 1:
                ArrayList<String> data = new ArrayList<String>();
                switch (tableId){
                    case 0:
                        Log.d("123", intent.getStringExtra("service") + " " + intent.getStringExtra("patient") + " " + intent.getStringExtra("doctor") + " " + intent.getFloatExtra("sum", -1) + " " + intent.getStringExtra("sum"));
                        data.add(intent.getStringExtra("service"));
                        data.add(intent.getStringExtra("patient"));
                        data.add(intent.getStringExtra("doctor"));
                        data.add(String.valueOf(intent.getFloatExtra("sum", 0)));
                        data.add(intent.getStringExtra("date"));
                        listPresenter.prepareDataForAddition(data, tableId);
                        break;
                    case 1:
                        Log.d("123", intent.getStringExtra("name") + " " + intent.getStringExtra("passport") + intent.getStringExtra("address") + " " + intent.getStringExtra("specialization") + " " + intent.getStringExtra("experience") + " " + intent.getStringExtra("berth"));
                        data.add(intent.getStringExtra("name"));
                        data.add(intent.getStringExtra("passport"));
                        data.add(intent.getStringExtra("address"));
                        data.add(intent.getStringExtra("specialization"));
                        data.add(String.valueOf(intent.getIntExtra("experience", 0)));
                        data.add(intent.getStringExtra("berth"));
                        listPresenter.prepareDataForAddition(data, tableId);
                        break;
                    case 2:
                        Log.d("123", intent.getStringExtra("name") + " " + intent.getStringExtra("passport") + " " + intent.getStringExtra("address") + " " + intent.getStringExtra("disease"));
                        data.add(intent.getStringExtra("name"));
                        data.add(intent.getStringExtra("passport"));
                        data.add(intent.getStringExtra("address"));
                        data.add(intent.getStringExtra("disease"));
                        listPresenter.prepareDataForAddition(data, tableId);
                        break;
                    case 3:
                        Log.d("OLD", intent.getStringExtra("patient") + " " + intent.getStringExtra("doctor") + " " + intent.getStringExtra("manipulation") + " " + intent.getStringExtra("cost") + " " + intent.getStringExtra("date"));
                        data.add(intent.getStringExtra("manipulation"));
                        data.add(intent.getStringExtra("patient"));
                        data.add(intent.getStringExtra("doctor"));
                        data.add(String.valueOf(intent.getFloatExtra("cost", 0)));
                        data.add(intent.getStringExtra("date"));
                        listPresenter.prepareDataForAddition(data, tableId);//убрать в конец приминение функции
                        break;
                    case 4:
                        Log.d("123", intent.getStringExtra("patient") + " " + intent.getStringExtra("service") + " " + intent.getStringExtra("date"));
                        data.add(intent.getStringExtra("patient"));
                        data.add(intent.getStringExtra("date"));
                        data.add(intent.getStringExtra("service"));
                        listPresenter.prepareDataForAddition(data, tableId);
                }

                break;
            case 2:
                ArrayList<String> oldData = new ArrayList<String>();
                ArrayList<String> newData = new ArrayList<String>();
                switch (tableId){
                    case 0:
                        Log.d("UPDATE", intent.getStringExtra("oldService") + " " + intent.getStringExtra("oldPatient") + " " + intent.getStringExtra("oldDoctor") + " " + intent.getFloatExtra("oldSum", -1) + " " + intent.getStringExtra("oldDate"));
                        oldData.add(intent.getStringExtra("oldService"));
                        oldData.add(intent.getStringExtra("oldPatient"));
                        oldData.add(intent.getStringExtra("oldDoctor"));
                        oldData.add(String.valueOf(intent.getFloatExtra("oldSum", -1)));
                        oldData.add(intent.getStringExtra("oldDate"));
                        Log.d("UPDATE", intent.getStringExtra("service") + " " + intent.getStringExtra("patient") + " " + intent.getStringExtra("doctor") + " " + intent.getFloatExtra("sum", -1) + " " + intent.getStringExtra("date"));
                        newData.add(intent.getStringExtra("service"));
                        newData.add(intent.getStringExtra("patient"));
                        newData.add(intent.getStringExtra("doctor"));
                        newData.add(String.valueOf(intent.getFloatExtra("sum", -1)));
                        newData.add(intent.getStringExtra("date"));
                        listPresenter.prepareDataForUpdate(oldData, newData, tableId);
                        break;
                    case 1:
                        Log.d("saddasdasd", intent.getStringExtra("oldName"));
                        oldData.add(String.valueOf(intent.getIntExtra("code", -1)));
                        oldData.add(intent.getStringExtra("oldName"));
                        oldData.add(intent.getStringExtra("oldPassport"));
                        oldData.add(intent.getStringExtra("oldAddress"));
                        oldData.add(intent.getStringExtra("oldSpecialization"));
                        oldData.add(String.valueOf(intent.getIntExtra("oldExperience", -1)));
                        oldData.add(intent.getStringExtra("oldBerth"));

                        Log.d("saddasdasd", intent.getStringExtra("name"));
                        newData.add(String.valueOf(intent.getIntExtra("code", -1)));
                        newData.add(intent.getStringExtra("name"));
                        newData.add(intent.getStringExtra("passport"));
                        newData.add(intent.getStringExtra("address"));
                        newData.add(intent.getStringExtra("specialization"));
                        newData.add(String.valueOf(intent.getIntExtra("experience", -1)));
                        newData.add(intent.getStringExtra("berth"));
                        listPresenter.prepareDataForUpdate(oldData, newData, tableId);
                        break;
                    case 2:
                        Log.d("saddasdasd", intent.getStringExtra("oldName"));
                        oldData.add(String.valueOf(intent.getIntExtra("code", -1)));
                        oldData.add(intent.getStringExtra("oldName"));
                        oldData.add(intent.getStringExtra("oldPassport"));
                        oldData.add(intent.getStringExtra("oldAddress"));
                        oldData.add(intent.getStringExtra("oldDisease"));

                        Log.d("saddasdasd", intent.getStringExtra("name"));
                        newData.add(String.valueOf(intent.getIntExtra("code", -1)));
                        newData.add(intent.getStringExtra("name"));
                        newData.add(intent.getStringExtra("passport"));
                        newData.add(intent.getStringExtra("address"));
                        newData.add(intent.getStringExtra("disease"));
                        listPresenter.prepareDataForUpdate(oldData, newData, tableId);
                        break;
                    case 3:
                        Log.d("saddasdasd", intent.getStringExtra("oldManipulation"));
                        oldData.add(intent.getStringExtra("oldManipulation"));
                        oldData.add(intent.getStringExtra("oldPatient"));
                        oldData.add(intent.getStringExtra("oldDoctor"));
                        oldData.add(String.valueOf(intent.getFloatExtra("oldCost", -1)));
                        oldData.add(intent.getStringExtra("oldDate"));

                        Log.d("saddasdasd", intent.getStringExtra("manipulation"));
                        newData.add(intent.getStringExtra("manipulation"));
                        newData.add(intent.getStringExtra("patient"));
                        newData.add(intent.getStringExtra("doctor"));
                        newData.add(String.valueOf(intent.getFloatExtra("cost", -1)));
                        newData.add(intent.getStringExtra("date"));
                        listPresenter.prepareDataForUpdate(oldData, newData, tableId);
                        break;
                    case 4:
                        Log.d("saddasdasd", intent.getStringExtra("oldPatient"));
                        oldData.add(intent.getStringExtra("oldPatient"));
                        oldData.add(intent.getStringExtra("oldDate"));
                        oldData.add(intent.getStringExtra("oldService"));

                        Log.d("saddasdasd", intent.getStringExtra("patient"));
                        newData.add(intent.getStringExtra("patient"));
                        newData.add(intent.getStringExtra("date"));
                        newData.add(intent.getStringExtra("service"));
                        listPresenter.prepareDataForUpdate(oldData, newData, tableId);
                        break;
                }

        }
    }



}
