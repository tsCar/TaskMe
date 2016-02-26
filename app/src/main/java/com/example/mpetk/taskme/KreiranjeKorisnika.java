package com.example.mpetk.taskme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
}