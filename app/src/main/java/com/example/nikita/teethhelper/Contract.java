package com.example.nikita.teethhelper;

/**
 * Created by Nikita on 28.05.2018.
 */

public interface Contract {
    interface View {
        public void showMessage(String result);
    }

    interface Presenter {
        public void onStart();
        public void onStop();
    }
}
