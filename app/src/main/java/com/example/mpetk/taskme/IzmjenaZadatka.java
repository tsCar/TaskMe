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
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

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
    ArrayAdapter<String> adapterEmployee;
    ArrayAdapter<String> adapterClient;
    ArrayAdapter<String> adapterTaskType;
    Spinner spinnerEmployee;
    Spinner spinnerClient;
    Spinner spinnerTaskType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kreiranje_zad);
        stariZadatak = getIntent().getStringExtra(PretragaKorisnika.id_extra);


        arrayEmployee =new ArrayList<>();arrayEmployee.add("Select user");
        arrayClient =new ArrayList<>();arrayClient.add("Select client");
        arrayTaskType =new ArrayList<>();arrayTaskType.add("Select type");
        arrayDefaultValues=new ArrayList<>();arrayDefaultValues.add("");

        Button izmijeni = (Button) findViewById(R.id.button_create);
        izmijeni.setOnClickListener(this);
//resdfsdfdghjkadfhgjkhdf


    }
    @Override
    protected void onResume() {
        super.onResume();
        Thread t = new Thread() {
            @Override
            public void run() {
                makeArrayEmployee();
                //testFuture();
                makeArrayClient();
                makeArrayTaskType();
            }
        };

        t.start(); // spawn thread
        try {
            t.join();  // wait for thread to finish
        } catch (InterruptedException e) {
            System.out.print("neću joinat");
        }
        defaultValues();

        adapterEmployee =new ArrayAdapter<String>(this,R.layout.spinner_zadatak_employee,arrayEmployee);
        spinnerEmployee = (Spinner)findViewById(R.id.spinner_employe);
        spinnerEmployee.setAdapter(adapterEmployee);
        adapterEmployee.notifyDataSetChanged();

        adapterClient=new ArrayAdapter<String>(this,R.layout.spinner_zadatak_employee,arrayClient);
        spinnerClient = (Spinner)findViewById(R.id.spinner_klijent);
        spinnerClient.setAdapter(adapterClient);
        adapterClient.notifyDataSetChanged();

        adapterTaskType=new ArrayAdapter<String>(this,R.layout.spinner_zadatak_employee,arrayTaskType);
        spinnerTaskType = (Spinner)findViewById(R.id.spinner_type_task);
        spinnerTaskType.setAdapter(adapterTaskType);
        adapterTaskType.notifyDataSetChanged();

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
                if (!((Spinner) findViewById(idtxt[3])).getSelectedItem().toString().equalsIgnoreCase("unasigned"))
                    params.put("KORISNIK_ID",((Spinner) findViewById(idtxt[3])).getSelectedItem().toString());
                params.put("KRAJNJIDATUMIZVRSENJA", ((DatePicker) findViewById(idtxt[4])).getCalendarView().toString());
                params.put("OPIS", ((EditText) findViewById(idtxt[5])).getText().toString());
                if(((Spinner) findViewById(idtxt[3])).getSelectedItem().toString().equals("unasigned"))
                    params.put("STATUSDODJELJENOSTI", "0");
                else
                    params.put("STATUSDODJELJENOSTI", "1");

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
                        arrayEmployee.clear();
                        arrayEmployee.add("unasigned");
                        arrayEmployee.addAll(useri);
                        adapterEmployee.notifyDataSetChanged();


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
                        arrayClient.clear();
                        arrayClient.addAll(useri);
                        adapterClient.notifyDataSetChanged();

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
                        arrayTaskType.clear();
                        arrayTaskType.addAll(useri);
                        adapterTaskType.notifyDataSetChanged();

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
                "http://whackamile.byethost3.com/taskme/taskmeZadatakPodaci.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        List<String> podaci = Arrays.asList(response.split("\\|t", -1));
                        arrayDefaultValues.clear();
                        arrayDefaultValues.addAll(podaci);
                        System.out.println(Arrays.asList(arrayDefaultValues + " --> arrayDefaultValues"));


                        ((EditText)findViewById(idtxt[0])).setText(arrayDefaultValues.get(0).toString().trim());

                        Spinner tmp=(Spinner)findViewById(idtxt[1]);
                        System.out.println("adapter: " + adapterEmployee.toString());
                         //   ArrayAdapter myAdap = (ArrayAdapter) tmp.getAdapter(); //cast to an ArrayAdapter
                            adapterEmployee.notifyDataSetChanged();
                            tmp.setAdapter(adapterEmployee);
                            int spinnerPosition = adapterEmployee.getPosition(arrayDefaultValues.get(3).toString());
                            System.out.println(Arrays.asList(arrayEmployee + " --> arraEmployee"));
                            System.out.println(arrayDefaultValues.get(3).toString() + " --> d. val. 1 at position " + spinnerPosition);
                            tmp.setSelection(spinnerPosition);

                        tmp=(Spinner)findViewById(idtxt[2]);
                          //  myAdap = (ArrayAdapter) tmp.getAdapter(); //cast to an ArrayAdapter
                            adapterClient.notifyDataSetChanged();
                            tmp.setAdapter(adapterClient);
                            spinnerPosition = adapterClient.getPosition(arrayDefaultValues.get(2).toString());
                            System.out.println(Arrays.asList(arrayClient + " --> arrayClient"));
                            System.out.println(arrayDefaultValues.get(2).toString() + " --> d. val. 2 at position " + spinnerPosition);
                            tmp.setSelection(spinnerPosition);

                        tmp=(Spinner)findViewById(idtxt[3]);
                        // myAdap = (ArrayAdapter) tmp.getAdapter(); //cast to an ArrayAdapter
                            adapterTaskType.notifyDataSetChanged();
                            tmp.setAdapter(adapterTaskType);
                            System.out.println(Arrays.asList(arrayTaskType + " --> arrayTaskType"));
                            spinnerPosition = adapterTaskType.getPosition(arrayDefaultValues.get(1).toString());
                            System.out.println(arrayDefaultValues.get(1).toString()+" --> d. val. 3 at position "+spinnerPosition);
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
                params.put("NAZIV_ZADATKA", stariZadatak);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void testFuture(){
        RequestFuture<String> future = RequestFuture.newFuture();
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //77

        future.setRequest(requestQueue.add(new StringRequest(
                        Request.Method.POST,
                        "http://whackamile.byethost3.com/taskme/taskmeBazaCitanjeKorisnika.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //If we are getting success from server
                                List<String> useri = Arrays.asList(response.split(","));
                                arrayEmployee.clear();
                                arrayEmployee.add("unasigned");
                                arrayEmployee.addAll(useri);
                                adapterEmployee.notifyDataSetChanged();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                System.out.print("error: " + error.toString());
                            }
                        }))
        );

        try {
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    } //block forever



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



















