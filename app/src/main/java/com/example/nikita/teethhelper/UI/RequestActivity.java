package com.example.nikita.teethhelper.UI;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nikita.teethhelper.R;
import com.example.nikita.teethhelper.presenters.RequestActivityPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class RequestActivity extends AppCompatActivity {
    private int mTableId;
    private Fragment mFragment;
    @BindView(R.id.spinnerTypeOfRequest)
    Spinner mSpinnerTypeOfRequest;
    @BindView(R.id.spinnerTable)
    Spinner mSpinnerTable;
    @BindView(R.id.buttonCommit)
    Button mButtonCommit;
    @OnClick(R.id.buttonCommit)
    void onClickCommit(){
        Fragment fragment = getFragmentManager().findFragmentById(R.id.fragmentSlot);
        String tableName = "";
        tableName = mSpinnerTable.getSelectedItem().toString();
        String typeOfRequest = "";
        typeOfRequest = mSpinnerTypeOfRequest.getSelectedItem().toString();
        RequestActivityPresenter requestActivityPresenter = new RequestActivityPresenter(this);
        String request = requestActivityPresenter.getRequestText(tableName, typeOfRequest, fragment);
        Log.d("REQUEST", request);
        Toasty.info(getApplicationContext(), request, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        ButterKnife.bind(this);
        final RequestActivityPresenter requestActivityPresenter = new RequestActivityPresenter(this);
        requestActivityPresenter.fetchData();
        mSpinnerTable.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mTableId = position;
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();;
                mFragment = requestActivityPresenter.getFragmentByTableId(mTableId);
                fragmentTransaction.replace(R.id.fragmentSlot, mFragment);
                fragmentTransaction.commit();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    private void setTypeOfRequestAdapter(String[] typesOfRequest){
        ArrayAdapter<String> spinnerTypeOfRequestAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, typesOfRequest);
        spinnerTypeOfRequestAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerTypeOfRequest.setAdapter(spinnerTypeOfRequestAdapter);
    }

    private void setTableAdapter(String[] tableNames){
        ArrayAdapter<String> spinnerTableAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, tableNames);
        spinnerTableAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerTable.setAdapter(spinnerTableAdapter);
    }

    public void setAdapters(String[] typesOfRequest, String[] tableNames){
        setTypeOfRequestAdapter(typesOfRequest);
        setTableAdapter(tableNames);
    }
}
