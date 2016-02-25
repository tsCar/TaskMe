package com.example.mpetk.taskme;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

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
public class PretragaIzvjestaja  extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public Spinner spinParent;
    public Spinner spinChild;
    public ListView listaIzvjestaj;

    public ArrayList<String> arrayEmployee;
    public ArrayList<String> arrayClient;
    public ArrayList<String> arrayTask;
    public ArrayList<String> arrayFinalTasks;
    int[] idtxt = new int[] {R.id.spinner_izv};
    public ArrayAdapter adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prikaz_izvjestaja);

        spinParent = (Spinner) findViewById(R.id.spinner_kriterij_izv);
        spinParent.setOnItemSelectedListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();


    }



    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
        spinChild = (Spinner)findViewById(R.id.spinner_izv);



        if (spinParent.getSelectedItem().toString().equalsIgnoreCase("user"))  {


            spinChild.setVisibility(View.VISIBLE); //vraca vidljivost drugog spinnera
            //za popunjavati drugi spinner sa userima
            arrayEmployee =new ArrayList<>();arrayEmployee.add("Select user:");
            makeArrayEmployee();

            ArrayAdapter<String> adapterEmployee=new ArrayAdapter<String>(this,R.layout.spinner_zadatak_employee,arrayEmployee);//new String[]{"jebo","sam","ti","majku"});//                stringArrayEmployee);

            spinChild.setAdapter(adapterEmployee);
            adapterEmployee.notifyDataSetChanged();



            if(spinChild.isSelected()){
                String user = ((Spinner)findViewById(idtxt[0])).getSelectedItem().toString();
                System.out.print("izabran user");
                listaIzvjestaj = (ListView) findViewById(R.id.lista_za_napuhavanje);

                showListIzv("KORISNICKO_IME", user);
                arrayFinalTasks=new ArrayList<>();
                arrayFinalTasks.add("babina greda");
                adapter1=new ArrayAdapter<String>(this, R.layout.activity_listview,arrayFinalTasks);
                listaIzvjestaj = (ListView) findViewById(R.id.lista_klijenata);
                listaIzvjestaj.setAdapter(adapter1);
            }

           /* listaIzvjestaj = (ListView) findViewById(R.id.lista_za_napuhavanje);

            showListIzv("KORISNICKO_IME", user);
            arrayFinalTasks=new ArrayList<>();
            arrayFinalTasks.add("babina greda");
            adapter1=new ArrayAdapter<String>(this, R.layout.activity_listview,arrayFinalTasks);
            listaIzvjestaj = (ListView) findViewById(R.id.lista_klijenata);
            listaIzvjestaj.setAdapter(adapter1);*/

        }
        else if (spinParent.getSelectedItem().toString().equalsIgnoreCase("Client") )      {

            spinChild.setVisibility(View.VISIBLE); //vraca vidljivost drugog spinnera
            //za popunjavati drugi spinner sa klijentima
            arrayClient =new ArrayList<>();arrayClient.add("Select client:");
            makeArrayClient();

            ArrayAdapter<String> adapterClient=new ArrayAdapter<String>(this,R.layout.spinner_zadatak_employee,arrayClient);//new String[]{"jebo","sam","ti","majku"});//                stringArrayEmployee);
            spinChild.setAdapter(adapterClient);
            adapterClient.notifyDataSetChanged();

            String client = ((Spinner)findViewById(idtxt[0])).getSelectedItem().toString();
            int spinnerPosition = adapterClient.getPosition("Mars");
            spinChild.setSelection(spinnerPosition);

            //spinChild.setVisibility(View.VISIBLE);

           /* listaIzvjestaj = (ListView) findViewById(R.id.lista_za_napuhavanje);

            showListIzv("NAZIV", client);
            arrayFinalTasks=new ArrayList<>();
            adapter1=new ArrayAdapter<String>(this, R.layout.activity_listview,arrayFinalTasks);
            listaIzvjestaj = (ListView) findViewById(R.id.lista_klijenata);
            listaIzvjestaj.setAdapter(adapter1);*/

        }
        else if (spinParent.getSelectedItem().toString().equalsIgnoreCase("Type of task")  )      {

            spinChild.setVisibility(View.VISIBLE); //vraca vidljivost drugog spinnera
            arrayTask =new ArrayList<>();arrayTask.add("Select type of task:");
            makeArrayType();
            //


            ArrayAdapter<String> adapterTask=new ArrayAdapter<String>(this,R.layout.spinner_zadatak_employee,arrayTask);//new String[]{"jebo","sam","ti","majku"});//                stringArrayEmployee);
            spinChild.setAdapter(adapterTask);
            adapterTask.notifyDataSetChanged();

            String task = ((Spinner)findViewById(idtxt[0])).getSelectedItem().toString();
            int spinnerPosition = adapterTask.getPosition("Mars");
            spinChild.setSelection(spinnerPosition);

            //vraca vidljivost drugog spinnera
            //spinChild.setVisibility(View.VISIBLE);

            /*listaIzvjestaj = (ListView) findViewById(R.id.lista_za_napuhavanje);

            showListIzv("VRSTAZADATKA", task);
            arrayFinalTasks=new ArrayList<>();
            adapter1=new ArrayAdapter<String>(this, R.layout.activity_listview,arrayFinalTasks);
            listaIzvjestaj = (ListView) findViewById(R.id.lista_klijenata);
            listaIzvjestaj.setAdapter(adapter1);*/

        }
    else {



        }
    }




    public void makeArrayEmployee(){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Config.LOGIN_WAMP_URL+"taskmeBazaCitanjeKorisnika.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        List<String> useri = Arrays.asList(response.split(","));
                        System.out.print("useri "+useri);
                        arrayEmployee.clear();
                        arrayEmployee.addAll(useri);

                        System.out.print("iz respons e"+Arrays.asList(arrayEmployee));
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

    public void makeArrayClient(){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Config.LOGIN_WAMP_URL+"taskmeKijentCitanje.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        List<String> useri = Arrays.asList(response.split(","));
                        System.out.print("useri "+useri);
                        arrayClient.clear();
                        arrayClient.addAll(useri);

                        System.out.print("iz respons e"+Arrays.asList(arrayClient));
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

    public void makeArrayType(){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Config.LOGIN_WAMP_URL+"taskmeTipZadatkaCitanje.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        List<String> useri = Arrays.asList(response.split(","));
                        System.out.print("useri "+useri);
                        arrayTask.clear();
                        arrayTask.addAll(useri);

                        System.out.print("iz respons e"+Arrays.asList(arrayTask));
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


    private void showListIzv (final String uvjet, final String naziv) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Config.LOGIN_WAMP_URL+"taskmeIzvjestajIspisZadatka.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<String> klijent = Arrays.asList(response.split(","));
                        arrayFinalTasks.addAll(klijent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("UVJET", uvjet);
                params.put("IME", naziv);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        System.out.println("request " + stringRequest);
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

        }

        return super.onOptionsItemSelected(item);
    }
}