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

/**
 * Created by mpetk on 15.2.2016..
 */
public class IzmjenaZadatka  extends AppCompatActivity implements View.OnClickListener {

    int[] idtxt = new int[] { R.id.editxt_taskname, R.id.spinner_type_task,R.id.spinner_klijent, R.id.spinner_employe,
            R.id.datePicker,   R.id.editxt_taskDESC};
    public ArrayList<String> arrayEmployee;
    public ArrayList<String> arrayClient;
    public ArrayList<String> arrayTaskType;
    ArrayList<String> arrayDefaultValues;
    String stariZadatak;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kreiranje_zad);
        stariZadatak = getIntent().getStringExtra(PretragaKorisnika.id_extra);


        arrayEmployee =new ArrayList<>();arrayEmployee.add("Select user");
        arrayClient =new ArrayList<>();arrayClient.add("Select client");
        arrayTaskType =new ArrayList<>();arrayTaskType.add("Select type");
        arrayDefaultValues=new ArrayList<>();arrayDefaultValues.add("");

        makeArrayEmployee();
        makeArrayClient();
        makeArrayTaskType();
        defaultValues();


        Button izmijeni = (Button) findViewById(R.id.button_create);
        izmijeni.setOnClickListener(this);
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
                "http://whackamile.byethost3.com/taskme/taskmeZadatakIzmjena.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(IzmjenaZadatka.this, response, Toast.LENGTH_LONG).show();
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
                params.put("STARO", stariZadatak);
                params.put("NAZIV_ZADATKA", ((EditText) findViewById(idtxt[0])).getText().toString());
                params.put("VRSTAZADATKA", ((Spinner)findViewById(idtxt[1])).getSelectedItem().toString());
                params.put("KLIJENT_ID", ((Spinner) findViewById(idtxt[2])).getSelectedItem().toString());
                if(((Spinner) findViewById(idtxt[3])).getSelectedItem().toString()!="unasigned")
                    params.put("KORISNIK_ID",((Spinner) findViewById(idtxt[3])).getSelectedItem().toString());

                params.put("KRAJNJIDATUMIZVRSENJA", ((DatePicker) findViewById(idtxt[4])).getCalendarView().toString());
                params.put("OPIS", ((EditText) findViewById(idtxt[5])).getText().toString());
                if(((Spinner) findViewById(idtxt[3])).getSelectedItem().toString()=="unasigned")
                    params.put("STATUSDODJELJENOSTI", "0");
                else
                    params.put("STATUSDODJELJENOSTI", "1");



                //params.put("STATUSDODJELJENOSTI", ((Spinner) findViewById(idtxt[6])).getSelectedItem().toString());

                return params;
            }
        };
        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
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
                        System.out.print("useri " + useri);
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
                        System.out.print("useri " + useri);
                        arrayTaskType.clear();
                        arrayTaskType.addAll(useri);

                        System.out.print("klijenti iz responsa" + Arrays.asList(arrayTaskType));
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

    private void defaultValues () {
        System.out.println("usao u default");
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                "http://whackamile.byethost3.com/taskme/taskmePodaciKorisnik.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        List<String> podaci = Arrays.asList(response.split("\\|t", -1));
                        arrayDefaultValues.clear();
                        arrayDefaultValues.addAll(podaci);
                        ((EditText)findViewById(idtxt[0])).setText(arrayDefaultValues.get(0).toString());
                        Spinner tmp=(Spinner)findViewById(idtxt[1]);
                            ArrayAdapter myAdap = (ArrayAdapter) tmp.getAdapter(); //cast to an ArrayAdapter
                            int spinnerPosition = myAdap.getPosition(arrayDefaultValues.get(1).toString());
                            tmp.setSelection(spinnerPosition);
                        tmp=(Spinner)findViewById(idtxt[2]);
                            myAdap = (ArrayAdapter) tmp.getAdapter(); //cast to an ArrayAdapter
                            spinnerPosition = myAdap.getPosition(arrayDefaultValues.get(2).toString());
                            tmp.setSelection(spinnerPosition);
                        tmp=(Spinner)findViewById(idtxt[3]);
                            myAdap = (ArrayAdapter) tmp.getAdapter(); //cast to an ArrayAdapter
                            spinnerPosition = myAdap.getPosition(arrayDefaultValues.get(3).toString());
                            tmp.setSelection(spinnerPosition);
                        ((DatePicker)findViewById(idtxt[4])).updateDate(2, 2, 2); //Todo skužit u kojem je formatu datum i kako izvuć vrijednosti
                        ((EditText)findViewById(idtxt[0])).setText(arrayDefaultValues.get(5).toString());
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
                Map<String, String> params = new HashMap<>();
          //      params.put("imeTablice", "radnizadatak");
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



















