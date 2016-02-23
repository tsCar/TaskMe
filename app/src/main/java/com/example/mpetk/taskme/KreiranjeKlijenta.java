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

public class KreiranjeKlijenta extends AppCompatActivity implements View.OnClickListener {

    int[] idtxt = new int[] {R.id.create_klijenta_ime, R.id.create_klijenta_adresa,
            R.id.create_klijenta_telefon,R.id.create_klijenta_mail};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kreiranje_klijenta);
        Button dodaj = (Button) findViewById(R.id.button_create_client);
        dodaj.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                "http://whackamile.byethost3.com/taskme/taskmeBazaPisanje.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("response " );System.out.println(response);
                        Toast.makeText(KreiranjeKlijenta.this, response, Toast.LENGTH_LONG).show();
                        Intent intentPretraga = new Intent(getApplicationContext(), PretragaKlijenata.class);
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
                params.put("imeTablice", "klijent");
                params.put("NAZIV", ((EditText)findViewById(idtxt[0])).getText().toString());
                params.put("ADRESA", ((EditText) findViewById(idtxt[1])).getText().toString());
                params.put("BROJ_TELEFONA", ((EditText) findViewById(idtxt[2])).getText().toString());
                params.put("EMAIL", ((EditText) findViewById(idtxt[3])).getText().toString());

                return params;

            }
        };
        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        System.out.println("request " + stringRequest);
        requestQueue.add(stringRequest);
    }
}