package com.example.mpetk.taskme;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.NoCache;
import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mpetk on 15.2.2016..
 */
public class PretragaKorisnika  extends AppCompatActivity {



    ListView listview;
    Button NewUser;
    EditText GetValue;
    String[] ListElements = new String[] {
            "Android",
            "PHP"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // showList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showList();
    }

        private void showList () {
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    "http://192.168.42.138/taskmeBazaCitanjeKorisnika.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //If we are getting success from server

                            //System.out.print(response);
                            String[] Lista = response.split(",");
                            System.out.print("useri: " + Lista[0]);
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
                    return null;
                }


            };
            setContentView(R.layout.activity_pretraga_korisnika);
            //Adding the string request to the queue

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            // RequestQueue requestQueue = new RequestQueue(new NoCache(), new BasicNetwork(new HurlStack()));
            System.out.println("request " + stringRequest);
            requestQueue.add(stringRequest);
        }
/*
        listview = (ListView) findViewById(R.id.lista_usera);
        NewUser = (Button) findViewById(R.id.zaposlenik_button_mytasks);
        GetValue = (EditText)findViewById(R.id.editText1);

        final List<String> ListElementsArrayList = new ArrayList<String>(Arrays.asList(ListElements));


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (PretragaKorisnika.this, android.R.layout.simple_list_item_1, ListElementsArrayList);

        listview.setAdapter(adapter);

        Addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ListElementsArrayList.add(GetValue.getText().toString());

                adapter.notifyDataSetChanged();
            }
        });

    }
*/
    }