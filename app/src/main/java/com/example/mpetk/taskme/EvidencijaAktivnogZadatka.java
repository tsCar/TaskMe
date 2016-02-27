package com.example.mpetk.taskme;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mpetk on 15.2.2016..
 */
public class EvidencijaAktivnogZadatka  extends AppCompatActivity  implements View.OnClickListener{

    ArrayAdapter<String> adapterLog;
    String staraEvidencija;
    volatile ArrayList<String> podaci;
    Button dodajEvidenciju;

    public String stariZ;
 //   int[] idtxt1 = new int[] { R.id.evidencijaEdit};
    //komentar
    //PrikazZadatka pz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evidentiraj_bavljeni_posao);
        podaci  =new ArrayList<>();


        stariZ = getIntent().getStringExtra(MyTasks.id_extra);
        dohvatiStaruEvidenciju();
/*        pz=new PrikazZadatka();
        pz.stariZad =stariZ;
        pz.defaultValues();*/
        dodajEvidenciju = (Button) findViewById(R.id.button_evidentiraj);
        dodajEvidenciju.setOnClickListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        dodajEvidenciju.setAlpha(.5f);
        dodajEvidenciju.setClickable(false);
        ((EditText) findViewById(R.id.evidencijaEdit)).setText("please wait...");
        ((EditText) findViewById(R.id.evidencijaEdit)).setEnabled(false);

    }


    void dohvatiStaruEvidenciju() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Config.LOGIN_WAMP_URL+"taskmeZadatakPodaci.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println("response ");System.out.println(response);
                        List<String> taskovi = Arrays.asList(response.split("\\|t", -1));
                        podaci.clear();
                        podaci.addAll(taskovi);
                        System.out.println(Arrays.asList(podaci));
                        staraEvidencija=podaci.get(7).toString();
                        ((EditText) findViewById(R.id.evidencijaEdit)).setText(staraEvidencija);
                        ((EditText) findViewById(R.id.evidencijaEdit)).setEnabled(true);
                        dodajEvidenciju.setAlpha(1);
                        dodajEvidenciju.setClickable(true);
                        ((EditText) findViewById(R.id.evidencijaEdit)).setSelection(((EditText) findViewById(R.id.evidencijaEdit)).getText().length());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("error: "+error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("NAZIV_ZADATKA", stariZ.trim());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    @Override
    public void onClick(View v) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Config.LOGIN_WAMP_URL+"taskmeDodajEvidenciju.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(EvidencijaAktivnogZadatka.this, response, Toast.LENGTH_LONG).show();
                        Intent intentNatrag = new Intent(getApplicationContext(), MyTasks.class);
                        startActivity(intentNatrag);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("error: " + error);
                    }
                })

        { //
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //   nadiId();
                Map < String, String > params = new HashMap<>();
                System.out.print("Stari: "+stariZ);
                System.out.print("evidencija: "+((EditText)findViewById(R.id.evidencijaEdit)).getText().toString());
                params.put("NAZIV_ZADATKA", stariZ.trim());
                params.put("EVIDENCIJA", (staraEvidencija + "\n" + ((EditText) findViewById(R.id.evidencijaEdit)).getText()).toString().trim());

                return params;
            }
        };
        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

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
                        Intent intent = new Intent(getApplicationContext(), Login.class);
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

            Intent intent = new Intent(getApplicationContext(), HomeZaposlenik.class);
            startActivity(intent);

        }else if(id == R.id.action_profil){

            Intent intent = new Intent(getApplicationContext(), MojProfil.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}