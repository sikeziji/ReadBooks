package com.example.bookuilib;

import android.app.Application;
import android.content.SharedPreferences;

public class MApplication extends Application {

    private static MApplication instance;

    private SharedPreferences configPreferences;

    public static MApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        configPreferences = getSharedPreferences("CONFIG", 0);
    }

    public static SharedPreferences getConfigPreferences() {
        return getInstance().configPreferences;
    }

    public boolean isNightTheme() {
        return false;
    }
}
