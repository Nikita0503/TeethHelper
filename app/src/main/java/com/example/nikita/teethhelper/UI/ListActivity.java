package com.example.nikita.teethhelper.UI;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;

import com.example.nikita.teethhelper.presenters.ListPresenter;
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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        tableId = intent.getIntExtra("tableId", -1);
        registerForContextMenu(elvMain);
    }

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
        ArrayList<String> objectData = new ArrayList<String>();
        for(int i = 0; i < elvMain.getExpandableListAdapter().getChildrenCount(position); i++) {
            Map<String, String> m = (Map<String, String>) elvMain.getExpandableListAdapter().getChild(position, i);
            objectData.add(m.get("data"));
        }
        ListPresenter listPresenter = new ListPresenter(ListActivity.this);
        switch (item.getItemId()) {
            case R.id.edit:
                listPresenter.prepareDataForUpdate(objectData, tableId);
                return true;
            case R.id.delete:
                listPresenter.prepareDataForDelete(objectData, tableId);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
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

    @Override
    public void onResume(){
        super.onResume();
        ListPresenter presenter = new ListPresenter(this);
        presenter.fetchData(tableId);
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
                        data.add(intent.getStringExtra("service"));
                        data.add(intent.getStringExtra("patient"));
                        data.add(intent.getStringExtra("doctor"));
                        data.add(String.valueOf(intent.getFloatExtra("sum", 0)));
                        data.add(intent.getStringExtra("date"));
                        listPresenter.prepareDataForAddition(data, tableId);
                        break;
                    case 1:
                        data.add(intent.getStringExtra("name"));
                        data.add(intent.getStringExtra("passport"));
                        data.add(intent.getStringExtra("address"));
                        data.add(intent.getStringExtra("specialization"));
                        data.add(String.valueOf(intent.getIntExtra("experience", 0)));
                        data.add(intent.getStringExtra("berth"));
                        listPresenter.prepareDataForAddition(data, tableId);
                        break;
                    case 2:
                        data.add(intent.getStringExtra("name"));
                        data.add(intent.getStringExtra("passport"));
                        data.add(intent.getStringExtra("address"));
                        data.add(intent.getStringExtra("disease"));
                        listPresenter.prepareDataForAddition(data, tableId);
                        break;
                    case 3:
                        data.add(intent.getStringExtra("manipulation"));
                        data.add(intent.getStringExtra("patient"));
                        data.add(intent.getStringExtra("doctor"));
                        data.add(String.valueOf(intent.getFloatExtra("cost", 0)));
                        data.add(intent.getStringExtra("date"));
                        listPresenter.prepareDataForAddition(data, tableId);//убрать в конец приминение функции
                        break;
                    case 4:
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
                        oldData.add(intent.getStringExtra("oldService"));
                        oldData.add(intent.getStringExtra("oldPatient"));
                        oldData.add(intent.getStringExtra("oldDoctor"));
                        oldData.add(String.valueOf(intent.getFloatExtra("oldSum", -1)));
                        oldData.add(intent.getStringExtra("oldDate"));
                        newData.add(intent.getStringExtra("service"));
                        newData.add(intent.getStringExtra("patient"));
                        newData.add(intent.getStringExtra("doctor"));
                        newData.add(String.valueOf(intent.getFloatExtra("sum", -1)));
                        newData.add(intent.getStringExtra("date"));
                        listPresenter.prepareDataForUpdate(oldData, newData, tableId);
                        break;
                    case 1:
                        oldData.add(String.valueOf(intent.getIntExtra("code", -1)));
                        oldData.add(intent.getStringExtra("oldName"));
                        oldData.add(intent.getStringExtra("oldPassport"));
                        oldData.add(intent.getStringExtra("oldAddress"));
                        oldData.add(intent.getStringExtra("oldSpecialization"));
                        oldData.add(String.valueOf(intent.getIntExtra("oldExperience", -1)));
                        oldData.add(intent.getStringExtra("oldBerth"));
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
                        oldData.add(String.valueOf(intent.getIntExtra("code", -1)));
                        oldData.add(intent.getStringExtra("oldName"));
                        oldData.add(intent.getStringExtra("oldPassport"));
                        oldData.add(intent.getStringExtra("oldAddress"));
                        oldData.add(intent.getStringExtra("oldDisease"));
                        newData.add(String.valueOf(intent.getIntExtra("code", -1)));
                        newData.add(intent.getStringExtra("name"));
                        newData.add(intent.getStringExtra("passport"));
                        newData.add(intent.getStringExtra("address"));
                        newData.add(intent.getStringExtra("disease"));
                        listPresenter.prepareDataForUpdate(oldData, newData, tableId);
                        break;
                    case 3:
                        oldData.add(intent.getStringExtra("oldManipulation"));
                        oldData.add(intent.getStringExtra("oldPatient"));
                        oldData.add(intent.getStringExtra("oldDoctor"));
                        oldData.add(String.valueOf(intent.getFloatExtra("oldCost", -1)));
                        oldData.add(intent.getStringExtra("oldDate"));
                        newData.add(intent.getStringExtra("manipulation"));
                        newData.add(intent.getStringExtra("patient"));
                        newData.add(intent.getStringExtra("doctor"));
                        newData.add(String.valueOf(intent.getFloatExtra("cost", -1)));
                        newData.add(intent.getStringExtra("date"));
                        listPresenter.prepareDataForUpdate(oldData, newData, tableId);
                        break;
                    case 4:
                        oldData.add(intent.getStringExtra("oldPatient"));
                        oldData.add(intent.getStringExtra("oldDate"));
                        oldData.add(intent.getStringExtra("oldService"));
                        newData.add(intent.getStringExtra("patient"));
                        newData.add(intent.getStringExtra("date"));
                        newData.add(intent.getStringExtra("service"));
                        listPresenter.prepareDataForUpdate(oldData, newData, tableId);
                        break;
                }

        }
    }

    public void setListAdapter(SimpleExpandableListAdapter adapter){
        elvMain.setAdapter(adapter);
    }

    public void setColors(int titleBarColor, int statusBarColor, String tableName){
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(statusBarColor));
        getSupportActionBar().setTitle(tableName);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.setStatusBarColor(titleBarColor);
        }
    }

    public void showMessage(String message){
        Toasty.success(getApplicationContext(), message, Toast.LENGTH_SHORT, true).show();
    }
}
