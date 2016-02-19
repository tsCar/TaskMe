package com.example.mpetk.taskme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
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
public class IzmjenaKorisnika  extends AppCompatActivity {

    List<String> useri;
    public ArrayList list;
    public String stariUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grga);

        stariUser = getIntent().getStringExtra(PretragaKorisnika.id_extra);

    }

    @Override
    protected void onResume() {
        super.onResume();
        defaultValues();
        populate(useri);
    }

    private void defaultValues () {
        System.out.println("usao u default");
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://192.168.42.138/taskmePodaciKorisnik.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server

                        System.out.print("response:  ");
                        useri = Arrays.asList(response.split("\t"));
                        list.addAll(useri);
                        //  System.out.print("useri: " + lista[0]);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(Config.KEY_KOR_IME, stariUser);
                return params;

            }


        };
        setContentView(R.layout.activity_pretraga_korisnika);
        //Adding the string request to the queue

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        // RequestQueue requestQueue = new RequestQueue(new NoCache(), new BasicNetwork(new HurlStack()));
        System.out.println("request " + stringRequest);
        requestQueue.add(stringRequest);
    }
    public void populate(List<String> useri){
        EditText name22= (EditText) findViewById(R.id.izmjena_korisnika_ime);
        System.out.println(R.id.izmjena_korisnika_ime);
        System.out.println("name:  "+name22);
        String txt="grga";//useri.get(0);
        name22.setText("grgurGrozni Veličanstveni");

    }

}