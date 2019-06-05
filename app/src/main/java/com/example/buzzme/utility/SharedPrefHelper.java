package com.example.buzzme.utility;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefHelper {

    private static final String PREF_FILE_NAME = "BuzzMePrefs";
    private static final String PREF_MEMBER_ID = "key_memberId";
    private static final String PREF_KEY_USER_NAME = "key_user_name";
    private static final String PREF_KEY_PASSWORD = "key_password";

    private static SharedPreferences initSharedPref(Context context) {
        return context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    //save data to shared preference file
    public static void saveDataSharedPreference(Context context, String username, String password, int memberId) {
        SharedPreferences.Editor editor = initSharedPref(context).edit();
        editor.putString(PREF_KEY_USER_NAME, username);
        editor.putString(PREF_KEY_PASSWORD, password);
        editor.putInt(PREF_MEMBER_ID, memberId);
        editor.apply();
    }

    //remove all data from shared preference file
    public static void deleteDataSharedPref(Context context) {
        SharedPreferences.Editor editor = initSharedPref(context).edit();
        editor.clear();
        editor.apply();
    }

    //returns member id from shared pref file
    public static int getPrefMemberId(Context context) {
        return initSharedPref(context).getInt(PREF_MEMBER_ID, -1);
    }

    //returns member username from shared pref file
    public static String getPrefUserName(Context context) {
        return initSharedPref(context).getString(PREF_KEY_USER_NAME, null);
    }

    //returns member password from shared pref file
    public static String getPrefPassword(Context context) {
        return initSharedPref(context).getString(PREF_KEY_PASSWORD, null);
    }

}
