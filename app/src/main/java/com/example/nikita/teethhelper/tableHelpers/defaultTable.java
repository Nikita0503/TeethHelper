package com.example.nikita.teethhelper.tableHelpers;

import com.example.nikita.teethhelper.data.defaultObject;

import io.reactivex.Observer;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by Nikita on 09.04.2018.
 */

public interface defaultTable {
    public void fetchData();
    public DisposableObserver<defaultObject> addRow();
    public DisposableObserver<defaultObject> deleteRow();
    public DisposableObserver<defaultObject[]> updateRow();
}
