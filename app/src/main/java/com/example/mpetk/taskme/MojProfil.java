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
import java.util.HashMap;
import java.util.Map;

public class MojProfil  extends AppCompatActivity implements View.OnClickListener{

    String[] podaci =new String[33];
    int[] idtxt = new int[] { R.id.izmjena_korisnika_ime, R.id.izmjena_korisnika_prezime, R.id.izmjena_korisnika_OIB,
            R.id.izmjena_korisnika_broj_osobne,R.id.izmjena_korisnika_adresa,R.id.izmjena_korisnika_telefon,
            R.id.izmjena_korisnika_mail,R.id.izmjena_korisnika_username,
            R.id.izmjena_korisnika_pass, R.id.datePickerMojProfilKorisnik};
    // public ArrayList list;
    public String stariUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moj_profil);
        Button promijeni = (Button) findViewById(R.id.button_modify_user);
        promijeni.setOnClickListener(this);

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        stariUser = sharedPreferences.getString(Config.IME_SHARED_PREF,"Not Available");
    }

    @Override
    protected void onResume() {
        super.onResume();
        defaultValues();
    }

    private void defaultValues () {
        System.out.println("usao u default");
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Config.LOGIN_WAMP_URL+"taskmePodaciKorisnik.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        podaci = response.split("\\|t",-1);
                        for (int i = 0; i < podaci.length-2; i++) {//zadnje je spinner, ne edittext
                            EditText tmp = (EditText) findViewById(idtxt[i]);
                            tmp.setText(podaci[i]);
                        }
                        Spinner tmp= (Spinner)findViewById(R.id.spinner_tip_korisnika);
                        String myString = podaci[podaci.length-1].trim();//"some value"; //the value you want the position for
                        ArrayAdapter myAdap = (ArrayAdapter) tmp.getAdapter(); //cast to an ArrayAdapter
                        int spinnerPosition = myAdap.getPosition(myString);
                        tmp.setSelection(spinnerPosition);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("error: "+error);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(Config.KEY_KOR_IME, stariUser);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        System.out.println("request " + stringRequest);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Config.LOGIN_WAMP_URL+"taskmeModifikacijaKorisnika.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MojProfil.this, response, Toast.LENGTH_LONG).show();
                        Intent intentPretraga = new Intent(getApplicationContext(), MojProfil.class);
                        startActivity(intentPretraga);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("error: "+error);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("STARO_IME", stariUser);
                params.put("IME", ((EditText)findViewById(idtxt[0])).getText().toString());
                params.put("PREZIME", ((EditText) findViewById(idtxt[1])).getText().toString());
                params.put("JMBG", ((EditText) findViewById(idtxt[2])).getText().toString());
                params.put("BR_LK", ((EditText) findViewById(idtxt[3])).getText().toString());
                params.put("ADRESA", ((EditText) findViewById(idtxt[4])).getText().toString());
                params.put("TELEFON", ((EditText) findViewById(idtxt[5])).getText().toString());
                params.put("EMAIL", ((EditText) findViewById(idtxt[6])).getText().toString());
                params.put("KORISNICKO_IME", ((EditText) findViewById(idtxt[7])).getText().toString());
                params.put("LOZINKA", ((EditText) findViewById(idtxt[8])).getText().toString());
                params.put("DATUM_ZAPOSLENJA", ((DatePicker) findViewById(idtxt[9])).getYear() + "-" + (((DatePicker) findViewById(idtxt[9])).getMonth() + 1) + "-" + ((DatePicker) findViewById(idtxt[9])).getDayOfMonth());
                params.put("TIP_KORISNIKA", ((Spinner)findViewById(R.id.spinner_tip_korisnika)).getSelectedItem().toString() );
                return params;
            }
        };
        //Adding the string request to the queue
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

