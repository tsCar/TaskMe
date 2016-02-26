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
import android.widget.Button;
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

public class KreiranjeKorisnika  extends AppCompatActivity implements View.OnClickListener {

    int[] idtxt = new int[] { R.id.kreiranje_korisnika_ime, R.id.kreiranje_korisnika_prezime, R.id.kreiranje_korisnika_OIB,
            R.id.kreiranje_korisnika_broj_osobne,R.id.kreiranje_korisnika_adresa,R.id.kreiranje_korisnika_telefon,
            R.id.kreiranje_korisnika_mail,R.id.kreiranje_korisnika_username,
            R.id.kreiranje_korisnika_pass, R.id.kreiranje_korisnika_datum_zap};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kreiranje_korisnika);
        Button dodaj = (Button) findViewById(R.id.button_modify_user);
        dodaj.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Config.LOGIN_WAMP_URL+"taskmeBazaPisanje.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("response " );System.out.println(response);
                        Toast.makeText(KreiranjeKorisnika.this, response, Toast.LENGTH_LONG).show();
                        Intent intentPretraga = new Intent(getApplicationContext(), PretragaKorisnika.class);
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
                Map < String, String > params = new HashMap<>();
                params.put("imeTablice", "korisnik");
                params.put("IME", ((EditText)findViewById(idtxt[0])).getText().toString());
                params.put("PREZIME", ((EditText) findViewById(idtxt[1])).getText().toString());
                params.put("JMBG", ((EditText) findViewById(idtxt[2])).getText().toString());
                params.put("BR_LK", ((EditText) findViewById(idtxt[3])).getText().toString());
                params.put("ADRESA", ((EditText) findViewById(idtxt[4])).getText().toString());
                params.put("TELEFON", ((EditText) findViewById(idtxt[5])).getText().toString());
                params.put("EMAIL", ((EditText) findViewById(idtxt[6])).getText().toString());
                params.put("KORISNICKO_IME", ((EditText) findViewById(idtxt[7])).getText().toString());
                params.put("LOZINKA", ((EditText) findViewById(idtxt[8])).getText().toString());
                params.put("DATUM_ZAPOSLENJA", ((EditText) findViewById(idtxt[9])).getText().toString());
                params.put("TIPKORISNIKA_ID", ((Spinner)findViewById(R.id.kreiranje_spinner_tip_korisnika)).getSelectedItem().toString() );

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