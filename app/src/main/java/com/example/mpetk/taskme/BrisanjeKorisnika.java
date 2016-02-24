package com.example.mpetk.taskme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
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

/**
 * Created by mpetk on 15.2.2016..
 */
public class BrisanjeKorisnika  extends AppCompatActivity implements View.OnClickListener{


    public String stariUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brisanje_korisnika);
        Button brisi = (Button) findViewById(R.id.button_delete_yes);
        Button nebrisi = (Button) findViewById(R.id.button_delete_no);
        brisi.setOnClickListener(this);
        nebrisi.setOnClickListener(this);
        stariUser = getIntent().getStringExtra(PretragaKorisnika.id_extra);
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
                       "http://whackamile.byethost3.com/taskme/taskmeBrisanjeKorisnika.php",
                       new Response.Listener<String>() {
                           @Override
                           public void onResponse(String response) {
                               Toast.makeText(BrisanjeKorisnika.this, response, Toast.LENGTH_LONG).show();
                               Intent intentPretraga = new Intent(getApplicationContext(), PretragaKorisnika.class);
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
                       params.put("KORISNICKO_IME", stariUser);
                       return params;
                   }
               };
               //Adding the string request to the queue
               RequestQueue requestQueue = Volley.newRequestQueue(this);
               System.out.println("request " + stringRequest);
               requestQueue.add(stringRequest);
           }
           else {
               Intent mado = new Intent(BrisanjeKorisnika.this, PretragaKorisnika.class);
               startActivity(mado);
           }
       }
}
