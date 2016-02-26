package com.example.mpetk.taskme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.Thread;

public class KreiranjeZadatak  extends AppCompatActivity implements View.OnClickListener {

    int[] idtxt = new int[] { R.id.editxt_taskname, R.id.spinner_type_task,R.id.spinner_klijent, R.id.spinner_employe,
           R.id.datePicker,   R.id.editxt_taskDESC};
    public ArrayList<String> arrayEmployee;
    public ArrayList<String> arrayClient;
    public ArrayList<String> arrayTaskType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kreiranje_zad);

        arrayEmployee =new ArrayList<>();arrayEmployee.add("Select user");
        arrayClient =new ArrayList<>();arrayClient.add("Select client");
        arrayTaskType =new ArrayList<>();arrayTaskType.add("Select type");

        makeArrayEmployee();
        makeArrayClient();
        makeArrayTaskType();


        Button dodaj = (Button) findViewById(R.id.button_create);
        dodaj.setOnClickListener(this);
//resdfsdfdghjkadfhgjkhdf


    }
    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("iz onResume: " + Arrays.asList(arrayEmployee));

        ArrayAdapter<String> adapterEmployee=new ArrayAdapter<String>(this,R.layout.spinner_zadatak_employee,arrayEmployee);
        Spinner spinnerEmployee = (Spinner)findViewById(R.id.spinner_employe);
        spinnerEmployee.setAdapter(adapterEmployee);
        adapterEmployee.notifyDataSetChanged();

        ArrayAdapter<String> adapterClient=new ArrayAdapter<String>(this,R.layout.spinner_zadatak_employee,arrayClient);
        Spinner spinnerClient = (Spinner)findViewById(R.id.spinner_klijent);
        spinnerClient.setAdapter(adapterClient);
        adapterClient.notifyDataSetChanged();

        ArrayAdapter<String> adapterTaskType=new ArrayAdapter<String>(this,R.layout.spinner_zadatak_employee,arrayTaskType);
        Spinner spinnerTaskType = (Spinner)findViewById(R.id.spinner_type_task);
        spinnerTaskType.setAdapter(adapterTaskType);
        adapterTaskType.notifyDataSetChanged();





     //   int spinnerPosition = adapterEmployee.getPosition("Mars");
       // spinnerEmployee.setSelection(spinnerPosition);


    }
    @Override
    public void onClick(View v) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Config.LOGIN_WAMP_URL+"taskmeBazaPisanje.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(KreiranjeZadatak.this, response, Toast.LENGTH_LONG).show();
                        Intent intentPretraga = new Intent(getApplicationContext(), PretragaZadataka.class);
                        startActivity(intentPretraga);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("error: " + error);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
             //   nadiId();
                Map < String, String > params = new HashMap<>();
                params.put("imeTablice", "radnizadatak");
                params.put("NAZIV_ZADATKA", ((EditText) findViewById(idtxt[0])).getText().toString());
                params.put("VRSTAZADATKA", ((Spinner)findViewById(idtxt[1])).getSelectedItem().toString());
                params.put("KLIJENT_ID", ((Spinner) findViewById(idtxt[2])).getSelectedItem().toString());
                if(((Spinner) findViewById(idtxt[3])).getSelectedItem().toString()!="unasigned")
                    params.put("KORISNIK_ID",((Spinner) findViewById(idtxt[3])).getSelectedItem().toString());

                params.put("KRAJNJIDATUMIZVRSENJA", ((DatePicker) findViewById(idtxt[4])).getYear() + "-" + ((DatePicker) findViewById(idtxt[4])).getMonth()+1+"-"+((DatePicker) findViewById(idtxt[4])).getDayOfMonth());
                params.put("OPIS", ((EditText) findViewById(idtxt[5])).getText().toString());
                if(((Spinner) findViewById(idtxt[3])).getSelectedItem().toString()=="unasigned")
                    params.put("STATUSDODJELJENOSTI", "0");
                else
                    params.put("STATUSDODJELJENOSTI", "1");
                params.put("STATUSIZVRSENOSTI", "0");
                params.put("VIDLJIVO", "1");


                return params;
            }
        };
        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
//fdčlskglsdf
    public void makeArrayEmployee(){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                "http://whackamile.byethost3.com/taskme/taskmeBazaCitanjeKorisnika.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        List<String> useri = Arrays.asList(response.split(","));
                        System.out.print("useri "+useri);
                        arrayEmployee.clear();
                        arrayEmployee.add("unasigned");
                        arrayEmployee.addAll(useri);

                        System.out.print("iz respons e" + Arrays.asList(arrayEmployee));
                }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.print("error: " + error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return null;
            }

        };
        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void makeArrayClient(){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                "http://whackamile.byethost3.com/taskme/taskmeKijentCitanje.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        List<String> useri = Arrays.asList(response.split(","));
                        System.out.print("useri "+useri);
                        arrayClient.clear();
                        arrayClient.addAll(useri);

                        System.out.print("klijenti iz responsa"+Arrays.asList(arrayClient));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.print("error: "+error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return null;
            }

        };
        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void makeArrayTaskType(){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                "http://whackamile.byethost3.com/taskme/taskmeTipZadatkaCitanje.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        List<String> useri = Arrays.asList(response.split(","));
                        System.out.print("useri "+useri);
                        arrayTaskType.clear();
                        arrayTaskType.addAll(useri);

                        System.out.print("klijenti iz responsa"+Arrays.asList(arrayTaskType));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.print("error: "+error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return null;
            }

        };
        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}

