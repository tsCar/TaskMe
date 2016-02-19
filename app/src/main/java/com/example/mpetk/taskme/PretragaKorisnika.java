package com.example.mpetk.taskme;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import android.widget.ArrayAdapter;
import android.widget.Button;

import com.android.volley.RequestQueue;
//import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.Volley;

/**
 * Created by mpetk on 15.2.2016..
 */
public class PretragaKorisnika  extends Activity {

    public ArrayList list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pretraga_korisnika);
        list = new ArrayList();
       // setContentView(R.layout.activity_pretraga_korisnika);


    }

    @Override
    protected void onResume() {
        super.onResume();
        showList();
       // String[] lista={"Android","IPhone","WindowsMobile","Blackberry","WebOS","Ubuntu","Windows7","Max OS X"};
        //String[] lista = getResources().getStringArray(R.array.lista_proba);
        ArrayAdapter adapter1=new ArrayAdapter<String>(this, R.layout.activity_listview, list);
        ListView listview = (ListView) findViewById(R.id.lista_usera);
System.out.println("to, ovo"+listview);
        listview.setAdapter(adapter1);
        //setContentView(R.layout.activity_pretraga_korisnika);
    }

    private void showList () {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://192.168.42.138/taskmeBazaCitanjeKorisnika.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server

                        System.out.print(response);
                        List<String> useri = Arrays.asList(response.split(","));
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


    }