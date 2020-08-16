package com.demo.productcatalogue.SharedPreferences;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

public class SharedPreferenceUtil {

    private SharedPreferenceUtil() {}

    private static SharedPreferenceUtil preferences = new SharedPreferenceUtil();
    private static SharedPreferences sharedPreferences;
    public static SharedPreferenceUtil getInstance(Context context){
        if(sharedPreferences==null){
            sharedPreferences = new ContextWrapper(context).getSharedPreferences("DB_Related",Context.MODE_PRIVATE);
        }
        return preferences;
    }

    public boolean isDBInitilized() {
        return sharedPreferences.getBoolean("IS_DB_INITIALIZED",false);
    }

    public void setDBInitilized(boolean DBInitilized) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("IS_DB_INITIALIZED", DBInitilized);
        editor.apply();
    }
}
