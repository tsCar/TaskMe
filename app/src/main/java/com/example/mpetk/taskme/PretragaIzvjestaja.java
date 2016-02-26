package com.example.mpetk.taskme;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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

    public static final String id_extra = "com.example.mpetk.taskme._ID";

    public Spinner spinParent;
    public Spinner spinChild;
    public ListView listaIzvjestaj;
    public EditText inputSearch;

    public ArrayList<String> arrayEmployee;
    public ArrayList<String> arrayClient;
    public ArrayList<String> arrayTask;
    public ArrayList<String> arrayFinalTasks;
    int[] idtxt = new int[] {R.id.spinner_izv};
    public ArrayAdapter adapter1;


    Runnable run = new Runnable() {
        public void run() {
            //reload content
            arrayFinalTasks.clear();
            adapter1.notifyDataSetChanged();
            listaIzvjestaj.invalidateViews();
            listaIzvjestaj.refreshDrawableState();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prikaz_izvjestaja);

        spinParent = (Spinner) findViewById(R.id.spinner_kriterij_izv);
        spinChild = (Spinner)findViewById(R.id.spinner_izv);
        listaIzvjestaj = (ListView) findViewById(R.id.lista_za_napuhavanje);
        inputSearch = (EditText) findViewById(R.id.inputSearch_report);
        spinParent.setOnItemSelectedListener(this);
        spinChild.setOnItemSelectedListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();


        registerForContextMenu(listaIzvjestaj); //za meni nesto

          /*-----------SEARCH liste--------------*/
        inputSearch = (EditText) findViewById(R.id.inputSearch_report);
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                PretragaIzvjestaja.this.adapter1.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });


    }



    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

        switch (parentView.getId()){

            case R.id.spinner_kriterij_izv:
                if (spinParent.getSelectedItem().toString().equalsIgnoreCase("user")) {


                    spinChild.setVisibility(View.VISIBLE); //vraca vidljivost drugog spinnera
                    //za popunjavati drugi spinner sa userima
                    arrayEmployee = new ArrayList<>();
                    arrayEmployee.add("Select user:");
                    makeArrayEmployee();

                    ArrayAdapter<String> adapterEmployee = new ArrayAdapter<String>(this, R.layout.spinner_zadatak_employee, arrayEmployee);//new String[]{"jebo","sam","ti","majku"});//                stringArrayEmployee);

                    spinChild.setAdapter(adapterEmployee);
                    adapterEmployee.notifyDataSetChanged();




                } else if (spinParent.getSelectedItem().toString().equalsIgnoreCase("Client")) {

                    spinChild.setVisibility(View.VISIBLE); //vraca vidljivost drugog spinnera

                    arrayClient = new ArrayList<>();
                    arrayClient.add("Select client:");
                    makeArrayClient();

                    ArrayAdapter<String> adapterClient = new ArrayAdapter<String>(this, R.layout.spinner_zadatak_employee, arrayClient);//new String[]{"jebo","sam","ti","majku"});//                stringArrayEmployee);
                    spinChild.setAdapter(adapterClient);
                    adapterClient.notifyDataSetChanged();


                } else if (spinParent.getSelectedItem().toString().equalsIgnoreCase("Type of task")) {

                    spinChild.setVisibility(View.VISIBLE); //vraca vidljivost drugog spinnera
                    arrayTask = new ArrayList<>();
                    arrayTask.add("Select type of task:");
                    makeArrayType();

                    ArrayAdapter<String> adapterTask = new ArrayAdapter<String>(this, R.layout.spinner_zadatak_employee, arrayTask);//new String[]{"jebo","sam","ti","majku"});//                stringArrayEmployee);
                    spinChild.setAdapter(adapterTask);
                    adapterTask.notifyDataSetChanged();

                }
                break;


            case R.id.spinner_izv:

                if(spinParent.getSelectedItem().toString().equalsIgnoreCase("user")) {

                    listaIzvjestaj.setVisibility(View.VISIBLE); //vraca vidljivost liste
                    inputSearch.setVisibility(View.VISIBLE); //vraca vidljivost srČa
                    String user = ((Spinner) findViewById(idtxt[0])).getSelectedItem().toString();
                    showListIzv("KORISNICKO_IME", user);
                    arrayFinalTasks = new ArrayList<>();

                    adapter1 = new ArrayAdapter<String>(this, R.layout.activity_listview, arrayFinalTasks);
                    listaIzvjestaj.setAdapter(adapter1);
                    runOnUiThread(run);
                }
                else if(spinParent.getSelectedItem().toString().equalsIgnoreCase("client")){

                    listaIzvjestaj.setVisibility(View.VISIBLE); //vraca vidljivost liste
                    inputSearch.setVisibility(View.VISIBLE); //vraca vidljivost srČa
                    String user = ((Spinner) findViewById(idtxt[0])).getSelectedItem().toString();
                    showListIzv("NAZIV", user);
                    arrayFinalTasks = new ArrayList<>();

                    adapter1 = new ArrayAdapter<String>(this, R.layout.activity_listview, arrayFinalTasks);
                    listaIzvjestaj.setAdapter(adapter1);
                    runOnUiThread(run);
                }

                else if(spinParent.getSelectedItem().toString().equalsIgnoreCase("Type of task")){

                    listaIzvjestaj.setVisibility(View.VISIBLE); //vraca vidljivost liste
                    inputSearch.setVisibility(View.VISIBLE); //vraca vidljivost srČa
                    String user = ((Spinner) findViewById(idtxt[0])).getSelectedItem().toString();
                    showListIzv("VRSTAZADATKA", user);
                    arrayFinalTasks = new ArrayList<>();

                    adapter1 = new ArrayAdapter<String>(this, R.layout.activity_listview, arrayFinalTasks);
                    listaIzvjestaj.setAdapter(adapter1);
                    runOnUiThread(run);
                }

                break;
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
                      //  arrayEmployee.clear();
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
                System.out.print("UVJET "+uvjet);
                System.out.print("NAZIV  "+naziv);
                params.put("UVJET", uvjet);
                params.put("IME", naziv);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        System.out.println("request " + stringRequest);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.lista_za_napuhavanje) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_report, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index =info.position;
        String user = arrayFinalTasks.get(index);
        switch(item.getItemId()) {
            case R.id.show:
                Intent intent_showZ = new Intent(getApplicationContext(), PrikazZadatka.class);
                intent_showZ.putExtra(id_extra,user);
                startActivity(intent_showZ);
                return true;


            default:
                return super.onContextItemSelected(item);
        }
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