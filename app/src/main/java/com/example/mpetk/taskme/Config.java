package com.example.mpetk.taskme;


public class Config {
    //URL to our login.php file
    public static final String LOGIN_URL = "http://whackamile.byethost3.com/taskme/";
    public static final String LOGIN_WAMP_URL = "http://192.168.42.138/";

    //Keys for email and password as defined in our $_POST['key'] in login.php
    public static final String KEY_KOR_IME = "KORISNICKO_IME";
    public static final String KEY_LOZINKA = "LOZINKA";

    //If server response is equal to this that means login is successful
    public static final String LOGIN_SUCCESS = "1";

    //Keys for Sharedpreferences
    //This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "myloginapp";

    //This would be used to store the email of current logged in user
    public static final String IME_SHARED_PREF = "username";

    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";
}