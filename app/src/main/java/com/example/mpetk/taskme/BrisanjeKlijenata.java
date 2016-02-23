package com.example.mpetk.taskme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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

/**
 * Created by mpetk on 15.2.2016..
 */
public class BrisanjeKlijenata  extends AppCompatActivity implements View.OnClickListener{

    String[] podaci =new String[33];
    int[] idtxt = new int[] {R.id.izmjena_klijenta_ime, R.id.izmjena_klijenta_adresa,
            R.id.izmjena_klijenta_telefon,R.id.izmjena_klijenta_mail};
    // public ArrayList list;
    public String stariKlijent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brisanje_klijenata);
        Button brisi = (Button) findViewById(R.id.button_delete_yes);
        Button nebrisi = (Button) findViewById(R.id.button_delete_no);
        brisi.setOnClickListener(this);
        nebrisi.setOnClickListener(this);
        stariKlijent = getIntent().getStringExtra(PretragaKorisnika.id_extra);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == (R.id.button_delete_yes)) {
            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    "http://whackamile.byethost3.com/taskme/taskmeKlijentBrisanje.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(BrisanjeKlijenata.this, response, Toast.LENGTH_LONG).show();
                            Intent intentPretraga = new Intent(getApplicationContext(), PretragaKlijenata.class);
                            startActivity(intentPretraga);
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
                    params.put("NAZIV", stariKlijent);
                    return params;
                }
            };
            //Adding the string request to the queue
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            System.out.println("request " + stringRequest);
            requestQueue.add(stringRequest);
        }
        else {
            Intent mado = new Intent(BrisanjeKlijenata.this, PretragaKlijenata.class);
            startActivity(mado);
        }
    }
}
