package com.waakan.kishnagold;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "myspf";
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_UNAME = "uname";
    public static final String KEY_PASS= "pass";

    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

  //loggin session creation
    public void createLoginSession(String uname, String pass){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        // Storing name in pref
        editor.putString(KEY_UNAME, uname);
        // Storing email in pref
        editor.putString(KEY_PASS, pass);
        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent intent = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(intent);
        }

    }
//get stores session data
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_UNAME, pref.getString(KEY_UNAME, null));
        // user email id
        user.put(KEY_PASS, pref.getString(KEY_PASS, null));

        // return user
        return user;
    }
//logout(clearing data from Shared Preferences)
    public  void logoutUser(){
      //  Toast.makeText(_context, "log out called", Toast.LENGTH_SHORT).show();

        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);
    }


    // Get Login State quickly
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}