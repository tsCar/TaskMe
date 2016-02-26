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
import android.widget.Spinner;
import android.widget.TextView;

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
public class PrikazZadatkaZaposlenik  extends AppCompatActivity{

    ArrayList<String> podaci;

    int[] idtxt = new int[] { R.id.prikaz_task_name_p, R.id.prikaz_task_type_p,R.id.prikaz_task_klijent_p, R.id.prikaz_task_zaposlenik_p,
            R.id.date_prikaz_task_p,R.id.completion_status, /*R.id.opis_prikaz_dodjeljenosti,*/ R.id.opis_prikaz_task,R.id.opis_evidencija_task};


    // public ArrayList list;
    public String stariZad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        podaci  =new ArrayList<>();
        setContentView(R.layout.prikaz_radnog_zadataka);
        final Bundle bundle = getIntent().getExtras();
        stariZad = getIntent().getStringExtra(ZadaciZaposlenika.id_extra);



    }

    @Override
    protected void onResume() {
        super.onResume();
        defaultValues();
        //   populate();
    }

    public void defaultValues () {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Config.LOGIN_WAMP_URL+"taskmeZadatakPodaci.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println("response " );System.out.println(response);
                        List<String> taskovi = Arrays.asList(response.split("\\|t", -1));
                        podaci.addAll(taskovi);
                        System.out.println(Arrays.asList(podaci) + "-->podaci");
                        for (int i = 0; i < podaci.size(); i++) {
                            TextView tmp = (TextView) findViewById(idtxt[i]);
                            tmp.setText(podaci.get(i));
                        }
                        ((TextView)findViewById(idtxt[3])).setText("Not assigned");
                        if(podaci.get(5).trim().equalsIgnoreCase("0")||podaci.get(5).trim().equalsIgnoreCase("")) ((TextView)findViewById(idtxt[5])).setText("Not completed");
                        else ((TextView)findViewById(idtxt[5])).setText("Completed");
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
                params.put("NAZIV_ZADATKA", stariZad.trim());
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

            Intent intent = new Intent(getApplicationContext(), MojProfil.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}
