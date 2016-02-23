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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KreiranjeZadatak  extends AppCompatActivity implements View.OnClickListener {

    String id;
    int[] idtxt = new int[] { R.id.editxt_taskname, R.id.spinner_type_task,R.id.spinner_klijent, R.id.spinner_employe,
           R.id.datePicker,   R.id.editxt_taskDESC,R.id.spinner_asigned_task};
    public ArrayList<String> arrayEmployee =new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kreiranje_zad);
        makeArrayEmployee();
//re
        Button dodaj = (Button) findViewById(R.id.button_create);
        dodaj.setOnClickListener(this);
        Spinner spinnerEmployee = (Spinner)findViewById(R.id.spinner_employe);

System.out.println("array: "+ Arrays.asList(arrayEmployee));
        ArrayAdapter<String> adapterEmployee=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, arrayEmployee);
        System.out.println("array nakon što napravi adapter ali ga ne doda na spinner: " + Arrays.asList(arrayEmployee));

        spinnerEmployee.setAdapter(adapterEmployee);
        System.out.println("array nakon što napravi spinner: " + Arrays.asList(arrayEmployee));

        //       int spinnerPosition = adapterEmployee.getPosition(arrayEmployee.get(3));
     //   spinnerEmployee.setSelection(spinnerPosition);

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
        System.out.println("request " + stringRequest);
        requestQueue.add(stringRequest);
    }
    public void makeArrayEmployee(){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                "http://whackamile.byethost3.com/taskme/taskmeBazaCitanjeKorisnika.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        List<String> useri = Arrays.asList(response.split(","));
                        arrayEmployee.addAll(useri);
                        System.out.println("array iz make array:" + arrayEmployee);

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
        // RequestQueue requestQueue = new RequestQueue(new NoCache(), new BasicNetwork(new HurlStack()));
        System.out.println("request " + stringRequest);
        requestQueue.add(stringRequest);
    }



}