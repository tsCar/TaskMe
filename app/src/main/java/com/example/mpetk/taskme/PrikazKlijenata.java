package com.example.mpetk.taskme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class PrikazKlijenata  extends AppCompatActivity{

    ArrayList<String> podaci =new ArrayList<>();
    int[] idtxt = new int[] { R.id.prikaz_klijenta_ime, R.id.prikaz_klijent_adresa,
            R.id.prikaz_klijent_telefon,R.id.prikaz_klijent_mail};


    // public ArrayList list;
    public String stariKlijent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prikaz_klijenta);
        stariKlijent = getIntent().getStringExtra(PretragaKlijenata.id_extra);

    }

    @Override
    protected void onResume() {
        super.onResume();
        defaultValues();
        //   populate();
    }

    private void defaultValues () {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://whackamile.byethost3.com/taskme/taskmeKlijentPodaci.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println("response " );System.out.println(response);
                        List<String> taskovi = Arrays.asList(response.split("\\|t",-1));
                        podaci.addAll(taskovi);
        System.out.println("podaci "+ Arrays.asList(podaci));

                        for (int i = 0; i < podaci.size(); i++) {
                            TextView tmp= (TextView) findViewById(idtxt[i]);
                            tmp.setText(podaci.get(i));
                        }
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
                params.put("NAZIV", stariKlijent);
                return params;

            }


        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        System.out.println("request " + stringRequest);
        requestQueue.add(stringRequest);
    }



}