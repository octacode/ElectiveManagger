package com.nith.electiveManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL = "http://192.168.43.34:4000/api/";
    private static Retrofit retrofit = null;
    private static Context mContext;

    public static Retrofit getClient(Context context) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        mContext = context;
        return retrofit;
    }

    public static boolean getLogin() {
        return PreferenceManager.getDefaultSharedPreferences(mContext).getBoolean("login", false);
    }

    public static void setLogin(boolean value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("login", value);
        editor.apply();
    }

    public static boolean getSubmit() {
        return PreferenceManager.getDefaultSharedPreferences(mContext).getBoolean("submit", false);
    }

    public static void setSubmit(boolean value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("submit", value);
        editor.apply();
    }

    public static boolean isFirstLaunch() {
        return PreferenceManager.getDefaultSharedPreferences(mContext).getBoolean("firstLaunch", true);
    }

    public static void setFirstLaunch(boolean value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("firstLaunch", value);
        editor.apply();
    }
}