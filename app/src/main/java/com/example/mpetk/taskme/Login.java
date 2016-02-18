package com.example.mpetk.taskme;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.support.v7.widget.AppCompatButton;
//import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.NoCache;
import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.toolbox.Volley.*;

public class Login extends AppCompatActivity implements View.OnClickListener {

    //Defining views
    private EditText editTextUser;
    private EditText editTextPassword;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Initializing views
        editTextUser = (EditText) findViewById(R.id.editxt_username);
        editTextPassword = (EditText) findViewById(R.id.editxt_password);

        Button buttonLogin = (Button) findViewById(R.id.button_login);

        //Adding click listener
        buttonLogin.setOnClickListener(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        boolean loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);

        //If we will get true
        if (loggedIn) {
            //We will start the Profile Activity
            Intent intent = new Intent(Login.this, Home.class);
            startActivity(intent);
        }
    }

    private void login() {
        //Getting values from edit texts
        final String username = editTextUser.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.42.138/taskmeBazaCitanjeUsername.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                  //If we are getting success from server

                        if (response.trim().equalsIgnoreCase("1")) {
                            //Creating a shared preference
                            SharedPreferences sharedPreferences = Login.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                            //Creating editor to store values to shared preferences
                            SharedPreferences.Editor editor;
                            editor = sharedPreferences.edit();

                            //Adding values to editor
                            editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                            editor.putString(Config.IME_SHARED_PREF, username);

                            //Saving values to editor
                            editor.apply();

                            //Starting profile activity
                            Intent intent = new Intent(Login.this, Home.class);
                            startActivity(intent);

                        } else {
                            //If the server response is not success
                            //Displaying an error message on toast
                            Toast.makeText(Login.this, "Invalid username or password", Toast.LENGTH_LONG).show();
                        }
                        System.out.println("response");
                        System.out.println(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
               params.put(Config.KEY_KOR_IME, username);
               params.put(Config.KEY_LOZINKA, password);

                //returning parameter
                System.out.println("params:");
                System.out.println(params);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // Removed this line if you dont need it or Use application/json
                // params.put("Content-Type", "application/x-www-form-urlencoded");

                return new HashMap<String, String>();
            }
        };

        //Adding the string request to the queue

        RequestQueue requestQueue = Volley.newRequestQueue(this);
      //  RequestQueue requestQueue = new RequestQueue(new NoCache(), new BasicNetwork(new HurlStack()));
        System.out.println("request " + stringRequest);
        requestQueue.add(stringRequest);



    }

    @Override
    public void onClick(View v) {
        //Calling the login function
        login();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }
}