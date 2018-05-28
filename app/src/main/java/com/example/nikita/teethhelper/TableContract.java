package com.example.nikita.teethhelper;

/**
 * Created by Nikita on 28.05.2018.
 */

public interface TableContract {
    interface TableView {
        public void showError(String result);
    }

    interface TablePresenter {
        public void checkData();
        public void sendError(String result);
    }
}
