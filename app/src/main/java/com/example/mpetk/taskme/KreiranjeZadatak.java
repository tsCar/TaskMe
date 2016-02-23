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
           R.id.datePicker,   R.id.editxt_taskDESC,R.id.spinner_asigned_task};
    public ArrayList<String> arrayEmployee;
    public String[] stringArrayEmployee;
    String ready="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrayEmployee =new ArrayList<>();//arrayEmployee.add("jebemli");arrayEmployee.add("te");
        setContentView(R.layout.activity_kreiranje_zad);

        Button dodaj = (Button) findViewById(R.id.button_create);
        dodaj.setOnClickListener(this);
        arrayEmployee=makeArrayEmployee();
        System.out.print("o createv "+Arrays.asList(arrayEmployee));
//resdfsdfdghjkadfhgjkhdf


    }
    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("iz onResume: " + Arrays.asList(arrayEmployee));

        ArrayAdapter<String> adapterEmployee=new ArrayAdapter<String>(this,R.layout.spinner_zadatak_employee,arrayEmployee);//new String[]{"jebo","sam","ti","majku"});//                stringArrayEmployee);
        Spinner spinnerEmployee = (Spinner)findViewById(R.id.spinner_employe);
        spinnerEmployee.setAdapter(adapterEmployee);
        adapterEmployee.notifyDataSetChanged();


        int spinnerPosition = adapterEmployee.getPosition("Mars");
        spinnerEmployee.setSelection(spinnerPosition);


    }
    @Override
    public void onClick(View v) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                "http://whackamile.byethost3.com/taskme/taskmeBazaPisanje.php",
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
                params.put("KORISNIK_ID", ((Spinner) findViewById(idtxt[3])).getSelectedItem().toString());
                params.put("KRAJNJIDATUMIZVRSENJA", ((DatePicker) findViewById(idtxt[4])).getCalendarView().toString());
                params.put("OPIS", ((EditText) findViewById(idtxt[5])).getText().toString());
                params.put("STATUSDODJELJENOSTI", ((Spinner) findViewById(idtxt[6])).getSelectedItem().toString());

                return params;
            }
        };
        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    public ArrayList<String> makeArrayEmployee(){
        final ArrayList<String> zavratit=new ArrayList<>();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                "http://whackamile.byethost3.com/taskme/taskmeBazaCitanjeKorisnika.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        List<String> useri = Arrays.asList(response.split(","));
                        zavratit.addAll(useri);
                }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
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

        return zavratit;
    }



}