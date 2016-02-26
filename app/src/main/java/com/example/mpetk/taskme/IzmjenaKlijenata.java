package com.example.mpetk.taskme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class IzmjenaKlijenata  extends AppCompatActivity implements View.OnClickListener{

    ArrayList<String> podaci;
    int[] idtxt = new int[] { R.id.izmjena_klijenta_ime, R.id.izmjena_klijenta_adresa,
            R.id.izmjena_klijenta_telefon,R.id.izmjena_klijenta_mail};

    public String stariKlijent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_izmjena_klijenata);
        Button promijeni = (Button) findViewById(R.id.button_modify_client);
        promijeni.setOnClickListener(this);
        stariKlijent = getIntent().getStringExtra(PretragaKlijenata.id_extra);
    }

    @Override
    protected void onResume() {
        super.onResume();
        podaci  =new ArrayList<>();
        defaultValues();
    }

    private void defaultValues () {
        System.out.println("usao u default");
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                "http://whackamile.byethost3.com/taskme/taskmeKlijentPodaci.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<String> taskovi = Arrays.asList(response.split("\\|t"));
                        podaci.addAll(taskovi);
                        for (int i = 0; i < podaci.size(); i++) {
                            EditText tmp = (EditText) findViewById(idtxt[i]);
                            tmp.setText(podaci.get(i));
                        }
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
                params.put("NAZIV", stariKlijent);
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
                Config.LOGIN_WAMP_URL+"taskmeKlijentIzmjena.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(IzmjenaKlijenata.this, response, Toast.LENGTH_LONG).show();
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
                Map<String, String> params = new HashMap<>();
                params.put("STARO_IME", stariKlijent);
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

