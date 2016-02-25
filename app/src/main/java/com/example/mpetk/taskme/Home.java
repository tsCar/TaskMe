package com.example.mpetk.taskme;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Home extends AppCompatActivity {



    //Textview to show currently logged in user
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button user1 = (Button) findViewById(R.id.button_users);
        Button task = (Button) findViewById(R.id.button_tasks);
        Button report = (Button) findViewById(R.id.button_reports);
        Button client = (Button) findViewById(R.id.button_clients);

        user1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PretragaKorisnika.class);
                startActivity(intent);

            }
        });
        task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), PretragaZadataka.class);
                startActivity(intent2);
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(getApplicationContext(), PretragaIzvjestaja.class);
                startActivity(intent3);
            }
        });
        client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(getApplicationContext(), PretragaKlijenata.class);
                startActivity(intent4);
            }
        });


        //Initializing textview
                textView = (TextView) findViewById(R.id.textView);

                //Fetching email from shared preferences
                SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                String us = sharedPreferences.getString(Config.IME_SHARED_PREF, "Not Available");

                //Showing the current logged in email to textview
                textView.setText(us);
            }






            //Logout function
            private void logout() {
                //Creating an alert dialog to confirm logout
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage("Are you sure you want to logout?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                //Getting out sharedpreferences
                                SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                                //Getting editor
                                SharedPreferences.Editor editor = preferences.edit();

                                //Puting the value false for loggedin
                                editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);

                                //Putting blank value to email
                                editor.putString(Config.IME_SHARED_PREF, "");

                                //Saving the sharedpreferences
                                editor.commit();

                                //Starting login activity
                                Intent intent = new Intent(Home.this, Login.class);
                                startActivity(intent);
                            }
                        });

                alertDialogBuilder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        });

                //Showing the alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }

            @Override
            public boolean onCreateOptionsMenu(Menu menu) {
                //Adding our menu to toolbar
                getMenuInflater().inflate(R.menu.menu, menu);
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.action_bar_mojprofil, menu);
                return true;
            }

            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.menuLogout) {
                    //calling logout method when the logout button is clicked
                    logout();
                }else if (id == R.id.menuHome){

                    Intent intent = new Intent(getApplicationContext(), Home.class);
                    startActivity(intent);

                }else if(id == R.id.action_profil){

                    Intent intent = new Intent(getApplicationContext(), PretragaKorisnika.class); //TODO moramo staviti da odvede na modifikaciju trenutno ulogiranog korisnika
                    startActivity(intent);
                }

                return super.onOptionsItemSelected(item);
            }





        }


/*

//MOJ PROFL  U ACTION BARU

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_mojprofil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_profil:
                // ovdje staviti da otvara prikaz korisnika


                break;
            default:
                break;
        }

        return true;
    }
 */