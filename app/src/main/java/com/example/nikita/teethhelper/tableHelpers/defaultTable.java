package com.example.nikita.teethhelper.tableHelpers;

import com.example.nikita.teethhelper.data.defaultObject;

/**
 * Created by Nikita on 09.04.2018.
 */

public interface defaultTable {
    public void fetchData();
    public void addRow(defaultObject defaultObject);
    public boolean deleteRow(defaultObject defaultObject);
    public boolean updateRow(defaultObject oldDefaultObject, defaultObject newDefaultObject);
}
